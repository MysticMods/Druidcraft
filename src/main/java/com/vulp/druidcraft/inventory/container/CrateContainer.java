package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.blocks.tileentities.CrateTileEntity;
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
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class CrateContainer extends Container {
    private IItemHandler mainCrateInv;
    private final int numRows;
    private CrateTileEntity crate;

    private CrateContainer(ContainerType<?> type, int id, PlayerInventory player, int rows) {
        this(type, id, player, new ItemStackHandler(9 * rows), rows, null);
    }

    public static CrateContainer createGeneric9X3(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X3, id, player, 3);
    }
    public static CrateContainer createGeneric9X6(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X6, id, player, 6);
    }
    public static CrateContainer createGeneric9X12(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X12, id, player, 12);
    }
    public static CrateContainer createGeneric9X24(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X24, id, player, 24);
    }

/*    public static CrateContainer createGeneric9X12(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X12, id, player, blockEntity, 12);
    }
    public static CrateContainer createGeneric9X24(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X24, id, player, blockEntity, 24);
    }*/

    public CrateContainer(ContainerType<?> type, int id, PlayerInventory playerInventoryIn, IItemHandler inventory, int rows, @Nullable CrateTileEntity tile) {
        super(type, id);
        assertIItemHandlerSize(inventory, rows * 9);
        this.mainCrateInv = inventory;
        this.numRows = rows;
        this.crate = tile;
        if (this.crate != null) {
            this.crate.openInventory(playerInventoryIn.player);
        }
        int i1;
        int j1;

        if (rows == 3) {
            for (int j = 0; j < 9; ++j) {
                for (int k = 0; k < 3; ++k) {
                    this.addSlot(new SlotItemHandler(inventory, k + j * 12, 8 + k * 18, 18 + j * 18));
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
        if (rows == 6) {
            for (int j = 0; j < 9; ++j) {
                for (int k = 0; k < 6; ++k) {
                    this.addSlot(new SlotItemHandler(inventory, k + j * 12, 8 + k * 18, 18 + j * 18));
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
        if (rows == 12) {
            for (int j = 0; j < 9; ++j) {
                for (int k = 0; k < 12; ++k) {
                    this.addSlot(new SlotItemHandler(inventory, k + j * 12, 8 + k * 18, 18 + j * 18));
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
                    this.addSlot(new SlotItemHandler(inventory, k + j * 18, 8 + k * 18, 18 + j * 18));
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
        Slot slot = this.inventorySlots.get(index);
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
        if (this.crate != null) {
            this.crate.closeInventory(playerIn);
        }
    }

    /**
     * Gets the inventory associated with this chest container.
     */
    public IItemHandler getMainInventory() {
        return this.mainCrateInv;
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        // TODO: Check distance
        return true;
    }

   private static void assertIItemHandlerSize(IItemHandler inventoryIn, int minSize) {
      int i = inventoryIn.getSlots();
      if (i < minSize) {
         throw new IllegalArgumentException("Container size " + i + " is smaller than expected " + minSize);
      }
   }
}
