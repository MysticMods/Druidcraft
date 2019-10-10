package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.advancements.CriteriaTriggers;
import com.vulp.druidcraft.entities.AI.goals.SitGoalMonster;
import com.vulp.druidcraft.pathfinding.FlyingPathNavigator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class TameableAirSwimMonster extends TameableMonster {

    TameableAirSwimMonster(EntityType<? extends TameableMonster> type, World p_i48553_2_) {
        super(type, p_i48553_2_);
    }

    @Override
    public FlyingPathNavigator getNavigator() {
        if (this.isPassenger() && this.getRidingEntity() instanceof TameableAirSwimMonster) {
            MobEntity mobentity = (MobEntity)this.getRidingEntity();
            return (FlyingPathNavigator) mobentity.getNavigator();
        } else {
            return (FlyingPathNavigator) this.navigator;
        }
    }
}