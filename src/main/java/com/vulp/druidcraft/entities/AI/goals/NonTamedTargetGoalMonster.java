package com.vulp.druidcraft.entities.AI.goals;

import com.vulp.druidcraft.entities.TameableMonster;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.TameableEntity;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class NonTamedTargetGoalMonster<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final TameableMonster tameable;

    public NonTamedTargetGoalMonster(TameableMonster entity, Class<T> target, boolean p_i48571_3_, @Nullable Predicate<LivingEntity> p_i48571_4_) {
        super(entity, target, 10, p_i48571_3_, false, p_i48571_4_);
        this.tameable = entity;
    }

    public NonTamedTargetGoalMonster(TameableMonster entity, Class<T> target, boolean p_i48571_3_) {
        super(entity, target, 10, p_i48571_3_, false, null);
        this.tameable = entity;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        return !this.tameable.isTamed() && super.shouldExecute();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.targetEntitySelector != null ? this.targetEntitySelector.canTarget(this.goalOwner, this.nearestTarget) : super.shouldContinueExecuting();
    }
}