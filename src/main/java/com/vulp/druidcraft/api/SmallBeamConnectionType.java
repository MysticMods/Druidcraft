package com.vulp.druidcraft.api;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum SmallBeamConnectionType implements IStringSerializable {
    NONE,
    ATTACHED;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
