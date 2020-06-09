package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.items.TravelPackItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class TravelPackSlot extends Slot {

    public TravelPackSlot(IInventory inventory, int index, int posX, int posY) {
        super(inventory, index, posX, posY);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return !(itemStack.getItem() instanceof TravelPackItem);
    }
}

