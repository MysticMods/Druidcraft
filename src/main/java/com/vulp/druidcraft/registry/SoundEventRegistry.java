package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundEventRegistry {

    public static SoundEvent fill_bottle = setupSoundEvent("fill_bottle");

    private static SoundEvent setupSoundEvent(String name) {
        SoundEvent soundEvent = new SoundEvent(new ResourceLocation(Druidcraft.MODID, name));
        soundEvent.setRegistryName(new ResourceLocation(Druidcraft.MODID, name));
        return soundEvent;
    }
}
