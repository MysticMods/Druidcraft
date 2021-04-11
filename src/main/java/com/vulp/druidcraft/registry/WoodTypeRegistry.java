package com.vulp.druidcraft.registry;

import net.minecraft.block.WoodType;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class WoodTypeRegistry {

    public static WoodType DARKWOOD = WoodType.register(new WoodType("darkwood"));

}
