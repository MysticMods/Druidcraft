package com.vulp.druidcraft.world.features;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vulp.druidcraft.blocks.WoodBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;
import java.util.*;

public class CustomTreeFeature extends Feature<BaseTreeFeatureConfig> {

    public final Block log;
    public final Block wood;
    public final Block leaves;

    public CustomTreeFeature(Block log, Block wood, Block leaves) {
        super(BaseTreeFeatureConfig.CODEC);
        this.log = log;
        this.wood = wood;
        this.leaves = leaves;
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BaseTreeFeatureConfig config) {
        Set<BlockPos> lvt_7_1_ = Sets.newHashSet();
        Set<BlockPos> lvt_8_1_ = Sets.newHashSet();
        Set<BlockPos> lvt_9_1_ = Sets.newHashSet();
        MutableBoundingBox lvt_10_1_ = MutableBoundingBox.getNewBoundingBox();
        boolean lvt_11_1_ = this.place(reader, rand, pos, lvt_7_1_, lvt_8_1_, lvt_10_1_);
        if (lvt_11_1_ && !lvt_7_1_.isEmpty()) {
            if (!config.decorators.isEmpty()) {
                List<BlockPos> lvt_12_1_ = Lists.newArrayList(lvt_7_1_);
                List<BlockPos> lvt_13_1_ = Lists.newArrayList(lvt_8_1_);
                lvt_12_1_.sort(Comparator.comparingInt(Vector3i::getY));
                lvt_13_1_.sort(Comparator.comparingInt(Vector3i::getY));
                config.decorators.forEach((p_236405_6_) -> {
                    p_236405_6_.func_225576_a_(reader, rand, lvt_12_1_, lvt_13_1_, lvt_9_1_, lvt_10_1_);
                });
            }

            VoxelShapePart lvt_12_2_ = this.func_236403_a_(reader, lvt_10_1_, lvt_7_1_, lvt_9_1_);
            Template.func_222857_a(reader, 3, lvt_12_2_, lvt_10_1_.minX, lvt_10_1_.minY, lvt_10_1_.minZ);
            return true;
        } else {
            return false;
        }
    }

    public boolean place(ISeedReader seedReader, Random rand, BlockPos position, Set<BlockPos> placedLogs, Set<BlockPos> placedLeaves, MutableBoundingBox boundsIn) {
        return false;
    }

    public void placeLogAt(Set<BlockPos> setPos, ISeedReader reader, BlockPos pos, MutableBoundingBox boundingBox, boolean isBaseWood) {
        this.setWood(reader, pos, setPos, boundingBox, isBaseWood, null);
    }

    protected boolean setWood(ISeedReader seedReader, BlockPos pos, Set<BlockPos> blockPosSet, MutableBoundingBox boundingBox, boolean isBaseWood, @Nullable Direction.Axis setAxis) {
        if ((!isAirOrLeaves(seedReader, pos) && !isTallPlants(seedReader, pos) && !isFluid(seedReader, pos)) && !isReplaceable(seedReader, pos)) {
            return false;
        } else {
            BlockState blockType = this.log.getDefaultState();
            BlockState baseType = this.wood.getDefaultState().with(WoodBlock.dropSelf, false);
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
            this.setBlockState(seedReader, pos, blockType);
            blockPosSet.add(pos.toImmutable());
            return true;
        }
    }

    public boolean isFluid(ISeedReader seedReader, BlockPos pos) {
        return !seedReader.getBlockState(pos).getFluidState().isEmpty();
    }

    public boolean isTallPlants(ISeedReader seedReader, BlockPos pos) {
        return seedReader.getBlockState(pos).getBlock() instanceof TallFlowerBlock || seedReader.getBlockState(pos).getBlock() instanceof TallGrassBlock || seedReader.getBlockState(pos).getBlock() instanceof TallSeaGrassBlock;
    }

    public boolean isAirOrLeaves(ISeedReader seedReader, BlockPos pos) {
        return seedReader.getBlockState(pos).getBlock() instanceof AirBlock || seedReader.getBlockState(pos).getBlock() instanceof LeavesBlock;
    }

    public static boolean isReplaceable(ISeedReader worldIn, BlockPos pos) {
        if (!(worldIn instanceof net.minecraft.world.IBlockReader)) // FORGE: Redirect to state method when possible
            return worldIn.hasBlockState(pos, BlockState::isAir);
        else return worldIn.hasBlockState(pos, state -> state.getMaterial().isReplaceable());
    }

    public void placeRotatedLog(Set<BlockPos> setPos, ISeedReader reader, BlockPos pos, MutableBoundingBox boundingBox, Direction.Axis setAxis) {
        this.setWood(reader, pos, setPos, boundingBox, false, setAxis);
    }

    public void placeLeafAt(Set<BlockPos> set, ISeedReader reader, BlockPos pos) {
        Random random = new Random();
        this.setLeaf(reader, random, pos, set);
    }

    protected boolean setLeaf(ISeedReader p_227219_1_, Random p_227219_2_, BlockPos p_227219_3_, Set<BlockPos> p_227219_4_) {
        if (!isAirOrLeaves(p_227219_1_, p_227219_3_) && !isTallPlants(p_227219_1_, p_227219_3_) && !isFluid(p_227219_1_, p_227219_3_)) {
            return false;
        } else {
            this.setBlockState(p_227219_1_, p_227219_3_, this.leaves.getDefaultState());
            p_227219_4_.add(p_227219_3_.toImmutable());
            return true;
        }
    }

