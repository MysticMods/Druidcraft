package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class DebugBlock extends Block {
    private boolean isWorking = false;
    public DebugBlock(Properties properties) {
        super(properties);
    }

    @Override
    public synchronized BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        BlockState newState;
        String string;
        if (!this.isWorking) {
            this.isWorking = true;
            newState = stateIn;
            string = currentPos + "This block sees itself as the main block.";
        } else {
            newState = Blocks.GOLD_BLOCK.getDefaultState();
            string = currentPos + "This block sees itself as a side block.";
        }
        this.isWorking = false;
        worldIn.setBlockState(currentPos, newState, 1);
        Druidcraft.LOGGER.debug(string);
        return newState;
    }
}
