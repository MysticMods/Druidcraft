package com.vulp.druidcraft.entities.AI.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class RandomFlyingGoal extends RandomSwimmingGoal {
    public RandomFlyingGoal(CreatureEntity p_i48937_1_, double p_i48937_2_, int p_i48937_4_) {
        super(p_i48937_1_, p_i48937_2_, p_i48937_4_);
    }

    @Override
    protected Vector3d getPosition() {
        Vector3d vec3d = RandomPositionGenerator.findRandomTarget(this.creature, 10, 7);

        for(int i = 0; vec3d != null && !this.creature.world.getBlockState(new BlockPos(vec3d)).allowsMovement(this.creature.world, new BlockPos(vec3d), PathType.WATER) && i++ < 10; vec3d = RandomPositionGenerator.findRandomTarget(this.creature, 10, 7)) {

        }
        return vec3d;
    }
}
