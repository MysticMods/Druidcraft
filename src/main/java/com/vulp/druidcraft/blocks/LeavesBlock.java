package com.vulp.druidcraft.blocks;

import javafx.geometry.Side;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.command.impl.data.BlockDataAccessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class LeavesBlock extends net.minecraft.block.LeavesBlock {
    public LeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public MaterialColor getMaterialColor(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return MaterialColor.FOLIAGE;
    }
}
