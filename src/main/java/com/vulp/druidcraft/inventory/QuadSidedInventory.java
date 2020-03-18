package com.vulp.druidcraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class QuadSidedInventory implements IInventory {
    private final IInventory inv1;
    private final IInventory inv2;
    private final IInventory inv3;
    private final IInventory inv4;
    private final ArrayList<IInventory> invList = new ArrayList<>();

    public QuadSidedInventory(IInventory inventory1, IInventory inventory2, IInventory inventory3, IInventory inventory4) {
        IInventory[] invList = {inventory1, inventory2, inventory3, inventory4};

        for (int i = 0; i > invList.length; i++) {
            for (int j = 0; j > invList.length; j++) {
                if (invList[i] != invList[j]) {
                    if (invList[i] == null && invList[j] != null) {
                        invList[i] = invList[j];
                    }
                }
            }
        }

        this.inv1 = inventory1;
        this.inv2 = inventory2;
        this.inv3 = inventory3;
        this.inv4 = inventory4;
        this.invList.add(inv1);
        this.invList.add(inv2);
        this.invList.add(inv3);
        this.invList.add(inv4);
    }

    public int getSizeInventory() {
        return this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory();
    }

    public boolean isEmpty() {
        return this.inv1.isEmpty() && this.inv2.isEmpty() && this.inv3.isEmpty() && this.inv4.isEmpty();
    }

    public boolean isPartOfQuadCrate(IInventory inventoryIn) {
        return this.inv1 == inventoryIn || this.inv2 == inventoryIn || this.inv3 == inventoryIn || this.inv4 == inventoryIn;
    }

    public ItemStack getStackInSlot(int index) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory()) {
                    return this.inv4.getStackInSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()));
                }
                return this.inv3.getStackInSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()));
            }
            return this.inv2.getStackInSlot(index - this.inv1.getSizeInventory());
        } else return this.inv1.getStackInSlot(index);
    }

    public ItemStack decrStackSize(int index, int count) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory()) {
                    return this.inv4.decrStackSize(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()), count);
                }
                return this.inv3.decrStackSize(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()), count);
            }
            return this.inv2.decrStackSize(index - this.inv1.getSizeInventory(), count);
        } else return this.inv1.decrStackSize(index, count);
    }

    public ItemStack removeStackFromSlot(int index) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory()) {
                    return this.inv4.removeStackFromSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()));
                }
                return this.inv3.removeStackFromSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()));
            }
            return this.inv2.removeStackFromSlot(index - this.inv1.getSizeInventory());
        } else return this.inv1.removeStackFromSlot(index);
    }

    public void setInventorySlotContents(int index, ItemStack stack) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory()) {
                    this.inv4.setInventorySlotContents(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()), stack);
                }
                this.inv3.setInventorySlotContents(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()), stack);
            }
            this.inv2.setInventorySlotContents(index - this.inv1.getSizeInventory(), stack);
        } else this.inv1.setInventorySlotContents(index, stack);
    }

    public int getInventoryStackLimit() {
        return this.inv1.getInventoryStackLimit();
    }

    public void markDirty() {
        this.inv1.markDirty();
        this.inv2.markDirty();
        this.inv3.markDirty();
        this.inv4.markDirty();
    }

    public boolean isUsableByPlayer(PlayerEntity player) {
        return this.inv1.isUsableByPlayer(player) && this.inv2.isUsableByPlayer(player) && this.inv3.isUsableByPlayer(player) && this.inv4.isUsableByPlayer(player);
    }

    public void openInventory(PlayerEntity player) {
        this.inv1.openInventory(player);
        this.inv2.openInventory(player);
        this.inv3.openInventory(player);
        this.inv4.openInventory(player);
    }

    public void closeInventory(PlayerEntity player) {
        this.inv1.closeInventory(player);
        this.inv2.closeInventory(player);
        this.inv3.closeInventory(player);
        this.inv4.closeInventory(player);
    }

    public boolean isItemValidForSlot(int index, ItemStack stack) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory()) {
                    return this.inv4.isItemValidForSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()), stack);
                }
                return this.inv3.isItemValidForSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()), stack);
            }
            return this.inv2.isItemValidForSlot(index - this.inv1.getSizeInventory(), stack);
        } else return this.inv1.isItemValidForSlot(index, stack);
    }

    public void clear() {
        this.inv1.clear();
        this.inv2.clear();
        this.inv3.clear();
        this.inv4.clear();
    }
}