package com.vulp.druidcraft.blocks;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class HangingPlantBlock extends HangingBlock implements IGrowable {

    public HangingPlantBlock(Properties properties, Direction growDirection, boolean breaksInWater) {
        super(properties);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random, BlockPos blockPos, BlockState blockState) {

    }

/*    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        return updatePart(this.getDefaultState(), world, pos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState aboveBlock = context.getWorld().getBlockState(context.getPos().up());
        return !aboveBlock.isIn(this.getTopPlantBlock()) && !aboveBlock.isIn(this.getBodyPlantBlock()) ? this.grow(context.getWorld()) : this.getBodyPlantBlock().getDefaultState();
    }

    public boolean isValidPosition(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        BlockPos lvt_4_1_ = p_196260_3_.offset(this.growthDirection.getOpposite());
        BlockState lvt_5_1_ = p_196260_2_.getBlockState(lvt_4_1_);
        Block lvt_6_1_ = lvt_5_1_.getBlock();
        if (!this.canGrowOn(lvt_6_1_)) {
            return false;
        } else {
            return lvt_6_1_ == this.getTopPlantBlock() || lvt_6_1_ == this.getBodyPlantBlock() || lvt_5_1_.isSolidSide(p_196260_2_, lvt_4_1_, this.growthDirection);
        }
    }

    public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
        if (!p_225534_1_.isValidPosition(p_225534_2_, p_225534_3_)) {
            p_225534_2_.destroyBlock(p_225534_3_, true);
        }

    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        Optional<BlockPos> optional = this.nextGrowPosition(worldIn, pos, state);
        return optional.isPresent() && this.getTopPlantBlock().canGrowIn(worldIn.getBlockState(optional.get().offset(this.growthDirection)));
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        Optional<BlockPos> optional = this.nextGrowPosition(worldIn, pos, state);
        if (optional.isPresent()) {
            BlockState blockstate = worldIn.getBlockState(optional.get());
            ((AbstractTopPlantBlock)blockstate.getBlock()).grow(worldIn, rand, optional.get(), blockstate);
        }

    }

    private Optional<BlockPos> nextGrowPosition(IBlockReader reader, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos;

        Block block;
        do {
            blockpos = blockpos.offset(this.growthDirection);
            block = reader.getBlockState(blockpos).getBlock();
        } while(block == state.getBlock());

        return block == this.getTopPlantBlock() ? Optional.of(blockpos) : Optional.empty();
    }

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        boolean flag = super.isReplaceable(state, useContext);
        return flag && useContext.getItem().getItem() == this.getTopPlantBlock().asItem() ? false : flag;
    }

    protected boolean canGrowOn(Block p_230333_1_) {
        return true;
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return this.shape;
    }*/

}
