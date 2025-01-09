package com.jdev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdev.dto.request.TestRequestDto;
import org.junit.jupiter.api.Test;

public class MixTest {

    @Test
    void test_requestDto() throws JsonProcessingException {
        TestRequestDto testRequestDto = new TestRequestDto();
        testRequestDto.setAge(25);
        TestRequestDto.PersonInfo personInfo = new TestRequestDto.PersonInfo();
        personInfo.setFirstName("Jackson");
        personInfo.setLastName("John");
        testRequestDto.setPersonInfo(personInfo);
        System.out.println(new ObjectMapper().writeValueAsString(testRequestDto));
    }

}
