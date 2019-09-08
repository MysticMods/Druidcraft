package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.world.features.DarkwoodTreeFeature;
import com.vulp.druidcraft.world.features.MegaDarkwoodTreeFeature;
import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class DarkwoodTree extends BigTree {

    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
        return new DarkwoodTreeFeature(NoFeatureConfig::deserialize, true);
    }

    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getBigTreeFeature(Random random) {
        return new MegaDarkwoodTreeFeature(NoFeatureConfig::deserialize, false, random.nextBoolean());
    }
}
