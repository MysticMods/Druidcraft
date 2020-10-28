package com.vulp.druidcraft.world.biomes;

import com.google.common.collect.ImmutableList;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.FeatureRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BiomeFeatures {

    public static Random rand = new Random();
    public static final BaseTreeFeatureConfig darkwood_tree_feature = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.darkwood_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new SpruceFoliagePlacer(2, 1, 0, 2, 1, 1), new StraightTrunkPlacer(5, 2, 1), new TwoLayerFeature(2, 0, 2)).setIgnoreVines().build();
    public static final BaseTreeFeatureConfig mega_darkwood_tree_feature = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.darkwood_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new MegaPineFoliagePlacer(0, 0, 0, 0, 4, 13), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2)).setIgnoreVines().build();
    public static final BaseTreeFeatureConfig darkwood_bush_feature = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.darkwood_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new BlobFoliagePlacer(2, 0, 0, 0, 3), new StraightTrunkPlacer(4, 8, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build();
    public static final BaseTreeFeatureConfig empty = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()), new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()), new BlobFoliagePlacer(0, 0, 0, 0, 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0)).build();

/*    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        registry.register(darkwood_tree.setRegistryName(Druidcraft.MODID, "darkwood_tree"));
    }*/

    // Custom biome features.
    public static void addDarkwoodTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.field_236291_c_.withConfiguration(mega_darkwood_tree_feature).withChance(0.5F), Feature.field_236291_c_.withConfiguration(darkwood_tree_feature).withChance(0.5F)), Feature.field_236291_c_.withConfiguration(darkwood_tree_feature))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(12, 0.3F, 3))));
    }

    public static void addDarkwoodShrubs(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(darkwood_bush_feature).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(5, 0.3F, 2))));
    }

    public static void addElderTree(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureRegistry.elder_tree_feature.withConfiguration(empty).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.01F, 1))));
    }
}