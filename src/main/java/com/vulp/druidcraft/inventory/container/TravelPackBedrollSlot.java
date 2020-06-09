package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.items.BedrollItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TravelPackBedrollSlot extends Slot {
    private final TravelPackContainer container;

    public TravelPackBedrollSlot(TravelPackContainer container, IInventory inventory, int p_i50084_3_, int p_i50084_4_, int p_i50084_5_) {
        super(inventory, p_i50084_3_, p_i50084_4_, p_i50084_5_);
        this.container = container;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return itemStack.getItem() instanceof BedrollItem;
    }

}