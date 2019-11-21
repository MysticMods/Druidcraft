package com.vulp.druidcraft.api;

import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import java.util.Locale;

public enum CropLifeStageType implements IStringSerializable {
    NONE,
    FLOWER,
    BERRY;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
