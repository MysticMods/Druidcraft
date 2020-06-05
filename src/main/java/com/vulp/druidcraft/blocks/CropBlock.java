package com.vulp.druidcraft.blocks;

import net.minecraft.block.*;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CropBlock extends BushBlock {

    public CropBlock(Properties properties) {
        super(properties);
    }

    public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getBlock() == Blocks.FARMLAND;
    }
}