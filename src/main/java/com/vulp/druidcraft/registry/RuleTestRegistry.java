package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.util.OreGenTest;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Druidcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RuleTestRegistry {
    public static IRuleTestType<OreGenTest> ORE_GEN;

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ORE_GEN = Registry.register(Registry.RULE_TEST, "ore_gen", () -> OreGenTest.CODEC);
        });
    }
}
