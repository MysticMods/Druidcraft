package com.vulp.druidcraft.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BeetleEntity extends TameableMonster implements IInventoryChangedListener {
    private static final DataParameter<Boolean> DATA_ID_CHEST = EntityDataManager.createKey(BeetleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(BeetleEntity.class, DataSerializers.BYTE);
    private Inventory beetleChest;

    public BeetleEntity(EntityType<? extends TameableMonster> type, World worldIn)
    {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_ID_CHEST, false);
    }

    public boolean hasChest() {
        return this.dataManager.get(DATA_ID_CHEST);
    }

    private void setChested(boolean chested) {
        this.dataManager.set(DATA_ID_CHEST, chested);
    }

    private int getInventorySize() {
        return this.hasChest() ? 17 : 1;
    }

    protected void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    public int getInventoryColumns() {
        return 5;
    }

    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            if (!this.world.isRemote) {
                this.entityDropItem(Blocks.CHEST);
            }

            this.setChested(false);
        }

    }

    private void initBeetleChest() {
        Inventory inventory = this.beetleChest;
        this.beetleChest = new Inventory(this.getInventorySize());
        if (inventory != null) {
            inventory.removeListener(this);
            int i = Math.min(inventory.getSizeInventory(), this.beetleChest.getSizeInventory());

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    this.beetleChest.setInventorySlotContents(j, itemstack.copy());
                }
            }
        }

        this.beetleChest.addListener(this);
        this.updateBeetleSlots();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.beetleChest));
    }

    private boolean canBeSaddled() {
        return true;
    }

    public boolean isBeetleSaddled() {
        return this.getBeetleWatchableBoolean(4);
    }

    private void updateBeetleSlots() {
        if (!this.world.isRemote) {
            this.setBeetleSaddled(!this.beetleChest.getStackInSlot(0).isEmpty() && this.canBeSaddled());
        }
    }

    private void setBeetleSaddled(boolean saddled) {
        this.getBeetleWatchableBoolean(saddled);
    }

    private boolean getBeetleWatchableBoolean(int p_110233_1_) {
        return (this.dataManager.get(STATUS) & p_110233_1_) != 0;
    }

    private void getBeetleWatchableBoolean(boolean p_110208_2_) {
        byte b0 = this.dataManager.get(STATUS);
        if (p_110208_2_) {
            this.dataManager.set(STATUS, (byte)(b0 | 4));
        } else {
            this.dataManager.set(STATUS, (byte)(b0 & ~4));
        }

    }

    public boolean isTame() {
        return this.getBeetleWatchableBoolean(2);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("ChestedBeetle", this.hasChest());
        if (this.hasChest()) {
            ListNBT listnbt = new ListNBT();

            for(int i = 2; i < this.beetleChest.getSizeInventory(); ++i) {
                ItemStack itemstack = this.beetleChest.getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    CompoundNBT compoundnbt = new CompoundNBT();
                    compoundnbt.putByte("Slot", (byte)i);
                    itemstack.write(compoundnbt);
                    listnbt.add(compoundnbt);
                }
            }

            compound.put("Items", listnbt);
        }

    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setChested(compound.getBoolean("ChestedBeetle"));
        if (this.hasChest()) {
            ListNBT listnbt = compound.getList("Items", 10);
            this.initBeetleChest();

            for(int i = 0; i < listnbt.size(); ++i) {
                CompoundNBT compoundnbt = listnbt.getCompound(i);
                int j = compoundnbt.getByte("Slot") & 255;
                if (j >= 2 && j < this.beetleChest.getSizeInventory()) {
                    this.beetleChest.setInventorySlotContents(j, ItemStack.read(compoundnbt));
                }
            }
        }

        this.updateBeetleSlots();
    }

    @Override
    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        if (inventorySlot == 499) {
            if (this.hasChest() && itemStackIn.isEmpty()) {
                this.setChested(false);
                this.initBeetleChest();
                return true;
            }

            if (!this.hasChest() && itemStackIn.getItem() == Blocks.CHEST.asItem()) {
                this.setChested(true);
                this.initBeetleChest();
                return true;
            }
        }

        return super.replaceItemInInventory(inventorySlot, itemStackIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return null;
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void remove(boolean keepData) {
        super.remove(keepData);
        if (!keepData && itemHandler != null) {
            itemHandler.invalidate();
            itemHandler = null;
        }
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {
        boolean flag = this.isBeetleSaddled();
        this.updateBeetleSlots();
        if (this.ticksExisted > 20 && !flag && this.isBeetleSaddled()) {
            this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
        }

    }
}
