package com.vulp.druidcraft.events;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MonsterTameEvent extends LivingEvent
{
    private final MonsterEntity monster;
    private final PlayerEntity tamer;

    public MonsterTameEvent(MonsterEntity monster, PlayerEntity tamer)
    {
        super(monster);
        this.monster = monster;
        this.tamer = tamer;
    }

    public MonsterEntity getMonster()
    {
        return monster;
    }

    public PlayerEntity getTamer()
    {
        return tamer;
    }
}