package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.tileentities.CrateTileEntity;
import com.vulp.druidcraft.blocks.tileentities.GrowthLampTileEntity;
import com.vulp.druidcraft.blocks.tileentities.SmallBeamTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityRegistry {

    public static TileEntityType<CrateTileEntity> crate;
    public static TileEntityType<GrowthLampTileEntity> growth_lamp;
    public static TileEntityType<SmallBeamTileEntity> small_beam;

    public static <T extends TileEntity> TileEntityType<T> register(String id, TileEntityType.Builder<T> builder)
    {
        TileEntityType<T> type = builder.build(null);
        type.setRegistryName(Druidcraft.MODID, id);
        return type;
    }
}
