package com.vulp.druidcraft.world;

import com.vulp.druidcraft.config.OreGenerationConfig;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {

    public static void setupOreGeneration() {
        if (OreGenerationConfig.generate_ores.get()) {
            for (Biome biome : ForgeRegistries.BIOMES) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.amber_ore.getDefaultState(), OreGenerationConfig.amber_size.get()), Placement.COUNT_RANGE, new CountRangeConfig(OreGenerationConfig.amber_weight.get(), 0, 0, 256)));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.moonstone_ore.getDefaultState(), OreGenerationConfig.moonstone_size.get()), Placement.COUNT_RANGE, new CountRangeConfig(OreGenerationConfig.moonstone_weight.get(), 0, 0, 256)));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.fiery_glass_ore.getDefaultState(), OreGenerationConfig.fiery_glass_size.get()), Placement.COUNT_RANGE, new CountRangeConfig(OreGenerationConfig.fiery_glass_weight.get(), 0, 0, 32)));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.rockroot_ore.getDefaultState(), OreGenerationConfig.rockroot_size.get()), Placement.COUNT_RANGE, new CountRangeConfig(OreGenerationConfig.rockroot_weight.get(), 64, 16, 256)));
            }
        }
    }
}