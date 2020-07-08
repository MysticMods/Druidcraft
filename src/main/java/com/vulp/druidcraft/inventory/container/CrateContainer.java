package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.registry.GUIRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;

public class CrateContainer extends Container {

    private final IInventory mainCrateInv;
    private final int numRows;

    private CrateContainer(ContainerType<?> type, int id, PlayerInventory player, int rows) {
        this(type, id, player, new Inventory(9 * rows), rows);
    }

    public static CrateContainer createGeneric9X12(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X12, id, player, 12);
    }
    public static CrateContainer createGeneric9X24(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X24, id, player, 24);
    }
    public static CrateContainer createGeneric9X12(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X12, id, player, blockEntity, 12);
    }
    public static CrateContainer createGeneric9X24(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X24, id, player, blockEntity, 24);
    }

    public CrateContainer(ContainerType<?> type, int id, PlayerInventory playerInventoryIn, IInventory inventory, int rows) {
        super(type, id);
        assertInventorySize(inventory, rows * 9);
        this.mainCrateInv = inventory;
        this.numRows = rows;
        inventory.openInventory(playerInventoryIn.player);
        int i1;
        int j1;

        if (rows == 12) {
            for (int j = 0; j < 9; ++j) {
                for (int k = 0; k < 12; ++k) {
                    this.addSlot(new Slot(inventory, k + j * 12, 8 + k * 18, 18 + j * 18));
                }
            }
            for (i1 = 0; i1 < 3; ++i1) {
                for (j1 = 0; j1 < 9; ++j1) {
                    this.addSlot(new Slot(playerInventoryIn, j1 + i1 * 9 + 9, 35 + j1 * 18, 194 + i1 * 18));
                }
            }
            for (i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventoryIn, i1, 35 + i1 * 18, 252));
            }
        }
        if (rows == 24) {
            for (int j = 0; j < 12; ++j) {
                for (int k = 0; k < 18; ++k) {
                    this.addSlot(new Slot(inventory, k + j * 18, 8 + k * 18, 18 + j * 18));
                }
            }
            for (i1 = 0; i1 < 3; ++i1) {
                for (j1 = 0; j1 < 9; ++j1) {
                    this.addSlot(new Slot(playerInventoryIn, j1 + i1 * 9 + 9, 89 + j1 * 18, 248 + i1 * 18));
                }
            }
            for (i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventoryIn, i1, 89 + i1 * 18, 306));
            }
        }
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.numRows * 9) {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
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

    /**
     * Called when the container is closed.
     */
    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.mainCrateInv.closeInventory(playerIn);
    }

    /**
     * Gets the inventory associated with this chest container.
     */
    public IInventory getMainInventory() {
        return this.mainCrateInv;
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
