package com.vulp.druidcraft.inventory.container;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.lang.reflect.AccessibleObject;
import java.util.List;

import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.recipes.WoodcuttingRecipe;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.GUIRegistry;
import com.vulp.druidcraft.registry.SoundEventRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.data.TagsProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WoodcutterContainer extends Container {
    /**
     * The list of items that can be accepted into the input slot of the Woodcutter container.
     */
    static final ImmutableList<Tag<Item>> ACCEPTED_INPUT_ITEMS = ImmutableList.of(ItemTags.LOGS, ItemTags.PLANKS);
    private final IWorldPosCallable worldPosCallable;
    /**
     * The index of the selected recipe in the GUI.
     */
    private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
    private final World world;
    private List<WoodcuttingRecipe> recipes = Lists.newArrayList();
    /**
     * The {@plainlink ItemStack} set in the input slot by the player.
     */
    private ItemStack itemStackInput = ItemStack.EMPTY;
    /**
     * Stores the game time of the last time the player took items from the the crafting result slot. This is used to
     * prevent the sound from being played multiple times on the same tick.
     */
    private long lastOnTake;
    final Slot inputInventorySlot;
    /**
     * The inventory slot that stores the output of the crafting recipe.
     */
    final Slot outputInventorySlot;
    private Runnable inventoryUpdateListener = () -> {
    };
    public final IInventory inputInventory = new Inventory(1) {
        /**
         * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
         * it hasn't changed and skip it.
         */
        @Override
        public void markDirty() {
            super.markDirty();
            WoodcutterContainer.this.onCraftMatrixChanged(this);
            WoodcutterContainer.this.inventoryUpdateListener.run();
        }
    };
    /**
     * The inventory that stores the output of the crafting recipe.
     */
    private final CraftResultInventory inventory = new CraftResultInventory();

    public WoodcutterContainer(int windowIdIn, PlayerInventory playerInventoryIn) {
        this(windowIdIn, playerInventoryIn, IWorldPosCallable.DUMMY);
    }

    public WoodcutterContainer(int windowIdIn, PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallableIn) {
        super(GUIRegistry.woodcutter, windowIdIn);
        this.worldPosCallable = worldPosCallableIn;
        this.world = playerInventoryIn.player.world;
        this.inputInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
        this.outputInventorySlot = this.addSlot(new Slot(this.inventory, 1, 143, 33) {
            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
             */
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            @Override
            public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
                ItemStack itemstack = WoodcutterContainer.this.inputInventorySlot.decrStackSize(1);
                if (!itemstack.isEmpty()) {
                    WoodcutterContainer.this.updateRecipeResultSlot();
                }

                stack.getItem().onCreated(stack, thePlayer.world, thePlayer);
                worldPosCallableIn.consume((p_216954_1_, p_216954_2_) -> {
                    long l = p_216954_1_.getGameTime();
                    if (WoodcutterContainer.this.lastOnTake != l) {
                        p_216954_1_.playSound((PlayerEntity) null, p_216954_2_, SoundEventRegistry.wood_cutter_take, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        WoodcutterContainer.this.lastOnTake = l;
                    }

                });
                return super.onTake(thePlayer, stack);
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
        }

        this.trackInt(this.selectedRecipe);
    }

    /**
     * Returns the index of the selected recipe.
     */
    @OnlyIn(Dist.CLIENT)
    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    @OnlyIn(Dist.CLIENT)
    public List<WoodcuttingRecipe> getRecipeList() {
        return this.recipes;
    }

    @OnlyIn(Dist.CLIENT)
    public int getRecipeListSize() {
        return this.recipes.size();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasItemsinInputSlot() {
        return this.inputInventorySlot.getHasStack() && !this.recipes.isEmpty();
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, BlockRegistry.woodcutter);
    }

    /**
     * Handles the given Button-click on the server, currently only used by enchanting. Name is for legacy.
     */
    @Override
    public boolean enchantItem(PlayerEntity playerIn, int id) {
        if (id >= 0 && id < this.recipes.size()) {
            this.selectedRecipe.set(id);
            this.updateRecipeResultSlot();
        }

        return true;
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        ItemStack itemstack = this.inputInventorySlot.getStack();
        if (itemstack.getItem() != this.itemStackInput.getItem()) {
            this.itemStackInput = itemstack.copy();
            this.updateAvailableRecipes(inventoryIn, itemstack);
        }

    }

    private void updateAvailableRecipes(IInventory inventoryIn, ItemStack stack) {
        this.recipes.clear();
        this.selectedRecipe.set(-1);
        this.outputInventorySlot.putStack(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.recipes = this.world.getRecipeManager().getRecipes(IModdedRecipeType.woodcutting, inventoryIn, this.world);
        }

    }

    private void updateRecipeResultSlot() {
        if (!this.recipes.isEmpty()) {
            WoodcuttingRecipe woodcuttingRecipe = this.recipes.get(this.selectedRecipe.get());
            this.outputInventorySlot.putStack(woodcuttingRecipe.getCraftingResult(this.inputInventory));
        } else {
            this.outputInventorySlot.putStack(ItemStack.EMPTY);
        }

        this.detectAndSendChanges();
    }

    @Override
    public ContainerType<?> getType() {
        return GUIRegistry.woodcutter;
    }

    @OnlyIn(Dist.CLIENT)
    public void setInventoryUpdateListener(Runnable listenerIn) {
        this.inventoryUpdateListener = listenerIn;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return false;
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
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            // TODO: This modifies itemstack1 to be a full stack resulting in duplication. I've replaced it with a call to the recipe manager similar to the stonecutter, but I'm not sure what this was meant for.
/*            boolean flag = true;
            for (Tag<Item> tags : ACCEPTED_INPUT_ITEMS.asList()) {
                if (tags.contains(item)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        flag = false;
                    }
                }
            }*/
            if (index == 1) {
                item.onCreated(itemstack1, playerIn.world, playerIn);
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            }  else if (this.world.getRecipeManager().getRecipe(IModdedRecipeType.woodcutting, new Inventory(itemstack1), this.world).isPresent()) {
                if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                  return ItemStack.EMPTY;
                }
            } else if (index < 29) {
                if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
                  return ItemStack.EMPTY;
                }
            } else if (index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }

            slot.onSlotChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
            this.detectAndSendChanges();
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.inventory.removeStackFromSlot(1);
        this.worldPosCallable.consume((p_217079_2_, p_217079_3_) -> {
            this.clearContainer(playerIn, playerIn.world, this.inputInventory);
        });
    }
}