package com.vulp.druidcraft.events;

import com.vulp.druidcraft.config.EntitySpawnConfig;
import com.vulp.druidcraft.config.WorldGenConfig;
import com.vulp.druidcraft.registry.EntityRegistry;
import com.vulp.druidcraft.registry.ConfiguredFeatureRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BiomeLoadEventHandler {

    @SubscribeEvent
    public void onBiomeLoading(BiomeLoadingEvent event) {

        EntitySpawnConfig.verifyLists();

        // ELDER TREES
        if (event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.RIVER) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatureRegistry.elder_tree.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01F, 1))));
        }
        if (event.getCategory() == Biome.Category.FOREST) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatureRegistry.elder_tree.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.025F, 1))));
        }
        // BLUEBERRIES
        if (event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.RIVER || event.getCategory() == Biome.Category.EXTREME_HILLS || event.getCategory() == Biome.Category.TAIGA) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatureRegistry.blueberry_bush.withPlacement(Placement.CHANCE.configure(new ChanceConfig(100))));
        }
        // LAVENDER
        if (event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.RIVER || event.getCategory() == Biome.Category.EXTREME_HILLS) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ConfiguredFeatureRegistry.lavender.withPlacement(Features.Placements.FLOWER_TALL_GRASS_PLACEMENT).square().withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 16, 5))));
        }

        // -------------------------------------------------------------------------------------------------------------------

        if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND) {
            BiomeGenerationSettingsBuilder settings = event.getGeneration();
            settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatureRegistry.amber.withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().func_242731_b(WorldGenConfig.amber_weight.get()));
            settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatureRegistry.moonstone.withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().func_242731_b(WorldGenConfig.moonstone_weight.get()));
            settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatureRegistry.fiery_glass.withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 32))).square().func_242731_b(WorldGenConfig.fiery_glass_weight.get()));
            settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ConfiguredFeatureRegistry.rockroot.withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(48, 16, 256))).square().func_242731_b(WorldGenConfig.rockroot_weight.get()));
        }

        // -------------------------------------------------------------------------------------------------------------------

        if (event.getName() != null) {
            Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
            if (biome != null) {
                List<BiomeDictionary.Type> biomeTypes = new ArrayList<>(BiomeDictionary.getTypes(RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, event.getName())));
                // BEETLE
                if (biomeCategoryChecker(EntitySpawnConfig.beetle_biome_whitelist, EntitySpawnConfig.beetle_biome_blacklist, biomeTypes, EntitySpawnConfig.beetle_spawn.get())) {
                    event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityRegistry.beetle_entity, EntitySpawnConfig.beetle_weight.get(), EntitySpawnConfig.beetle_min_group.get(), EntitySpawnConfig.beetle_max_group.get()));
                }

                // DREADFISH
                if (biomeCategoryChecker(EntitySpawnConfig.dreadfish_biome_whitelist, EntitySpawnConfig.dreadfish_biome_blacklist, biomeTypes, EntitySpawnConfig.dreadfish_spawn.get())) {
                    event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityRegistry.dreadfish_entity, EntitySpawnConfig.dreadfish_weight.get(), EntitySpawnConfig.dreadfish_min_group.get(), EntitySpawnConfig.dreadfish_max_group.get()));
                }

                // LUNAR MOTH
                if (biomeCategoryChecker(EntitySpawnConfig.lunar_moth_biome_whitelist, EntitySpawnConfig.lunar_moth_biome_blacklist, biomeTypes, EntitySpawnConfig.lunar_moth_spawn.get())) {
                    event.getSpawns().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityRegistry.lunar_moth_entity, EntitySpawnConfig.lunar_moth_weight.get(), EntitySpawnConfig.lunar_moth_min_group.get(), EntitySpawnConfig.lunar_moth_max_group.get()));
                }
            }
        }

    }

    public static boolean biomeCategoryChecker(ArrayList<String> whitelist, ArrayList<String> blacklist, List<BiomeDictionary.Type> biomeTypes, boolean canSpawn) {
        if (canSpawn) {
            boolean checker = false;
            for (BiomeDictionary.Type biomeType : biomeTypes) {
                if (whitelist.contains(biomeType.getName())) {
                    checker = true;
                }
                if (blacklist.contains(biomeType.getName())) {
                    return false;
                }
            }
            return checker;
        }
        return false;
    }

}
