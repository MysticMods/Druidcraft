package com.vulp.druidcraft.api;

import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import java.util.Locale;

public enum CropLifeStageType implements IStringSerializable {
    NONE,
    FLOWER,
    BERRY;

    @Override
    public String getString() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static int getTwoDayCycle(World world) {
        return (int)world.getWorldInfo().getDayTime() % (int)96000L;
    }

    public static CropLifeStageType checkCropLife(World world) {
        double stageDouble = getTwoDayCycle(world);
        if (stageDouble >= 48000) {
            if (stageDouble >= 72000) {
                return CropLifeStageType.NONE;
            }
            return CropLifeStageType.BERRY;
        }
        else return CropLifeStageType.FLOWER;
    }

}
