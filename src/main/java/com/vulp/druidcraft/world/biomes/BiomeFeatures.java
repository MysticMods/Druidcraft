package com.vulp.druidcraft.world.biomes;

import com.google.common.collect.ImmutableList;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import java.util.Random;

public class BiomeFeatures {

    public static Random rand = new Random();
    public static final BaseTreeFeatureConfig darkwood_tree_feature_config = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.darkwood_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)), new StraightTrunkPlacer(5, 2, 1), new TwoLayerFeature(2, 0, 2)).setIgnoreVines().build();
    public static final BaseTreeFeatureConfig mega_darkwood_tree_feature_config = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.darkwood_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new MegaPineFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), FeatureSpread.func_242253_a(11, 4)), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2)).setIgnoreVines().build();
    public static final BaseTreeFeatureConfig darkwood_bush_feature_config = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.darkwood_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0)).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).setIgnoreVines().build();
    public static final BaseTreeFeatureConfig empty_tree_config = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()), new SimpleBlockStateProvider(Blocks.AIR.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0)).build();

    public static ConfiguredFeature<?, ?> darkwood_trees_feature;
    public static ConfiguredFeature<?, ?> darkwood_bushes_feature;

/*    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        registry.register(darkwood_tree.setRegistryName(Druidcraft.MODID, "darkwood_tree"));
    }*/

    // Custom biome features.
    public static void addDarkwoodTrees(BiomeGenerationSettings.Builder settings) {
        settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, darkwood_trees_feature);
    }

    public static void addDarkwoodBushes(BiomeGenerationSettings.Builder settings) {
        settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, darkwood_bushes_feature);
    }

}