package com.vulp.druidcraft.api;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum CrateType implements IStringSerializable {

    SINGLE("single"),
    DOUBLE_X("double_x"),
    DOUBLE_Y("double_y"),
    DOUBLE_Z("double_z"),
    QUAD_X("quad_x"),
    QUAD_Y("quad_y"),
    QUAD_Z("quad_z"),
    OCTO("octo");

    private final String name;

    @Override
    public String getString() {
        return name().toLowerCase(Locale.ROOT);
    }

    CrateType(String name) {
        this.name = name;
    }
}