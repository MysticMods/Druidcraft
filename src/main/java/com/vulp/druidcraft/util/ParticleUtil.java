package com.vulp.druidcraft.util;

import com.vulp.druidcraft.registry.ParticleRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.BreakingParticle;
import net.minecraft.client.particle.DiggingParticle;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@OnlyIn(Dist.CLIENT)
public class ParticleUtil {

    @OnlyIn(Dist.CLIENT)
    public static void makeBlockParticles(IParticleData particleData, World world, BlockPos pos, double velX, double velY, double velZ) {

        ThreadLocalRandom rand = ThreadLocalRandom.current();
        Direction[] dirList = Direction.values();
        BlockState state = world.getBlockState(pos);
        VoxelShape shape = state.getShape(world, pos).simplify();

        int j = (shape == VoxelShapes.fullCube() ? 3 : 2);
        for (int i = 0; i < rand.nextInt(3) + 5 * j; i++) {

            Direction randomDir = dirList[rand.nextInt(dirList.length)];
            double x = rand.nextDouble(shape.getBoundingBox().minX - 0.2D, shape.getBoundingBox().maxX + 0.2D);
            if (randomDir.getAxis() == Direction.Axis.X) {
                x = randomDir == Direction.WEST ? shape.getBoundingBox().minX - 0.2D : shape.getBoundingBox().maxX + 0.2D;
            }
            double y = rand.nextDouble(shape.getBoundingBox().minY - 0.2D, shape.getBoundingBox().maxY + 0.2D);
            if (randomDir.getAxis() == Direction.Axis.Y) {
                y = randomDir == Direction.DOWN ? shape.getBoundingBox().minY - 0.2D : shape.getBoundingBox().maxY + 0.2D;
            }
            double z = rand.nextDouble(shape.getBoundingBox().minZ - 0.2D, shape.getBoundingBox().maxZ + 0.2D);
            if (randomDir.getAxis() == Direction.Axis.Z) {
                z = randomDir == Direction.NORTH ? shape.getBoundingBox().minZ - 0.2D : shape.getBoundingBox().maxZ + 0.2D;
            }

            world.addParticle(particleData, (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D);
        }

    }

 /*   public static void makeBlockParticles(World world, BlockPos pos, BlockState state) {
        if (!state.isAir(world, pos) && !state.addDestroyEffects(world, pos, this)) {
            VoxelShape voxelshape = state.getShape(this.world, pos);
            double d0 = 0.25D;
            voxelshape.forEachBox((x1, y1, z1, x2, y2, z2) -> {
                double d1 = Math.min(1.0D, x2 - x1);
                double d2 = Math.min(1.0D, y2 - y1);
                double d3 = Math.min(1.0D, z2 - z1);
                int i = Math.max(2, MathHelper.ceil(d1 / 0.25D));
                int j = Math.max(2, MathHelper.ceil(d2 / 0.25D));
                int k = Math.max(2, MathHelper.ceil(d3 / 0.25D));

                for(int l = 0; l < i; ++l) {
                    for(int i1 = 0; i1 < j; ++i1) {
                        for(int j1 = 0; j1 < k; ++j1) {
                            double d4 = ((double)l + 0.5D) / (double)i;
                            double d5 = ((double)i1 + 0.5D) / (double)j;
                            double d6 = ((double)j1 + 0.5D) / (double)k;
                            double d7 = d4 * d1 + x1;
                            double d8 = d5 * d2 + y1;
                            double d9 = d6 * d3 + z1;
                            this.addEffect((new DiggingParticle(this.world, (double)pos.getX() + d7, (double)pos.getY() + d8, (double)pos.getZ() + d9, d4 - 0.5D, d5 - 0.5D, d6 - 0.5D, state)).setBlockPos(pos));
                        }
                    }
                }

            });
        }
    }*/

}
