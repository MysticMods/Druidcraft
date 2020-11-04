package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.world.biomes.BiomeFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeRegistry {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister
            .create(ForgeRegistries.BIOMES, Druidcraft.MODID);

    // -------------------------------------------------------------------------------------------------------------------

    public static final RegistryKey<Biome> darkwood_forest_key = makeBiomeKey("darkwood_forest");

    public static Biome darkwood_forest = biomeBuilder(0.2F, 0.25F, Biome.Category.TAIGA, 0.3F, Biome.RainType.RAIN, defaultSpawnInfo().isValidSpawnBiomeForPlayer().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 8, 2, 4)), false, 0.8F,
            biomeAmbienceBuilder(12638463, 0x305B2A, 0x5786E5, 0x38577F, getSkyColorFromTemp(0.25F), 0x508948), darkwoodForestSettings(), new ResourceLocation(Druidcraft.MODID, "darkwood_forest"));

    public static BiomeGenerationSettings darkwoodForestSettings() {
        net.minecraft.world.biome.BiomeGenerationSettings.Builder settings = (new net.minecraft.world.biome.BiomeGenerationSettings.Builder()).withSurfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.SAND.getDefaultState())));
        settings.withStructure(StructureFeatures.PILLAGER_OUTPOST);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(settings);
        settings.withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withCavesAndCanyons(settings);
        DefaultBiomeFeatures.withLavaAndWaterLakes(settings);
        DefaultBiomeFeatures.withMonsterRoom(settings);
        DefaultBiomeFeatures.withLargeFern(settings);
        DefaultBiomeFeatures.withForestRocks(settings);
        DefaultBiomeFeatures.withCommonOverworldBlocks(settings);
        DefaultBiomeFeatures.withOverworldOres(settings);
        DefaultBiomeFeatures.withDisks(settings);
        BiomeFeatures.addDarkwoodTrees(settings);
        BiomeFeatures.addDarkwoodBushes(settings);
        DefaultBiomeFeatures.withDefaultFlowers(settings);
        DefaultBiomeFeatures.withTaigaGrassVegetation(settings);
        DefaultBiomeFeatures.withNormalMushroomGeneration(settings);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(settings);
        DefaultBiomeFeatures.withLavaAndWaterSprings(settings);
        DefaultBiomeFeatures.withSparseBerries(settings);
        return settings.build();
    }

    // -------------------------------------------------------------------------------------------------------------------

    public static void addBiomes() {
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(darkwood_forest_key, 1));

        BiomeDictionary.addTypes(darkwood_forest_key, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL);
    }

    private static int getSkyColorFromTemp(float temp) {
        float i = temp / 3.0F;
        i = MathHelper.clamp(i, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - i * 0.05F, 0.5F + i * 0.1F, 1.0F);
    }

    public static Biome biomeBuilder(float scale, float temp, Biome.Category category, float depth, Biome.RainType rainType, MobSpawnInfo.Builder mobSpawnInfo, boolean playerSpawn, float downfall, BiomeAmbience ambience, BiomeGenerationSettings genSettings, ResourceLocation resourceLocation) {
        if (playerSpawn) {
            mobSpawnInfo.isValidSpawnBiomeForPlayer();
        }
        return new Biome.Builder().scale(scale).temperature(temp).category(category).depth(depth).precipitation(rainType).withMobSpawnSettings(mobSpawnInfo.copy()).setEffects(ambience).downfall(downfall).withGenerationSettings(genSettings).build().setRegistryName(resourceLocation);
    }

    public static BiomeAmbience biomeAmbienceBuilder(int fogColor, int foliageColor, int waterColor, int waterFogColor, int skyColor, int grassColor) {
        return new BiomeAmbience.Builder().setFogColor(fogColor).withFoliageColor(foliageColor).setWaterColor(waterColor).setWaterFogColor(waterFogColor).withSkyColor(skyColor).withGrassColor(grassColor).build();
    }

    public static MobSpawnInfo.Builder defaultSpawnInfo() {
        MobSpawnInfo.Builder spawnInfo = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(spawnInfo);
        DefaultBiomeFeatures.withBatsAndHostiles(spawnInfo);
        return spawnInfo;
    }

    public static RegistryKey<Biome> makeBiomeKey(String name) {
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(Druidcraft.MODID, name));
    }

    static {
        BIOMES.register("darkwood_forest", () -> darkwood_forest);
    }

}
