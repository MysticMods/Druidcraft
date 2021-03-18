package com.vulp.druidcraft.network.message;

import com.vulp.druidcraft.entities.DuragemProtectionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class BlockProtectionVisualMessage implements IMessage<BlockProtectionVisualMessage> {

    private BlockPos blockPos;

    public BlockProtectionVisualMessage() {
    }

    public BlockProtectionVisualMessage(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    @Override
    public void encode(BlockProtectionVisualMessage message, PacketBuffer buffer) {
        buffer.writeBlockPos(message.blockPos);
    }

    @Override
    public BlockProtectionVisualMessage decode(PacketBuffer buffer) {
        return new BlockProtectionVisualMessage(buffer.readBlockPos());
    }

    @Override
    public void handle(BlockProtectionVisualMessage message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() ->
        {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player != null) {
                BlockPos pos = message.blockPos;
                World world = Minecraft.getInstance().player.getEntityWorld();
                List<DuragemProtectionEntity> list = world.getEntitiesWithinAABB(DuragemProtectionEntity.class, new AxisAlignedBB(pos.add(1.0D, 1.0D, 1.0D), pos));
                if (list.size() > 0) {
                    list.get(0).setVisible(true);
                }
            }
        });
        supplier.get().setPacketHandled(true);
    }
}