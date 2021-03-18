package com.vulp.druidcraft.network;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.network.message.DeployBedrollMessage;
import com.vulp.druidcraft.network.message.BlockProtectionVisualMessage;
import com.vulp.druidcraft.network.message.IMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    private static int nextId = 0;

    public static SimpleChannel instance;

    public static void init() {
        instance = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Druidcraft.MODID, "network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();
        register(DeployBedrollMessage.class, new DeployBedrollMessage());
        register(BlockProtectionVisualMessage.class, new BlockProtectionVisualMessage());
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message)
    {
        instance.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }

}
