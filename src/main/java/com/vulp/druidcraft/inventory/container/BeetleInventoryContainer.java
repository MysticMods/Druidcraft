package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.entities.BeetleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BeetleInventoryContainer extends Container {
    private IInventory beetleInventory;
    private BeetleEntity beetle;

    public BeetleInventoryContainer(int windowID, PlayerInventory playerInventory, PacketBuffer extraData, IInventory inventory, final BeetleEntity beetle) {
        super((ContainerType)null, windowID);
        this.beetleInventory = inventory;
        this.beetle = beetle;
        inventory.openInventory(playerInventory.player);
        this.addSlot(new Slot(inventory, 0, 18, 72) {
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() == Items.SADDLE && !this.getHasStack();
            }

            @OnlyIn(Dist.CLIENT)
            public boolean isEnabled() {
                return true;
            }
        });

        int j1;
        int k1;
        if (beetle instanceof BeetleEntity && beetle.hasChest()) {
            for(j1 = 0; j1 < 3; ++j1) {
                for(k1 = 0; k1 < 9; ++k1) {
                    this.addSlot(new Slot(inventory, 2 + k1 + j1 * 9, 80 + k1 * 18, 18 + j1 * 18));
                }
            }
        }

        for(j1 = 0; j1 < 3; ++j1) {
            for(k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(inventory, k1 + j1 * 9 + 9, 8 + k1 * 18, 102 + j1 * 18 + -18));
            }
        }

        for(j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(inventory, j1, 8 + j1 * 18, 142));
        }

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.beetleInventory.isUsableByPlayer(playerIn) && this.beetle.isAlive() && this.beetle.getDistance(playerIn) < 8.0F;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.beetleInventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, this.beetleInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack()) {
                if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).isItemValid(itemstack1)) {
                if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.beetleInventory.getSizeInventory() <= 2 || !this.mergeItemStack(itemstack1, 2, this.beetleInventory.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.beetleInventory.closeInventory(playerIn);
    }

}
