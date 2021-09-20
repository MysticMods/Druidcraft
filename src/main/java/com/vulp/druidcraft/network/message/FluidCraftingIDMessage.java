/*
package com.vulp.druidcraft.network.message;

import com.vulp.druidcraft.blocks.tileentities.FluidCraftingTableTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidCraftingIDMessage implements IMessage<FluidCraftingIDMessage> {

    private int fluidID;
    private BlockPos tilePos;

    public FluidCraftingIDMessage() {
    }

    public FluidCraftingIDMessage(int fluidID, BlockPos tilePos) {
        this.fluidID = fluidID;
        this.tilePos = tilePos;
    }

    @Override
    public void encode(FluidCraftingIDMessage message, PacketBuffer buffer) {
        buffer.writeInt(message.fluidID);
        buffer.writeBlockPos(message.tilePos);
    }

    @Override
    public FluidCraftingIDMessage decode(PacketBuffer buffer) {
        return new FluidCraftingIDMessage(buffer.readInt(), buffer.readBlockPos());
    }

    @Override
    public void handle(FluidCraftingIDMessage message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() ->
        {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player != null) {
                BlockPos pos = message.tilePos;
                World world = Minecraft.getInstance().player.getEntityWorld();
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof FluidCraftingTableTileEntity) {
                    ((FluidCraftingTableTileEntity) tile).setFluidID(message.fluidID);
                }
                world.getLightManager().checkBlock(pos);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}*/
