package com.vulp.druidcraft.registry;

import com.google.common.collect.ImmutableList;
import com.vulp.druidcraft.config.WorldGenConfig;
import com.vulp.druidcraft.world.config.BlockStateRadiusFeatureConfig;
import com.vulp.druidcraft.world.config.ElderTreeFeatureConfig;
import com.vulp.druidcraft.world.config.FeatureConfigurations;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

public class ConfiguredFeatureRegistry {
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> darkwood_tree = register("darkwood_tree", Feature.TREE.withConfiguration(FeatureConfigurations.Trees.darkwood_tree));
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> mega_darkwood_tree = register("mega_darkwood_tree", Feature.TREE.withConfiguration(FeatureConfigurations.Trees.mega_darkwood_tree));
  public static ConfiguredFeature<ElderTreeFeatureConfig, ?> elder_tree = register("elder_tree", FeatureRegistry.elder_tree.withConfiguration(FeatureConfigurations.Trees.elder_tree));

  public static ConfiguredFeature<?, ?> darkwood_bush = register("darkwood_bush", Feature.TREE.withConfiguration(FeatureConfigurations.Trees.darkwood_bush));
  public static ConfiguredFeature<?, ?> blueberry_bush = register("blueberry_bush", Feature.RANDOM_PATCH.withConfiguration(FeatureConfigurations.Bushes.blueberry_bush));
  public static ConfiguredFeature<?, ?> lavender = register("lavender", Feature.FLOWER.withConfiguration(FeatureConfigurations.Bushes.lavender).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2).chance(10));


  public static ConfiguredFeature<?, ?> darkwood_trees_feature = register("darkwood_trees", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(mega_darkwood_tree.withChance(0.5F), ConfiguredFeatureRegistry.darkwood_tree.withChance(0.5F)), darkwood_tree)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(12, 0.3F, 3))));
  public static ConfiguredFeature<?, ?> darkwood_bushes_feature = register("darkwood_bushes", darkwood_bush.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(5, 0.1F, 2))));

  public static ConfiguredFeature<?, ?> amber = register("amber_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.amber_ore.getDefaultState(), WorldGenConfig.amber_size.get())));
  public static ConfiguredFeature<?, ?> moonstone = register("moonstone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.moonstone_ore.getDefaultState(), WorldGenConfig.moonstone_size.get())));
  public static ConfiguredFeature<?, ?> fiery_glass = register("fiery_glass", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.fiery_glass_ore.getDefaultState(), WorldGenConfig.fiery_glass_size.get())));
  public static ConfiguredFeature<?, ?> rockroot = register("rockroot", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistry.rockroot_ore.getDefaultState(), WorldGenConfig.rockroot_size.get())));

  public static ConfiguredFeature<?, ?> boulder = register("boulder", FeatureRegistry.taiga_rock.withConfiguration(new BlockStateRadiusFeatureConfig(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 0)));

  // TODO: Move these to a more reasonable location
  // Custom biome features.
  public static void addDarkwoodTrees(BiomeGenerationSettings.Builder settings) {
    settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, darkwood_trees_feature);
  }

  public static void addDarkwoodBushes(BiomeGenerationSettings.Builder settings) {
    settings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, darkwood_bushes_feature);
  }

  public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String p_243968_0_, ConfiguredFeature<FC, ?> p_243968_1_) {
    return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, p_243968_0_, p_243968_1_);
  }

}