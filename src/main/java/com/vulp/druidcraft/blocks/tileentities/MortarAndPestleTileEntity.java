package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.MortarAndPestleContainer;
import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.recipes.MortarAndPestleRecipe;
import com.vulp.druidcraft.registry.GUIRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MortarAndPestleTileEntity extends LockableTileEntity implements ITickableTileEntity {

    protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private int grindAmount;
    private boolean selfGrinding;
    private boolean markedDirty;
    protected final IIntArray mortarData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return MortarAndPestleTileEntity.this.grindAmount;
                case 1:
                    return MortarAndPestleTileEntity.this.selfGrinding ? 1 : 0;
                case 2:
                    return MortarAndPestleTileEntity.this.markedDirty ? 1 : 0;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    MortarAndPestleTileEntity.this.grindAmount = value;
                    break;
                case 1:
                    MortarAndPestleTileEntity.this.selfGrinding = value == 1;
                    break;
                case 2:
                    MortarAndPestleTileEntity.this.markedDirty = value == 1;
                    break;
            }
        }

        public int size() {
            return 3;
        }
    };

    public MortarAndPestleTileEntity() {
        this(TileEntityRegistry.mortar_and_pestle);
    }

    public MortarAndPestleTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
        this.grindAmount = nbt.getInt("GrindAmount");
        this.selfGrinding = nbt.getBoolean("SelfGrinding");
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("GrindAmount", this.grindAmount);
        compound.putBoolean("SelfGrinding", this.selfGrinding);
        ItemStackHelper.saveAllItems(compound, this.items);
        return compound;
    }

    @Override
    public void tick() {
        if (this.selfGrinding) {
            if (this.grindAmount < 5) {
                this.grindAmount++;
            } else {
                this.grindAmount = 0;
            }
        }
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Druidcraft.MODID + ".mortar_and_pestle");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new MortarAndPestleContainer(GUIRegistry.mortar_and_pestle, id, player, this, this.mortarData, IWorldPosCallable.of(this.world, this.pos));
    }

    @Override
    public int getSizeInventory() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void markDirty() {
        this.markedDirty = true;
        super.markDirty();
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
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
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