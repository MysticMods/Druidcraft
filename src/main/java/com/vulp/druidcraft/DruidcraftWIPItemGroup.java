package com.vulp.druidcraft;

import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class DruidcraftWIPItemGroup extends ItemGroup
{
    public DruidcraftWIPItemGroup()
    {
        super("druidcraft_wip");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.knife
        );
    }


}
