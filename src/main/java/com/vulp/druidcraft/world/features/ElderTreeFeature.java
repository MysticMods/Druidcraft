package com.vulp.druidcraft.world.features;

import com.mojang.serialization.Codec;
import com.vulp.druidcraft.blocks.WoodBlock;
import com.vulp.druidcraft.world.config.DummyTreeFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationReader;

import javax.annotation.Nullable;
import java.util.Random;

public class ElderTreeFeature extends DumbTreeFeature {

    public ElderTreeFeature(Codec<DummyTreeFeatureConfig> codec) {
        super(codec);
    }

    private void setElderLogAt(IWorldGenerationReader world, BlockPos pos, boolean isBaseWood, @Nullable Direction.Axis setAxis, DummyTreeFeatureConfig config, boolean forcePlacement) {
        if (isReplaceableAt(world, pos) || forcePlacement) {
            BlockState blockType = config.trunkProvider.getBlockState(random, pos);
            BlockState baseType = config.woodProvider.getBlockState(random, pos).with(WoodBlock.dropSelf, false);
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

    private void placeRotatedLog(IWorldGenerationReader reader, BlockPos pos, Direction.Axis setAxis, DummyTreeFeatureConfig config, boolean forcePlacement) {
        this.setElderLogAt(reader, pos, false, setAxis, config, forcePlacement);
    }

    private void placeLogAt(IWorldGenerationReader reader, BlockPos pos, boolean isBaseWood, DummyTreeFeatureConfig config, boolean forcePlacement) {
        this.setElderLogAt(reader, pos, isBaseWood, null, config, forcePlacement);
    }

    @Override
    public boolean generate(ISeedReader seedReader, ChunkGenerator generator, Random rand, BlockPos position, DummyTreeFeatureConfig config) {
        int height = rand.nextInt(2) + 6;
        boolean canGrow = canGrow(position, height, seedReader);

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
                                this.placeLogAt(seedReader, blockpos.north().down(), true, config, false);
                            } else if (isReplaceableAt(seedReader, blockpos.north())) {
                                this.placeLogAt(seedReader, blockpos.north(), true, config, false);
                            }
                        }
                        if (rand.nextBoolean()) {
                            if (isReplaceableAt(seedReader, blockpos.east().down())) {
                                this.placeLogAt(seedReader, blockpos.east().down(), true, config, false);
                            } else if (isReplaceableAt(seedReader, blockpos.east())) {
                                this.placeLogAt(seedReader, blockpos.east(), true, config, false);
                            }
                        }
                        if (rand.nextBoolean()) {
                            if (isReplaceableAt(seedReader, blockpos.south().down())) {
                                this.placeLogAt(seedReader, blockpos.south().down(), true, config, false);
                            } else if (isReplaceableAt(seedReader, blockpos.south())) {
                                this.placeLogAt(seedReader, blockpos.south(), true, config, false);
                            }
                        }
                        if (rand.nextBoolean()) {
                            if (isReplaceableAt(seedReader, blockpos.west().down())) {
                                this.placeLogAt(seedReader, blockpos.west().down(), true, config, false);
                            } else if (isReplaceableAt(seedReader, blockpos.west())) {
                                this.placeLogAt(seedReader, blockpos.west(), true, config, false);
                            }
                        }
                        if (rand.nextBoolean()) {
                            if (isReplaceableAt(seedReader, blockpos.north().east().down())) {
                                this.placeLogAt(seedReader, blockpos.north().east().down(), true, config, false);
                            } else if (isReplaceableAt(seedReader, blockpos.north().east())) {
                                this.placeLogAt(seedReader, blockpos.north().east(), true, config, false);
                            }
                        }
                        if (rand.nextBoolean()) {
                            if (isReplaceableAt(seedReader, blockpos.north().west().down())) {
                                this.placeLogAt(seedReader, blockpos.north().west().down(), true, config, false);
                            } else if (isReplaceableAt(seedReader, blockpos.north().west())) {
                                this.placeLogAt(seedReader, blockpos.north().west(), true, config, false);
                            }
                        }
                        if (rand.nextBoolean()) {
                            if (isReplaceableAt(seedReader, blockpos.south().east().down())) {
                                this.placeLogAt(seedReader, blockpos.south().east().down(), true, config, false);
                            } else if (isReplaceableAt(seedReader, blockpos.south().east())) {
                                this.placeLogAt(seedReader, blockpos.south().east(), true, config, false);
                            }
                        }
                        if (rand.nextBoolean()) {
                            if (isReplaceableAt(seedReader, blockpos.south().west().down())) {
                                this.placeLogAt(seedReader, blockpos.south().west().down(), true, config, false);
                            } else if (isReplaceableAt(seedReader, blockpos.south().west())) {
                                this.placeLogAt(seedReader, blockpos.south().west(), true, config, false);
                            }
                        }
                        this.placeLogAt(seedReader, blockpos, false, config, true);
                    }

                    if (base <= height - 1) {
                        this.placeLogAt(seedReader, blockpos, false, config, false);
                        if (base == height - 2 || base == height - 3) {
                            if (logSide1 == 0) {
                                this.placeRotatedLog(seedReader, blockpos.north(1), Direction.Axis.Z, config, false);
                            }
                            if (logSide1 == 1) {
                                this.placeRotatedLog(seedReader, blockpos.east(1), Direction.Axis.X, config, false);
                            }
                            if (logSide1 == 2) {
                                this.placeRotatedLog(seedReader, blockpos.south(1), Direction.Axis.Z, config, false);
                            }
                            if (logSide1 == 3) {
                                this.placeRotatedLog(seedReader, blockpos.west(1), Direction.Axis.X, config, false);
                            }
                        }
                        if (base == height - 2 || base == height - 3) {
                            if (logSide1 == 0) {
                                this.placeRotatedLog(seedReader, blockpos.north(1), Direction.Axis.Z, config, false);
                            }
                            if (logSide1 == 1) {
                                this.placeRotatedLog(seedReader, blockpos.east(1), Direction.Axis.X, config, false);
                            }
                            if (logSide1 == 2) {
                                this.placeRotatedLog(seedReader, blockpos.south(1), Direction.Axis.Z, config, false);
                            }
                            if (logSide1 == 3) {
                                this.placeRotatedLog(seedReader, blockpos.west(1), Direction.Axis.X, config, false);
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
    }

}
