package com.vulp.druidcraft.registry;

import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class ConfiguredCarverRegistry {

    public static final ConfiguredCarver<ProbabilityConfig> CUSTOM_NETHER_CAVE = register("custom_nether_cave", CarverRegistry.CUSTOM_NETHER_CAVE.func_242761_a(new ProbabilityConfig(0.2F)));

    private static <WC extends ICarverConfig> ConfiguredCarver<WC> register(String p_243773_0_, ConfiguredCarver<WC> p_243773_1_) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_CARVER, p_243773_0_, p_243773_1_);
    }

}
