package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.events.LootHandler;
import com.vulp.druidcraft.events.SpawnHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventRegistry {

    @SubscribeEvent
    public static void handleEvents() {
        MinecraftForge.EVENT_BUS.register(new LootHandler());
        MinecraftForge.EVENT_BUS.register(new SpawnHandler());
    }
}
