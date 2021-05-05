package com.vulp.druidcraft.world.features;

import com.mojang.serialization.Codec;
import com.vulp.druidcraft.blocks.BramblerootBlock;
import com.vulp.druidcraft.blocks.ThickRootBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.*;

public class BramblerootFeature extends Feature<NoFeatureConfig> {

    private static final Direction[] cardinals = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    private static final Direction[] upperDirections = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};
    private static final Direction[] lowerDirections = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.DOWN};
    private static Random RANDOM = new Random();

    public BramblerootFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (!reader.isAirBlock(pos)) {
            return false;
        } else {
            BlockState belowState = reader.getBlockState(pos.down());
            if (!belowState.isIn(Blocks.NETHERRACK) && !belowState.isIn(BlockTags.NYLIUM) && !belowState.isIn(BlockTags.WART_BLOCKS)) {
                return false;
            } else {
                this.calculateAndBuild(reader, rand, pos);
                return true;
            }
        }
    }

    private void calculateAndBuild(ISeedReader reader, Random rand, BlockPos pos) {
        List<BlockPos> brambleList = new ArrayList<>();
        List<BlockPos> brambleSplit = new ArrayList<>();
        boolean split = false;
        brambleList.add(pos);
        brambleList.addAll(startBramble(brambleList));
        if (RANDOM.nextInt(5) == 0) {
            brambleSplit = split(brambleList.get(brambleList.size() - 1));
            split = true;
        }
        brambleList.addAll(continueBramble(brambleList));
        if (!split && RANDOM.nextInt(5) == 0) {
            brambleSplit = split(brambleList.get(brambleList.size() - 1));
        }
        brambleList.addAll(endBramble(brambleList));
        build(reader, brambleList, brambleSplit);
    }

    private void build(ISeedReader reader, List<BlockPos> brambleList, List<BlockPos> brambleSplit) {
        for (int i = 0; i < brambleList.size(); i++) {
            if (i == 0) {
                //setBlockState(reader, brambleList.get(i), ThickRootBlock.getStateForFeature(BlockRegistry.brambleroot.getDefaultState(), ));
            }
        }
    }

    private Direction posToDirection(BlockPos originalPos, BlockPos newPos) {
        if (originalPos.getX() != newPos.getX()) {

        }
        // TODO: Remove this return.
        return Direction.UP;
    }

    private List<BlockPos> startBramble(List<BlockPos> brambleList) {
        BlockPos currentPos = brambleList.get(brambleList.size() - 1);
        currentPos.offset(directionPicker(true));
        brambleList.add(currentPos);
        if (RANDOM.nextBoolean()) {
            currentPos.offset(directionPicker(true));
            brambleList.add(currentPos);
            currentPos.offset(cardinalPicker());
            brambleList.add(currentPos);
        } else {
            currentPos.offset(cardinalPicker());
            brambleList.add(currentPos);
        }
        return brambleList;
    }

    private List<BlockPos> continueBramble(List<BlockPos> brambleList) {
        BlockPos currentPos = brambleList.get(brambleList.size() - 1);
        for (int i = 0; i < RANDOM.nextInt(6); i++) {
            if (RANDOM.nextInt(3) == 0) {
                currentPos.offset(directionPicker());
                brambleList.add(currentPos);
            } else {
                currentPos.offset(cardinalPicker());
                brambleList.add(currentPos);
            }
        }
        return brambleList;
    }

    private List<BlockPos> endBramble(List<BlockPos> brambleList) {
        BlockPos currentPos = brambleList.get(brambleList.size() - 1);
        currentPos.offset(directionPicker(false));
        brambleList.add(currentPos);
        for (int i = 0; i < RANDOM.nextInt(10); i++) {
            if (RANDOM.nextInt(3) != 0) {
                currentPos.offset(Direction.DOWN);
            } else {
                currentPos.offset(directionPicker(false));
            }
            brambleList.add(currentPos);
        }
        return brambleList;
    }

    private List<BlockPos> split(BlockPos originPos) {
        List<BlockPos> list = new ArrayList<>();
        continueBramble(list);
        return endBramble(list);
    }

    private Direction directionPicker() {
        return Direction.getRandomDirection(RANDOM);
    }

    private Direction directionPicker(boolean upward) {
        if (upward) {
            return Util.getRandomObject(upperDirections, RANDOM);
        } else {
            return Util.getRandomObject(lowerDirections, RANDOM);
        }
    }

    private Direction cardinalPicker() {
        return Util.getRandomObject(cardinals, RANDOM);
    }

    private void setBramblerootBlock(ISeedReader reader, BlockPos pos) {

    }

}
