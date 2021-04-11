package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CustomSignTileEntity extends SignTileEntity {

    @Override
    public TileEntityType<?> getType() {
        return TileEntityRegistry.custom_sign;
    }

}
