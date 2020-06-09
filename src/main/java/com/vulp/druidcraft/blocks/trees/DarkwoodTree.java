package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.registry.FeatureRegistry;
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

    @Nullable
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random p_225546_1_, boolean p_225546_2_) {
        return Feature.NORMAL_TREE.withConfiguration(FeatureRegistry.darkwood_tree_feature);
    }

    @Nullable
    protected ConfiguredFeature<HugeTreeFeatureConfig, ?> getHugeTreeFeature(Random p_225547_1_) {
        return Feature.MEGA_SPRUCE_TREE.withConfiguration(FeatureRegistry.giant_darkwood_tree_feature);
    }
}
