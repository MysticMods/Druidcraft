package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.blocks.GrowthLampBlock;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;
import org.lwjgl.system.CallbackI;

import java.util.List;
import java.util.Random;

public class GrowthLampTileEntity extends TileEntity implements ITickableTileEntity {

    public GrowthLampTileEntity() {
        this(TileEntityRegistry.growth_lamp);
    }

    public GrowthLampTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if (world != null) {
        if (world.rand.nextInt(40) == 0) {

                Random random = world.rand;

            float yMod = 0.0f;
            if (world.getBlockState(pos).get(GrowthLampBlock.HANGING)) {
                yMod = 0.05f;
            }

            if (world.isRemote) {
                for (int i = 0; i < 3; i++) {
                    world.addParticle(ParticleRegistry.magic_smoke, (double) pos.getX() + 0.20f + (random.nextInt(7) / 10.0f), pos.getY() + 0.05f + yMod + (random.nextInt(6) / 10.0f), (double) pos.getZ() + 0.20f + (random.nextInt(7) / 10.0f), 215 / 255.0f, 255 / 255.0f, 65 / 255.0f);
                }
            }

            // MOB GROWTH
            List<AnimalEntity> animalEntities = world.getEntitiesWithinAABB(AnimalEntity.class, new AxisAlignedBB(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3));
            if (animalEntities.size() != 0) {
                for (int j = 0; j < animalEntities.size(); j++) {
                    if (animalEntities.get(j).isChild()) {
                        if (animalEntities.get(j).getGrowingAge() >= 10) {
                            animalEntities.get(j).addGrowth(random.nextInt(2) + 1);
                        } else {
                            animalEntities.get(j).addGrowth(1);
                        }
                    }
                }
            }

            // PLANT GROWTH
            for (int x = -2; x < 3; x++) {
                for (int y = -2; y < 3; y++) {
                    for (int z = -2; z < 3; z++) {

                        BlockPos newPos = pos.add(x, y, z);

                        if (world.getBlockState(newPos).getBlock() instanceof IPlantable || world.getBlockState(newPos).getBlock() instanceof IGrowable) {
                            world.getPendingBlockTicks().scheduleTick(newPos, world.getBlockState(newPos).getBlock(), 0);
                            }
                        }
                    }
                }
            }
        }
    }
}