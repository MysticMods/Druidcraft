package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.recipes.AdvancedSmithingRecipe;
import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.GUIRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.List;

public class SmithingWorkbenchContainer extends Container {

    private final World world;
    private AdvancedSmithingRecipe recipe;
    public final List<AdvancedSmithingRecipe> recipeList;
    protected final CraftResultInventory resultInventory = new CraftResultInventory();
    protected final IInventory craftInventory = new Inventory(7) {
        public void markDirty() {
            super.markDirty();
            onCraftMatrixChanged(this);
        }
    };
    protected final IWorldPosCallable worldPosCallable;
    protected final PlayerEntity playerEntity;

    public SmithingWorkbenchContainer(int id, PlayerInventory playerInventory) {
        this(GUIRegistry.smithing_workbench, id, playerInventory, IWorldPosCallable.DUMMY);
    }

    public SmithingWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        this(GUIRegistry.smithing_workbench, id, playerInventory, worldPosCallable);
    }

    public SmithingWorkbenchContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(type, id);
        this.worldPosCallable = worldPosCallable;
        this.playerEntity = playerInventory.player;
        this.addSlot(new Slot(this.craftInventory, 0, 51, 40));
        this.addSlot(new Slot(this.craftInventory, 1, 51, 17));
        this.addSlot(new Slot(this.craftInventory, 2, 51, 63));
        this.addSlot(new Slot(this.craftInventory, 3, 28, 28));
        this.addSlot(new Slot(this.craftInventory, 4, 28, 52));
        this.addSlot(new Slot(this.craftInventory, 5, 74, 28));
        this.addSlot(new Slot(this.craftInventory, 6, 74, 52));
        this.addSlot(new Slot(this.resultInventory, 7, 134, 40) {

            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
             */
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            /**
             * Return whether this slot's stack can be taken from this slot.
             */
            public boolean canTakeStack(PlayerEntity playerIn) {
                return doesRecipeMatch(playerIn, this.getHasStack());
            }

            public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
                return takeResult(thePlayer, stack);
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 152));
        }

        this.world = playerInventory.player.world;
        this.recipeList = packSmithingRecipes();

    }

    public boolean isEmpty() {
        return this.craftInventory.isEmpty();
    }

    // Grabs advanced recipe list, then injects regular recipes transformed into the advanced kind.
    public List<AdvancedSmithingRecipe> packSmithingRecipes() {
        List<AdvancedSmithingRecipe> list1 = this.world.getRecipeManager().getRecipesForType(IModdedRecipeType.advanced_smithing);
        List<SmithingRecipe> list2 = this.world.getRecipeManager().getRecipesForType(IRecipeType.SMITHING);
        for (SmithingRecipe smithingRecipe : list2) {
            NonNullList<Ingredient> ingredientList = NonNullList.create();
            ingredientList.add(smithingRecipe.addition);
            list1.add(new AdvancedSmithingRecipe(smithingRecipe.getId(), smithingRecipe.base, ingredientList, smithingRecipe.getRecipeOutput()));
        }
        return list1;
    }

    private ItemStack takeResult(PlayerEntity player, ItemStack itemStack) {
        itemStack.onCrafting(player.world, player, itemStack.getCount());
        this.resultInventory.onCrafting(player);
        for (int i = 0; i < 7; i++) {
            this.updateSlots(i);
        }
        this.worldPosCallable.consume((world, pos) -> {
            world.playEvent(1044, pos, 0);
        });
        return itemStack;
    }

    private void updateSlots(int slot) {
        ItemStack itemstack = this.craftInventory.getStackInSlot(slot);
        itemstack.shrink(1);
        this.resultInventory.setInventorySlotContents(slot, itemstack);
    }

    public boolean doesRecipeMatch(PlayerEntity player, boolean hasStack) {
        return this.recipe != null && this.recipe.matches(this.craftInventory, this.world);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.worldPosCallable.applyOrElse((world, pos) -> this.doesBlockExist(world.getBlockState(pos)) && playerIn.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D, true);
    }

    protected boolean doesBlockExist(BlockState state) {
        return state.isIn(BlockRegistry.smithing_workbench);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        super.onCraftMatrixChanged(inventoryIn);
        if (inventoryIn == this.craftInventory) {
            this.updateRepairOutput();
        }
    }

    public void updateRepairOutput() {
        List<AdvancedSmithingRecipe> list = packSmithingRecipes();
        if (list.isEmpty()) {
            this.resultInventory.setInventorySlotContents(0, ItemStack.EMPTY);
        } else {
            boolean flag = false;
            for (AdvancedSmithingRecipe rec : list) {
                if (rec.matches(this.craftInventory, this.world)) {
                    this.recipe = rec;
                    ItemStack itemstack = this.recipe.getCraftingResult(this.craftInventory);
                    this.resultInventory.setRecipeUsed(this.recipe);
                    this.resultInventory.setInventorySlotContents(0, itemstack);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                this.resultInventory.setInventorySlotContents(0, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume((world, pos) -> this.clearContainer(playerIn, world, this.craftInventory));
    }

    protected boolean func_241210_a_(ItemStack stack) {
        return this.recipeList.stream().anyMatch((recipe) -> recipe.isValidAdditionItem(stack));
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 7) {
                if (!this.mergeItemStack(itemstack1, 8, 44, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index > 7) {
                if (index < 44) {
                    int i = func_241210_a_(itemstack) ? 1 : 0;
                    if (!this.mergeItemStack(itemstack1, i, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(itemstack1, 8, 44, false)) {
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


    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.resultInventory && super.canMergeSlot(stack, slotIn);
    }

}
