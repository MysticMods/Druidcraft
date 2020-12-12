package com.vulp.druidcraft.registry;

import com.google.common.collect.ImmutableList;
import com.vulp.druidcraft.config.WorldGenConfig;
import com.vulp.druidcraft.world.config.FeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ConfiguredSurfaceRegistry {
  public static ConfiguredSurfaceBuilder<?> DARKWOOD_FOREST = register("darkwood_forest", new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.SAND.getDefaultState())));

  public static <FC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<FC> register(String p_243968_0_, ConfiguredSurfaceBuilder<FC> p_243968_1_) {
    return Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, p_243968_0_, p_243968_1_);
  }

}