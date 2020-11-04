package com.vulp.druidcraft.events;

import com.vulp.druidcraft.config.EntitySpawnConfig;
import com.vulp.druidcraft.config.WorldGenConfig;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.EntityRegistry;
import com.vulp.druidcraft.registry.FeatureRegistry;
import com.vulp.druidcraft.world.biomes.BiomeFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BiomeLoadEventHandler {

    @SubscribeEvent
    public void onBiomeLoading(BiomeLoadingEvent event) {

        // ELDER TREES
        if (event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.RIVER) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.elder_tree_feature_config.withConfiguration(BiomeFeatures.empty_tree_config).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01F, 1))));
        }
        // BLUEBERRIES
        if (event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.RIVER || event.getCategory() == Biome.Category.EXTREME_HILLS || event.getCategory() == Biome.Category.TAIGA) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(FeatureRegistry.blueberry_bush_feature_config).withPlacement(Placement.CHANCE.configure(new ChanceConfig(100))));
        }
        // LAVENDER
        if (event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.RIVER || event.getCategory() == Biome.Category.EXTREME_HILLS) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(FeatureRegistry.lavender_feature_config).withPlacement(Features.Placements.FLOWER_TALL_GRASS_PLACEMENT).square().withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 16, 5))));
        }

        // -------------------------------------------------------------------------------------------------------------------

        if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.amber_ore.getDefaultState(), WorldGenConfig.amber_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().func_242731_b(WorldGenConfig.amber_weight.get()));
            settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.moonstone_ore.getDefaultState(), WorldGenConfig.moonstone_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().func_242731_b(WorldGenConfig.moonstone_weight.get()));
            settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.fiery_glass_ore.getDefaultState(), WorldGenConfig.fiery_glass_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 32))).square().func_242731_b(WorldGenConfig.fiery_glass_weight.get()));
            settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.rockroot_ore.getDefaultState(), WorldGenConfig.rockroot_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(48, 16, 256))).square().func_242731_b(WorldGenConfig.rockroot_weight.get()));
        }

        // -------------------------------------------------------------------------------------------------------------------

        // BEETLE
        if (EntitySpawnConfig.beetle_biome_types.get().contains(event.getCategory().getName())) {
            if (EntitySpawnConfig.beetle_spawn.get()) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityRegistry.beetle_entity, EntitySpawnConfig.beetle_weight.get(), EntitySpawnConfig.beetle_min_group.get(), EntitySpawnConfig.beetle_max_group.get()));
            }
        }
        // DREADFISH
        if (EntitySpawnConfig.dreadfish_biome_types.get().contains(event.getCategory().getName())) {
            if (EntitySpawnConfig.dreadfish_spawn.get()) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityRegistry.dreadfish_entity, EntitySpawnConfig.dreadfish_weight.get(), EntitySpawnConfig.dreadfish_min_group.get(), EntitySpawnConfig.dreadfish_max_group.get()));
            }
        }
        // LUNAR MOTH
        if (EntitySpawnConfig.lunar_moth_biome_types.get().contains(event.getCategory().getName())) {
            if (EntitySpawnConfig.lunar_moth_spawn.get()) {
                event.getSpawns().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityRegistry.lunar_moth_entity, EntitySpawnConfig.lunar_moth_weight.get(), EntitySpawnConfig.lunar_moth_min_group.get(), EntitySpawnConfig.lunar_moth_max_group.get()));
            }
        }

    }
}
