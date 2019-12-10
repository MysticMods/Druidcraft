package com.vulp.druidcraft.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum LunarMothColors {
    TURQUOISE,
    WHITE,
    LIME,
    YELLOW,
    ORANGE,
    PINK;

    public static ArrayList<LunarMothColors> colorArray() {
        ArrayList<LunarMothColors> array = new ArrayList<>();
        array.add(TURQUOISE);
        array.add(WHITE);
        array.add(LIME);
        array.add(YELLOW);
        array.add(ORANGE);
        array.add(PINK);
        return array;
    }

    public static LunarMothColors randomColor(Random random) {
        return colorArray().get(random.nextInt(6));
    }

    public static int colorToInt(LunarMothColors color) {
        return colorArray().indexOf(color);
    }
}
