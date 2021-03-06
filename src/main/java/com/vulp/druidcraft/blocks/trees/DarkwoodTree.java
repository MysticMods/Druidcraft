package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.registry.ConfiguredFeatureRegistry;
import net.minecraft.block.trees.SpruceTree;
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
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random p_225546_1_, boolean p_225546_2_) {
        return ConfiguredFeatureRegistry.darkwood_tree;
    }

    @Override
    @Nullable
    public ConfiguredFeature<BaseTreeFeatureConfig, ?> getHugeTreeFeature(Random p_225547_1_) {
        return ConfiguredFeatureRegistry.mega_darkwood_tree;
    }
}
