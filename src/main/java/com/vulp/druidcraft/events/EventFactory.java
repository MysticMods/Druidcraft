package com.vulp.druidcraft.events;

import com.vulp.druidcraft.entities.TameableMonster;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;

public class EventFactory extends ForgeEventFactory {

    public static boolean onMonsterTame(TameableMonster monster, PlayerEntity tamer)
    {
        return MinecraftForge.EVENT_BUS.post(new MonsterTameEvent(monster, tamer));
    }
}
