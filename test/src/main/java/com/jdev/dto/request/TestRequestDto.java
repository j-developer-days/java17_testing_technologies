package com.jdev.dto.request;

import lombok.Data;

@Data
public class TestRequestDto {
    private int age;
    private PersonInfo personInfo;


    @Data
    public static class PersonInfo {
        private String firstName;
        private String lastName;
    }

}
