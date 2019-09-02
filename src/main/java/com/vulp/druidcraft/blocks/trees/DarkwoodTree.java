package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.SpruceTree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.IWorldGenerationReader;

public class DarkwoodTree extends SpruceTree
{
    public static final BlockState TRUNK;
    public static final BlockState LEAF;

    public DarkwoodTree()
    {

    }

    static {
        TRUNK = BlockRegistry.darkwood_log.getDefaultState();
        LEAF = BlockRegistry.darkwood_leaves.getDefaultState();
    }
}