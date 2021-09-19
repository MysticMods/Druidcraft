/*
package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.recipes.AdvancedSmithingRecipe;
import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.recipes.MortarAndPestleRecipe;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.GUIRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

import java.util.List;

public class MortarAndPestleContainer extends Container {

    private final World world;
    private MortarAndPestleRecipe recipe;
    public List<MortarAndPestleRecipe> recipeList;
    private final IIntArray mortarData;
    public final IWorldPosCallable worldPosCallable;
    protected final PlayerEntity playerEntity;
    private final IInventory inventory;

    public MortarAndPestleContainer(int id, PlayerInventory playerInventory) {
        this(GUIRegistry.mortar_and_pestle, id, playerInventory, new Inventory(2), new IntArray(3), IWorldPosCallable.DUMMY);
    }

    // TODO: Needs better logic when calculating grinding stuff!
    public MortarAndPestleContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, IInventory containerInventory, IIntArray mortarData, IWorldPosCallable worldPosCallable) {
        super(type, id);
        this.worldPosCallable = worldPosCallable;
        this.playerEntity = playerInventory.player;
        assertInventorySize(containerInventory, 2);
        assertIntArraySize(mortarData, 3);
        this.inventory = containerInventory;
        this.mortarData = mortarData;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(containerInventory, 0, 45, 35));
        this.addSlot(new Slot(containerInventory, 1, 114, 35) {

            public boolean isItemValid(ItemStack stack) {
                return false;
            }

        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.recipeList = this.world.getRecipeManager().getRecipesForType(IModdedRecipeType.mortar_and_pestle);
        this.trackIntArray(mortarData);
    }

    public void grind() {
        int i = this.mortarData.get(0);
        if (i > 24) {
            this.mortarData.set(0, 0);
            grindInput();
        } else {
            this.mortarData.set(0, i + 1);
        }
    }

    public void resetGrind() {
        this.mortarData.set(0, 0);
    }

    // TODO: Write code.
    public boolean canGrind() {
        if (!this.inventory.getStackInSlot(0).isEmpty()) {
            ItemStack endSlot = this.inventory.getStackInSlot(1);
            if (this.recipe == null || !this.recipe.matches(this.inventory, world)) {
                updateRecipeAndProgress();
            }
            if (endSlot.isEmpty()) {
                if (this.recipe != null) {
                    return true;
                }
            }
            if (endSlot.getMaxStackSize() != endSlot.getCount()) {
                if (this.recipe != null && (this.recipe.getRecipeOutput().getItem() == endSlot.getItem())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (this.mortarData.get(2) == 1) {
            updateRecipeAndProgress();
            this.mortarData.set(2, 0);
        }
    }

    // CALCULATES WHEN INVENTORY IS CHANGED
    private void updateRecipeAndProgress() {
        if (this.recipe == null || !this.recipe.matches(this.inventory, this.world)) {
            resetGrind();
        }
        if (!this.recipeList.isEmpty()) {
            boolean flag = false;
            for (MortarAndPestleRecipe rec : this.recipeList) {
                if (rec.matches(this.inventory, this.world)) {
                    this.recipe = rec;
                    flag = true;
                }
            }
            if (!flag) {
                this.recipe = null;
            }
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        this.resetGrind();
    }

    // CALCULATES WHEN SUCCESSFULLY CRAFTING NEW ITEM
    private void grindInput() {
        if (!this.recipeList.isEmpty()) {
            for (MortarAndPestleRecipe rec : this.recipeList) {
                if (rec.matches(this.inventory, this.world)) {
                    this.recipe = rec;
                    ItemStack resultStack = this.recipe.getCraftingResult(this.inventory);
                    ItemStack slotStack = this.inventory.getStackInSlot(1);
                    if (slotStack.getItem() == resultStack.getItem()) {
                        slotStack.grow(1);
                        this.inventory.decrStackSize(0, 1);
                    } else {
                        if (slotStack.isEmpty()) {
                            this.inventory.setInventorySlotContents(1, resultStack);
                            this.inventory.decrStackSize(0, 1);
                        }
                    }
                    break;
                }
            }
        }
    }

    public int getGrindValue() {
        return this.mortarData.get(0);
    }

    public boolean isAuto() {
        return this.mortarData.get(1) == 1;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.worldPosCallable.applyOrElse((world, pos) -> this.doesBlockExist(world.getBlockState(pos)) && playerIn.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D, true);
    }

    protected boolean doesBlockExist(BlockState state) {
        return state.isIn(BlockRegistry.mortar_and_pestle);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index > 1) {
                if (index < 38 && !this.mergeItemStack(itemstack1, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

}
*/
