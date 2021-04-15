package com.vulp.druidcraft.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class NetherDoublePlantBlock extends DoublePlantBlock {

    public NetherDoublePlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.isIn(BlockTags.NYLIUM) || state.isIn(Blocks.SOUL_SOIL) || super.isValidGround(state, reader, pos);
    }

}
