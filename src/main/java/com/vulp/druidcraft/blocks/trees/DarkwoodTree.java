package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.registry.FeatureRegistry;
import com.vulp.druidcraft.world.biomes.BiomeFeatures;
import net.minecraft.block.trees.SpruceTree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class DarkwoodTree extends SpruceTree {

    public DarkwoodTree() {
    }

    @Override
    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random p_225546_1_, boolean p_225546_2_) {
        return Feature.NORMAL_TREE.withConfiguration(BiomeFeatures.darkwood_tree_feature);
    }

    @Override
    @Nullable
    protected ConfiguredFeature<HugeTreeFeatureConfig, ?> getHugeTreeFeature(Random p_225547_1_) {
        return Feature.MEGA_SPRUCE_TREE.withConfiguration(BiomeFeatures.mega_darkwood_tree_feature);
    }
}
