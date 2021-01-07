package com.vulp.druidcraft.world.features;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.vulp.druidcraft.blocks.WoodBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.world.config.ElderTreeFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ElderTreeFeature extends Feature<ElderTreeFeatureConfig> {
  public static Random random = new Random();

  public ElderTreeFeature(Codec<ElderTreeFeatureConfig> codec) {
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
    return isAirOrLeavesAt(p_236404_0_, p_236404_1_) || isTallPlantAt(p_236404_0_, p_236404_1_) || isWaterAt(p_236404_0_, p_236404_1_);
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

  private static boolean isDirtOrFarmlandAt(IWorldGenerationBaseReader p_236418_0_, BlockPos p_236418_1_) {
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
  
  @Override
  public boolean generate(ISeedReader seedReader, ChunkGenerator generator, Random rand, BlockPos position, ElderTreeFeatureConfig config) {
    int height = rand.nextInt(2) + 6;
    boolean canGrow = true;

    if (position.getY() >= 1 && position.getY() + height + 1 <= getMaxHeight(seedReader)) {
      for (int cy = position.getY(); cy <= position.getY() + 1 + height; ++cy) {
        int k = 1;

        if (cy == position.getY()) {
          k = 0;
        }

        if (cy >= position.getY() + 1 + height - 2) {
          k = 2;
        }

        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

        for (int cx = position.getX() - k; cx <= position.getX() + k && canGrow; ++cx) {
          for (int cz = position.getZ() - k; cz <= position.getZ() + k && canGrow; ++cz) {
            if (cy >= 0 && cy < 256) {
              if (!func_236410_c_(seedReader, blockpos$mutableblockpos.setPos(cx, cy, cz))) {
                canGrow = false;
              }
            } else {
              canGrow = false;
            }
          }
        }
      }

      if (!canGrow) {
        return false;
      } else if (isDirtOrGrass(seedReader, position.down()) && position.getY() < getMaxHeight(seedReader) - height - 1) {
        int posX = position.getX();
        int posZ = position.getZ();
        int posY = 0;

        int logSide1 = rand.nextInt(3);

        for (int base = 0; base < height; ++base) {
          int currentY = position.getY() + base;

          BlockPos blockpos = new BlockPos(posX, currentY, posZ);
          if (isAirOrLeavesAt(seedReader, blockpos) || seedReader.hasBlockState(blockpos, (state) -> state.getBlock() instanceof SaplingBlock)) {
            if (base == 0) {
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.north().down())) {
                  this.placeLogAt(seedReader, blockpos.north().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.north())) {
                  this.placeLogAt(seedReader, blockpos.north(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.east().down())) {
                  this.placeLogAt(seedReader, blockpos.east().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.east())) {
                  this.placeLogAt(seedReader, blockpos.east(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.south().down())) {
                  this.placeLogAt(seedReader, blockpos.south().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.south())) {
                  this.placeLogAt(seedReader, blockpos.south(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.west().down())) {
                  this.placeLogAt(seedReader, blockpos.west().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.west())) {
                  this.placeLogAt(seedReader, blockpos.west(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.north().east().down())) {
                  this.placeLogAt(seedReader, blockpos.north().east().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.north().east())) {
                  this.placeLogAt(seedReader, blockpos.north().east(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.north().west().down())) {
                  this.placeLogAt(seedReader, blockpos.north().west().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.north().west())) {
                  this.placeLogAt(seedReader, blockpos.north().west(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.south().east().down())) {
                  this.placeLogAt(seedReader, blockpos.south().east().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.south().east())) {
                  this.placeLogAt(seedReader, blockpos.south().east(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.south().west().down())) {
                  this.placeLogAt(seedReader, blockpos.south().west().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.south().west())) {
                  this.placeLogAt(seedReader, blockpos.south().west(), true, config);
                }
              }
              this.placeLogAt(seedReader, blockpos, false, config);
            }

            if (base <= height - 1) {
              this.placeLogAt(seedReader, blockpos, false, config);
              if (base == height - 2 || base == height - 3) {
                if (logSide1 == 0) {
                  this.placeRotatedLog(seedReader, blockpos.north(1), Direction.Axis.Z, config);
                }
                if (logSide1 == 1) {
                  this.placeRotatedLog(seedReader, blockpos.east(1), Direction.Axis.X, config);
                }
                if (logSide1 == 2) {
                  this.placeRotatedLog(seedReader, blockpos.south(1), Direction.Axis.Z, config);
                }
                if (logSide1 == 3) {
                  this.placeRotatedLog(seedReader, blockpos.west(1), Direction.Axis.X, config);
                }
              }
              if (base == height - 2 || base == height - 3) {
                if (logSide1 == 0) {
                  this.placeRotatedLog(seedReader, blockpos.north(1), Direction.Axis.Z, config);
                }
                if (logSide1 == 1) {
                  this.placeRotatedLog(seedReader, blockpos.east(1), Direction.Axis.X, config);
                }
                if (logSide1 == 2) {
                  this.placeRotatedLog(seedReader, blockpos.south(1), Direction.Axis.Z, config);
                }
                if (logSide1 == 3) {
                  this.placeRotatedLog(seedReader, blockpos.west(1), Direction.Axis.X, config);
                }
              }
            }
            posY = currentY;
          }
        }

        //LEAVES
        BlockPos blockpos2 = new BlockPos(posX, posY, posZ).down();
        for (int leafLayer1and3X = -3; leafLayer1and3X <= 3; ++leafLayer1and3X) {
          for (int leafLayer1and3Z = -3; leafLayer1and3Z <= 3; ++leafLayer1and3Z) {
            if (!((leafLayer1and3X == 3 || leafLayer1and3X == -3) && (leafLayer1and3Z == 3 || leafLayer1and3Z == -3))) {
              if ((leafLayer1and3X == -3 && (leafLayer1and3Z == -2 || leafLayer1and3Z == 2)) || (leafLayer1and3X == 3 && (leafLayer1and3Z == -2 || leafLayer1and3Z == 2)) || (leafLayer1and3Z == -3 && (leafLayer1and3X == -2 || leafLayer1and3X == 2)) || (leafLayer1and3Z == 3 && (leafLayer1and3X == -2 || leafLayer1and3X == 2))) {
                if (rand.nextBoolean() || rand.nextBoolean()) {
                  this.placeLeafAt(seedReader, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z), config);
                }
                if (rand.nextBoolean() || rand.nextBoolean()) {
                  this.placeLeafAt(seedReader, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z), config);
                }
              } else {
                this.placeLeafAt(seedReader, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z), config);
                this.placeLeafAt(seedReader, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z), config);
              }
            }
          }
        }

        blockpos2 = blockpos2.up();

        for (int leafLayer4X = -2; leafLayer4X <= 2; ++leafLayer4X) {
          for (int leafLayer4Z = -2; leafLayer4Z <= 2; ++leafLayer4Z) {
            if (!((leafLayer4X == -2 || leafLayer4X == 2) && (leafLayer4Z == -2 || leafLayer4Z == 2))) {
              this.placeLeafAt(seedReader, blockpos2.add(leafLayer4X, 0, leafLayer4Z), config);
            } else {
              if (rand.nextBoolean()) {
                this.placeLeafAt(seedReader, blockpos2.add(leafLayer4X, 0, leafLayer4Z), config);
              }
            }
          }
        }

        blockpos2 = blockpos2.up();

        for (int leafLayer5X = -1; leafLayer5X <= 1; ++leafLayer5X) {
          for (int leafLayer5Z = -1; leafLayer5Z <= 1; ++leafLayer5Z) {
            if (!((leafLayer5X == -1 || leafLayer5X == 1) && (leafLayer5Z == -1 || leafLayer5Z == 1)))
              this.placeLeafAt(seedReader, blockpos2.add(leafLayer5X, 0, leafLayer5Z), config);
          }
        }

        blockpos2 = blockpos2.down(3);
        for (int leafLayer2X = -3; leafLayer2X <= 3; ++leafLayer2X) {
          for (int leafLayer2Z = -3; leafLayer2Z <= 3; ++leafLayer2Z) {
            if (!((leafLayer2X == -3 || leafLayer2X == 3) && (leafLayer2Z == -3 || leafLayer2Z == 3)))
              this.placeLeafAt(seedReader, blockpos2.add(leafLayer2X, 0, leafLayer2Z), config);
          }
        }
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  private void placeRotatedLog(IWorldGenerationReader reader, BlockPos pos, Direction.Axis setAxis, ElderTreeFeatureConfig config) {
    this.setElderLog(reader, pos, false, setAxis, config);
  }

  private void placeLogAt(IWorldGenerationReader reader, BlockPos pos, boolean isBaseWood, ElderTreeFeatureConfig config) {
    this.setElderLog(reader, pos, isBaseWood, null, config);
  }

  private void placeLeafAt(IWorldGenerationReader reader, BlockPos pos, ElderTreeFeatureConfig config) {
    this.setElderLeaf(reader, random, pos, config);
  }

  private void setElderLeaf(IWorldGenerationReader world, Random random, BlockPos pos, ElderTreeFeatureConfig config) {
    if (isAirOrLeavesAt(world, pos)) {
      this.setBlockState(world, pos, config.leavesProvider.getBlockState(random, pos));
    }
  }

  private void setElderLog(IWorldGenerationReader world, BlockPos pos, boolean isBaseWood, @Nullable Direction.Axis setAxis, ElderTreeFeatureConfig config) {
    if (isAirOrLeavesAt(world, pos) || !world.hasBlockState(pos, (state) -> state.getMaterial() == Material.TALL_PLANTS) || !world.hasBlockState(pos, (state) -> state.isIn(Blocks.WATER))) {
      BlockState blockType = config.trunkProvider.getBlockState(random, pos);
      BlockState baseType = BlockRegistry.elder_wood.getDefaultState().with(WoodBlock.dropSelf, false);
      if (setAxis == null) {
        Random rand = new Random();
        if (isBaseWood) {
          if (rand.nextInt(3) == 0) {
            blockType = baseType.with(WoodBlock.AXIS, Direction.Axis.X);
          } else if (rand.nextInt(3) == 0) {
            blockType = baseType.with(WoodBlock.AXIS, Direction.Axis.Y);
          } else {
            blockType = baseType.with(WoodBlock.AXIS, Direction.Axis.Z);
          }
        }
      } else {
        blockType = blockType.with(WoodBlock.AXIS, setAxis);
      }
      this.setBlockState(world, pos, blockType);
    }
  }

}
