package com.vulp.druidcraft.world.features;

import com.mojang.serialization.Codec;
import com.vulp.druidcraft.blocks.WoodBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.world.config.DummyTreeFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class DumbTreeFeature extends Feature<DummyTreeFeatureConfig> {

  public static Random random = new Random();
  public static Direction[] cardinals = new Direction[]{ Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

  public DumbTreeFeature(Codec<DummyTreeFeatureConfig> codec) {
    super(codec);
  }

  public static boolean isDirtOrGrass(IWorldGenerationReader world, BlockPos pos) {
    return world.hasBlockState(pos, (state) -> {
      Block block = state.getBlock();
      return isDirt(block) || block == Blocks.FARMLAND;
    });
  }

  public static int getMaxHeight(IWorldGenerationReader reader) {
    return (reader instanceof IServerWorld) ? ((IServerWorld) reader).getWorld().getHeight() : 256;
  }

  public static boolean func_236410_c_(IWorldGenerationBaseReader p_236410_0_, BlockPos p_236410_1_) {
    return isReplaceableAt(p_236410_0_, p_236410_1_) || p_236410_0_.hasBlockState(p_236410_1_, (p_236417_0_) -> {
      return p_236417_0_.isIn(BlockTags.LOGS);
    });
  }

  public static boolean isReplaceableAt(IWorldGenerationBaseReader p_236404_0_, BlockPos p_236404_1_) {
    return isAirOrLeavesAt(p_236404_0_, p_236404_1_) || isTallPlantAt(p_236404_0_, p_236404_1_) || isWaterAt(p_236404_0_, p_236404_1_) || isPlantAt(p_236404_0_, p_236404_1_) || isNetherPlantAt(p_236404_0_, p_236404_1_) || isOceanPlantAt(p_236404_0_, p_236404_1_);
  }

  private static boolean isWaterAt(IWorldGenerationBaseReader p_236416_0_, BlockPos p_236416_1_) {
    return p_236416_0_.hasBlockState(p_236416_1_, (p_236413_0_) -> {
      return p_236413_0_.isIn(Blocks.WATER);
    });
  }

  public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader p_236412_0_, BlockPos p_236412_1_) {
    return p_236412_0_.hasBlockState(p_236412_1_, (p_236411_0_) -> {
      return p_236411_0_.isAir() || p_236411_0_.isIn(BlockTags.LEAVES);
    });
  }

  public static boolean isDirtOrFarmlandAt(IWorldGenerationBaseReader p_236418_0_, BlockPos p_236418_1_) {
    return p_236418_0_.hasBlockState(p_236418_1_, (p_236409_0_) -> {
      Block lvt_1_1_ = p_236409_0_.getBlock();
      return isDirt(lvt_1_1_) || lvt_1_1_ == Blocks.FARMLAND;
    });
  }

  private static boolean isTallPlantAt(IWorldGenerationBaseReader p_236419_0_, BlockPos p_236419_1_) {
    return p_236419_0_.hasBlockState(p_236419_1_, (p_236406_0_) -> {
      Material lvt_1_1_ = p_236406_0_.getMaterial();
      return lvt_1_1_ == Material.TALL_PLANTS;
    });
  }

  private static boolean isPlantAt(IWorldGenerationBaseReader p_236419_0_, BlockPos p_236419_1_) {
    return p_236419_0_.hasBlockState(p_236419_1_, (p_236406_0_) -> {
      Material lvt_1_1_ = p_236406_0_.getMaterial();
      return lvt_1_1_ == Material.PLANTS;
    });
  }

  private static boolean isNetherPlantAt(IWorldGenerationBaseReader p_236419_0_, BlockPos p_236419_1_) {
    return p_236419_0_.hasBlockState(p_236419_1_, (p_236406_0_) -> {
      Material lvt_1_1_ = p_236406_0_.getMaterial();
      return lvt_1_1_ == Material.NETHER_PLANTS;
    });
  }

  private static boolean isOceanPlantAt(IWorldGenerationBaseReader p_236419_0_, BlockPos p_236419_1_) {
    return p_236419_0_.hasBlockState(p_236419_1_, (p_236406_0_) -> {
      Material lvt_1_1_ = p_236406_0_.getMaterial();
      return lvt_1_1_ == Material.OCEAN_PLANT;
    });
  }

  public void placeLeafAt(IWorldGenerationReader reader, BlockPos pos, DummyTreeFeatureConfig config) {
    this.setLeafAt(reader, random, pos, config);
  }

  public void setLeafAt(IWorldGenerationReader world, Random random, BlockPos pos, DummyTreeFeatureConfig config) {
    if (isAirOrLeavesAt(world, pos)) {
      this.setBlockState(world, pos, config.leavesProvider.getBlockState(random, pos));
    }
  }

  public void placeLogAt(IWorldGenerationReader reader, BlockPos pos, DummyTreeFeatureConfig config, boolean forcePlacement) {
    this.setLogAt(reader, pos, config, forcePlacement);
  }

  public void setLogAt(IWorldGenerationReader world, BlockPos pos, DummyTreeFeatureConfig config, boolean forcePlacement) {
    if (isReplaceableAt(world, pos) || forcePlacement) {
      this.setBlockState(world, pos, config.trunkProvider.getBlockState(random, pos));
    }
  }

  public boolean canGrow(BlockPos pos, int height, ISeedReader reader) {
    boolean canGrow = true;
    if (pos.getY() >= 1 && pos.getY() + height + 1 <= getMaxHeight(reader)) {
      for (int cy = pos.getY(); cy <= pos.getY() + 1 + height; ++cy) {
        int k = 1;

        if (cy == pos.getY()) {
          k = 0;
        }

        if (cy >= pos.getY() + 1 + height - 2) {
          k = 2;
        }

        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

        for (int cx = pos.getX() - k; cx <= pos.getX() + k && canGrow; ++cx) {
          for (int cz = pos.getZ() - k; cz <= pos.getZ() + k && canGrow; ++cz) {
            if (cy >= 0 && cy < 256) {
              if (!func_236410_c_(reader, blockpos$mutableblockpos.setPos(cx, cy, cz))) {
                canGrow = false;
              }
            } else {
              canGrow = false;
            }
          }
        }
      }
    }
    return canGrow;
  }

  public Direction getRandomCardinal() {
    return Util.getRandomObject(cardinals, random);
  }

  public Direction getRandomDirection() {
    return Util.getRandomObject(Direction.values(), random);
  }

  @Override
  public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, DummyTreeFeatureConfig config) {
    return false;
  }

}
