/*
package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.registry.ConfiguredFeatureRegistry;
import com.vulp.druidcraft.world.config.DummyTreeFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class GiantHeartburnFungus extends Tree {

    @Nullable
    protected ConfiguredFeature<DummyTreeFeatureConfig, ?> getGiantHeartburnFungusFeature(){
        return ConfiguredFeatureRegistry.giant_heartburn_fungus;
    }

    @Override
    public boolean attemptGrowTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random rand) {
        ConfiguredFeature<DummyTreeFeatureConfig, ?> configuredfeature = this.getGiantHeartburnFungusFeature();
        if (configuredfeature == null) {
            return false;
        } else {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            configuredfeature.config.forcePlacement();
            if (configuredfeature.generate(world, chunkGenerator, rand, pos)) {
                return true;
            } else {
                world.setBlockState(pos, state, 4);
                return false;
            }
        }
    }

    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return null;
    }

}
*/
