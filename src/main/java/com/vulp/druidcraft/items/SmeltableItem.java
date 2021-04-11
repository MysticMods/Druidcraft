package com.vulp.druidcraft.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;

public class SmeltableItem extends Item implements IForgeItem {
    private final int burnTime;

    public SmeltableItem(Properties properties) {
        super(properties);
        this.burnTime = properties.burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack)
    {
        return burnTime;
    }

    public static class Properties extends Item.Properties {
        private int burnTime = 0;

        public SmeltableItem.Properties burnTime(int burnTimeIn) {
            this.burnTime = burnTimeIn;
            return this;
        }
    }
}
