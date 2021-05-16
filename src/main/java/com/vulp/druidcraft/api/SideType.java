package com.vulp.druidcraft.api;

import net.minecraft.util.IStringSerializable;

public enum SideType implements IStringSerializable {
    LEFT("left", 1),
    RIGHT("right", 0);

    public static final SideType[] VALUES = values();
    private final String name;
    private final int opposite;

    SideType(String name, int oppositeIn) {
        this.name = name;
        this.opposite = oppositeIn;
    }

    public String getString() {
        return this.name;
    }

    public SideType opposite() {
        return VALUES[this.opposite];
    }
}
