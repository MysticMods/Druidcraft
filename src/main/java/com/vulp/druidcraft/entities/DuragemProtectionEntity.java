package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.block.AirBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DuragemProtectionEntity extends Entity {

    private int validityTimer;
    private int visibilityTimer;
    private float visibilityAmount;
    private boolean isVisible;

    public DuragemProtectionEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    @Override
    public void checkDespawn() {

    }

    @Override
    protected void registerData() {
        setVisible(true);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean hitByEntity(Entity entity) {
        return false;
    }

    @Override
    public void tick() {
        if (this.visibilityTimer > 0) {
            --this.visibilityTimer;
        } else if (getVisibility() > 0.0F) {
            this.visibilityAmount -= 0.1F;
        } else {
            setVisible(false);
        }
        if (this.validityTimer++ == 10 && !this.world.isRemote) {
            this.validityTimer = 0;
            if (isAlive() && world.getBlockState(getPosition()).getBlock() instanceof AirBlock) {
                remove();
            }
        }
    }

    public void setVisible(boolean flag) {
        if (flag) {
            this.visibilityAmount = 1.0F;
            this.visibilityTimer = 20;
            this.isVisible = true;
        } else {
            this.isVisible = false;
        }
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public float getVisibility() {
        return this.visibilityAmount;
    }

}
