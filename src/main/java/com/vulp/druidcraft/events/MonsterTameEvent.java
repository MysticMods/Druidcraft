package com.vulp.druidcraft.events;

import com.vulp.druidcraft.entities.TameableFlyingMonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MonsterTameEvent extends LivingEvent
{
    private final TameableFlyingMonsterEntity monster;
    private final PlayerEntity tamer;

    public MonsterTameEvent(TameableFlyingMonsterEntity monster, PlayerEntity tamer)
    {
        super(monster);
        this.monster = monster;
        this.tamer = tamer;
    }

    public TameableFlyingMonsterEntity getMonster()
    {
        return monster;
    }

    public PlayerEntity getTamer()
    {
        return tamer;
    }
}