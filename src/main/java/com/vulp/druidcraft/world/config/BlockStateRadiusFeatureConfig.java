package com.vulp.druidcraft.world.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlockStateRadiusFeatureConfig implements IFeatureConfig {
  public static final Codec<BlockStateRadiusFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockState.CODEC.fieldOf("state").forGetter((s) -> s.state), Codec.INT.fieldOf("startRadius").forGetter((s) -> s.startRadius)).apply(instance, BlockStateRadiusFeatureConfig::new));
  public final BlockState state;
  public final int startRadius;

  public BlockStateRadiusFeatureConfig(BlockState p_i225831_1_, int startRadius) {
    this.state = p_i225831_1_;
    this.startRadius = startRadius;
  }
}
