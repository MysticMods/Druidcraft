package com.vulp.druidcraft.events;

import com.vulp.druidcraft.entities.TameableMonster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MonsterTameEvent extends LivingEvent
{
    private final TameableMonster monster;
    private final PlayerEntity tamer;

    public MonsterTameEvent(TameableMonster monster, PlayerEntity tamer)
    {
        super(monster);
        this.monster = monster;
        this.tamer = tamer;
    }

    public TameableMonster getMonster()
    {
        return monster;
    }

    public PlayerEntity getTamer()
    {
        return tamer;
    }
}