package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.world.config.DummyTreeFeatureConfig;
import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class FungusBlock extends BushBlock implements IGrowable {

    protected VoxelShape SHAPE;
    private final Supplier<ConfiguredFeature<DummyTreeFeatureConfig, ?>> fungusFeature;

    public FungusBlock(Properties properties, Supplier<ConfiguredFeature<DummyTreeFeatureConfig, ?>> config, VoxelShape shape) {
        super(properties);
        this.fungusFeature = config;
        this.SHAPE = shape;
    }

    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    protected boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.isIn(BlockTags.NYLIUM) || state.isIn(Blocks.MYCELIUM) || state.isIn(Blocks.SOUL_SOIL) || super.isValidGround(state, reader, pos);
    }

    public boolean canGrow(IBlockReader reader, BlockPos pos, BlockState state, boolean flag) {
        return isValidGround(reader.getBlockState(pos.down()), reader, pos.down());
    }

    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return (double)rand.nextFloat() < 0.4D;
    }

    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        this.fungusFeature.get().generate(world, world.getChunkProvider().getChunkGenerator(), rand, pos);
    }

}
