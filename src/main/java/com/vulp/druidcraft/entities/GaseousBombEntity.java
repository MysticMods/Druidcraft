package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.registry.EntityRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Util;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class GaseousBombEntity extends ProjectileItemEntity {

    private short timer = -1;

    // TODO: Works now but needs to be fixed so that bombs don't clip through blocks. Also make them not hit the thrower. On the bright side, you know how to do projectiles a little easier now.

    public GaseousBombEntity(EntityType<? extends GaseousBombEntity> entityType, World world) {
        super(entityType, world);
    }

    public GaseousBombEntity(World world, LivingEntity livingEntity) {
        super(EntityRegistry.gaseous_bomb_entity, livingEntity, world);
    }

    public GaseousBombEntity(World world, double x, double y, double z) {
        super(EntityRegistry.gaseous_bomb_entity, x, y, z, world);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.gaseous_bomb;
    }

    public void writeAdditional(CompoundNBT compound) {
        compound.putShort("Timer", this.timer);
    }

    public void readAdditional(CompoundNBT compound) {
        this.timer = compound.getShort("Timer");
    }

    @Override
    public void tick() {
        if (this.timer > -1) {
            --this.timer;
            if (this.timer <= 0) {
                this.explode();
            }
        }
        super.tick();
    }

    private void explode() {
        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 3.5F, Explosion.Mode.NONE);
        this.remove();
    }

    protected void onEntityHit(EntityRayTraceResult rayTraceResult) {
        super.onEntityHit(rayTraceResult);
        if (rayTraceResult.getEntity().canBeAttackedWithItem()) {
            this.explode();
        }
    }

    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        this.timer = 40;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
