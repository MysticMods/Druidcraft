/*
package com.vulp.druidcraft.network.message;

import com.vulp.druidcraft.blocks.tileentities.MortarAndPestleTileEntity;
import com.vulp.druidcraft.inventory.container.MortarAndPestleContainer;
import com.vulp.druidcraft.items.TravelPackItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MortarGrindMessage implements IMessage<MortarGrindMessage> {

    private int windowID;

    public MortarGrindMessage() {

    }

    public MortarGrindMessage(int windowID) {
        this.windowID = windowID;
    }

    @Override
    public void encode(MortarGrindMessage message, PacketBuffer buffer)
    {
        buffer.writeInt(message.windowID);
    }

    @Override
    public MortarGrindMessage decode(PacketBuffer buffer)
    {
        return new MortarGrindMessage(buffer.readInt());
    }


    @Override
    public void handle(MortarGrindMessage message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            PlayerEntity player = supplier.get().getSender();
            if (player != null) {
                Container container = player.openContainer;
                if (container.windowId == message.windowID && container instanceof MortarAndPestleContainer) {
                    ((MortarAndPestleContainer) container).grind();
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
*/
