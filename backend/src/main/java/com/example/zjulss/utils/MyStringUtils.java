package com.example.zjulss.utils;

import java.util.Random;

public class MyStringUtils {
    public static boolean checkIsValid(String... input) {
        if (input == null) {
            return false;
        }
        for (var a : input) {
            if (a == null || a.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static String getRandom4() {
        int len = 4;
        String source = "0123456789";
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int j = 0; j < len; j++) {
            rs.append(source.charAt(r.nextInt(10)));
        }
        return rs.toString();
    }
}
