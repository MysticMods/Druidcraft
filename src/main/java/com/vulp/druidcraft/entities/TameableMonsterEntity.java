package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.registry.AdvancementRegistry;
import com.vulp.druidcraft.entities.AI.goals.SitGoalMonster;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class TameableMonsterEntity extends CreatureEntity {
    static final DataParameter<Byte> TAMED = EntityDataManager.createKey(TameableMonsterEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(TameableMonsterEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    SitGoalMonster sitGoal;

    TameableMonsterEntity(EntityType<? extends TameableMonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.setupTamedAI();
        this.experienceValue = 5;
    }

    @Override
    public PathNavigator getNavigator() {
        if (this.isPassenger() && this.getRidingEntity() instanceof TameableMonsterEntity) {
            MobEntity mobentity = (MobEntity)this.getRidingEntity();
            return mobentity.getNavigator();
        } else {
            return this.navigator;
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TAMED, (byte)0);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Override
    protected void registerAttributes() {
       super.registerAttributes();
       this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.getOwnerId() == null) {
            compound.putString("OwnerUUID", "");
        } else {
            compound.putString("OwnerUUID", this.getOwnerId().toString());
        }

        compound.putBoolean("Sitting", this.isSitting());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        String s;
        if (compound.contains("OwnerUUID", 8)) {
            s = compound.getString("OwnerUUID");
        } else {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(Objects.requireNonNull(this.getServer()), s1);
        }

        if (!s.isEmpty()) {
            try {
                this.setOwnerId(UUID.fromString(s));
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }

        if (this.sitGoal != null) {
            this.sitGoal.setSitting(compound.getBoolean("Sitting"));
        }

        this.setSitting(compound.getBoolean("Sitting"));
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return !this.isTamed() || !this.hasCustomName();
    }

    @Override
    public boolean preventDespawn() {
        return this.isTamed() || this.hasCustomName();
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return !this.getLeashed();
    }

    public boolean isPreventingPlayerRest(PlayerEntity playerIn) {
        return !this.isTamed();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL && !this.isTamed()) {
            this.remove();
        }
    }

    @Override
    public void livingTick() {
        this.updateArmSwingProgress();
        super.livingTick();
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    void playTameEffect(boolean play) {
        IParticleData particle = ParticleTypes.AMBIENT_ENTITY_EFFECT;
        if (play) {
            particle = ParticleTypes.HEART;
        }
        if (!play) {
            particle = ParticleTypes.SMOKE;
        }

        for(int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(particle, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + 0.5D + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d0, d1, d2);
        }

    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 7) {
            this.playTameEffect(true);
        } else if (id == 6) {
            this.playTameEffect(false);
        } else {
            super.handleStatusUpdate(id);
        }

    }

    public boolean isTamed() {
        return (this.dataManager.get(TAMED) & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte b0 = this.dataManager.get(TAMED);
        if (tamed) {
            this.dataManager.set(TAMED, (byte)(b0 | 4));
        } else {
            this.dataManager.set(TAMED, (byte)(b0 & -5));
        }

        this.setupTamedAI();
    }

    private void setupTamedAI() {
    }

    public boolean isSitting() {
        return (this.dataManager.get(TAMED) & 1) != 0;
    }

    public void setSitting(boolean sitting) {
        byte b0 = this.dataManager.get(TAMED);
        if (sitting) {
            this.dataManager.set(TAMED, (byte)(b0 | 1));
        } else {
            this.dataManager.set(TAMED, (byte)(b0 & -2));
        }

    }

    @Nullable
    private UUID getOwnerId() {
        return this.dataManager.get(OWNER_UNIQUE_ID).orElse(null);
    }

    private void setOwnerId(@Nullable UUID p_184754_1_) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
    }

    void setTamedBy(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerId(player.getUniqueID());
        if (player instanceof ServerPlayerEntity) {
            AdvancementRegistry.TAME_MONSTER.trigger((ServerPlayerEntity)player, this);
        }

    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return !this.isOwner(target) && super.canAttack(target);
    }

    boolean isOwner(LivingEntity entityIn) {
        return entityIn == this.getOwner();
    }

    @Override
    public boolean attemptTeleport(double p_213373_1_, double p_213373_3_, double p_213373_5_, boolean p_213373_7_) {
        return super.attemptTeleport(p_213373_1_, p_213373_3_, p_213373_5_, p_213373_7_);
    }

    /**
     * Returns the AITask responsible of the sit logic
     */
    public SitGoalMonster getAISit() {
        return this.sitGoal;
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        return true;
    }

    @Override
    public Team getTeam() {
        if (this.isTamed()) {
            LivingEntity livingentity = this.getOwner();
            if (livingentity != null) {
                return livingentity.getTeam();
            }
        }

        return super.getTeam();
    }

    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (this.isTamed()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }

            if (livingentity != null) {
                return livingentity.isOnSameTeam(entityIn);
            }
        }

        return super.isOnSameTeam(entityIn);
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource cause) {
        if (!this.world.isRemote && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
            this.getOwner().sendMessage(this.getCombatTracker().getDeathMessage());
        }

        super.onDeath(cause);
    }

   /**
    * Called when the entity is attacked.
    */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
       return !this.isInvulnerableTo(source) && super.attackEntityFrom(source, amount);
    }

    @Override
    protected boolean canDropLoot() {
        return true;
    }

    // Sound functions
    @Override
    public SoundCategory getSoundCategory() {
        return isTamed() ? SoundCategory.NEUTRAL : SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return isTamed() ? SoundEvents.ENTITY_GENERIC_SWIM : SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    @Override
    protected SoundEvent getSplashSound() {
        return isTamed() ? SoundEvents.ENTITY_GENERIC_SPLASH : SoundEvents.ENTITY_HOSTILE_SPLASH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return isTamed() ? SoundEvents.ENTITY_GENERIC_HURT : SoundEvents.ENTITY_HOSTILE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return isTamed() ? SoundEvents.ENTITY_GENERIC_DEATH : SoundEvents.ENTITY_HOSTILE_DEATH;
    }

    @Override
    protected SoundEvent getFallSound(int heightIn) {
        if (isTamed()) {
            return heightIn > 4 ? SoundEvents.ENTITY_GENERIC_BIG_FALL : SoundEvents.ENTITY_GENERIC_SMALL_FALL;
        } else {
            return heightIn > 4 ? SoundEvents.ENTITY_HOSTILE_BIG_FALL : SoundEvents.ENTITY_HOSTILE_SMALL_FALL;
        }
    }
}