package com.jdev.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class MathHelperTest {

    private static Stream<Arguments> methodSource_for__test_factorial() {
        return Stream.of(Arguments.of(0, 1),
                Arguments.of(1, 1),
                Arguments.of(2, 2),
                Arguments.of(3, 6),
                Arguments.of(4, 24),
                Arguments.of(5, 120),
                Arguments.of(6, 720),
                Arguments.of(7, 5040),
                Arguments.of(8, 40320),
                Arguments.of(9, 362880),
                Arguments.of(10, 3628800)
        );
    }

    @Test
    void test_() {
        for (int i = 1_000; i <= 1_025; i++) {
            List<Integer> divisors = MathHelper.getDivisors(i);
            System.out.println("#" + i);
            System.out.println("size - " + divisors.size());
            System.out.println("divisors - " + divisors);
            System.out.println("___________________________");
        }
    }

    @ParameterizedTest
    @MethodSource("methodSource_for__test_factorial")
    void test_factorial(int number, int expected) {
        int factorial = MathHelper.factorial(number);
        Assertions.assertEquals(expected, factorial);
    }

    @Test
    void test_f() {
        for (int i = 3; i <= 1_000; i++) {
            boolean b = (i % 3 == 0) || (i % 4 == 0) || (i % 5 == 0);
            if (!b) {
                System.out.println("#" + i);
            }
        }
    }

}