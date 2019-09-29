package com.vulp.druidcraft.world.features;

import com.mojang.datafixers.Dynamic;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TallTaigaTreeFeature;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class DarkwoodTreeFeature extends TallTaigaTreeFeature {
    private static final BlockState TRUNK;
    private static final BlockState LEAF;

    public DarkwoodTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51429_1_, boolean p_i51429_2_) {
        super(p_i51429_1_, p_i51429_2_);
        this.setSapling((IPlantable) BlockRegistry.darkwood_sapling);
    }

    public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_) {
        int i = rand.nextInt(4) + 6;
        int j = 1 + rand.nextInt(2);
        int k = i - j;
        int l = 2 + rand.nextInt(2);
        boolean flag = true;
        if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight()) {
            int i3;
            int j3;
            int k1;
            int j4;
            for(i3 = position.getY(); i3 <= position.getY() + 1 + i && flag; ++i3) {
                if (i3 - position.getY() < j) {
                    j3 = 0;
                } else {
                    j3 = l;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for(k1 = position.getX() - j3; k1 <= position.getX() + j3 && flag; ++k1) {
                    for(j4 = position.getZ() - j3; j4 <= position.getZ() + j3 && flag; ++j4) {
                        if (i3 >= 0 && i3 < worldIn.getMaxHeight()) {
                            blockpos$mutableblockpos.setPos(k1, i3, j4);
                            if (!isAirOrLeaves(worldIn, blockpos$mutableblockpos)) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else if (isSoil(worldIn, position.down(), this.getSapling()) && position.getY() < worldIn.getMaxHeight() - i - 1) {
                this.setDirtAt(worldIn, position.down(), position);
                i3 = rand.nextInt(2);
                j3 = 1;
                int k3 = 0;

                for(k1 = 0; k1 <= k; ++k1) {
                    j4 = position.getY() + i - k1;

                    for(int i2 = position.getX() - i3; i2 <= position.getX() + i3; ++i2) {
                        int j2 = i2 - position.getX();

                        for(int k2 = position.getZ() - i3; k2 <= position.getZ() + i3; ++k2) {
                            int l2 = k2 - position.getZ();
                            if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0) {
                                BlockPos blockpos = new BlockPos(i2, j4, k2);
                                if (isAirOrLeaves(worldIn, blockpos) || isTallPlants(worldIn, blockpos)) {
                                    this.setLogState(changedBlocks, worldIn, blockpos, LEAF, p_208519_5_);
                                }
                            }
                        }
                    }

                    if (i3 >= j3) {
                        i3 = k3;
                        k3 = 1;
                        ++j3;
                        if (j3 > l) {
                            j3 = l;
                        }
                    } else {
                        ++i3;
                    }
                }

                k1 = rand.nextInt(3);

                for(j4 = 0; j4 < i - k1; ++j4) {
                    if (isAirOrLeaves(worldIn, position.up(j4))) {
                        this.setLogState(changedBlocks, worldIn, position.up(j4), TRUNK, p_208519_5_);
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

    static {
        TRUNK = BlockRegistry.darkwood_log.getDefaultState();
        LEAF = BlockRegistry.darkwood_leaves.getDefaultState();
    }
}
