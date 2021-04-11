package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.blocks.tileentities.CustomChestTileEntity;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class CustomChestBlock extends ChestBlock {

    public CustomChestBlock(Properties builder, Supplier<TileEntityType<? extends ChestTileEntity>> tileEntityTypeIn) {
        super(builder, tileEntityTypeIn);
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CustomChestTileEntity();
    }

}
