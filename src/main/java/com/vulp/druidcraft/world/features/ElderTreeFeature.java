package com.vulp.druidcraft.world.features;

import com.mojang.serialization.Codec;
import com.vulp.druidcraft.blocks.WoodBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;

public class ElderTreeFeature extends TreeFeature {
  public static Random random = new Random();

  public ElderTreeFeature(Codec<BaseTreeFeatureConfig> codec) {
    super(codec);
    /* super(BlockRegistry.elder_log, BlockRegistry.elder_wood, BlockRegistry.elder_leaves);*/
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

  @Override
  public boolean place(IWorldGenerationReader seedReader, Random rand, BlockPos position, Set<BlockPos> placedLogs, Set<BlockPos> placedLeaves, MutableBoundingBox boundsIn, BaseTreeFeatureConfig config) {
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
                  this.placeLogAt(placedLogs, seedReader, blockpos.north().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.north())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.north(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.east().down())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.east().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.east())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.east(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.south().down())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.south().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.south())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.south(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.west().down())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.west().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.west())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.west(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.north().east().down())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.north().east().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.north().east())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.north().east(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.north().west().down())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.north().west().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.north().west())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.north().west(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.south().east().down())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.south().east().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.south().east())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.south().east(), true, config);
                }
              }
              if (rand.nextBoolean()) {
                if (isReplaceableAt(seedReader, blockpos.south().west().down())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.south().west().down(), true, config);
                } else if (isReplaceableAt(seedReader, blockpos.south().west())) {
                  this.placeLogAt(placedLogs, seedReader, blockpos.south().west(), true, config);
                }
              }
              this.placeLogAt(placedLogs, seedReader, blockpos, false, config);
            }

            if (base <= height - 1) {
              this.placeLogAt(placedLogs, seedReader, blockpos, false, config);
              if (base == height - 2 || base == height - 3) {
                if (logSide1 == 0) {
                  this.placeRotatedLog(placedLogs, seedReader, blockpos.north(1), Direction.Axis.Z, config);
                }
                if (logSide1 == 1) {
                  this.placeRotatedLog(placedLogs, seedReader, blockpos.east(1), Direction.Axis.X, config);
                }
                if (logSide1 == 2) {
                  this.placeRotatedLog(placedLogs, seedReader, blockpos.south(1), Direction.Axis.Z, config);
                }
                if (logSide1 == 3) {
                  this.placeRotatedLog(placedLogs, seedReader, blockpos.west(1), Direction.Axis.X, config);
                }
              }
              if (base == height - 2 || base == height - 3) {
                if (logSide1 == 0) {
                  this.placeRotatedLog(placedLogs, seedReader, blockpos.north(1), Direction.Axis.Z, config);
                }
                if (logSide1 == 1) {
                  this.placeRotatedLog(placedLogs, seedReader, blockpos.east(1), Direction.Axis.X, config);
                }
                if (logSide1 == 2) {
                  this.placeRotatedLog(placedLogs, seedReader, blockpos.south(1), Direction.Axis.Z, config);
                }
                if (logSide1 == 3) {
                  this.placeRotatedLog(placedLogs, seedReader, blockpos.west(1), Direction.Axis.X, config);
                }
              }
            }
            posY = currentY;
          }
        }

        BlockPos blockpos2 = new BlockPos(posX, posY, posZ).down();
        for (int leafLayer1and3X = -3; leafLayer1and3X <= 3; ++leafLayer1and3X) {
          for (int leafLayer1and3Z = -3; leafLayer1and3Z <= 3; ++leafLayer1and3Z) {
            if (!((leafLayer1and3X == 3 || leafLayer1and3X == -3) && (leafLayer1and3Z == 3 || leafLayer1and3Z == -3))) {
              if ((leafLayer1and3X == -3 && (leafLayer1and3Z == -2 || leafLayer1and3Z == 2)) || (leafLayer1and3X == 3 && (leafLayer1and3Z == -2 || leafLayer1and3Z == 2)) || (leafLayer1and3Z == -3 && (leafLayer1and3X == -2 || leafLayer1and3X == 2)) || (leafLayer1and3Z == 3 && (leafLayer1and3X == -2 || leafLayer1and3X == 2))) {
                if (rand.nextBoolean() || rand.nextBoolean()) {
                  this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z), config);
                }
                if (rand.nextBoolean() || rand.nextBoolean()) {
                  this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z), config);
                }
              } else {
                this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z), config);
                this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z), config);
              }
            }
          }
        }

        blockpos2 = blockpos2.up();

        for (int leafLayer4X = -2; leafLayer4X <= 2; ++leafLayer4X) {
          for (int leafLayer4Z = -2; leafLayer4Z <= 2; ++leafLayer4Z) {
            if (!((leafLayer4X == -2 || leafLayer4X == 2) && (leafLayer4Z == -2 || leafLayer4Z == 2))) {
              this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer4X, 0, leafLayer4Z), config);
            } else {
              if (rand.nextBoolean()) {
                this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer4X, 0, leafLayer4Z), config);
              }
            }
          }
        }

        blockpos2 = blockpos2.up();

        for (int leafLayer5X = -1; leafLayer5X <= 1; ++leafLayer5X) {
          for (int leafLayer5Z = -1; leafLayer5Z <= 1; ++leafLayer5Z) {
            if (!((leafLayer5X == -1 || leafLayer5X == 1) && (leafLayer5Z == -1 || leafLayer5Z == 1)))
              this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer5X, 0, leafLayer5Z), config);
          }
        }

        blockpos2 = blockpos2.down(3);
        for (int leafLayer2X = -3; leafLayer2X <= 3; ++leafLayer2X) {
          for (int leafLayer2Z = -3; leafLayer2Z <= 3; ++leafLayer2Z) {
            if (!((leafLayer2X == -3 || leafLayer2X == 3) && (leafLayer2Z == -3 || leafLayer2Z == 3)))
              this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer2X, 0, leafLayer2Z), config);
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

  private void placeRotatedLog(Set<BlockPos> setPos, IWorldGenerationReader reader, BlockPos pos, Direction.Axis setAxis, BaseTreeFeatureConfig config) {
    this.setElderLog(reader, pos, setPos, false, setAxis, config);
  }

  private void placeLogAt(Set<BlockPos> setPos, IWorldGenerationReader reader, BlockPos pos, boolean isBaseWood, BaseTreeFeatureConfig config) {
    this.setElderLog(reader, pos, setPos, isBaseWood, null, config);
  }

  private void placeLeafAt(Set<BlockPos> set, IWorldGenerationReader reader, BlockPos pos, BaseTreeFeatureConfig config) {
    this.setElderLeaf(reader, random, pos, set, config);
  }

  private void setElderLeaf(IWorldGenerationReader world, Random random, BlockPos pos, Set<BlockPos> positions, BaseTreeFeatureConfig config) {
    if (isAirOrLeavesAt(world, pos) || !world.hasBlockState(pos, (state) -> state.getMaterial() == Material.TALL_PLANTS) || !world.hasBlockState(pos, (state) -> state.isIn(Blocks.WATER))) {
      this.setBlockState(world, pos, config.leavesProvider.getBlockState(random, pos));
      positions.add(pos.toImmutable());
    }
  }

  private void setElderLog(IWorldGenerationReader world, BlockPos pos, Set<BlockPos> logs, boolean isBaseWood, @Nullable Direction.Axis setAxis, BaseTreeFeatureConfig config) {
    if (isAirOrLeavesAt(world, pos) || !world.hasBlockState(pos, (state) -> state.getMaterial() == Material.TALL_PLANTS) || !world.hasBlockState(pos, (state) -> state.isIn(Blocks.WATER))) {
      BlockState blockType = config.trunkProvider.getBlockState(random, pos);
      // TODO: Fix config
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
      logs.add(pos.toImmutable());
    }
  }
}
