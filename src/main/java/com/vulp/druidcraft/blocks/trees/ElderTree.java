package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.blocks.WoodBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.FeatureRegistry;
import com.vulp.druidcraft.world.features.ElderTreeFeature;
import com.vulp.druidcraft.world.features.ElderTreeFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

public class ElderTree extends Tree {

    public ElderTree() {
    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
        return null;
    }

    @Nullable
    public ConfiguredFeature<ElderTreeFeatureConfig, ?> getElderTreeFeature(Random random, boolean b) {
        return FeatureRegistry.elder_tree.withConfiguration(FeatureRegistry.elder_tree_feature);
    }

    @Override
    public boolean place(IWorld p_225545_1_, ChunkGenerator<?> p_225545_2_, BlockPos p_225545_3_, BlockState p_225545_4_, Random p_225545_5_) {
        ConfiguredFeature<ElderTreeFeatureConfig, ?> lvt_6_1_ = this.getElderTreeFeature(p_225545_5_, this.func_230140_a_(p_225545_1_, p_225545_3_));
        if (lvt_6_1_ == null) {
            return false;
        } else {
            p_225545_1_.setBlockState(p_225545_3_, Blocks.AIR.getDefaultState(), 4);
            ((ElderTreeFeatureConfig)lvt_6_1_.config).forcePlacement();
            if (lvt_6_1_.place(p_225545_1_, p_225545_2_, p_225545_5_, p_225545_3_)) {
                return true;
            } else {
                p_225545_1_.setBlockState(p_225545_3_, p_225545_4_, 4);
                return false;
            }
        }
    }

    public boolean func_230140_a_(IWorld p_230140_1_, BlockPos p_230140_2_) {
        Iterator var3 = BlockPos.Mutable.getAllInBoxMutable(p_230140_2_.down().north(2).west(2), p_230140_2_.up().south(2).east(2)).iterator();

        BlockPos lvt_4_1_;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            lvt_4_1_ = (BlockPos)var3.next();
        } while(!p_230140_1_.getBlockState(lvt_4_1_).isIn(BlockTags.FLOWERS));

        return true;
    }

}
