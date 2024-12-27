#!/bin/bash

readonly DOCKER_IMAGE_NAME="spring-boot-image/password-generator"
readonly PATH_TO_JAR_FILE="../target/password-generator.jar"
readonly SERVICE_FILE="pg.service"
readonly JAR_FILE_FOLDER="/var/jdev_services/pg/"
readonly LOG_FILE_NAME="pg.log"
readonly USERNAME="java_dev"
readonly PORT_NUMBER=11001

#---------------------------------------------------
clean_install() {
  if [ -z $1 ]; then
    mvn --file ../pom.xml -U clean install
  else
    mvn --file ../pom.xml -U -P$1 clean install
  fi
}

clean_install_UT_and_IT() {
  mvn --file ../pom.xml -U -Dskip.UT.tests=false -P$1 clean install
  mvn --file ../pom.xml -U -Dskip.IT.tests=false -P$1 failsafe:integration-test
}

#------------------------------------------------------------------------------

echo '1 - clean install by maven profile'
echo '2 - clean install with UT and IT tests'
echo '3 - dependency tree'
echo '4 - sort pom'
echo ------------------------------------------------------------------------------
echo 'e | E - EXIT'
echo 'c | C - clear screen'

if [ -z $1 ]; then
    read -p "Enter your command number: " COMMAND_NUMBER
  else
    COMMAND_NUMBER=$1
fi

case "$COMMAND_NUMBER" in
   "1") clean_install
   ;;
   "2") clean_install_UT_and_IT
   ;;
   "3") clear && mvn --file ../pom.xml -U dependency:tree
   ;;
   "4") clear && mvn --file ../pom.xml -U com.github.ekryd.sortpom:sortpom-maven-plugin:2.15.0:sort
   		  sleep 1
		    find ../. -name '*pom.xml.bak' -delete
   ;;
   "e"|"E") exit 1
   ;;
   "c"|"C") clear
   ;;
    *) sh -e $0
   ;;
esac

echo "---------------------------------------------------------------------------------------------------"

if [ -z $2 ]; then
    sh -e $0
  else
    exit
fi