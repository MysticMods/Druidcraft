package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.api.StackPart;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ScorchingCurtainBlock extends HangingBlock {

    public static VoxelShape MAIN = Block.makeCuboidShape(2.0F, 0.0F, 2.0F, 14.0F, 16.0F, 14.0F);
    public static VoxelShape SINGLE = Block.makeCuboidShape(2.0F, 2.0F, 2.0F, 14.0F, 16.0F, 14.0F);

    public ScorchingCurtainBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        Block aboveBlock = world.getBlockState(pos.up()).getBlock();
        if (aboveBlock == this || aboveBlock == BlockRegistry.heartburn_cap) {
            return super.isValidPosition(state, world, pos);
        }
        return false;
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (!entityIn.isImmuneToFire()) {
            entityIn.forceFireTicks(entityIn.getFireTimer() + 1);
            if (entityIn.getFireTimer() == 0) {
                entityIn.setFire(4);
            }

            entityIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
        }

        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
        return state.get(PART) == StackPart.SINGLE ? SINGLE : MAIN;
    }

}
