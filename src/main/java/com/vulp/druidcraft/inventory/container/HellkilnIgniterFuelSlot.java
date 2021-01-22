package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class HellkilnIgniterFuelSlot extends Slot {

    public HellkilnIgniterFuelSlot(HellkilnIgniterContainer container, IInventory inventory, int p_i50084_3_, int p_i50084_4_, int p_i50084_5_) {
        super(inventory, p_i50084_3_, p_i50084_4_, p_i50084_5_);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() == ItemRegistry.crushed_fiery_glass;
    }

}
