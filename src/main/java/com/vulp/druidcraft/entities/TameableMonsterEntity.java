package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.pathfinding.FlyingPathNavigator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;

public class TameableMonsterEntity extends TameableFlyingMonsterEntity {

    TameableMonsterEntity(EntityType<? extends TameableFlyingMonsterEntity> type, World p_i48553_2_) {
        super(type, p_i48553_2_);
    }

    @Override
    public FlyingPathNavigator getNavigator() {
        if (this.isPassenger() && this.getRidingEntity() instanceof TameableMonsterEntity) {
            MobEntity mobentity = (MobEntity)this.getRidingEntity();
            return (FlyingPathNavigator) mobentity.getNavigator();
        } else {
            return (FlyingPathNavigator) this.navigator;
        }
    }
}