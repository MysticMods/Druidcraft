package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.client.gui.screen.inventory.CrateScreen;
import com.vulp.druidcraft.registry.GUIRegistry;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrateContainer extends Container {

    private final IInventory mainCrateInv;
    private final int numRows;
    private IInventory TMP_INVENTORY = new Inventory(54);
    private float scrollPos = 0.0f;

    private CrateContainer(ContainerType<?> type, int id, PlayerInventory player, int rows) {
        this(type, id, player, new Inventory(9 * rows), rows);
    }

    public static CrateContainer createGeneric9X9(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X9, id, player, 9);
    }

    public static CrateContainer createGeneric9X12(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X12, id, player, 12);
    }

    public static CrateContainer createGeneric9X15(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X15, id, player, 15);
    }

    public static CrateContainer createGeneric9X18(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X18, id, player, 18);
    }

    public static CrateContainer createGeneric9X21(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X21, id, player, 21);
    }

    public static CrateContainer createGeneric9X24(int id, PlayerInventory player) {
        return new CrateContainer(GUIRegistry.generic_9X24, id, player, 24);
    }

    public static CrateContainer createGeneric9X9(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X9, id, player, blockEntity, 9);
    }

    public static CrateContainer createGeneric9X12(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X12, id, player, blockEntity, 12);
    }

    public static CrateContainer createGeneric9X15(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X15, id, player, blockEntity, 15);
    }

    public static CrateContainer createGeneric9X18(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X18, id, player, blockEntity, 18);
    }

    public static CrateContainer createGeneric9X21(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X21, id, player, blockEntity, 21);
    }

    public static CrateContainer createGeneric9X24(int id, PlayerInventory player, IInventory blockEntity) {
        return new CrateContainer(GUIRegistry.generic_9X24, id, player, blockEntity, 24);
    }

    public CrateContainer(ContainerType<?> type, int id, PlayerInventory playerInventoryIn, IInventory inventory, int rows) {
        super(type, id);
        assertInventorySize(inventory, rows * 9);
        this.mainCrateInv = inventory;
        this.numRows = rows;
        this.scrollPos = 0.0f;
        inventory.openInventory(playerInventoryIn.player);
        int i = (this.numRows - 4) * 18;

        for(int j = 0; j < 6; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(this.TMP_INVENTORY, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        // FIX: Hotbar slots aren't showing signs of existing. Look closely at the mapped coords.

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventoryIn, j1 + l * 9 + 9, 8 + j1 * 18, 140 + l * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventoryIn, i1, 8 + i1 * 18, 198 + i));
        }

        this.scrollTo(0.0F);
    }


    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        int i = (int) ((double) (this.scrollPos * (float) (getNumRows() - 6)) + 0.5D);
        if (i < 0) {
            i = 0;
        }
        for (int k = 0; k < 6; ++k) {
            for (int l = 0; l < 9; ++l) {
                int i1 = l + (k + i) * 9;
                this.mainCrateInv.setInventorySlotContents(i1, this.TMP_INVENTORY.getStackInSlot(l + k * 9));
            }
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    /**
     * Updates the gui slots ItemStack's based on scroll position.
     */

    // FIX: Extreme desyncs of inventory. Idk what to do.

    public void scrollTo(float pos) {
        this.scrollPos = pos;
        int i = (int)((double)(pos * (float)(getNumRows() - 6)) + 0.5D);
        if (i < 0) {
            i = 0;
        }
        for(int k = 0; k < 6; ++k) {
            for(int l = 0; l < 9; ++l) {
                int i1 = l + (k + i) * 9;
                this.TMP_INVENTORY.setInventorySlotContents(l + k * 9, this.mainCrateInv.getStackInSlot(i1));
            }
        }
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < getNumRows() * 9) {
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

    @OnlyIn(Dist.CLIENT)
    public int getNumRows() {
        return this.numRows;
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
