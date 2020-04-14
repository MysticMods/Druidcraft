package com.vulp.druidcraft.events;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.capabilities.TempSpawnCapability;
import com.vulp.druidcraft.capabilities.TempSpawnFactory;
import com.vulp.druidcraft.capabilities.TempSpawnProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityManager {

    public static final ResourceLocation TEMP_SPAWN = new ResourceLocation(Druidcraft.MODID, "temp_spawn");

    @SubscribeEvent
    public void attachTempSpawn(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof PlayerEntity)) return;
        event.addCapability(TEMP_SPAWN, new TempSpawnProvider());
    }
}
