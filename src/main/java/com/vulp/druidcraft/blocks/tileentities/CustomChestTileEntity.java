package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CustomChestTileEntity extends ChestTileEntity {

    protected CustomChestTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public CustomChestTileEntity() {
        super(TileEntityRegistry.custom_chest);
    }

}
