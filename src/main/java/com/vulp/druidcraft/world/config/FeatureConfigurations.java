package com.vulp.druidcraft.world.config;

import com.google.common.collect.ImmutableSet;
import com.vulp.druidcraft.blocks.BerryBushBlock;
import com.vulp.druidcraft.blocks.WoodBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class FeatureConfigurations {
    public static class Trees {
        public static final BaseTreeFeatureConfig darkwood_tree = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.darkwood_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)), new StraightTrunkPlacer(5, 2, 1), new TwoLayerFeature(2, 0, 2)).setIgnoreVines().build();
        public static final BaseTreeFeatureConfig mega_darkwood_tree = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.darkwood_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new MegaPineFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), FeatureSpread.func_242253_a(11, 4)), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2)).setIgnoreVines().build();
        public static final BaseTreeFeatureConfig darkwood_bush = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.darkwood_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0)).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).setIgnoreVines().build();
        public static final DummyTreeFeatureConfig elder_tree = new DummyTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.elder_log.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.elder_wood.getDefaultState().with(WoodBlock.dropSelf, false)), new SimpleBlockStateProvider(BlockRegistry.elder_leaves.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.elder_sapling.getDefaultState())).build();
/*        public static final DummyTreeFeatureConfig giant_heartburn_fungus = new DummyTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.heartburn_stem.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.heartburn_hyphae.getDefaultState().with(WoodBlock.dropSelf, false)), new SimpleBlockStateProvider(BlockRegistry.heartburn_cap.getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.heartburn_fungus.getDefaultState())).build();*/
    }

    public static class Bushes {
        public static BlockClusterFeatureConfig blueberry_bush = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.blueberry_bush.getDefaultState().with(BerryBushBlock.AGE, 3)), new SimpleBlockPlacer()).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK.getBlock())).func_227317_b_().build();
        public static BlockClusterFeatureConfig lavender = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.lavender.getDefaultState()), SimpleBlockPlacer.PLACER).tries(64).build();

    }
}
