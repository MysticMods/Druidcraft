package com.vulp.druidcraft.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class HellkilnResultSlot extends Slot {

    private final PlayerEntity player;
    private int removeCount;

    public HellkilnResultSlot(PlayerEntity player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        if (this.getHasStack()) {
            this.removeCount += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    @Override
    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        this.onCrafting(stack);
        super.onTake(player, stack);
        return stack;
    }

/*    *//**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     *//*
    protected void onCrafting(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    *//**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     *//*
    protected void onCrafting(ItemStack stack) {
        stack.onCrafting(this.player.world, this.player, this.removeCount);
        if (!this.player.world.isRemote && this.inventory instanceof AbstractFurnaceTileEntity) {
            ((AbstractFurnaceTileEntity)this.inventory).unlockRecipes(this.player);
        }

        this.removeCount = 0;
        net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
    }*/
}
