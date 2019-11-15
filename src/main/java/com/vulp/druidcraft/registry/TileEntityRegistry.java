package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.entities.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.function.Supplier;

public class TileEntityRegistry {

    public static TileEntityType<?> sign_te = createTileEntity(SignTileEntity::new);


    private static <T extends TileEntity> TileEntityType<?> createTileEntity(Supplier<? extends T> factory) {

        TileEntityType<?> type = TileEntityType.Builder.create(factory).build(null).setRegistryName(Druidcraft.MODID, "sign");

        return type;
    }
}
