package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.Iterator;
import java.util.Random;

public class HempBlock extends CropBlock {
    public HempBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    boolean topBlockValid;

    @Override
    public boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        if (block == Blocks.FARMLAND || block == BlockRegistry.hemp_crop) {
            return true;
        } else {
            return false;
        }
    }

    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.nextInt(worldIn.rand, 1, 3);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState soil = worldIn.getBlockState(pos.down());
        if (soil.canSustainPlant(worldIn, pos.down(), Direction.UP, this)) {
            return true;
        } else {
            Block block = worldIn.getBlockState(pos.down()).getBlock();
            if (block == this || block == Blocks.FARMLAND) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if (isValidGround(state, worldIn, pos) == false) {
            worldIn.destroyBlock(pos, true);
        } else if ((worldIn.getBlockState(pos.down()).getBlock() != this) && (worldIn.isAirBlock(pos.up()))) {
            topBlockValid = true;
        } else {
            topBlockValid = false;
        }
        if (!worldIn.isAreaLoaded(pos, 1)) return;
        if (worldIn.getLightSubtracted(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, worldIn, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0) && (i < this.getMaxAge()) && (topBlockValid == false)) {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }

            } else {
                if ((topBlockValid == true) && (i == this.getMaxAge())) {
                    worldIn.setBlockState(pos.up(), this.getDefaultState());
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext selectionContext) {
        return Block.makeCuboidShape(4, 0, 4, 12.0d, 4.0d * (state.get(AGE) + 1), 12.0d);
    }
}