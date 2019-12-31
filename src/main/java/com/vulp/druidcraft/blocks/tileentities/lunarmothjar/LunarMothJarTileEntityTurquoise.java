package com.vulp.druidcraft.blocks.tileentities.lunarmothjar;

import com.vulp.druidcraft.blocks.tileentities.LunarMothJarTileEntity;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.tileentity.TileEntityType;

public class LunarMothJarTileEntityTurquoise extends LunarMothJarTileEntity {

    public LunarMothJarTileEntityTurquoise(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
        this.color = 1;
    }

    public LunarMothJarTileEntityTurquoise() {
        this(TileEntityRegistry.turquoise_lunar_moth_jar);
    }
}
