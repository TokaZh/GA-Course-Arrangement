package com.tokaku.utils;

import java.util.Random;

public class RandomUtil {
    public static int initTimePart(int timeSize) {
        return new Random().nextInt(timeSize);
    }

    public static float initProbability() {
        return new Random().nextFloat();
    }
}
