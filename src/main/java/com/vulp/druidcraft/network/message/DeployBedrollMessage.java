package com.vulp.druidcraft.network.message;

import com.vulp.druidcraft.items.BedrollItem;
import com.vulp.druidcraft.items.TravelPackItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DeployBedrollMessage implements IMessage<DeployBedrollMessage> {

    private int entityID;

public DeployBedrollMessage()
    {
    }

public DeployBedrollMessage(int entityID)
    {
        this.entityID = entityID;
    }

    @Override
    public void encode(DeployBedrollMessage message, PacketBuffer buffer)
    {
        buffer.writeInt(message.entityID);
    }

    @Override
    public DeployBedrollMessage decode(PacketBuffer buffer)
    {
        return new DeployBedrollMessage(buffer.readInt());
    }

    @Override
    public void handle(DeployBedrollMessage message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            TravelPackItem.deployBedroll(message.entityID, supplier.get().getSender().world);
        });
        supplier.get().setPacketHandled(true);
    }
}
