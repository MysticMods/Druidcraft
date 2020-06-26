package com.vulp.druidcraft.api;

import net.minecraft.entity.LivingEntity;

public interface ICooldownAttackMob {
    boolean canUseAttack(LivingEntity entity);
}
