package com.vulp.druidcraft.entities.projectiles;

import com.vulp.druidcraft.entities.DreadfishEntity;
import com.vulp.druidcraft.registry.EntityRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

public class DreadfishFireBurst extends DamagingProjectileEntity implements IProjectile {

    public DreadfishEntity projectile_owner;
    public CompoundNBT owner_NBT;

    public DreadfishFireBurst(EntityType<? extends DreadfishFireBurst> entity, World world) {
        super(entity, world);
    }

    public DreadfishFireBurst(World worldIn, DreadfishEntity entity) {
        this(EntityRegistry.dreadfish_fire_burst, worldIn);
        this.projectile_owner = entity;
        double x = entity.getPosX() - (double)(entity.getWidth() + 1.0F) * 0.5D * (double) MathHelper.sin(entity.renderYawOffset * ((float)Math.PI / 180F));
        double y = entity.getPosYEye() - (double)0.1F;
        double z = entity.getPosZ() + (double)(entity.getWidth() + 1.0F) * 0.5D * (double)MathHelper.cos(entity.renderYawOffset * ((float)Math.PI / 180F));
        this.setPosition(entity.getPosX() - (double)(entity.getWidth() + 1.0F) * 0.5D * (double) MathHelper.sin(entity.renderYawOffset * ((float)Math.PI / 180F)), entity.getPosYEye() - (double)0.1F, entity.getPosZ() + (double)(entity.getWidth() + 1.0F) * 0.5D * (double)MathHelper.cos(entity.renderYawOffset * ((float)Math.PI / 180F)));
        create(worldIn, x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public void create(World worldIn, double x, double y, double z) {
        this.setPosition(x, y, z);
        double altX = 0;
        double altY = 0;
        double altZ = 0;
        for(int i = 0; i < 7; ++i) {
            double d0 = 0.4D + 0.1D * (double)i;
            altX = ((MathHelper.clamp(this.accelerationX, -3.9D, 3.9D) / 8000.0D) * 8000.0D) * d0;
            altY = ((MathHelper.clamp(this.accelerationY, -3.9D, 3.9D) / 8000.0D) * 8000.0D);
            altZ = ((MathHelper.clamp(this.accelerationZ, -3.9D, 3.9D) * 8000.0D) / 8000.0D) * d0;
            worldIn.addParticle(ParticleTypes.SPIT, x, y, z, altX, altY, altZ);
        }

        this.setMotion(altX, altY, altZ);
    }

    public void tick() {
        super.tick();
        if (this.owner_NBT != null) {
            this.restoreOwnerFromSave();
        }

        Vec3d vec3d = this.getMotion();
        RayTraceResult raytraceresult = ProjectileHelper.rayTrace(this, this.getBoundingBox().expand(vec3d).grow(1.0D), (p_213879_1_) -> {
            return !p_213879_1_.isSpectator() && p_213879_1_ != this.projectile_owner;
        }, RayTraceContext.BlockMode.OUTLINE, true);
        if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onHit(raytraceresult);
        }

        double d0 = this.getPosX() + vec3d.x;
        double d1 = this.getPosY() + vec3d.y;
        double d2 = this.getPosZ() + vec3d.z;
        float f = MathHelper.sqrt(horizontalMag(vec3d));
        this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));

        for(this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
            ;
        }

        while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = MathHelper.lerp(0.2F, this.prevRotationPitch, this.rotationPitch);
        this.rotationYaw = MathHelper.lerp(0.2F, this.prevRotationYaw, this.rotationYaw);
        float f1 = 0.99F;
        float f2 = 0.06F;
        if (!this.world.isMaterialInBB(this.getBoundingBox(), Material.AIR)) {
            this.remove();
        } else if (this.isInWaterOrBubbleColumn()) {
            this.remove();
        } else {
            this.setMotion(vec3d.scale((double)0.99F));
            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0D, (double)-0.06F, 0.0D));
            }

            this.setPosition(d0, d1, d2);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void setVelocity(double x, double y, double z) {
        this.setMotion(x, y, z);
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (double)(180F / (float)Math.PI));
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (double)(180F / (float)Math.PI));
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
        }

    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy).scale((double)velocity);
        this.setMotion(vec3d);
        float f = MathHelper.sqrt(horizontalMag(vec3d));
        this.rotationYaw = (float)(MathHelper.atan2(vec3d.x, z) * (double)(180F / (float)Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(vec3d.y, (double)f) * (double)(180F / (float)Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void onHit(RayTraceResult p_190536_1_) {
        RayTraceResult.Type raytraceresult$type = p_190536_1_.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY && this.owner_NBT != null) {
            ((EntityRayTraceResult)p_190536_1_).getEntity().attackEntityFrom(DamageSource.causeIndirectDamage(this, this.projectile_owner).setProjectile(), 1.0F);
        } else if (raytraceresult$type == RayTraceResult.Type.BLOCK && !this.world.isRemote) {
            this.remove();
        }

    }

    @Override
    protected void registerData() {

    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        if (compound.contains("Owner", 10)) {
            this.owner_NBT = compound.getCompound("Owner");
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        if (this.projectile_owner != null) {
            CompoundNBT compoundnbt = new CompoundNBT();
            UUID uuid = this.projectile_owner.getUniqueID();
            compoundnbt.putUniqueId("OwnerUUID", uuid);
            compound.put("Owner", compoundnbt);
        }
    }

    private void restoreOwnerFromSave() {
        if (this.owner_NBT != null && this.owner_NBT.hasUniqueId("OwnerUUID")) {
            UUID uuid = this.owner_NBT.getUniqueId("OwnerUUID");

            for(DreadfishEntity dreadfish : this.world.getEntitiesWithinAABB(DreadfishEntity.class, this.getBoundingBox().grow(15.0D))) {
                if (dreadfish.getUniqueID().equals(uuid)) {
                    this.projectile_owner = dreadfish;
                    break;
                }
            }
        }

        this.owner_NBT = null;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

}
