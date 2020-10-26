package com.vulp.druidcraft.world;

import com.vulp.druidcraft.config.WorldGenConfig;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {

    public static void setupOreGeneration() {
        if (WorldGenConfig.generate_ores.get()) {
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.amber_ore.getTranslationKey(), Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BlockRegistry.amber_ore.getDefaultState(), WorldGenConfig.amber_size.get())).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(128, 128))).func_242728_a().func_242731_b(WorldGenConfig.amber_weight.get()));
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.moonstone_ore.getTranslationKey(), Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BlockRegistry.moonstone_ore.getDefaultState(), WorldGenConfig.moonstone_size.get())).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(128, 128))).func_242728_a().func_242731_b(WorldGenConfig.moonstone_weight.get()));
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.fiery_glass_ore.getTranslationKey(), Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BlockRegistry.fiery_glass_ore.getDefaultState(), WorldGenConfig.fiery_glass_size.get())).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(24, 24))).func_242728_a().func_242731_b(WorldGenConfig.fiery_glass_weight.get()));
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BlockRegistry.rockroot_ore.getTranslationKey(), Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, BlockRegistry.rockroot_ore.getDefaultState(), WorldGenConfig.rockroot_size.get())).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(80, 32))).func_242728_a().func_242731_b(WorldGenConfig.rockroot_weight.get()));
        }
    }
}