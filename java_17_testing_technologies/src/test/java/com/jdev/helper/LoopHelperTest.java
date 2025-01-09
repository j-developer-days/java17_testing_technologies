package com.jdev.helper;

import com.jdev.Common;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

class LoopHelperTest {

    @Test
    void test_loop() throws IOException {
        byte[] bytes = Files.readAllBytes(Common.getPath("excel_template_for_test_success.xlsx"));
        Byte[] bytesWrapper = new Byte[bytes.length];
        int index = -1;
        for (byte b : bytes) {
            bytesWrapper[index++] = b;
        }
        LoopHelper.loop(bytesWrapper, s -> System.out.println("#" + s));
    }

    @Test
    void test_() {
        List<Integer> noSolutions = new ArrayList<>();
        for (int i = 1000; i < 1010; i++) {
            boolean hasSolution = false;
            System.out.println("current number - " + i);
            for (int j = 2; j < 50; j++) {
                if (i % j == 0) {
                    hasSolution = true;
                    System.out.println("\tSOLUTION - " + j);
                }
            }
            if (!hasSolution) {
                noSolutions.add(i);
            }
        }
        System.out.println("----------------");
        System.out.println("No solutions - " + noSolutions);

    }

}