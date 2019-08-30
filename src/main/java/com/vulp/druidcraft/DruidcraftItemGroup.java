package com.vulp.druidcraft;

import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class DruidcraftItemGroup extends ItemGroup
{
    public DruidcraftItemGroup()
    {
        super("druidcraft");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.hemp);
    }
}
