package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.registry.FeatureRegistry;
import com.vulp.druidcraft.world.biomes.BiomeFeatures;
import net.minecraft.block.trees.SpruceTree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class DarkwoodTree extends SpruceTree {

    public DarkwoodTree() {
    }

    @Override
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random p_225546_1_, boolean p_225546_2_) {
        return Feature.TREE.withConfiguration(BiomeFeatures.darkwood_tree_feature);
    }

    @Override
    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getHugeTreeFeature(Random p_225547_1_) {
        return Feature.TREE.withConfiguration(BiomeFeatures.mega_darkwood_tree_feature);
    }
}
