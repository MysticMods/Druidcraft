package com.vulp.druidcraft.network.message;

import com.vulp.druidcraft.blocks.FlareTorchBlock;
import com.vulp.druidcraft.registry.ParticleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FlareTorchParticleMessage implements IMessage<FlareTorchParticleMessage> {

    private double x;
    private double y;
    private double z;

    public FlareTorchParticleMessage() {
    }

    public FlareTorchParticleMessage(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encode(FlareTorchParticleMessage message, PacketBuffer buffer) {
        buffer.setDouble(1, message.x);
        buffer.setDouble(2, message.y);
        buffer.setDouble(3, message.z);
    }

    @Override
    public FlareTorchParticleMessage decode(PacketBuffer buffer) {
        return new FlareTorchParticleMessage(buffer.getDouble(1), buffer.getDouble(2), buffer.getDouble(3));
    }

    @Override
    public void handle(FlareTorchParticleMessage message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player != null) {
                World world = Minecraft.getInstance().player.getEntityWorld();
                world.addOptionalParticle(ParticleRegistry.flare, true, message.x, message.y, message.z, 0.0F, 0.0F, 0.0F);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
