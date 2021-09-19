package com.vulp.druidcraft.registry;

import com.google.common.collect.ImmutableList;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.config.OreConfig;
import com.vulp.druidcraft.world.config.BlockStateRadiusFeatureConfig;
import com.vulp.druidcraft.world.config.DummyTreeFeatureConfig;
import com.vulp.druidcraft.world.config.FeatureConfigurations;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;

public class ConfiguredFeatureRegistry {

/*  public static final BlockState overgrown_roots_state = BlockRegistry.overgrown_roots.getDefaultState();
  public static final BlockState tall_overgrown_roots_state = BlockRegistry.tall_overgrown_roots.getDefaultState();
  public static final BlockState heartburn_fungus_state = BlockRegistry.heartburn_fungus.getDefaultState();*/
  public static final BlockState crimson_roots_state = Blocks.CRIMSON_ROOTS.getDefaultState();
  public static final BlockState warped_fungus_state = Blocks.WARPED_FUNGUS.getDefaultState();
  public static final BlockState crimson_fungus_state = Blocks.CRIMSON_FUNGUS.getDefaultState();

/*  public static final BlockStateProvidingFeatureConfig glowing_jungle_vegetation_config = new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).addWeightedBlockstate(overgrown_roots_state, 85).addWeightedBlockstate(crimson_roots_state, 1).addWeightedBlockstate(heartburn_fungus_state, 13).addWeightedBlockstate(crimson_fungus_state, 1).addWeightedBlockstate(warped_fungus_state, 1));
  public static final BlockClusterFeatureConfig tall_overgrown_roots_config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(tall_overgrown_roots_state), new DoublePlantBlockPlacer())).tries(96).func_227317_b_().build();
  public static final BlockClusterFeatureConfig overgrown_roots_config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(overgrown_roots_state), new SimpleBlockPlacer())).tries(96).func_227317_b_().build();*/

  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> darkwood_tree = register("darkwood_tree", Feature.TREE.withConfiguration(FeatureConfigurations.Trees.darkwood_tree));
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> mega_darkwood_tree = register("mega_darkwood_tree", Feature.TREE.withConfiguration(FeatureConfigurations.Trees.mega_darkwood_tree));
  public static ConfiguredFeature<DummyTreeFeatureConfig, ?> elder_tree = register("elder_tree", FeatureRegistry.elder_tree.withConfiguration(FeatureConfigurations.Trees.elder_tree));
/*  public static ConfiguredFeature<DummyTreeFeatureConfig, ?> giant_heartburn_fungus = register("huge_heartburn_fungus", FeatureRegistry.giant_heartburn_fungus.withConfiguration(FeatureConfigurations.Trees.giant_heartburn_fungus));*/
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> darkwood_bush = register("darkwood_bush", Feature.TREE.withConfiguration(FeatureConfigurations.Trees.darkwood_bush));

  public static ConfiguredFeature<?, ?> blueberry_bush = register("blueberry_bush", Feature.RANDOM_PATCH.withConfiguration(FeatureConfigurations.Bushes.blueberry_bush).withPlacement(Placement.CHANCE.configure(new ChanceConfig(100))));
  public static ConfiguredFeature<?, ?> lavender = register("lavender", Feature.FLOWER.withConfiguration(FeatureConfigurations.Bushes.lavender).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2).chance(10));
  public static ConfiguredFeature<?, ?> darkwood_trees_feature = register("darkwood_trees", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(mega_darkwood_tree.withChance(0.5F), ConfiguredFeatureRegistry.darkwood_tree.withChance(0.5F)), darkwood_tree)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(12, 0.3F, 3))));
  public static ConfiguredFeature<?, ?> darkwood_bushes_feature = register("darkwood_bushes", darkwood_bush.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(5, 0.1F, 2))));
  public static ConfiguredFeature<?, ?> amber = register("amber_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.amber_ore.getDefaultState(), OreConfig.amber_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().func_242731_b(OreConfig.amber_weight.get()));
  public static ConfiguredFeature<?, ?> moonstone = register("moonstone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.moonstone_ore.getDefaultState(), OreConfig.moonstone_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().func_242731_b(OreConfig.moonstone_weight.get()));
  public static ConfiguredFeature<?, ?> fiery_glass = register("fiery_glass", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.fiery_glass_ore.getDefaultState(), OreConfig.fiery_glass_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 32))).square().func_242731_b(OreConfig.fiery_glass_weight.get()));
  public static ConfiguredFeature<?, ?> rockroot = register("rockroot", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.rockroot_ore.getDefaultState(), OreConfig.rockroot_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(48, 16, 256))).square().func_242731_b(OreConfig.rockroot_weight.get()));
/*  public static ConfiguredFeature<?, ?> nether_fiery_glass = register("nether_fiery_glass", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, BlockRegistry.nether_fiery_glass_ore.getDefaultState(), OreConfig.fiery_glass_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().func_242731_b(OreConfig.fiery_glass_weight.get()));
  public static ConfiguredFeature<?, ?> brightstone = register("brightstone", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, BlockRegistry.brightstone_ore.getDefaultState(), OreConfig.amber_size.get())).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 256))).square().func_242731_b(OreConfig.amber_weight.get()));*/
  public static ConfiguredFeature<?, ?> boulder = register("boulder", FeatureRegistry.taiga_rock.withConfiguration(new BlockStateRadiusFeatureConfig(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 0)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242732_c(2));
/*  public static ConfiguredFeature<?, ?> huge_heartburn_fungi = register("huge_heartburn_fungi", giant_heartburn_fungus.withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(8))));
  public static ConfiguredFeature<?, ?> glowing_jungle_vegetation = register("glowing_jungle_vegetation", Feature.NETHER_FOREST_VEGETATION.withConfiguration(glowing_jungle_vegetation_config).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(5))));
  public static ConfiguredFeature<?, ?> patch_tall_overgrown_roots = register("patch_tall_overgrown_roots", Feature.RANDOM_PATCH.withConfiguration(tall_overgrown_roots_config).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(16));
  public static ConfiguredFeature<?, ?> patch_overgrown_roots = register("patch_overgrown_roots", Feature.RANDOM_PATCH.withConfiguration(overgrown_roots_config).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(16));*/
  public static ConfiguredFeature<?, ?> plains_river_elder_tree = register("plains_river_elder_tree", elder_tree.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.01F, 1))));
  public static ConfiguredFeature<?, ?> forest_elder_tree = register("forest_elder_tree", ConfiguredFeatureRegistry.elder_tree.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.025F, 1))));
/*  public static ConfiguredFeature<?, ?> gaseous_growth_feature = register("gaseous_growths", FeatureRegistry.gaseous_growth.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).range(128).square().func_242731_b(20));*/

  // TODO: Move these to a more reasonable location
  // Custom biome features.
  public static void addDarkwoodTrees(BiomeGenerationSettings.Builder settings) {
    settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, darkwood_trees_feature);
  }

  public static void addDarkwoodBushes(BiomeGenerationSettings.Builder settings) {
    settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, darkwood_bushes_feature);
  }

  public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
    return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Druidcraft.MODID, name), configuredFeature);
  }

}