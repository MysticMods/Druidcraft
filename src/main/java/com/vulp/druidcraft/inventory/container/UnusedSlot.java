package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.items.BedrollItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class UnusedSlot extends Slot {

    public UnusedSlot(IInventory inventory, int index, int posX, int posY) {
        super(inventory, index, posX, posY);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }
}
