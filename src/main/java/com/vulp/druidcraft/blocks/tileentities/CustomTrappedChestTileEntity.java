package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.tileentity.TileEntityType;

public class CustomTrappedChestTileEntity extends CustomChestTileEntity {

    protected CustomTrappedChestTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public CustomTrappedChestTileEntity() {
        super(TileEntityRegistry.custom_trapped_chest);
    }


    protected void onOpenOrClose() {
        super.onOpenOrClose();
        if (world != null) {
            this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockState().getBlock());
        }
    }

}
