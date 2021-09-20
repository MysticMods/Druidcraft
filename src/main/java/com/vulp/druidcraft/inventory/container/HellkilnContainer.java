/*
package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.recipes.HellkilnRecipe;
import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.registry.GUIRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HellkilnContainer extends RecipeBookContainer<IInventory> {
    private final IInventory hellkilnInventory;
    private final IIntArray hellkilnData;
    protected final World world;
    private final IRecipeType<? extends HellkilnRecipe> recipeType;
    private final RecipeBookCategory recipeCategory;

    public HellkilnContainer(int id, PlayerInventory playerInventory) {
        this(GUIRegistry.hellkiln_igniter, IModdedRecipeType.hellkiln_smelting, RecipeBookCategory.FURNACE, id, playerInventory, new Inventory(3), new IntArray(8));
    }

    public HellkilnContainer(ContainerType<?> type, IRecipeType<? extends HellkilnRecipe> recipeType, RecipeBookCategory recipeCategory, int id, PlayerInventory playerInventory, IInventory containerInventory, IIntArray hellkilnData) {
        super(type, id);
        this.recipeType = recipeType;
        this.recipeCategory = recipeCategory;
        assertInventorySize(containerInventory, 3);
        assertIntArraySize(hellkilnData, 8);
        this.hellkilnInventory = containerInventory;
        this.hellkilnData = hellkilnData;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(containerInventory, 0, 52, 26));
        this.addSlot(new Slot(containerInventory, 1, 52, 51));
        this.addSlot(new HellkilnResultSlot(playerInventory.player, containerInventory, 2, 108, 38));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.trackIntArray(hellkilnData);
    }

    @Override
    public void fillStackedContents(RecipeItemHelper itemHelperIn) {
        if (this.hellkilnInventory instanceof IRecipeHelperPopulator) {
            ((IRecipeHelperPopulator)this.hellkilnInventory).fillStackedContents(itemHelperIn);
        }
    }

    public int[] getIgniterFuel() {
        return new int[]{this.hellkilnData.get(5), this.hellkilnData.get(6), this.hellkilnData.get(7)};
    }

    public int getCookTime() {
        return this.hellkilnData.get(2);
    }

    public int getTotalCookTime() {
        return this.hellkilnData.get(3);
    }

    @Override
    public void clear() {
        this.hellkilnInventory.clear();
    }

    protected boolean hasRecipe(ItemStack stack1, ItemStack stack2) {
        for (IRecipe<IInventory> recipe : this.world.getRecipeManager().getRecipes(this.recipeType).values()) {
            HellkilnRecipe rec = (HellkilnRecipe) recipe;
            if (rec.isIngredient(stack1) && (stack2.isEmpty() || rec.isIngredient(stack2))) {
                return true;
            }
        }
        return false;
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                ItemStack input1 = this.inventorySlots.get(0).getStack();
                ItemStack input2 = this.inventorySlots.get(1).getStack();
                if (!input1.isEmpty() && !input2.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                if (!input2.isEmpty()) {
                    input1 = input2;
                }
                if (this.hasRecipe(itemstack1, input1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
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

    @Override
    public boolean matches(IRecipe<? super IInventory> recipeIn) {
        return recipeIn.matches(this.hellkilnInventory, this.world);
    }

    @Override
    public int getOutputSlot() {
        return 2;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 2;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getSize() {
        return 3;
    }

    @Override
    public RecipeBookCategory func_241850_m() {
        return this.recipeCategory;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.hellkilnInventory.isUsableByPlayer(playerIn);
    }
}
*/
