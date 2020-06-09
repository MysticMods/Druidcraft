package com.vulp.druidcraft.world.features;

import com.mojang.datafixers.Dynamic;
import com.vulp.druidcraft.blocks.WoodBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class ElderTreeFeature extends AbstractTreeFeature<ElderTreeFeatureConfig> {

    public ElderTreeFeature(Function<Dynamic<?>, ? extends ElderTreeFeatureConfig> config) {
        super(config);
    }

    @Override
    protected boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos position, Set<BlockPos> placedLogs, Set<BlockPos> placedLeaves, MutableBoundingBox boundsIn, ElderTreeFeatureConfig configIn) {

        int height = rand.nextInt(2) + configIn.baseHeight;
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
                            if (!canBeReplacedByLogs(generationReader, blockpos$mutableblockpos.setPos(cx, cy, cz))) {
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
            } else if (isSoil(generationReader, position.down(), configIn.getSapling()) && position.getY() < generationReader.getMaxHeight() - height - 1) {
                this.setDirtAt(generationReader, position.down(), position);
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
                    if (isAirOrLeaves(generationReader, blockpos)) {
                        if (base == 0) {
                            if (rand.nextBoolean()) {
                                if (isReplaceable(generationReader, blockpos.north().down())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.north().down(), boundsIn, true);
                                } else if (isReplaceable(generationReader, blockpos.north())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.north(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(generationReader, blockpos.east().down())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.east().down(), boundsIn, true);
                                } else if (isReplaceable(generationReader, blockpos.east())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.east(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(generationReader, blockpos.south().down())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.south().down(), boundsIn, true);
                                } else if (isReplaceable(generationReader, blockpos.south())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.south(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(generationReader, blockpos.west().down())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.west().down(), boundsIn, true);
                                } else if (isReplaceable(generationReader, blockpos.west())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.west(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(generationReader, blockpos.north().east().down())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.north().east().down(), boundsIn, true);
                                } else if (isReplaceable(generationReader, blockpos.north().east())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.north().east(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(generationReader, blockpos.north().west().down())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.north().west().down(), boundsIn, true);
                                } else if (isReplaceable(generationReader, blockpos.north().west())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.north().west(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(generationReader, blockpos.south().east().down())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.south().east().down(), boundsIn, true);
                                } else if (isReplaceable(generationReader, blockpos.south().east())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.south().east(), boundsIn, true);
                                }
                            }
                            if (rand.nextBoolean()) {
                                if (isReplaceable(generationReader, blockpos.south().west().down())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.south().west().down(), boundsIn, true);
                                } else if (isReplaceable(generationReader, blockpos.south().west())) {
                                    this.placeLogAt(placedLogs, generationReader, blockpos.south().west(), boundsIn, true);
                                }
                            }
                            this.placeLogAt(placedLogs, generationReader, blockpos, boundsIn, false);
                        }

                        if (base <= height - 1) {
                            this.placeLogAt(placedLogs, generationReader, blockpos, boundsIn, false);
                            if (base == height - 2 || base == height - 3) {
                                if (logSide1 == 0) {
                                    this.placeRotatedLog(placedLogs, generationReader, blockpos.north(1), boundsIn, Direction.Axis.Z);
                                }
                                if (logSide1 == 1) {
                                    this.placeRotatedLog(placedLogs, generationReader, blockpos.east(1), boundsIn, Direction.Axis.X);
                                }
                                if (logSide1 == 2) {
                                    this.placeRotatedLog(placedLogs, generationReader, blockpos.south(1), boundsIn, Direction.Axis.Z);
                                }
                                if (logSide1 == 3) {
                                    this.placeRotatedLog(placedLogs, generationReader, blockpos.west(1), boundsIn, Direction.Axis.X);
                                }
                            }
                            if (base == height - 2 || base == height - 3) {
                                if (logSide1 == 0) {
                                    this.placeRotatedLog(placedLogs, generationReader, blockpos.north(1), boundsIn, Direction.Axis.Z);
                                }
                                if (logSide1 == 1) {
                                    this.placeRotatedLog(placedLogs, generationReader, blockpos.east(1), boundsIn, Direction.Axis.X);
                                }
                                if (logSide1 == 2) {
                                    this.placeRotatedLog(placedLogs, generationReader, blockpos.south(1), boundsIn, Direction.Axis.Z);
                                }
                                if (logSide1 == 3) {
                                    this.placeRotatedLog(placedLogs, generationReader, blockpos.west(1), boundsIn, Direction.Axis.X);
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
                                    this.placeLeafAt(placedLeaves, generationReader, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z), boundsIn, configIn);
                                }
                                if (rand.nextBoolean() || rand.nextBoolean()) {
                                    this.placeLeafAt(placedLeaves, generationReader, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z), boundsIn, configIn);
                                }
                            } else {
                                this.placeLeafAt(placedLeaves, generationReader, blockpos2.add(leafLayer1and3X, 0, leafLayer1and3Z), boundsIn, configIn);
                                this.placeLeafAt(placedLeaves, generationReader, blockpos2.add(leafLayer1and3X, -2, leafLayer1and3Z), boundsIn, configIn);
                            }
                        }
                    }
                }

                blockpos2 = blockpos2.up();

                for (int leafLayer4X = -2; leafLayer4X <= 2; ++leafLayer4X) {
                    for (int leafLayer4Z = -2; leafLayer4Z <= 2; ++leafLayer4Z) {
                        if (!((leafLayer4X == -2 || leafLayer4X == 2) && (leafLayer4Z == -2 || leafLayer4Z == 2))) {
                            this.placeLeafAt(placedLeaves, generationReader, blockpos2.add(leafLayer4X, 0, leafLayer4Z), boundsIn, configIn);
                        } else {
                            if (rand.nextBoolean()) {
                                this.placeLeafAt(placedLeaves, generationReader, blockpos2.add(leafLayer4X, 0, leafLayer4Z), boundsIn, configIn);
                            }
                        }
                    }
                }

                blockpos2 = blockpos2.up();

                for (int leafLayer5X = -1; leafLayer5X <= 1; ++leafLayer5X) {
                    for (int leafLayer5Z = -1; leafLayer5Z <= 1; ++leafLayer5Z) {
                        if (!((leafLayer5X == -1 || leafLayer5X == 1) && (leafLayer5Z == -1 || leafLayer5Z == 1)))
                            this.placeLeafAt(placedLeaves, generationReader, blockpos2.add(leafLayer5X, 0, leafLayer5Z), boundsIn, configIn);
                    }
                }

                blockpos2 = blockpos2.down(3);
                for (int leafLayer2X = -3; leafLayer2X <= 3; ++leafLayer2X) {
                    for (int leafLayer2Z = -3; leafLayer2Z <= 3; ++leafLayer2Z) {
                        if (!((leafLayer2X == -3 || leafLayer2X == 3) && (leafLayer2Z == -3 || leafLayer2Z == 3)))
                            this.placeLeafAt(placedLeaves, generationReader, blockpos2.add(leafLayer2X, 0, leafLayer2Z), boundsIn, configIn);
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

    private void placeLogAt(Set<BlockPos> setPos, IWorldGenerationReader reader, BlockPos pos, MutableBoundingBox boundingBox, boolean isBaseWood) {
        this.setElderLog(reader, pos, setPos, boundingBox, isBaseWood, null);
    }

    protected boolean setElderLog(IWorldGenerationReader p_227216_1_, BlockPos p_227216_3_, Set<BlockPos> p_227216_4_, MutableBoundingBox p_227216_5_, boolean isBaseWood, @Nullable Direction.Axis setAxis) {
        if (!isAirOrLeaves(p_227216_1_, p_227216_3_) && !isTallPlants(p_227216_1_, p_227216_3_) && !isWater(p_227216_1_, p_227216_3_)) {
            return false;
        } else {
            BlockState blockType = BlockRegistry.elder_log.getDefaultState();
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
            this.setBlockState(p_227216_1_, p_227216_3_, blockType, p_227216_5_);
            p_227216_4_.add(p_227216_3_.toImmutable());
            return true;
        }
    }

    protected static boolean isReplaceable(IWorldGenerationBaseReader worldIn, BlockPos pos) {
        if (!(worldIn instanceof net.minecraft.world.IBlockReader)) // FORGE: Redirect to state method when possible
            return worldIn.hasBlockState(pos, BlockState::isAir);
        else return worldIn.hasBlockState(pos, state -> state.getMaterial().isReplaceable());
    }

    private void placeRotatedLog(Set<BlockPos> setPos, IWorldGenerationReader reader, BlockPos pos, MutableBoundingBox boundingBox, Direction.Axis setAxis) {
        this.setElderLog(reader, pos, setPos, boundingBox, false, setAxis);
    }

    private void placeLeafAt(Set<BlockPos> set, IWorldGenerationReader reader, BlockPos pos, MutableBoundingBox boundingBox, ElderTreeFeatureConfig config) {
        Random random = new Random();
        this.setElderLeaf(reader, random, pos, set, boundingBox, config);
    }

    protected boolean setElderLeaf(IWorldGenerationReader p_227219_1_, Random p_227219_2_, BlockPos p_227219_3_, Set<BlockPos> p_227219_4_, MutableBoundingBox p_227219_5_, ElderTreeFeatureConfig p_227219_6_) {
        if (!isAirOrLeaves(p_227219_1_, p_227219_3_) && !isTallPlants(p_227219_1_, p_227219_3_) && !isWater(p_227219_1_, p_227219_3_)) {
            return false;
        } else {
            this.setBlockState(p_227219_1_, p_227219_3_, p_227219_6_.leavesProvider.getBlockState(p_227219_2_, p_227219_3_), p_227219_5_);
            p_227219_4_.add(p_227219_3_.toImmutable());
            return true;
        }
    }

}