package com.vulp.druidcraft.blocks;

import net.minecraft.util.math.MathHelper;
import java.util.Random;

public class OreXPBlock extends net.minecraft.block.OreBlock {
    private final int minXP;
    private final int maxXP;

    public OreXPBlock(Properties properties, int minXP, int maxXP) {
        super(properties);
        this.minXP = minXP;
        this.maxXP = maxXP;
    }

    @Override
    protected int getExperience(Random rand) {
        return MathHelper.nextInt(rand, minXP, maxXP);
    }
}