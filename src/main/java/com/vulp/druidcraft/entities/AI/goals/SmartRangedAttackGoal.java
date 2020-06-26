package com.vulp.druidcraft.entities.AI.goals;

import com.vulp.druidcraft.api.ICooldownAttackMob;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class SmartRangedAttackGoal extends Goal {
    private final MobEntity entityHost;
    private final IRangedAttackMob rangedAttackMob;
    private final ICooldownAttackMob cooldownAttackMob;
    private LivingEntity attackTarget;
    private int rangedAttackTime;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int rangeInterval;
    private final int maxRangeInterval;
    private final float attackRadius;
    private final float maxAttackDistance;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public SmartRangedAttackGoal(IRangedAttackMob mob, double moveSpeed, int cooldown, int cooldownMax, float radius) {
        this.rangedAttackTime = -1;
        if (!(mob instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob & CooldownAttackMob");
        } else {
            this.rangedAttackMob = mob;
            this.cooldownAttackMob = (ICooldownAttackMob)mob;
            this.entityHost = (MobEntity)mob;
            this.entityMoveSpeed = moveSpeed;
            this.rangeInterval = cooldown;
            this.maxRangeInterval = cooldownMax;
            this.attackRadius = radius;
            this.maxAttackDistance = radius * radius;
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }
    }

    public boolean shouldExecute() {
        if (cooldownAttackMob.canUseAttack(entityHost)) {
            LivingEntity target = this.entityHost.getAttackTarget();
            if (target != null && target.isAlive()) {
                this.attackTarget = target;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting() {
        if (cooldownAttackMob.canUseAttack(entityHost)) {
            return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
        }
        return false;
    }

    public void resetTask() {
        this.attackTarget = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;
    }

    public void tick() {
        if (cooldownAttackMob.canUseAttack(entityHost)) {
            double distance = this.entityHost.getDistanceSq(this.attackTarget.getPosX(), this.attackTarget.getPosY(), this.attackTarget.getPosZ());
            boolean canSee = this.entityHost.getEntitySenses().canSee(this.attackTarget);
            if (canSee) {
                ++this.seeTime;
            } else {
                this.seeTime = 0;
            }

            if (distance <= (double) this.maxAttackDistance && this.seeTime >= 5) {
                this.entityHost.getNavigator().clearPath();
                ++this.strafingTime;
            } else {
                this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if ((double)this.entityHost.getRNG().nextFloat() < 0.3D) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if ((double)this.entityHost.getRNG().nextFloat() < 0.3D) {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (distance > (double)(this.maxAttackDistance * 0.75F)) {
                    this.strafingBackwards = false;
                } else if (distance < (double)(this.maxAttackDistance * 0.25F)) {
                    this.strafingBackwards = true;
                }

                this.entityHost.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                this.entityHost.faceEntity(attackTarget, 30.0F, 30.0F);
            } else {
                this.entityHost.getLookController().setLookPositionWithEntity(attackTarget, 30.0F, 30.0F);
            }

            float lvt_4_1_;
            if (--this.rangedAttackTime == 0) {
                if (!canSee) {
                    return;
                }

                lvt_4_1_ = MathHelper.sqrt(distance) / this.attackRadius;
                float lvt_5_1_ = MathHelper.clamp(lvt_4_1_, 0.1F, 1.0F);
                this.rangedAttackMob.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
                this.rangedAttackTime = MathHelper.floor(lvt_4_1_ * (float) (this.maxRangeInterval - this.rangeInterval) + (float) this.rangeInterval);
            } else if (this.rangedAttackTime < 0) {
                lvt_4_1_ = MathHelper.sqrt(distance) / this.attackRadius;
                this.rangedAttackTime = MathHelper.floor(lvt_4_1_ * (float) (this.maxRangeInterval - this.rangeInterval) + (float) this.rangeInterval);
            }
        }
    }
}