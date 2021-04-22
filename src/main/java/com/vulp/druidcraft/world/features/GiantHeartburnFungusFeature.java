package com.vulp.druidcraft.world.features;

import com.mojang.serialization.Codec;
import com.vulp.druidcraft.api.StackPart;
import com.vulp.druidcraft.blocks.ScorchingCurtainBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.world.config.DummyTreeFeatureConfig;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BigRedMushroomFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class GiantHeartburnFungusFeature extends DumbTreeFeature {

    public GiantHeartburnFungusFeature(Codec<DummyTreeFeatureConfig> codec) {
        super(codec);
    }

    public static boolean isOvergrownNylium(IWorldGenerationReader world, BlockPos pos) {
        return world.hasBlockState(pos, (state) -> {
            Block block = state.getBlock();
            return block == BlockRegistry.overgrown_nylium;
        });
    }



    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, DummyTreeFeatureConfig config) {
        boolean stemTall = rand.nextBoolean();
        int capHeight = rand.nextInt(2) + 1;
        int height1 = !stemTall ? rand.nextInt(2) + 3 : rand.nextInt(1) + 3;
        int height2 = !stemTall ? rand.nextInt(2) + 3 : rand.nextInt(2) + 2;
        int height3 = rand.nextInt(2) + 2;
        int height = stemTall ? height1 + height2 + height3 : height1 + height2;
        BlockPos originalPos = pos;
        boolean canGrow = canGrow(originalPos, height, reader);

        if (!canGrow) {
            return false;
        } else if (isOvergrownNylium(reader, originalPos.down()) && originalPos.getY() < getMaxHeight(reader) - height - 1) {
            Direction lean1 = getRandomCardinal();
            Direction lean2 = rand.nextBoolean() ? lean1.rotateY() : lean1.rotateYCCW();
            int baseModifier = rand.nextInt(2);
            this.placeLogAt(reader, pos, config, true);
            this.placeHyphaeNonFloating(reader, pos.offset(lean1.getOpposite()), config, null);
            if (baseModifier > 0) {
                this.placeHyphaeNonFloating(reader, pos.offset(lean1), config, null);
                this.placeHyphaeNonFloating(reader, pos.offset(lean1.rotateY()), config, null);
                this.placeHyphaeNonFloating(reader, pos.offset(lean1.rotateYCCW()), config, null);
                this.placeHyphaeNonFloating(reader, pos.offset(lean1.getOpposite()), config, null);
            } else {
                this.placeHyphaeNonFloating(reader, pos.offset(lean1.getOpposite()), config, null);
                if (rand.nextBoolean()) {
                    this.placeHyphaeNonFloating(reader, pos.offset(lean1.rotateY()), config, null);
                } else {
                    this.placeHyphaeNonFloating(reader, pos.offset(lean1.rotateYCCW()), config, null);
                }
            }
            pos = pos.up();
            this.placeLogAt(reader, pos, config, true);
            if (baseModifier > 0) {
                this.placeHyphaeNonFloating(reader, pos.offset(lean1.getOpposite()), config, null);
                if (rand.nextBoolean()) {
                    this.placeHyphaeNonFloating(reader, pos.offset(lean1.rotateY()), config, null);
                } else {
                    this.placeHyphaeNonFloating(reader, pos.offset(lean1.rotateYCCW()), config, null);
                }
            }
            for (int i = 0; i < height1 - 2; i++) {
                pos = pos.up();
                this.placeLogAt(reader, pos, config, true);
            }
            pos = pos.up();
            this.placeHyphaeAt(reader, pos, config, null);
            pos = pos.offset(lean1);
            this.placeHyphaeAt(reader, pos, config, null);
            for (int i = 0; i < height2; i++) {
                pos = pos.up();
                this.placeLogAt(reader, pos, config, true);
            }
            pos = pos.up();
            if (stemTall) {
                this.placeHyphaeAt(reader, pos, config, null);
                pos = pos.offset(lean2);
                this.placeHyphaeAt(reader, pos, config, null);
                for (int i = 0; i < height2; i++) {
                    pos = pos.up();
                    this.placeLogAt(reader, pos, config, true);
                }
                pos = pos.up();
            }
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    placeHeartburnBlockAt(reader, pos.add(x, 0, z), config, x, z);
                }
            }
            pos = pos.down();
            for (int y = 0; y < capHeight + 1; y++) {
                for (int x = -2; x < 3; x++) {
                    for (int z = -2; z < 3; z++) {
                        if (Math.abs(x) < 2 && Math.abs(z) < 2) {
                            if (x != 0 && z != 0) {
                                if (y != capHeight) {
                                    forceGlowAirAt(reader, pos.add(x, -y, z), config);
                                }
                            }
                        } else if (!(Math.abs(x) == 2 && Math.abs(z) == 2)) {
                            if (y != capHeight) {
                                placeHeartburnBlockAt(reader, pos.add(x, -y, z), config, x, z);
                            } else {
                                int j = rand.nextInt(6);
                                for (int k = 0; k < j; k++) {
                                    if (j == 1) {
                                        placeScorchingCurtainAt(reader, StackPart.SINGLE, pos.add(x, (-y) - k, z), config);
                                    } else if (k == 0) {
                                        placeScorchingCurtainAt(reader, StackPart.TOP, pos.add(x, (-y) - k, z), config);
                                    } else if (k == j - 1) {
                                        placeScorchingCurtainAt(reader, StackPart.BOTTOM, pos.add(x, (-y) - k, z), config);
                                    } else {
                                        placeScorchingCurtainAt(reader, StackPart.MIDDLE, pos.add(x, (-y) - k, z), config);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void forceGlowAirAt(IWorldGenerationReader reader, BlockPos pos, DummyTreeFeatureConfig config) {
        this.setBlockState(reader, pos, BlockRegistry.glow_air.getDefaultState());
    }

    public void placeHyphaeNonFloating(IWorldGenerationReader reader, BlockPos pos, DummyTreeFeatureConfig config, @Nullable Direction.Axis axis) {
        if (!isReplaceableAt(reader, pos.down())) {
            placeHyphaeAt(reader, pos, config, axis);
        }
    }

    public void placeHyphaeAt(IWorldGenerationReader reader, BlockPos pos, DummyTreeFeatureConfig config, @Nullable Direction.Axis axis) {
        if (axis == null) {
            axis = Direction.Axis.Y;
        }
        if (isReplaceableAt(reader, pos)) {
            this.setBlockState(reader, pos, config.woodProvider.getBlockState(random, pos).with(RotatedPillarBlock.AXIS, axis));
        }
    }

    public void placeHeartburnBlockAt(IWorldGenerationReader reader, BlockPos pos, DummyTreeFeatureConfig config, int x, int z) {
        if (isAirOrLeavesAt(reader, pos)) {
            BlockState state = config.leavesProvider.getBlockState(random, pos).with(HugeMushroomBlock.DOWN, false);
            state = x > 0 ? state.with(HugeMushroomBlock.WEST, false) : state.with(HugeMushroomBlock.EAST, false);
            state = z > 0 ? state.with(HugeMushroomBlock.NORTH, false) : state.with(HugeMushroomBlock.SOUTH, false);
            this.setBlockState(reader, pos, state);
        }
    }

    public void placeScorchingCurtainAt(IWorldGenerationReader reader, StackPart part, BlockPos pos, DummyTreeFeatureConfig config) {
        if (isAirOrLeavesAt(reader, pos)) {
            this.setBlockState(reader, pos, BlockRegistry.scorching_curtain.getDefaultState().with(ScorchingCurtainBlock.PART, part));
        }
    }

}
