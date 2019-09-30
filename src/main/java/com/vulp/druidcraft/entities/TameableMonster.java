package com.vulp.druidcraft.entities;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vulp.druidcraft.advancements.CriteriaTriggers;
import com.vulp.druidcraft.entities.AI.goals.SitGoalMonster;
import com.vulp.druidcraft.pathfinding.FlyingPathNavigator;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.TameAnimalTrigger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class TameableMonster extends MonsterEntity {
    static final DataParameter<Byte> TAMED = EntityDataManager.createKey(TameableEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(TameableEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    SitGoalMonster sitGoal;

    TameableMonster(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.setupTamedAI();
    }

    @Override
    public FlyingPathNavigator getNavigator() {
        if (this.isPassenger() && this.getRidingEntity() instanceof TameableMonster) {
            MobEntity mobentity = (MobEntity)this.getRidingEntity();
            return (FlyingPathNavigator) mobentity.getNavigator();
        } else {
            return (FlyingPathNavigator) this.navigator;
        }
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(TAMED, (byte)0);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    }

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

    public boolean canBeLeashedTo(PlayerEntity player) {
        return !this.getLeashed();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL && !this.isTamed()) {
            this.remove();
        }
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
        return this.dataManager.get(OWNER_UNIQUE_ID).orElse((UUID)null);
    }

    private void setOwnerId(@Nullable UUID p_184754_1_) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
    }

    void setTamedBy(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerId(player.getUniqueID());
        if (player instanceof ServerPlayerEntity) {
            CriteriaTriggers.TAME_MONSTER((ServerPlayerEntity)player, this);
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
}