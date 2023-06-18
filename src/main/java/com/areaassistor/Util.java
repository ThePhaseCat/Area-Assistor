package com.areaassistor;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Util {
    public static int biggestValue(int b1, int b2, int b3) {
        return max(max(b1, b2), b3);
    }

    public static int smallestValue(int b1, int b2, int b3) {
        return min(min(b1, b2), b3);
    }
}
