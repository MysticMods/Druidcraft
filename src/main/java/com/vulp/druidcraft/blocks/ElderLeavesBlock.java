package com.vulp.druidcraft.blocks;

import com.sun.scenario.effect.Crop;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.CropLifeStageType;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class ElderLeavesBlock extends LeavesBlock {

    public static final IntegerProperty GROWTH_TRIES = IntegerProperty.create("growth_tries", 0, 2);

    public ElderLeavesBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(GROWTH_TRIES, 0));
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (!worldIn.isRemote && (worldIn.rand.nextInt(4) == 0) && CropLifeStageType.checkCropLife(worldIn) == CropLifeStageType.FLOWER && CropLifeStageType.getTwoDayCycle(worldIn) <= 12000) {
            if (state.get(GROWTH_TRIES) < 2) {
                Direction[] var3 = Direction.values();

                for (Direction direction : var3) {
                    if (worldIn.getBlockState(pos.offset(direction)).getMaterial().isReplaceable()) {
                        if (worldIn.rand.nextInt(3) == 0) {
                            worldIn.setBlockState(pos.offset(direction), BlockRegistry.elder_fruit.getDefaultState().with(ElderFruitBlock.FACING, direction.getOpposite()));
                        } else {
                        }
                        worldIn.setBlockState(pos, state.with(GROWTH_TRIES, state.get(GROWTH_TRIES) + 1));
                    } else {
                    }
                }
            }
        }

        if (!worldIn.isRemote && CropLifeStageType.checkCropLife(worldIn) != CropLifeStageType.FLOWER && state.get(GROWTH_TRIES) > 0) {
            worldIn.setBlockState(pos, state.with(GROWTH_TRIES, 0));
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, GROWTH_TRIES);
    }
}