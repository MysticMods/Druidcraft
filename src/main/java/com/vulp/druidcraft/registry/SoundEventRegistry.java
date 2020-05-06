package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundEventRegistry {

    public static SoundEvent fill_bottle = setupSoundEvent("fill_bottle");
    public static SoundEvent open_crate = setupSoundEvent("open_crate");
    public static SoundEvent close_crate = setupSoundEvent("close_crate");
    public static SoundEvent wood_cutter_take = setupSoundEvent("woodcutter_take_result");

    private static SoundEvent setupSoundEvent(String name) {
        SoundEvent soundEvent = new SoundEvent(new ResourceLocation(Druidcraft.MODID, name));
        soundEvent.setRegistryName(new ResourceLocation(Druidcraft.MODID, name));
        return soundEvent;
    }
}
