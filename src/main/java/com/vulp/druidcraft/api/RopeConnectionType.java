package com.vulp.druidcraft.api;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum RopeConnectionType implements IStringSerializable {
    NONE,
    REGULAR,
    TIED_FENCE,
    TIED_BEAM_1,
    TIED_BEAM_2;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
