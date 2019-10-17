package com.vulp.druidcraft.entities.AI.goals;

import com.vulp.druidcraft.entities.TameableAirSwimMonsterEntityEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SitGoalMonster extends Goal {
    private final TameableAirSwimMonsterEntityEntity tameable;
    private boolean isSitting;

    public SitGoalMonster(TameableAirSwimMonsterEntityEntity entityIn) {
        this.tameable = entityIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean shouldContinueExecuting() {
        return this.isSitting;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute() {
        if (!this.tameable.isTamed()) {
            return false;
        } else if (this.tameable.isInWaterOrBubbleColumn()) {
            return false;
        } else {
            LivingEntity livingentity = this.tameable.getOwner();
            if (livingentity == null) {
                return true;
            } else {
                return this.tameable.getDistanceSq(livingentity) < 144.0D && livingentity.getRevengeTarget() != null ? false : this.isSitting;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting() {
        this.tameable.getNavigator().clearPath();
        this.tameable.setSitting(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
	public void resetTask() {
        this.tameable.setSitting(false);
    }

    /**
     * Sets the sitting flag.
     */
    public void setSitting(boolean sitting) {
        this.isSitting = sitting;
    }
}