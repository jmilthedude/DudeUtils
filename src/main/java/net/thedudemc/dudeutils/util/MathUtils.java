package net.thedudemc.dudeutils.util;

import java.util.Random;

public class MathUtils {

    private static final Random rand = new Random();

    public static int getRandomInt(int min, int max) {
        if (min >= max) return min;
        return min + rand.nextInt(max - min);
    }

}
