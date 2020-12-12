package com.vulp.druidcraft.world.biomes;

import com.vulp.druidcraft.registry.ConfiguredFeatureRegistry;
import com.vulp.druidcraft.registry.ConfiguredSurfaceRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class DarkwoodForestBiome {
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
    settings.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ConfiguredFeatureRegistry.boulder.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242732_c(2));
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

  private static int getSkyColorFromTemp(float temp) {
    float i = temp / 3.0F;
    i = MathHelper.clamp(i, -1.0F, 1.0F);
    return MathHelper.hsvToRGB(0.62222224F - i * 0.05F, 0.5F + i * 0.1F, 1.0F);
  }
}
