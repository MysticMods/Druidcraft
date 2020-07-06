package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.api.CropLifeStageType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class ElderLeafLayerBlock extends BushBlock {

    public static final IntegerProperty DECAY = IntegerProperty.create("decay", 0, 2);

    public ElderLeafLayerBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(DECAY, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0.0F, 0.0F, 0.0F, 16.0F, 1.0F, 16.0F);
    }

    public int getDecay(BlockState state) {
        return state.get(DECAY);
    }

    public boolean isMaxDecay(BlockState state) {
        return getDecay(state) > 1;
    }

    public void increaseDecay(BlockState state, ServerWorld world, BlockPos pos) {
        world.setBlockState(pos, state.with(DECAY, state.get(DECAY) + 1));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (rand.nextInt(9) == 0) {
            if (isMaxDecay(state)) {
                world.destroyBlock(pos, false);
            } else {
                increaseDecay(state, world, pos);
            }
        }
        super.randomTick(state, world, pos, rand);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DECAY);
    }

}
