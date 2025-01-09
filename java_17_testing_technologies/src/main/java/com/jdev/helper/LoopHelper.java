package com.jdev.helper;

import java.util.function.Consumer;

public class LoopHelper {

    public static <T> void loop(T[] ts, Consumer<String> consumer) {
        final int length = ts.length;
        if (length > 1_000) {

        } else {
            for (int i = 0; i < length; i++){
                consumer.accept(i + " - ");
            }
        }
    }

}
