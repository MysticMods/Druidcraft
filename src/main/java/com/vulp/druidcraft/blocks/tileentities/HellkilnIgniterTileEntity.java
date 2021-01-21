package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.inventory.container.HellKilnIgniterContainer;
import com.vulp.druidcraft.registry.GUIRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class HellkilnIgniterTileEntity extends LockableTileEntity implements ITickableTileEntity {

    protected NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private int fuel = 0;
    protected final IIntArray hellkilnIgniterData = new IIntArray() {
        public int get(int index) {
                    return HellkilnIgniterTileEntity.this.fuel;
        }

        public void set(int index, int value) {
                    HellkilnIgniterTileEntity.this.fuel = value;
        }

        public int size() {
            return 1;
        }
    };

    public HellkilnIgniterTileEntity() {
        this(TileEntityRegistry.hellkiln_igniter);
    }

    public HellkilnIgniterTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    @Override
    public void tick() {
        int missing = 100 - this.fuel;
        while (missing >= 20) {
            ItemStack itemStack = this.items.get(0);
            if (itemStack.getItem() != ItemRegistry.crushed_fiery_glass || itemStack.getCount() <= 0) {
                return;
            }
            this.fuel = this.fuel + 20;
            missing = missing - 20;
            this.items.get(0).setCount(itemStack.getCount() - 1);
            this.markDirty();
        }
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.druidcraft.hellkiln_igniter");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new HellKilnIgniterContainer(GUIRegistry.hellkiln_igniter, id, player, this, this.hellkilnIgniterData);
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return !items.get(0).isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.items.set(index, stack);
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }

}
