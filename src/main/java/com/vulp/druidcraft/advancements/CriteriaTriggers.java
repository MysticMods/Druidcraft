package com.vulp.druidcraft.advancements;

import com.vulp.druidcraft.advancements.criterion.TameMonsterTrigger;
import com.vulp.druidcraft.entities.TameableAirSwimMonster;
import com.vulp.druidcraft.entities.TameableMonster;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CriteriaTriggers extends net.minecraft.advancements.CriteriaTriggers {
        public static final TameMonsterTrigger TAME_MONSTER = register(new TameMonsterTrigger());

    public static void TAME_MONSTER(ServerPlayerEntity player, TameableMonster tameableMonster) {
    }
}