package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.pathfinding.FlyingFishPathNavigator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;

public class TameableFlyingMonsterEntity extends TameableMonsterEntity {

    TameableFlyingMonsterEntity(EntityType<? extends TameableMonsterEntity> type, World p_i48553_2_) {
        super(type, p_i48553_2_);
    }

    @Override
    public FlyingFishPathNavigator getNavigator() {
        if (this.isPassenger() && this.getRidingEntity() instanceof TameableFlyingMonsterEntity) {
            MobEntity mobentity = (MobEntity)this.getRidingEntity();
            return (FlyingFishPathNavigator) mobentity.getNavigator();
        } else {
            return (FlyingFishPathNavigator) this.navigator;
        }
    }
}