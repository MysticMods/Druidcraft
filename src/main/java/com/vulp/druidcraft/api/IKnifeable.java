package com.vulp.druidcraft.api;

import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

@FunctionalInterface
public interface IKnifeable {
    ActionResultType onKnife(ItemUseContext context);
}
