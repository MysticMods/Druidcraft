package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.world.features.ElderTreeFeature;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class ElderTree extends Tree {
    public ElderTree() {
    }

    @Override
    @Nullable
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
        return new ElderTreeFeature(NoFeatureConfig::deserialize, true);
    }
}
