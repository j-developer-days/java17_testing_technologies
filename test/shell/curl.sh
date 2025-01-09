#!/bin/bash
echo 'I - Test endpoints:'
echo '\t11 - create'
echo '\t12 - get by id'
echo '\t13 - get all'
echo '\t14 - check throw Exception'
echo '\t15 - post with payload'

echo '-----------------------------'
echo 'e | E - EXIT'
echo 'c | C - clear screen'

if [ -z $1 ]; then
    read -p "Enter your command number: " COMMAND_NUMBER
  else
    COMMAND_NUMBER=$1
fi

URL_WITH_CONTEXT_PATH="http://localhost:10027/test/"

case "$COMMAND_NUMBER" in
   "11") curl --http2-prior-knowledge -i -X POST "${URL_WITH_CONTEXT_PATH}test"
   ;;
   "12")
       read -p "Write please uuid: " UUID
       curl --http2-prior-knowledge -i -X GET "${URL_WITH_CONTEXT_PATH}test/get/${UUID}"
   ;;
   "13") curl --http2-prior-knowledge -i -o ./simple.json -X GET "${URL_WITH_CONTEXT_PATH}test/get-all"
   ;;
   "14")
       read -p "Need to throw exception: " IS_THROW_EXCEPTION
       if [ -z $IS_THROW_EXCEPTION ]; then
          IS_THROW_EXCEPTION=false
       else
          IS_THROW_EXCEPTION=true
       fi
       curl --http2-prior-knowledge -i -X GET "${URL_WITH_CONTEXT_PATH}test/check-exception?isThrowException=${IS_THROW_EXCEPTION}"
   ;;
   "15")
#       curl --http2-prior-knowledge -i -X POST -H 'Content-Type: application/json' \
#        -d '{"age":25,"personInfo":{"firstName":"Jackson","lastName":"John"}}' \
#         "${URL_WITH_CONTEXT_PATH}test/with-payload"

#       curl --http2-prior-knowledge -i -X POST -H 'Content-Type: application/json' \
#        -d {"age":25,"personInfo":{"firstName":"J\\'ackson","lastName":"John"}} \
#         "${URL_WITH_CONTEXT_PATH}test/with-payload"

         curl --http2-prior-knowledge -i -X POST -H 'Content-Type: application/json' \
                 --data @request.json \
                  "${URL_WITH_CONTEXT_PATH}test/with-payload"
   ;;
   "e"|"E") exit 1
   ;;
   "c"|"C") clear
   ;;
    *) sh -e $0
   ;;
esac

echo
echo "---------------------------------------------------------------------------------------------------"

sh -e $0