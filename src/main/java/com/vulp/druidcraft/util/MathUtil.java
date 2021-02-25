package com.vulp.druidcraft.util;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class MathUtil {

    private static final NavigableMap<Integer, String> suffixes = new TreeMap<>();

    public static String formatInteger(int value) {
        if (value == Integer.MIN_VALUE) return formatInteger(Integer.MIN_VALUE + 1);
        if (value < 0) return "-" + formatInteger(-value);
        if (value < 1000) return Long.toString(value);
        Map.Entry<Integer, String> e = suffixes.floorEntry(value);
        Integer divideBy = e.getKey();
        String suffix = e.getValue();
        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    static {
        suffixes.put(1000, "k");
        suffixes.put(1000000, "M");
        suffixes.put(1000000000, "B");
    }
}
