package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.registry.GUIRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrateContainer extends Container {

    private final IInventory mainCrateInv;
    private final int numRows;
    private static final Inventory TMP_INVENTORY = new Inventory(45);
    public final NonNullList<ItemStack> itemList = NonNullList.create();


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

    public CrateContainer(ContainerType<?> type, int id, PlayerInventory playerInventoryIn, IInventory p_i50092_4_, int rows) {
        super(type, id);
        assertInventorySize(p_i50092_4_, rows * 9);
        this.mainCrateInv = p_i50092_4_;
        this.numRows = rows;
        p_i50092_4_.openInventory(playerInventoryIn.player);
        int i = (this.numRows - 4) * 18;

        for(int j = 0; j < this.numRows; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(p_i50092_4_, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventoryIn, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventoryIn, i1, 8 + i1 * 18, 161 + i));
        }

        this.scrollTo(0.0F);
    }

    /**
     * Updates the gui slots ItemStack's based on scroll position.
     */
    public void scrollTo(float pos) {
        int i = (this.itemList.size() + 9 - 1) / 9 - 6;
        int j = (int)((double)(pos * (float)i) + 0.5D);
        if (j < 0) {
            j = 0;
        }

        for(int k = 0; k < 6; ++k) {
            for(int l = 0; l < 9; ++l) {
                int i1 = l + (k + j) * 9;
                if (i1 >= 0 && i1 < this.itemList.size()) {
                    CrateContainer.TMP_INVENTORY.setInventorySlotContents(l + k * 9, this.itemList.get(i1));
                } else {
                    CrateContainer.TMP_INVENTORY.setInventorySlotContents(l + k * 9, ItemStack.EMPTY);
                }
            }
        }

    }

    public boolean canScroll() {
        return this.itemList.size() > 54;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
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
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.mainCrateInv.closeInventory(playerIn);
    }

    /**
     * Gets the inventory associated with this chest container.
     */
    public IInventory getLowerCrateInventory() {
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
