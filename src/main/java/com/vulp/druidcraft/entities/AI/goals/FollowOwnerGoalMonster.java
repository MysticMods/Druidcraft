package com.vulp.druidcraft.entities.AI.goals;

import com.vulp.druidcraft.entities.TameableAirSwimMonsterEntityEntity;
import com.vulp.druidcraft.pathfinding.FlyingPathNavigator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

import java.util.EnumSet;

public class FollowOwnerGoalMonster extends Goal {
    private final TameableAirSwimMonsterEntityEntity tameable;
    private LivingEntity owner;
    protected final IWorldReader world;
    private final double followSpeed;
    private final FlyingPathNavigator navigator;
    private int timeToRecalcPath;
    private final float maxDist;
    private final float minDist;
    private float oldWaterCost;

    public FollowOwnerGoalMonster(TameableAirSwimMonsterEntityEntity tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
        this.tameable = tameableIn;
        this.world = tameableIn.world;
        this.followSpeed = followSpeedIn;
        this.navigator = tameableIn.getNavigator();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        LivingEntity livingentity = this.tameable.getOwner();
        if (livingentity == null) {
            return false;
        } else if (livingentity instanceof PlayerEntity && livingentity.isSpectator()) {
            return false;
        } else if (this.tameable.isSitting()) {
            return false;
        } else if (this.tameable.getDistanceSq(livingentity) < (double) (this.minDist * this.minDist)) {
            return false;
        } else {
            this.owner = livingentity;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.navigator.noPath() && this.tameable.getDistanceSq(this.owner) > (double) (this.maxDist * this.maxDist) && !this.tameable.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tameable.getPathPriority(PathNodeType.WATER);
        this.tameable.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void resetTask() {
        this.owner = null;
        this.navigator.clearPath();
        this.tameable.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        this.tameable.getLookController().setLookPositionWithEntity(this.owner, 10.0F, (float)this.tameable.getVerticalFaceSpeed());
        if (!this.tameable.isSitting()) {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                if (!this.tameable.getLeashed() && !this.tameable.isPassenger()) {
                    if (this.tameable.getDistanceSq(this.owner) > 576.0D) {
                        int i = MathHelper.floor(this.owner.posX) - 2;
                        int j = MathHelper.floor(this.owner.posZ) - 2;
                        int k = MathHelper.floor(this.owner.getBoundingBox().minY);

                        for (int l = 0; l <= 4; ++l) {
                            for (int i1 = 0; i1 <= 4; ++i1) {
                                if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.canTeleportToBlock(new BlockPos(i + l, k - 1, j + i1))) {
                                    this.tameable.setLocationAndAngles((float) (i + l) + 0.5F, k + 1, (float) (j + i1) + 0.5F, this.tameable.rotationYaw, this.tameable.rotationPitch);
                                    this.navigator.clearPath();

                                }
                            }
                        }
                    }
                }
                this.navigator.tryMoveToEntityLiving(this.owner, this.followSpeed);
            }
        }
    }

    private boolean canTeleportToBlock(BlockPos pos) {
        BlockState blockstate = this.world.getBlockState(pos);
        return blockstate.func_215682_a(this.world, pos, this.tameable) && this.world.isAirBlock(pos.up(2)) && this.world.isAirBlock(pos.up(3));
    }
}