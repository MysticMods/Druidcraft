package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BramblerootBlock extends ThickRootBlock {

    public static final DamageSource BRAMBLE = new DamageSource("bramble");

    public BramblerootBlock(float thicknessModifier, Properties properties) {
        super(thicknessModifier, properties);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.attackEntityFrom(BRAMBLE, 1.0F);
    }

}
