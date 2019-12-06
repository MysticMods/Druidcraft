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

    public static double getTwoDayCycle(World world) {
        double timeValue = world.getWorldInfo().getDayTime() % 24000L;
        if ((world.getWorldInfo().getDayTime() / 24000L % 2147483647L) % 2 == 1) {
            return timeValue + 24000L;
        }
        else return timeValue;
    }

    public static CropLifeStageType checkCropLife(World world) {
        double stageDouble = getTwoDayCycle(world);
        if (stageDouble >= 6000 || stageDouble >= 24000) {
            if (stageDouble >= 24000) {
                return CropLifeStageType.BERRY;
            }
            return CropLifeStageType.FLOWER;
        }
        else return CropLifeStageType.NONE;
    }

}
