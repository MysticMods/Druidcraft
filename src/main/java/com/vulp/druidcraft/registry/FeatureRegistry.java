package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.world.biomes.BiomeFeatures;
import com.vulp.druidcraft.world.features.ElderTreeFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class FeatureRegistry {

    public static BlockClusterFeatureConfig blueberry_bush_feature;
    public static BlockClusterFeatureConfig lavender_feature;
    public static Feature<BaseTreeFeatureConfig> elder_tree_feature;

    public static <V extends R, R extends IForgeRegistryEntry<R>> V register(IForgeRegistry<R> registry, V feature, String name) {
        ResourceLocation id = new ResourceLocation(Druidcraft.MODID, name);
        feature.setRegistryName(id);
        registry.register(feature);
        return feature;
    }

    public static void spawnFeatures() {
        addBlueberryBushes();
        addElderTrees();
        addLavenderPatches();
    }

    // Vanilla / Multi biome features.
    public static void addElderTrees() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if ((BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MOUNTAIN)) && BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.WET)) {
                BiomeFeatures.addElderTree(biome);
            }
            else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MOUNTAIN)) {
                BiomeFeatures.addElderTree(biome);
            }
        }
    }

    public static void addBlueberryBushes() {
        for (Biome biome : ForgeRegistries.BIOMES)
            if ((BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS)) && !BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.CONIFEROUS))
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(blueberry_bush_feature).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(12))));
    }

    public static void addLavenderPatches() {
        for (Biome biome : ForgeRegistries.BIOMES)
            if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST))
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(lavender_feature).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
    }

}
