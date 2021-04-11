package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.blocks.tileentities.CustomTrappedChestTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class CustomTrappedChestBlock extends CustomChestBlock {

    public CustomTrappedChestBlock(Properties builder, Supplier<TileEntityType<? extends ChestTileEntity>> tileEntityTypeIn) {
        super(builder, tileEntityTypeIn);
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CustomTrappedChestTileEntity();
    }

    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    public boolean canProvidePower(BlockState state) {
        return true;
    }

    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return MathHelper.clamp(ChestTileEntity.getPlayersUsing(blockAccess, pos), 0, 15);
    }

    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return side == Direction.UP ? blockState.getWeakPower(blockAccess, pos, side) : 0;
    }
}
