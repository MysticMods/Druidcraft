package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.registry.FeatureRegistry;
import com.vulp.druidcraft.world.biomes.BiomeFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class ElderTree extends Tree {

    public ElderTree() {
    }

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
        return FeatureRegistry.elder_tree_feature.withConfiguration(BiomeFeatures.empty);
    }

}
