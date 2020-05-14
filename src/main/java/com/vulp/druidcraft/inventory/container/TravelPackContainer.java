package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.TravelPackInventory;
import com.vulp.druidcraft.items.BedrollItem;
import com.vulp.druidcraft.registry.GUIRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class TravelPackContainer extends Container {

    private final IInventory travelPackInventory;
    private ItemStack travelPack;

    // Client
    public static TravelPackContainer createClientContainer(int id, PlayerInventory playerInventory) {
        return new TravelPackContainer(id, playerInventory, new Inventory(10), playerInventory.getCurrentItem());
    }

    // Server
    public TravelPackContainer(int id, PlayerInventory playerInventory, IInventory inventory, ItemStack travelPack) {
        super(GUIRegistry.travel_pack, id);
        this.travelPackInventory = inventory;
        this.travelPack = travelPack;

        // Storage
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new Slot(inventory, 4 + j + i * 3, 62 + j * 18, 20 + i * 18));
            }
        }

        // Misc Slots
        this.addSlot(new TravelPackBedrollSlot(this, inventory, 0, 26, 20));
        this.addSlot(new UnusedSlot(inventory, 1, 26, 38));
        this.addSlot(new UnusedSlot(inventory, 2, 134, 20));
        this.addSlot(new UnusedSlot(inventory, 3, 134, 38));

        // Player Inventory
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + i1 * 9 + 9, 8 + j1 * 18, 69 + i1 * 18));
            }
        }
        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 127));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (travelPackInventory instanceof TravelPackInventory) {
            ((TravelPackInventory) travelPackInventory).writeItemStack();
            ItemStack bedroll = travelPackInventory.getStackInSlot(0);
            CompoundNBT packTags = travelPack.getTag();
                if (bedroll.getItem() instanceof BedrollItem) {
                    int colorID = ((BedrollItem) bedroll.getItem()).getColor().getId();
                    if (packTags != null && (!packTags.contains("color") || colorID != packTags.getInt("color")))
                        packTags.putInt("color", colorID);
                } else {
                    if (packTags != null && packTags.contains("color"))
                        packTags.remove("color");
                }

                // DEBUG
                if (packTags != null && packTags.contains("color")) {
                    Druidcraft.LOGGER.debug(DyeColor.byId(packTags.getInt("color")).getTranslationKey());
                } else {
                    Druidcraft.LOGGER.debug("null");
                }
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 10) {
                if (!this.mergeItemStack(itemstack1, 10, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 10, false)) {
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
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, PlayerEntity player) {
        Slot tmpSlot;
        if (slotId >= 0 && slotId < inventorySlots.size()) {
            tmpSlot = inventorySlots.get(slotId);
        } else {
            tmpSlot = null;
        }
        if (tmpSlot != null) {
            if (tmpSlot.inventory == player.inventory && tmpSlot.getSlotIndex() == player.inventory.currentItem) {
                return tmpSlot.getStack();
            }
        }
        if (clickType == ClickType.SWAP) {
            ItemStack stack = player.inventory.getStackInSlot(dragType);
            if (stack == player.inventory.getCurrentItem()) {
                return ItemStack.EMPTY;
            }
        }
        return super.slotClick(slotId, dragType, clickType, player);
    }

}
