package com.vulp.druidcraft.inventory;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class OctoSidedInventory implements IInventory {
    private final IInventory inv1;
    private final IInventory inv2;
    private final IInventory inv3;
    private final IInventory inv4;
    private final IInventory inv5;
    private final IInventory inv6;
    private final IInventory inv7;
    private final IInventory inv8;

    public OctoSidedInventory(IInventory inventory1, IInventory inventory2, IInventory inventory3, IInventory inventory4, IInventory inventory5, IInventory inventory6, IInventory inventory7, IInventory inventory8) {

        this.inv1 = inventory1;
        this.inv2 = inventory2;
        this.inv3 = inventory3;
        this.inv4 = inventory4;
        this.inv5 = inventory5;
        this.inv6 = inventory6;
        this.inv7 = inventory7;
        this.inv8 = inventory8;
        ArrayList<IInventory> invList = new ArrayList<>();
        invList.add(inventory1);
        invList.add(inventory2);
        invList.add(inventory3);
        invList.add(inventory4);
        invList.add(inventory5);
        invList.add(inventory6);
        invList.add(inventory7);
        invList.add(inventory8);
        for (int i = 0; i < invList.size(); i++) {
            for (int j = 0; j < invList.size(); j++) {
                if (invList.get(i) != invList.get(j)) {
                    if (invList.get(i) == null && invList.get(j) != null) {
                        invList.set(i, invList.get(j));
                    }
                }
            }
        }
    }

    public int getSizeInventory() {
        return this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory() + this.inv7.getSizeInventory() + this.inv8.getSizeInventory();
    }

    public boolean isEmpty() {
        return this.inv1.isEmpty() && this.inv2.isEmpty() && this.inv3.isEmpty() && this.inv4.isEmpty() && this.inv5.isEmpty() && this.inv6.isEmpty() && this.inv7.isEmpty() && this.inv8.isEmpty();
    }

    public boolean isPartOfOctoCrate(IInventory inventoryIn) {
        return this.inv1 == inventoryIn || this.inv2 == inventoryIn || this.inv3 == inventoryIn || this.inv4 == inventoryIn || this.inv5 == inventoryIn || this.inv6 == inventoryIn || this.inv7 == inventoryIn || this.inv8 == inventoryIn;
    }

    public ItemStack getStackInSlot(int index) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                    if (index >= this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                        if (index >= this.inv5.getSizeInventory()  + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                            if (index >= this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                if (index >= this.inv7.getSizeInventory() + this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                    return this.inv8.getStackInSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory() + this.inv7.getSizeInventory()));
                                } return this.inv7.getStackInSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory()));
                            } return this.inv6.getStackInSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + inv5.getSizeInventory()));
                        } return this.inv5.getStackInSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory()));
                    } return this.inv4.getStackInSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()));
                } return this.inv3.getStackInSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()));
            } return this.inv2.getStackInSlot(index - this.inv1.getSizeInventory());
        } else return this.inv1.getStackInSlot(index);
    }

    public ItemStack decrStackSize(int index, int count) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                    if (index >= this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                        if (index >= this.inv5.getSizeInventory()  + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                            if (index >= this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                if (index >= this.inv7.getSizeInventory() + this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                    return this.inv8.decrStackSize(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory() + this.inv7.getSizeInventory()), count);
                                } return this.inv7.decrStackSize(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory()), count);
                            } return this.inv6.decrStackSize(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + inv5.getSizeInventory()), count);
                        } return this.inv5.decrStackSize(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory()), count);
                    } return this.inv4.decrStackSize(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()), count);
                } return this.inv3.decrStackSize(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()), count);
            } return this.inv2.decrStackSize(index - this.inv1.getSizeInventory(), count);
        } else return this.inv1.decrStackSize(index, count);
    }

    public ItemStack removeStackFromSlot(int index) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                    if (index >= this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                        if (index >= this.inv5.getSizeInventory()  + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                            if (index >= this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                if (index >= this.inv7.getSizeInventory() + this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                    return this.inv8.removeStackFromSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory() + this.inv7.getSizeInventory()));
                                } return this.inv7.removeStackFromSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory()));
                            } return this.inv6.removeStackFromSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + inv5.getSizeInventory()));
                        } return this.inv5.removeStackFromSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory()));
                    } return this.inv4.removeStackFromSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()));
                } return this.inv3.removeStackFromSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()));
            } return this.inv2.removeStackFromSlot(index - this.inv1.getSizeInventory());
        } else return this.inv1.removeStackFromSlot(index);
    }

    public void setInventorySlotContents(int index, ItemStack stack) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                    if (index >= this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                        if (index >= this.inv5.getSizeInventory()  + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                            if (index >= this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                if (index >= this.inv7.getSizeInventory() + this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                    this.inv8.setInventorySlotContents(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory() + this.inv7.getSizeInventory()), stack);
                                    return;
                                } this.inv7.setInventorySlotContents(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory()), stack);
                                return;
                            } this.inv6.setInventorySlotContents(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + inv5.getSizeInventory()), stack);
                            return;
                        } this.inv5.setInventorySlotContents(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory()), stack);
                        return;
                    } this.inv4.setInventorySlotContents(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()), stack);
                    return;
                } this.inv3.setInventorySlotContents(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()), stack);
                return;
            } this.inv2.setInventorySlotContents(index - this.inv1.getSizeInventory(), stack);
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
        this.inv5.markDirty();
        this.inv6.markDirty();
        this.inv7.markDirty();
        this.inv8.markDirty();
    }

    public boolean isUsableByPlayer(PlayerEntity player) {
        return this.inv1.isUsableByPlayer(player) && this.inv2.isUsableByPlayer(player) && this.inv3.isUsableByPlayer(player) && this.inv4.isUsableByPlayer(player) && this.inv5.isUsableByPlayer(player) && this.inv6.isUsableByPlayer(player) && this.inv7.isUsableByPlayer(player) && this.inv8.isUsableByPlayer(player);
    }

    public void openInventory(PlayerEntity player) {
        this.inv1.openInventory(player);
        this.inv2.openInventory(player);
        this.inv3.openInventory(player);
        this.inv4.openInventory(player);
        this.inv5.openInventory(player);
        this.inv6.openInventory(player);
        this.inv7.openInventory(player);
        this.inv8.openInventory(player);
    }

    public void closeInventory(PlayerEntity player) {
        this.inv1.closeInventory(player);
        this.inv2.closeInventory(player);
        this.inv3.closeInventory(player);
        this.inv4.closeInventory(player);
        this.inv5.closeInventory(player);
        this.inv6.closeInventory(player);
        this.inv7.closeInventory(player);
        this.inv8.closeInventory(player);
    }

    public boolean isItemValidForSlot(int index, ItemStack stack) {

        if (index >= this.inv1.getSizeInventory()) {
            if (index >= this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                if (index >= this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                    if (index >= this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                        if (index >= this.inv5.getSizeInventory()  + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                            if (index >= this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                if (index >= this.inv7.getSizeInventory() + this.inv6.getSizeInventory() + this.inv5.getSizeInventory() + this.inv4.getSizeInventory() + this.inv3.getSizeInventory() + this.inv2.getSizeInventory() + this.inv1.getSizeInventory()) {
                                    return this.inv8.isItemValidForSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory() + this.inv7.getSizeInventory()), stack);
                                } return this.inv7.isItemValidForSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + this.inv5.getSizeInventory() + this.inv6.getSizeInventory()), stack);
                            } return this.inv6.isItemValidForSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory() + inv5.getSizeInventory()), stack);
                        } return this.inv5.isItemValidForSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory() + this.inv4.getSizeInventory()), stack);
                    } return this.inv4.isItemValidForSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory() + this.inv3.getSizeInventory()), stack);
                } return this.inv3.isItemValidForSlot(index - (this.inv1.getSizeInventory() + this.inv2.getSizeInventory()), stack);
            } return this.inv2.isItemValidForSlot(index - this.inv1.getSizeInventory(), stack);
        } else return this.inv1.isItemValidForSlot(index, stack);
    }

    public void clear() {
        this.inv1.clear();
        this.inv2.clear();
        this.inv3.clear();
        this.inv4.clear();
        this.inv5.clear();
        this.inv6.clear();
        this.inv7.clear();
        this.inv8.clear();
    }
}
