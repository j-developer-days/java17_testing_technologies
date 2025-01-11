package com.jdev.avro;

import org.apache.avro.Schema;
import org.apache.avro.SchemaParser;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
mvn compile
mvn -q exec:java -Dexec.mainClass=com.jdev.avro.AvroDemo

clear
mvn compile
mvn exec:java -Dexec.mainClass=com.jdev.avro.AvroDemo
 */
public class AvroDemo {

    //https://avro.apache.org/docs/1.12.0/getting-started-java/
    public static void main(String[] args) throws IOException {
//        demoCreateInstances();
        User userFull = new User("Test Name 3", 15, "Blue");
//        System.out.println("userFull - " + userFull);
//        writeToFile(userFull, "user1.avro");
//        writeToFileListOfInstances(List.of(userFull, new User("Test2", 199, "Gray")), "user1_multi.avro");
//        readFromAvroFile("user1_multi.avro").forEach(System.out::println);
//        writeGenericObjectToFile("src/main/avro/user.avsc");
        readGenericObjectFromFile("src/main/avro/user.avsc");
    }

    private static void readGenericObjectFromFile(String schemaFileName) throws IOException {
        Schema schema = new SchemaParser().parse(new File(schemaFileName)).mainSchema();
        DatumReader<GenericRecord> genericRecordDatumReader = new GenericDatumReader<>(schema);
        DataFileReader<GenericRecord> genericRecordDataFileReader = new DataFileReader<>(new File("genericRecord.avro"), genericRecordDatumReader);
        GenericRecord genericRecord = null;
        while (genericRecordDataFileReader.hasNext()){
            genericRecord = genericRecordDataFileReader.next();
            System.out.println(genericRecord);
        }
    }

    private static void writeGenericObjectToFile(String schemaFileName) throws IOException {
        Schema schema = new SchemaParser().parse(new File(schemaFileName)).mainSchema();
        GenericRecord user = new GenericData.Record(schema);
        user.put("name", "Barcelona");
        user.put("favorite_number", 10);
        user.put("favorite_color", "red");
        DatumWriter<GenericRecord> genericRecordDatumWriter = new GenericDatumWriter<>(schema);
        DataFileWriter<GenericRecord> genericRecordDataFileWriter = new DataFileWriter<>(genericRecordDatumWriter);
        genericRecordDataFileWriter.create(schema, new File("genericRecord.avro"));
        genericRecordDataFileWriter.append(user);
        genericRecordDataFileWriter.close();
    }

    private static List<User> readFromAvroFile(String fileName) throws IOException {
        DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
        DataFileReader<User> userDataFileReader = new DataFileReader<>(new File(fileName), userDatumReader);
        List<User> result = new ArrayList<>();
        while (userDataFileReader.hasNext()){
            result.add(userDataFileReader.next());
        }
        return result;
    }

    private static void writeToFileListOfInstances(List<User> users, String fileName) throws IOException {
        //Serialize
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter);
        User user = users.get(0);
        dataFileWriter.create(user.getSchema(), new File(fileName));
        dataFileWriter.append(user);
        for (int i = 1; i < users.size(); i++) {
            dataFileWriter.append(users.get(i));
        }
        dataFileWriter.close();
    }

    private static void writeToFile(User user, String fileName) throws IOException {
        //Serialize
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter);
        dataFileWriter.create(user.getSchema(), new File(fileName));
        dataFileWriter.append(user);
        dataFileWriter.close();
    }

    private static void demoCreateInstances() {
        //        option 1
//        intermediate using
        User userRequiredName = new User();
        userRequiredName.setFavoriteColor("Green");
        userRequiredName.setFavoriteNumber(10);
        System.out.println(userRequiredName);

//        option 2
//        slow working
        User userWithoutColor = User.newBuilder()
                .setName("TestName 2")
                .setFavoriteColor(null)
                .setFavoriteNumber(100)
                .build();
        System.out.println(userWithoutColor);

        //using constructors directly generally offers better performance, as builders create a copy of the datastructure before it is written.
        //better using
        User userFull = new User("Test Name 3", 15, "Blue");
        System.out.println("userFull - " + userFull);
    }

}
