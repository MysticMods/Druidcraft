package com.vulp.druidcraft.registry;

import com.google.common.collect.ImmutableList;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.trees.ElderTree;
import com.vulp.druidcraft.world.features.ElderTreeFeature;
import com.vulp.druidcraft.world.features.ElderTreeFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class FeatureRegistry {

    public static Feature<ElderTreeFeatureConfig> elder_tree;

    public static BaseTreeFeatureConfig darkwood_bush_feature;
    public static ElderTreeFeatureConfig elder_tree_feature;
    public static TreeFeatureConfig darkwood_tree_feature;
    public static HugeTreeFeatureConfig giant_darkwood_tree_feature;
    public static BlockClusterFeatureConfig blueberry_bush_feature;
    public static BlockClusterFeatureConfig lavender_feature;

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
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, elder_tree.withConfiguration(elder_tree_feature).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.015F, 1))));
            }
            else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MOUNTAIN)) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, elder_tree.withConfiguration(elder_tree_feature).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.01F, 1))));            }
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
