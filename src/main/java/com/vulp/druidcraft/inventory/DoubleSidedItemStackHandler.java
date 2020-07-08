package com.vulp.druidcraft.inventory;

import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class DoubleSidedItemStackHandler extends ItemStackHandler {

    protected NonNullList<ItemStack> inv1;
    protected NonNullList<ItemStack> inv2;

    public DoubleSidedItemStackHandler(int size) {
        inv1 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv2 = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public DoubleSidedItemStackHandler(NonNullList<ItemStack> inv1, NonNullList<ItemStack> inv2) {
        this.inv1 = inv1;
        this.inv2 = inv2;
    }

    public void setSize(int size) {
        inv1 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv2 = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public Pair<NonNullList<ItemStack>, Integer> getInv(int slot) {
        if (slot >= this.inv1.size()) {
            return new Pair<>(this.inv2, 2);
        }
        return new Pair<>(this.inv1, 1);
    }

    public int getRemainderInv(int slot) {
        if (slot >= this.inv1.size()) {
            return slot - this.inv1.size();
        }
        return slot;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        validateSlotIndex(slot);

        if (slot >= this.inv1.size()) {
            this.inv2.set(slot - this.inv1.size(), stack);
            onContentsChanged(slot);
            return;
        } this.inv1.set(slot, stack);
        onContentsChanged(slot);
    }

    @Override
    public int getSlots()
    {
        return inv1.size() + inv2.size();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);

        if (slot >= this.inv1.size()) {
            return this.inv2.get(slot - this.inv1.size());
        } else return this.inv1.get(slot);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isItemValid(slot, stack))
            return stack;

        validateSlotIndex(slot);

        ItemStack existing;

        if (slot >= this.inv1.size()) {
            existing = this.inv2.get(slot - this.inv1.size());
        } else existing = this.inv1.get(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                setStackInSlot(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        validateSlotIndex(slot);

        ItemStack existing;

        if (slot >= this.inv1.size()) {
            existing = this.inv2.get(slot - this.inv1.size());
        } else existing = this.inv1.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                setStackInSlot(slot, ItemStack.EMPTY);
                onContentsChanged(slot);
                return existing;
            }
            else {
                return existing.copy();
            }
        }
        else {
            if (!simulate) {
                setStackInSlot(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                onContentsChanged(slot);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
        return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public CompoundNBT serializeNBT() {
        ListNBT nbtTagList = new ListNBT();

        for (int i = 0; i < getSlots(); i++) {
            Pair<NonNullList<ItemStack>, Integer> inv = getInv(i);
            if (!inv.getKey().get(getRemainderInv(i)).isEmpty()) {
                CompoundNBT itemTag = new CompoundNBT();
                itemTag.putInt("Slot", i);
                itemTag.putInt("Inv", inv.getValue());
                inv.getKey().get(i).write(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", getSlots());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setSize(nbt.contains("Size", Constants.NBT.TAG_INT) ? nbt.getInt("Size") : getSlots());
        ListNBT tagList = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundNBT itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");
            if (slot >= 0 && slot < getInv(slot).getKey().size() - getRemainderInv(slot)) {
                int invNum = itemTags.getInt("Inv");
                if (invNum == 1)
                    inv1.set(slot, ItemStack.read(itemTags));
                if (invNum == 2)
                    inv2.set(slot, ItemStack.read(itemTags));
            }
        }
        onLoad();
    }

    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= getSlots())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + inv1.size() + ")");
    }

    protected void onLoad() {

    }

    protected void onContentsChanged(int slot) {

    }
}
