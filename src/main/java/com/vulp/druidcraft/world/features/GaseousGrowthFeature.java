package com.vulp.druidcraft.world.features;

import com.mojang.serialization.Codec;
import com.vulp.druidcraft.api.StackPart;
import com.vulp.druidcraft.blocks.GaseousGrowthBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GaseousGrowthFeature extends Feature<NoFeatureConfig> {

    public GaseousGrowthFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        if (!reader.isAirBlock(pos)) {
            return false;
        } else {
            BlockState aboveState = reader.getBlockState(pos.up());
            if (!aboveState.isIn(Blocks.NETHERRACK) && !aboveState.isIn(BlockTags.NYLIUM)) {
                return false;
            } else {
                this.calculateAndBuild(reader, random, pos);
                return true;
            }
        }
    }

    private void calculateAndBuild(IWorld world, Random rand, BlockPos pos) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        for(int i = 0; i < 25; ++i) {
            mutablePos.setAndOffset(pos, rand.nextInt(8) - rand.nextInt(8), rand.nextInt(2) - rand.nextInt(7), rand.nextInt(8) - rand.nextInt(8));
            if (world.isAirBlock(mutablePos)) {
                BlockState aboveState = world.getBlockState(mutablePos.up());
                if (aboveState.isIn(Blocks.NETHERRACK) || aboveState.isIn(BlockTags.NYLIUM)) {
                    int multipier = MathHelper.nextInt(rand, 1, 8);
                    if (rand.nextInt(6) == 0) {
                        multipier *= 2;
                    }

                    if (rand.nextInt(5) == 0) {
                        multipier = 1;
                    }

                    vineBuilder(world, rand, mutablePos, multipier, 17, 25);
                }
            }
        }

    }

    public static void vineBuilder(IWorld world, Random rand, BlockPos.Mutable pos, int multiplier, int min, int max) {
        for(int i = 0; i <= multiplier; ++i) {
            if (world.isAirBlock(pos)) {
                if (i == multiplier || !world.isAirBlock(pos.down())) {
                    // world.setBlockState(pos, getVineStateForPlacement(i, multiplier).with(GaseousGrowthBlock.AGE, MathHelper.nextInt(rand, min, max)), 2);
                    world.setBlockState(pos, getVineStateForPlacement(i, multiplier), 2);
                    break;
                }

                world.setBlockState(pos, getVineStateForPlacement(i, multiplier), 2);
            }

            pos.move(Direction.DOWN);
        }

    }

    private static BlockState getVineStateForPlacement(int loop, int multiplier) {
        StackPart part = StackPart.MIDDLE;
        if (multiplier == loop) {
            part = StackPart.BOTTOM;
        } else if (loop == 0 && multiplier > 1) {
            part = StackPart.TOP;
        }
        return BlockRegistry.gaseous_growth.getDefaultState().with(GaseousGrowthBlock.PART, part);
    }

}