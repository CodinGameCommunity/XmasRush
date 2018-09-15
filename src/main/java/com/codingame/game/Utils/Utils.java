package com.codingame.game.Utils;

public class Utils {
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
    public static int wrap(int val, int min, int max) {
        if (val < min) {
            val = max - (min - val) + 1;
        } else if (val > max) {
            val = min + (val - max) - 1;
        }
        return val;
    }
}
