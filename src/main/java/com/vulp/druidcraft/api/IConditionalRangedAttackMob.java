package com.vulp.druidcraft.api;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;

public interface IConditionalRangedAttackMob extends IRangedAttackMob {

    boolean shouldAttackWithRange();

    void resetCondition();

}
