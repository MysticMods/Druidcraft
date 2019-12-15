package com.vulp.druidcraft.registry;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.entities.tileentities.CrateTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;

import java.util.Set;
import java.util.function.Supplier;

public class TileEntityRegistry {

    public static TileEntityType<CrateTileEntity> crate;

    public static <T extends TileEntity> TileEntityType<T> register(String id, TileEntityType.Builder<T> builder)
    {
        TileEntityType<T> type = builder.build(null);
        type.setRegistryName(Druidcraft.MODID, id);
        return type;
    }
}
