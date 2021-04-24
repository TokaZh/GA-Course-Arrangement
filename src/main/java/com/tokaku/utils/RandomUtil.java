package com.tokaku.utils;

import java.util.Random;

public class RandomUtil {
    public static int initTimePart(int timeSize) {
        Random random = new Random();
        return random.nextInt(timeSize);
    }
}
