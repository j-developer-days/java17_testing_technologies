package com.jdev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContextIT {

    @Test
    void test_context(ApplicationContext ctx) {
        Assertions.assertEquals(175, ctx.getBeanDefinitionCount());
    }

}