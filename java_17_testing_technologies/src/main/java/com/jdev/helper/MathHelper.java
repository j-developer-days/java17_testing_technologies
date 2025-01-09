package com.jdev.helper;

import java.util.ArrayList;
import java.util.List;

public class MathHelper {

    //Greatest common divisor
    public static List<Integer> getDivisors(int number1) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 1; i <= number1; i++) {
            if (number1 % i == 0) {
                integers.add(i);
            }
        }
        return integers;
    }

    public static int factorial(int number) {
        if (number == 0 || number == 1) {
            return 1;
        }
        return number * factorial(--number);
    }


}
