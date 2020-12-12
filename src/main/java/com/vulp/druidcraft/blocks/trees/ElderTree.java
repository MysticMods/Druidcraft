package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.registry.FeatureRegistry;
import com.vulp.druidcraft.world.config.FeatureConfig;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;

public class ElderTree extends Tree {

    public ElderTree() {
    }

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
        return FeatureRegistry.elder_tree.withConfiguration(FeatureConfig.Trees.elder_tree);
    }

}
