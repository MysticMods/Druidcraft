package com.vulp.druidcraft.world.biomes;

import com.vulp.druidcraft.registry.ConfiguredFeatureRegistry;
import com.vulp.druidcraft.registry.ConfiguredSurfaceRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.*;

public class BiomeCreation {



    public static Biome makeDarkwoodForest() {
        MobSpawnInfo.Builder spawn = new MobSpawnInfo.Builder()
            .isValidSpawnBiomeForPlayer()
            .withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 8, 2, 4));
        DefaultBiomeFeatures.withPassiveMobs(spawn);
        DefaultBiomeFeatures.withBatsAndHostiles(spawn);

        BiomeAmbience.Builder ambience = new BiomeAmbience.Builder()
            .setFogColor(12638463)
            .withFoliageColor(0x305B2A)
            .setWaterColor(0x5786E5)
            .setWaterFogColor(0x38577F)
            .withSkyColor(getSkyColorFromTemp(0.25f))
            .withGrassColor(0x508948);

        BiomeGenerationSettings.Builder settings = new BiomeGenerationSettings.Builder()
            .withSurfaceBuilder(ConfiguredSurfaceRegistry.DARKWOOD_FOREST)
            .withStructure(StructureFeatures.PILLAGER_OUTPOST)
            .withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(settings);
        DefaultBiomeFeatures.withCavesAndCanyons(settings);
        DefaultBiomeFeatures.withLavaAndWaterLakes(settings);
        DefaultBiomeFeatures.withMonsterRoom(settings);
        DefaultBiomeFeatures.withLargeFern(settings);
        settings.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ConfiguredFeatureRegistry.boulder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(settings);
        DefaultBiomeFeatures.withOverworldOres(settings);
        DefaultBiomeFeatures.withDisks(settings);
        ConfiguredFeatureRegistry.addDarkwoodTrees(settings);
        ConfiguredFeatureRegistry.addDarkwoodBushes(settings);
        DefaultBiomeFeatures.withDefaultFlowers(settings);
        DefaultBiomeFeatures.withTaigaGrassVegetation(settings);
        DefaultBiomeFeatures.withNormalMushroomGeneration(settings);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(settings);
        DefaultBiomeFeatures.withLavaAndWaterSprings(settings);
        DefaultBiomeFeatures.withSparseBerries(settings);

        Biome.Builder builder = new Biome.Builder()
            .scale(0.2f)
            .temperature(0.25f)
            .category(Biome.Category.TAIGA)
            .precipitation(Biome.RainType.RAIN)
            .downfall(0.8f)
            .depth(0.4f)
            .withMobSpawnSettings(spawn.copy())
            .setEffects(ambience.build())
            .withGenerationSettings(settings.build());

        return builder.build();
    }

    public static Biome makeFervidJungleBiome() {
        MobSpawnInfo.Builder spawn = new MobSpawnInfo.Builder()
                .withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.GHAST, 50, 4, 4))
                .withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4))
                .withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.MAGMA_CUBE, 2, 4, 4))
                .withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 4, 4))
                .withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.PIGLIN, 15, 4, 4))
                .withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.STRIDER, 60, 1, 2));

        BiomeAmbience.Builder ambience = new BiomeAmbience.Builder()
                .setWaterColor(4159204)
                .setWaterFogColor(329011)
                .setFogColor(3344392)
                .withSkyColor(getSkyColorFromTemp(2.0F));

        BiomeGenerationSettings.Builder settings = new BiomeGenerationSettings.Builder()
                .withSurfaceBuilder(ConfiguredSurfaceRegistry.FERVID_JUNGLE)
                .withStructure(StructureFeatures.RUINED_PORTAL_NETHER)
                .withStructure(StructureFeatures.FORTRESS)
                .withStructure(StructureFeatures.BASTION_REMNANT)
                .withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f)
                .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.BROWN_MUSHROOM_NETHER)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.RED_MUSHROOM_NETHER)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED);
        DefaultBiomeFeatures.withNormalMushroomGeneration(settings);
        DefaultBiomeFeatures.withCommonNetherBlocks(settings);

        Biome.Builder builder = new Biome.Builder()
                .scale(0.2f)
                .temperature(2.5f)
                .category(Biome.Category.NETHER)
                .precipitation(Biome.RainType.NONE)
                .downfall(0.0f)
                .depth(0.1f)
                .withMobSpawnSettings(spawn.copy())
                .setEffects(ambience.build())
                .withGenerationSettings(settings.build());

        return builder.build();
    }


    private static int getSkyColorFromTemp(float temp) {
        float i = temp / 3.0F;
        i = MathHelper.clamp(i, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - i * 0.05F, 0.5F + i * 0.1F, 1.0F);
    }

    private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> makeSurfaceBuilder(String p_244192_0_, ConfiguredSurfaceBuilder<SC> p_244192_1_) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, p_244192_0_, p_244192_1_);
    }
}
