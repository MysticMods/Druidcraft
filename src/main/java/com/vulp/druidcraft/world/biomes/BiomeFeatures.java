package com.vulp.druidcraft.world.biomes;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;

public class BiomeFeatures {

    public static Random rand = new Random();
    public static TreeFeatureConfig darkwood_tree_feature;
    public static BaseTreeFeatureConfig darkwood_bush_feature;
    public static HugeTreeFeatureConfig mega_darkwood_tree_feature;

/*    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        registry.register(darkwood_tree.setRegistryName(Druidcraft.MODID, "darkwood_tree"));
    }*/

    // Custom biome features.
    public static void addDarkwoodTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.MEGA_SPRUCE_TREE.withConfiguration(mega_darkwood_tree_feature).withChance(0.5F), Feature.NORMAL_TREE.withConfiguration(darkwood_tree_feature).withChance(0.5F)), Feature.NORMAL_TREE.withConfiguration(darkwood_tree_feature))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(12, 0.3F, 3))));
    }

    public static void addDarkwoodShrubs(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.JUNGLE_GROUND_BUSH.withConfiguration(darkwood_bush_feature).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(5, 0.3F, 2))));
    }
}