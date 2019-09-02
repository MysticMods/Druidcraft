package com.vulp.druidcraft.blocks;

import net.minecraft.util.math.MathHelper;
import java.util.Random;

public class OreBlock extends net.minecraft.block.OreBlock {
    private final int minXP;
    private final int maxXP;

    public OreBlock(Properties properties, int minXP, int maxXP) {
        super(properties);
        this.minXP = minXP;
        this.maxXP = maxXP;
    }

    @Override
    protected int func_220281_a(Random rand) {
        return MathHelper.nextInt(rand, minXP, maxXP);
    }
}