package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.tileentities.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityRegistry {

    public static TileEntityType<CrateTileEntity> crate;
    public static TileEntityType<GrowthLampTileEntity> growth_lamp;
    public static TileEntityType<SmallBeamTileEntity> small_beam;
    public static TileEntityType<HellkilnTileEntity> hellkiln;
    public static TileEntityType<HellkilnIgniterTileEntity> hellkiln_igniter;
    public static TileEntityType<InfernalFlareTileEntity> infernal_flare;
    public static TileEntityType<FlareTorchTileEntity> flare_torch;

    public static <T extends TileEntity> TileEntityType<T> register(String id, TileEntityType.Builder<T> builder)
    {
        TileEntityType<T> type = builder.build(null);
        type.setRegistryName(Druidcraft.MODID, id);
        return type;
    }
}
