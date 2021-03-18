package com.vulp.druidcraft.api;

import net.minecraft.state.EnumProperty;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum StackPart implements IStringSerializable {

    SINGLE("single"),
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom");

    private final String name;
    private static final EnumProperty<StackPart> property = EnumProperty.create("part", StackPart.class);

    @Override
    public String getString() {
        return name.toLowerCase(Locale.ROOT);
    }

    StackPart(String name) {
        this.name = name;
    }

    public static EnumProperty<StackPart> getEnumProperty() {
        return property;
    }
}
