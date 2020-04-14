package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.capabilities.ITempSpawnCapability;
import com.vulp.druidcraft.capabilities.TempSpawnFactory;
import com.vulp.druidcraft.capabilities.TempSpawnStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.Callable;

public class CapabilityRegistry {

    @SubscribeEvent
    public void registerCapabilities() {
        CapabilityManager.INSTANCE.register(ITempSpawnCapability.class, new TempSpawnStorage(), new TempSpawnFactory());
    }
}
