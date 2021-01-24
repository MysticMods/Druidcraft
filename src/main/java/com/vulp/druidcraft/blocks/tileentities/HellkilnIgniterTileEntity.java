package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.blocks.HellkilnBlock;
import com.vulp.druidcraft.blocks.HellkilnIgniterBlock;
import com.vulp.druidcraft.inventory.container.HellkilnIgniterContainer;
import com.vulp.druidcraft.registry.GUIRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

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

    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
        this.fuel = nbt.getInt("Fuel");
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("Fuel", this.fuel);
        ItemStackHelper.saveAllItems(compound, this.items);
        return compound;
    }

    private boolean hasFuel() {
        return this.fuel > 0;
    }

    private void updateMain() {
        World world = this.getWorld();
        BlockPos pos = this.getPos();
        TileEntity hellkiln = world.getTileEntity(pos.offset(world.getBlockState(pos).get(HellkilnIgniterBlock.FACING)));
        if (hellkiln instanceof HellkilnTileEntity) {
            ((HellkilnTileEntity) hellkiln).refreshIgniterList();
        }
    }

    public int getFuel() {
        return this.fuel;
    }

    public ItemStack getItemstack() {
        return this.items.get(0);
    }

    @Override
    public void tick() {
        boolean flag = hasFuel();
        int missing = 100 - this.fuel;
        while (missing >= 20) {
            ItemStack itemStack = getItemstack();
            if (itemStack.getItem() != ItemRegistry.crushed_fiery_glass) {
                break;
            }
            this.fuel = this.fuel + 20;
            missing = missing - 20;
            this.items.get(0).setCount(itemStack.getCount() - 1);
            this.markDirty();
        }
        if (flag != hasFuel()) {
            World world = this.world;
            BlockPos pos = this.pos;
            world.setBlockState(pos, world.getBlockState(pos).with(HellkilnBlock.LIT, hasFuel()), 3);
            BlockState state = this.getBlockState();
            if (state.getBlock() instanceof HellkilnIgniterBlock) {
                updateMain();
            }
        }
    }

    public void deductFuel(int amount) {
        this.fuel = this.fuel - amount;
        if (this.items.get(0).isEmpty() && this.fuel <= 0) {
            world.setBlockState(pos, world.getBlockState(pos).with(HellkilnBlock.LIT, false), 3);
            this.fuel = 0;
        }
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.druidcraft.hellkiln_igniter");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new HellkilnIgniterContainer(GUIRegistry.hellkiln_igniter, id, player, this, this.hellkilnIgniterData);
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
