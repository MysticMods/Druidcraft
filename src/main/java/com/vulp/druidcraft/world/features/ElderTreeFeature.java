package com.vulp.druidcraft.world.features;

import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;

import java.util.Random;
import java.util.Set;

public class ElderTreeFeature extends CustomTreeFeature {

    public ElderTreeFeature() {
        super(BlockRegistry.elder_log, BlockRegistry.elder_wood, BlockRegistry.elder_leaves);
    }

    @Override
    public boolean place(ISeedReader seedReader, Random rand, BlockPos position, Set<BlockPos> placedLogs, Set<BlockPos> placedLeaves, MutableBoundingBox boundsIn) {
        int height = rand.nextInt(2) + 6;
        boolean canGrow = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= 256) {
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
                            if (!isReplaceable(seedReader, blockpos$mutableblockpos.setPos(cx, cy, cz))) {
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
            } else if (isDirt(seedReader.getBlockState(position.down()).getBlock()) && position.getY() < seedReader.func_234938_ad_() - height - 1) {
                int posX = position.getX();
                int posZ = position.getZ();
                int posY = 0;
                int logSide1 = rand.nextInt(3);
                int logSide2 = rand.nextInt(3);
                if (logSide2 == logSide1) {
                    logSide2 = rand.nextInt(3);
                    if (logSide2 == logSide1) {
                        logSide2 = 4;
                    }
                }

                for (int base = 0; base < height; ++base) {
                    int currentY = position.getY() + base;

                    BlockPos blockpos = new BlockPos(posX, currentY, posZ);
                    if (isAirOrLeaves(seedReader, blockpos) || seedReader.getBlockState(blockpos).getBlock() instanceof SaplingBlock) {
                        if (base == 0) {
                            if (rand.nextBoolean()) {
                                if (isReplaceable(seedReader, blockpos.north().down())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.north().down(), boundsIn, true);
                                } else if (isReplaceable(seedReader, blockpos.north())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.north(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(seedReader, blockpos.east().down())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.east().down(), boundsIn, true);
                                } else if (isReplaceable(seedReader, blockpos.east())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.east(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(seedReader, blockpos.south().down())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.south().down(), boundsIn, true);
                                } else if (isReplaceable(seedReader, blockpos.south())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.south(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(seedReader, blockpos.west().down())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.west().down(), boundsIn, true);
                                } else if (isReplaceable(seedReader, blockpos.west())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.west(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(seedReader, blockpos.north().east().down())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.north().east().down(), boundsIn, true);
                                } else if (isReplaceable(seedReader, blockpos.north().east())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.north().east(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(seedReader, blockpos.north().west().down())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.north().west().down(), boundsIn, true);
                                } else if (isReplaceable(seedReader, blockpos.north().west())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.north().west(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(seedReader, blockpos.south().east().down())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.south().east().down(), boundsIn, true);
                                } else if (isReplaceable(seedReader, blockpos.south().east())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.south().east(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(seedReader, blockpos.south().west().down())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.south().west().down(), boundsIn, true);
                                } else if (isReplaceable(seedReader, blockpos.south().west())) {
                                    this.placeLogAt(placedLogs, seedReader, blockpos.south().west(), boundsIn, true);
                                }
                            }
                            this.placeLogAt(placedLogs, seedReader, blockpos, boundsIn, false);
                        }

                        if (base <= height - 1) {
                            this.placeLogAt(placedLogs, seedReader, blockpos, boundsIn, false);
                            if (base == height - 2 || base == height - 3) {
                                if (logSide1 == 0) {
                                    this.placeRotatedLog(placedLogs, seedReader, blockpos.north(1), boundsIn, Direction.Axis.Z);
                                }
                                if (logSide1 == 1) {
                                    this.placeRotatedLog(placedLogs, seedReader, blockpos.east(1), boundsIn, Direction.Axis.X);
                                }
                                if (logSide1 == 2) {
                                    this.placeRotatedLog(placedLogs, seedReader, blockpos.south(1), boundsIn, Direction.Axis.Z);
                                }
                                if (logSide1 == 3) {
                                    this.placeRotatedLog(placedLogs, seedReader, blockpos.west(1), boundsIn, Direction.Axis.X);
                                }
                            }
                            if (base == height - 2 || base == height - 3) {
                                if (logSide1 == 0) {
                                    this.placeRotatedLog(placedLogs, seedReader, blockpos.north(1), boundsIn, Direction.Axis.Z);
                                }
                                if (logSide1 == 1) {
                                    this.placeRotatedLog(placedLogs, seedReader, blockpos.east(1), boundsIn, Direction.Axis.X);
                                }
                                if (logSide1 == 2) {
                                    this.placeRotatedLog(placedLogs, seedReader, blockpos.south(1), boundsIn, Direction.Axis.Z);
                                }
                                if (logSide1 == 3) {
                                    this.placeRotatedLog(placedLogs, seedReader, blockpos.west(1), boundsIn, Direction.Axis.X);
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
                                    this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z));
                                }
                                if (rand.nextBoolean() || rand.nextBoolean()) {
                                    this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z));
                                }
                            } else {
                                this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z));
                                this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z));
                            }
                        }
                    }
                }

                blockpos2 = blockpos2.up();

                for (int leafLayer4X = -2; leafLayer4X <= 2; ++leafLayer4X) {
                    for (int leafLayer4Z = -2; leafLayer4Z <= 2; ++leafLayer4Z) {
                        if (!((leafLayer4X == -2 || leafLayer4X == 2) && (leafLayer4Z == -2 || leafLayer4Z == 2))) {
                            this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer4X, 0, leafLayer4Z));
                        } else {
                            if (rand.nextBoolean()) {
                                this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer4X, 0, leafLayer4Z));
                            }
                        }
                    }
                }

                blockpos2 = blockpos2.up();

                for (int leafLayer5X = -1; leafLayer5X <= 1; ++leafLayer5X) {
                    for (int leafLayer5Z = -1; leafLayer5Z <= 1; ++leafLayer5Z) {
                        if (!((leafLayer5X == -1 || leafLayer5X == 1) && (leafLayer5Z == -1 || leafLayer5Z == 1)))
                            this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer5X, 0, leafLayer5Z));
                    }
                }

                blockpos2 = blockpos2.down(3);
                for (int leafLayer2X = -3; leafLayer2X <= 3; ++leafLayer2X) {
                    for (int leafLayer2Z = -3; leafLayer2Z <= 3; ++leafLayer2Z) {
                        if (!((leafLayer2X == -3 || leafLayer2X == 3) && (leafLayer2Z == -3 || leafLayer2Z == 3)))
                            this.placeLeafAt(placedLeaves, seedReader, blockpos2.add(leafLayer2X, 0, leafLayer2Z));
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

}
