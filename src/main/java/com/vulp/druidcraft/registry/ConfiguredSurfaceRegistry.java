package com.vulp.druidcraft.registry;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ConfiguredSurfaceRegistry {
  public static ConfiguredSurfaceBuilder<?> DARKWOOD_FOREST = register("darkwood_forest", new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.SAND.getDefaultState())));
  public static ConfiguredSurfaceBuilder<?> FERVID_JUNGLE = register("fervid_jungle", new ConfiguredSurfaceBuilder<>(SurfaceBuilder.NETHER, new SurfaceBuilderConfig(BlockRegistry.overgrown_nylium.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState())));

  public static <FC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<FC> register(String p_243968_0_, ConfiguredSurfaceBuilder<FC> p_243968_1_) {
    return Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, p_243968_0_, p_243968_1_);
  }

}