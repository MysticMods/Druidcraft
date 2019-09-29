package com.vulp.druidcraft.world;

import com.vulp.druidcraft.config.WorldGenConfig;
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
        if (WorldGenConfig.generate_ores.get()) {
            for (Biome biome : ForgeRegistries.BIOMES) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.amber_ore.getDefaultState(), WorldGenConfig.amber_size.get()), Placement.COUNT_RANGE, new CountRangeConfig(WorldGenConfig.amber_weight.get(), 0, 0, 256)));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.moonstone_ore.getDefaultState(), WorldGenConfig.moonstone_size.get()), Placement.COUNT_RANGE, new CountRangeConfig(WorldGenConfig.moonstone_weight.get(), 0, 0, 256)));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.fiery_glass_ore.getDefaultState(), WorldGenConfig.fiery_glass_size.get()), Placement.COUNT_RANGE, new CountRangeConfig(WorldGenConfig.fiery_glass_weight.get(), 0, 0, 32)));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.rockroot_ore.getDefaultState(), WorldGenConfig.rockroot_size.get()), Placement.COUNT_RANGE, new CountRangeConfig(WorldGenConfig.rockroot_weight.get(), 192, 16, 256)));
            }
        }
    }
}