    private VoxelShapePart func_236403_a_(IWorld p_236403_1_, MutableBoundingBox p_236403_2_, Set<BlockPos> p_236403_3_, Set<BlockPos> p_236403_4_) {
        List<Set<BlockPos>> lvt_5_1_ = Lists.newArrayList();
        VoxelShapePart lvt_6_1_ = new BitSetVoxelShapePart(p_236403_2_.getXSize(), p_236403_2_.getYSize(), p_236403_2_.getZSize());

        for(int lvt_8_1_ = 0; lvt_8_1_ < 6; ++lvt_8_1_) {
            lvt_5_1_.add(Sets.newHashSet());
        }

        BlockPos.Mutable lvt_8_2_ = new BlockPos.Mutable();
        Iterator var9 = Lists.newArrayList(p_236403_4_).iterator();

        BlockPos lvt_10_2_;
        while(var9.hasNext()) {
            lvt_10_2_ = (BlockPos)var9.next();
            if (p_236403_2_.isVecInside(lvt_10_2_)) {
                lvt_6_1_.setFilled(lvt_10_2_.getX() - p_236403_2_.minX, lvt_10_2_.getY() - p_236403_2_.minY, lvt_10_2_.getZ() - p_236403_2_.minZ, true, true);
            }
        }

        var9 = Lists.newArrayList(p_236403_3_).iterator();

        while(var9.hasNext()) {
            lvt_10_2_ = (BlockPos)var9.next();
            if (p_236403_2_.isVecInside(lvt_10_2_)) {
                lvt_6_1_.setFilled(lvt_10_2_.getX() - p_236403_2_.minX, lvt_10_2_.getY() - p_236403_2_.minY, lvt_10_2_.getZ() - p_236403_2_.minZ, true, true);
            }

            Direction[] var11 = Direction.values();
            int var12 = var11.length;

            for(int var13 = 0; var13 < var12; ++var13) {
                Direction lvt_14_1_ = var11[var13];
                lvt_8_2_.setAndMove(lvt_10_2_, lvt_14_1_);
                if (!p_236403_3_.contains(lvt_8_2_)) {
                    BlockState lvt_15_1_ = p_236403_1_.getBlockState(lvt_8_2_);
                    if (lvt_15_1_.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
                        ((Set)lvt_5_1_.get(0)).add(lvt_8_2_.toImmutable());
                        func_236408_b_(p_236403_1_, lvt_8_2_, (BlockState)lvt_15_1_.with(BlockStateProperties.DISTANCE_1_7, 1));
                        if (p_236403_2_.isVecInside(lvt_8_2_)) {
                            lvt_6_1_.setFilled(lvt_8_2_.getX() - p_236403_2_.minX, lvt_8_2_.getY() - p_236403_2_.minY, lvt_8_2_.getZ() - p_236403_2_.minZ, true, true);
                        }
                    }
                }
            }
        }

        for(int lvt_9_1_ = 1; lvt_9_1_ < 6; ++lvt_9_1_) {
            Set<BlockPos> lvt_10_3_ = (Set)lvt_5_1_.get(lvt_9_1_ - 1);
            Set<BlockPos> lvt_11_1_ = (Set)lvt_5_1_.get(lvt_9_1_);
            Iterator var25 = lvt_10_3_.iterator();

            while(var25.hasNext()) {
                BlockPos lvt_13_1_ = (BlockPos)var25.next();
                if (p_236403_2_.isVecInside(lvt_13_1_)) {
                    lvt_6_1_.setFilled(lvt_13_1_.getX() - p_236403_2_.minX, lvt_13_1_.getY() - p_236403_2_.minY, lvt_13_1_.getZ() - p_236403_2_.minZ, true, true);
                }

                Direction[] var27 = Direction.values();
                int var28 = var27.length;

                for(int var16 = 0; var16 < var28; ++var16) {
                    Direction lvt_17_1_ = var27[var16];
                    lvt_8_2_.setAndMove(lvt_13_1_, lvt_17_1_);
                    if (!lvt_10_3_.contains(lvt_8_2_) && !lvt_11_1_.contains(lvt_8_2_)) {
                        BlockState lvt_18_1_ = p_236403_1_.getBlockState(lvt_8_2_);
                        if (lvt_18_1_.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
                            int lvt_19_1_ = (Integer)lvt_18_1_.get(BlockStateProperties.DISTANCE_1_7);
                            if (lvt_19_1_ > lvt_9_1_ + 1) {
                                BlockState lvt_20_1_ = (BlockState)lvt_18_1_.with(BlockStateProperties.DISTANCE_1_7, lvt_9_1_ + 1);
                                func_236408_b_(p_236403_1_, lvt_8_2_, lvt_20_1_);
                                if (p_236403_2_.isVecInside(lvt_8_2_)) {
                                    lvt_6_1_.setFilled(lvt_8_2_.getX() - p_236403_2_.minX, lvt_8_2_.getY() - p_236403_2_.minY, lvt_8_2_.getZ() - p_236403_2_.minZ, true, true);
                                }

                                lvt_11_1_.add(lvt_8_2_.toImmutable());
                            }
                        }
                    }
                }
            }
        }

        return lvt_6_1_;
    }

    public static void func_236408_b_(IWorldWriter p_236408_0_, BlockPos p_236408_1_, BlockState p_236408_2_) {
        p_236408_0_.setBlockState(p_236408_1_, p_236408_2_, 19);
    }

}
