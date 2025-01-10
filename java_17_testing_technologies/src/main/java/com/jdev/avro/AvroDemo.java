package com.jdev.avro;

public class AvroDemo {

    //https://avro.apache.org/docs/1.12.0/getting-started-java/
    public static void main(String[] args) {
//        option 1
//        intermediate using
        User userGreen = new User();
        userGreen.setName("TestName");
        userGreen.setFavoriteColor("Green");
        userGreen.setFavoriteNumber(10);
        System.out.println(userGreen);

//        option 2
//        slow working
        User userRed = User.newBuilder().setName("TestName 2").setFavoriteColor("Red").setFavoriteNumber(100).build();
        System.out.println(userRed);

        //using constructors directly generally offers better performance, as builders create a copy of the datastructure before it is written.
        //better using
        System.out.println(new User("Test Name 3", 15, "Blue"));
    }

}
