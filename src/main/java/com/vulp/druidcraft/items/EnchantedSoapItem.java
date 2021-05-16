package com.vulp.druidcraft.items;

import com.vulp.druidcraft.entities.BlockInhabitingEntity;
import com.vulp.druidcraft.entities.DuragemProtectionEntity;
import com.vulp.druidcraft.entities.FieryGlassGlowEntity;
import com.vulp.druidcraft.registry.EntityRegistry;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.util.ParticleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class EnchantedSoapItem extends Item {

    public EnchantedSoapItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        List<BlockInhabitingEntity> entityList = world.getEntitiesWithinAABB(BlockInhabitingEntity.class, new AxisAlignedBB(blockpos.add(1.0D, 1.0D, 1.0D), blockpos));
        if (!(world instanceof ServerWorld)) {
            if (entityList.size() != 0) {
                spawnWashParticles(world, blockpos);
                return ActionResultType.SUCCESS;
            } else return super.onItemUse(context);
        } else {
            ItemStack itemstack = context.getItem();
            if (entityList.size() > 0) {
                entityList.forEach(Entity::remove);
                itemstack.shrink(1);
                return ActionResultType.CONSUME;
            }
            return super.onItemUse(context);
        }
    }

/*    public void fieryGlassCheck(World world, BlockPos pos, List<BlockInhabitingEntity> entityList) {
        for (BlockInhabitingEntity entity : entityList) {
            if (entity instanceof FieryGlassGlowEntity) {
                ((FieryGlassGlowEntity) entity).toggleBlockLight(world, pos, false);
            }
        }
    }*/

    public static void spawnWashParticles(World world, BlockPos pos) {
        Random rand = new Random();
        ParticleUtil.makeBlockParticles(ParticleRegistry.enchanted_bubble, world, pos, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D);
/*
        Random rand = new Random();
        VoxelShape shape = world.getBlockState(pos).getShape(world, pos);
        float xStart = (float) (shape.getStart(Direction.Axis.X) / 16.0F);
        float yStart = (float) (shape.getStart(Direction.Axis.Y) / 16.0F);
        float zStart = (float) (shape.getStart(Direction.Axis.Z) / 16.0F);
        float xEnd = (float) (shape.getEnd(Direction.Axis.X) / 16.0F);
        float yEnd = (float) (shape.getEnd(Direction.Axis.Y) / 16.0F);
        float zEnd = (float) (shape.getEnd(Direction.Axis.Z) / 16.0F);
        for (int i = 0; i < rand.nextInt(10) + 16; i++) {
            int sideChooser = rand.nextInt(3);
            float x = (pos.getX() - 0.5F + xStart) + (rand.nextFloat() * xEnd);
            float y = (pos.getY() - 0.5F + yStart) + (rand.nextFloat() * yEnd);
            float z = (pos.getZ() - 0.5F + zStart) + (rand.nextFloat() * zEnd);
            if (sideChooser == 0) {
                x = pos.getX() - 0.5F + xEnd + rand.nextFloat() * 0.125F;
            } else if (sideChooser == 1) {
                y = pos.getY() - 0.5F + yEnd + rand.nextFloat() * 0.125F;
            } else {
                z = pos.getZ() - 0.5F + zEnd + rand.nextFloat() * 0.125F;
            }
            world.addParticle(ParticleTypes.END_ROD, x, y, z, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D);
        }
        int j = world.getBlockState(pos).isSolidSide(world, pos, Direction.UP) ? 2 : 1;
        for (int i = 0; i < rand.nextInt(10) + 10 * j; i++) {
            world.addParticle(ParticleRegistry.enchanted_bubble, pos.getX() - 0.2D + (rand.nextDouble() * 1.4D), pos.getY() - 0.2D + (rand.nextDouble() * 1.4D), pos.getZ() - 0.2D + (rand.nextDouble() * 1.4D), rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D);
        }*/
    }

}
