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

public class OctoSidedItemStackHandler extends ItemStackHandler {

    protected NonNullList<ItemStack> inv1;
    protected NonNullList<ItemStack> inv2;
    protected NonNullList<ItemStack> inv3;
    protected NonNullList<ItemStack> inv4;
    protected NonNullList<ItemStack> inv5;
    protected NonNullList<ItemStack> inv6;
    protected NonNullList<ItemStack> inv7;
    protected NonNullList<ItemStack> inv8;

    public OctoSidedItemStackHandler(int size) {
        inv1 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv2 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv3 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv4 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv5 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv6 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv7 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv8 = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public OctoSidedItemStackHandler(NonNullList<ItemStack> inv1, NonNullList<ItemStack> inv2, NonNullList<ItemStack> inv3, NonNullList<ItemStack> inv4, NonNullList<ItemStack> inv5, NonNullList<ItemStack> inv6, NonNullList<ItemStack> inv7, NonNullList<ItemStack> inv8) {
        this.inv1 = inv1;
        this.inv2 = inv2;
        this.inv3 = inv3;
        this.inv4 = inv4;
        this.inv5 = inv5;
        this.inv6 = inv6;
        this.inv7 = inv7;
        this.inv8 = inv8;
    }

    public void setSize(int size) {
        inv1 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv2 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv3 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv4 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv5 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv6 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv7 = NonNullList.withSize(size, ItemStack.EMPTY);
        inv8 = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public Pair<NonNullList<ItemStack>, Integer> getInv(int slot) {
        if (slot >= this.inv1.size()) {
            if (slot >= this.inv2.size() + this.inv1.size()) {
                if (slot >= this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                    if (slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                        if (this.inv5.size() + slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                            if (slot >= this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                if (slot >= this.inv7.size() + this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                    return new Pair<>(this.inv8, 8);
                                } return new Pair<>(this.inv7, 7);
                            } return new Pair<>(this.inv6, 6);
                        } return new Pair<>(this.inv5, 5);
                    } return new Pair<>(this.inv4, 4);
                } return new Pair<>(this.inv3, 3);
            } return new Pair<>(this.inv2, 2);
        } return new Pair<>(this.inv1, 1);
    }

    public int getRemainderInv(int slot) {
        if (slot >= this.inv1.size()) {
            if (slot >= this.inv2.size() + this.inv1.size()) {
                if (slot >= this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                    if (slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                        if (this.inv5.size() + slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                            if (slot >= this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                if (slot >= this.inv7.size() + this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                    return slot - this.inv7.size() + this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size();
                                } return slot - this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size();
                            } return slot - this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size();
                        } return slot - this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size();
                    } return slot - this.inv3.size() + this.inv2.size() + this.inv1.size();
                } return slot - this.inv2.size() + this.inv1.size();
            } return slot - this.inv1.size();
        } return slot;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        validateSlotIndex(slot);

        if (slot >= this.inv1.size()) {
            if (slot >= this.inv2.size() + this.inv1.size()) {
                if (slot >= this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                    if (slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                        if (slot >= this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                            if (slot >= this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                if (slot >= this.inv7.size() + this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                    this.inv8.set(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size() + this.inv4.size() + this.inv5.size() + this.inv6.size() + this.inv7.size()), stack);
                                    onContentsChanged(slot);
                                    return;
                                } this.inv7.set(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size() + this.inv4.size() + this.inv5.size() + this.inv6.size()), stack);
                                onContentsChanged(slot);
                                return;
                            } this.inv6.set(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size() + this.inv4.size() + this.inv5.size()), stack);
                            onContentsChanged(slot);
                            return;
                        } this.inv5.set(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size() + this.inv4.size()), stack);
                        onContentsChanged(slot);
                        return;
                    } this.inv4.set(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()), stack);
                    onContentsChanged(slot);
                    return;
                } this.inv3.set(slot - (this.inv1.size() + this.inv2.size()), stack);
                onContentsChanged(slot);
                return;
            } this.inv2.set(slot - this.inv1.size(), stack);
            onContentsChanged(slot);
            return;
        } this.inv1.set(slot, stack);
        onContentsChanged(slot);
    }

    @Override
    public int getSlots() {
        return inv1.size() + inv2.size() + inv3.size() + inv4.size() + inv5.size() + inv6.size() + inv7.size() + inv8.size();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        validateSlotIndex(slot);

        if (slot >= this.inv1.size()) {
            if (slot >= this.inv2.size() + this.inv1.size()) {
                if (slot >= this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                    if (slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                        if (this.inv5.size() + slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                            if (slot >= this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                if (slot >= this.inv7.size() + this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                    return this.inv8.get(slot - inv1.size() - inv2.size() - inv3.size() - inv4.size() - inv5.size() - inv6.size() - inv7.size());
                                }
                                return this.inv7.get(slot - inv1.size() - inv2.size() - inv3.size() - inv4.size() - inv5.size() - inv6.size());
                            }
                            return this.inv6.get(slot - inv1.size() - inv2.size() - inv3.size() - inv4.size() - inv5.size());
                        }
                        return this.inv5.get(slot - inv1.size() - inv2.size() - inv3.size() - inv4.size());
                    }
                    return this.inv4.get(slot - inv1.size() - inv2.size() - inv3.size());
                }
                return this.inv3.get(slot - inv1.size() - inv2.size());
            }
            return this.inv2.get(slot - inv1.size());
        }
        return this.inv1.get(slot);
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
            if (slot >= this.inv2.size() + this.inv1.size()) {
                if (slot >= this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                    if (slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                        if (this.inv5.size() + slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                            if (slot >= this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                if (slot >= this.inv7.size() + this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                    existing = this.inv7.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()) + this.inv4.size() + this.inv5.size() + this.inv6.size() + this.inv7.size());
                                } else existing = this.inv7.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()) + this.inv4.size() + this.inv5.size() + this.inv6.size());
                            } else existing = this.inv6.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()) + this.inv4.size() + this.inv5.size());
                        } else existing = this.inv5.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()) + this.inv4.size());
                    } else existing = this.inv4.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()));
                } else existing = this.inv3.get(slot - (this.inv1.size() + this.inv2.size()));
            } else existing = this.inv2.get(slot - this.inv1.size());
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
            if (slot >= this.inv2.size() + this.inv1.size()) {
                if (slot >= this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                    if (slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                        if (this.inv5.size() + slot >= this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                            if (slot >= this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                if (slot >= this.inv7.size() + this.inv6.size() + this.inv5.size() + this.inv4.size() + this.inv3.size() + this.inv2.size() + this.inv1.size()) {
                                    existing = this.inv7.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()) + this.inv4.size() + this.inv5.size() + this.inv6.size() + this.inv7.size());
                                } else existing = this.inv7.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()) + this.inv4.size() + this.inv5.size() + this.inv6.size());
                            } else existing = this.inv6.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()) + this.inv4.size() + this.inv5.size());
                        } else existing = this.inv5.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()) + this.inv4.size());
                    } else existing = this.inv4.get(slot - (this.inv1.size() + this.inv2.size() + this.inv3.size()));
                } else existing = this.inv3.get(slot - (this.inv1.size() + this.inv2.size()));
            } else existing = this.inv2.get(slot - this.inv1.size());
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
                if (invNum == 3)
                    inv3.set(slot, ItemStack.read(itemTags));
                if (invNum == 4)
                    inv4.set(slot, ItemStack.read(itemTags));
                if (invNum == 5)
                    inv5.set(slot, ItemStack.read(itemTags));
                if (invNum == 6)
                    inv6.set(slot, ItemStack.read(itemTags));
                if (invNum == 7)
                    inv7.set(slot, ItemStack.read(itemTags));
                if (invNum == 8)
                    inv8.set(slot, ItemStack.read(itemTags));
            }
        }
        onLoad();
    }

    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= getSlots())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + getSlots() + ")");
    }

    protected void onLoad() {

    }

    protected void onContentsChanged(int slot) {

    }
}
