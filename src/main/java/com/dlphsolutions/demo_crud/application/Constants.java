package com.dlphsolutions.demo_crud.application;

import java.util.Random;

public class Constants {
    private static final Random random = new Random();
    public static long generateRandomId() {
        return random.nextInt(10000000);
    }
}