package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
    protected int getBonemealAgeIncrease(World world) {
        return MathHelper.nextInt(world.rand, 1, 3);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return isValidGround(state, world, pos);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        Block block = world.getBlockState(pos.down()).getBlock();
        if (block == Blocks.FARMLAND || block == BlockRegistry.hemp_crop) {
            return true;
        }
        else return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected IItemProvider getSeedsItem() {
        return ItemRegistry.hemp_seeds;
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return (!isMaxAge(state)) || (world.getBlockState(pos.down()).getBlock() != this) && (world.getBlockState(pos.up()).getBlock() != this);
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random) {

        super.tick(state, world, pos, random);
        isValidPosition(state, world, pos);

        if ((world.getBlockState(pos.down()).getBlock() != this) && (world.isAirBlock(pos.up()))) {
            topBlockValid = true;
        } else {
            topBlockValid = false;
        }

        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.getLightSubtracted(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, world, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0) && (i < this.getMaxAge()) && (topBlockValid == false)) {
                    world.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state);
                }
            }
            else {
                if ((topBlockValid == true) && (i == this.getMaxAge())) {
                    world.setBlockState(pos.up(), this.getDefaultState());
                }
            }
        }
    }

    @Override
    public void grow(World world, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(world);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        if (this.getAge(state) != j) {
            world.setBlockState(pos, this.withAge(i), 2);
        }
        else if ((this.getAge(state) == j) && (topBlockValid = true)) {
            world.setBlockState(pos.up(), this.getDefaultState());
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext selectionContext) {
        return Block.makeCuboidShape(4, 0, 4, 12.0d, 4.0d * (state.get(AGE) + 1), 12.0d);
    }
}