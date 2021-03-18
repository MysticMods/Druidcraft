package com.vulp.druidcraft.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BramblerootBlock extends ThickRootBlock {

    public static final DamageSource BRAMBLE = new DamageSource("bramble");

    public BramblerootBlock(float thicknessModifier, Properties properties) {
        super(thicknessModifier, properties);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
    }

}
