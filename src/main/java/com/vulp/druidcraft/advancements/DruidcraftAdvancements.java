package com.vulp.druidcraft.advancements;

import com.vulp.druidcraft.advancements.criterion.TameMonsterTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class DruidcraftAdvancements {
  public static final TameMonsterTrigger TAME_MONSTER = CriteriaTriggers.register(new TameMonsterTrigger());
}