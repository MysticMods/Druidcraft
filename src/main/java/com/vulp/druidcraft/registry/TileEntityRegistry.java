package com.vulp.druidcraft.registry;

import com.google.common.collect.ImmutableSet;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.tileentity.SignTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.function.Supplier;

public class TileEntityRegistry {

//    public static TileEntityType<SignTileEntity> sign_te = createTileEntity(SignTileEntity::new, "sign", BlockRegistry.darkwood_sign);


    private static <T extends TileEntity> TileEntityType<?> createTileEntity(Supplier<T> factory, String name, Block... validBlocks) {

        TileEntityType<T> type = TileEntityType.Builder.create(factory, validBlocks).build(null);
        type.setRegistryName(Druidcraft.MODID, name);

        return type;
    }
}
