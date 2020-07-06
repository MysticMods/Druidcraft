package com.vulp.druidcraft.entities.AI.goals;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.IConditionalRangedAttackMob;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class ConditionalRangedAttackGoal extends Goal {

    private final MobEntity entityHost;
    private final IConditionalRangedAttackMob rangedAttackEntityHost;
    private LivingEntity attackTarget;
    private final double entityMoveSpeed;
    private int seeTime;
    private final float attackRadius;
    private final float maxAttackDistance;

    public ConditionalRangedAttackGoal(IConditionalRangedAttackMob mob, double moveSpeed, float attackRadius) {
        if (!(mob instanceof LivingEntity)) {
            throw new IllegalArgumentException("ConditionalRangedAttackGoal requires Mob implements IConditionalRangedAttackMob");
        } else {
            this.rangedAttackEntityHost = mob;
            this.entityHost = (MobEntity)mob;
            this.entityMoveSpeed = moveSpeed;
            this.attackRadius = attackRadius;
            this.maxAttackDistance = attackRadius * attackRadius;
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }
    }

    public boolean shouldExecute() {
        if (this.rangedAttackEntityHost.shouldAttackWithRange()) {
            LivingEntity entity = this.entityHost.getAttackTarget();
            if (entity != null && entity.isAlive()) {
                this.attackTarget = entity;
                return true;
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting() {
        if (this.rangedAttackEntityHost.shouldAttackWithRange()) {
            return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
        }
        return false;
    }

    public void resetTask() {
        this.attackTarget = null;
        this.seeTime = 0;
    }

    public void tick() {
        double lvt_1_1_ = this.entityHost.getDistanceSq(this.attackTarget.getPosX(), this.attackTarget.getPosY(), this.attackTarget.getPosZ());
        boolean lvt_3_1_ = this.entityHost.getEntitySenses().canSee(this.attackTarget);
        if (lvt_3_1_) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (lvt_1_1_ <= (double)this.maxAttackDistance && this.seeTime >= 5) {
            this.entityHost.getNavigator().clearPath();
        } else {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }

        this.entityHost.getLookController().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
        if (!lvt_3_1_) {
            return;
        }

        float lvt_4_1_ = MathHelper.sqrt(lvt_1_1_) / this.attackRadius;
        float lvt_5_1_ = MathHelper.clamp(lvt_4_1_, 0.1F, 1.0F);
        this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
        this.rangedAttackEntityHost.resetCondition();


    }

}