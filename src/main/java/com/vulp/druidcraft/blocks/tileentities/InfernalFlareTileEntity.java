package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.InfernalFlareBlock;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.CampfireBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class InfernalFlareTileEntity extends TileEntity implements ITickableTileEntity {

    private int maxTime = 0;

    public InfernalFlareTileEntity() {
        super(TileEntityRegistry.infernal_flare);
    }

    @Override
    public void tick() {
        World world = this.getWorld();
        if (world != null) {
            if (world.isRemote) {
                BlockPos blockpos = this.getPos();
                Random random = world.rand;
                if (random.nextFloat() < 0.11F || this.maxTime > 15) {
                    InfernalFlareBlock.spawnParticles(world, blockpos);
                    this.maxTime = 0;
                } else {
                    this.maxTime++;
                }
            }
        }
    }

}
