package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.events.EventFactory;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class BeetleEntity extends TameableMonster implements IInventoryChangedListener, INamedContainerProvider {
    private static final DataParameter<Boolean> SADDLE = EntityDataManager.createKey(BeetleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHEST = EntityDataManager.createKey(BeetleEntity.class, DataSerializers.BOOLEAN);
    private Inventory beetleChest;

    public BeetleEntity(EntityType<? extends TameableMonster> type, World worldIn) {
        super(type, worldIn);
        this.initBeetleChest();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(SADDLE, false);
        this.getDataManager().register(CHEST, false);
    }

    public boolean hasChest() {
        return this.getDataManager().get(CHEST);
    }

    private void setChested(boolean chested) {
        this.getDataManager().set(CHEST, chested);
    }

    public boolean hasSaddle() {
        return this.getDataManager().get(SADDLE);
    }

    private void setSaddled(boolean saddled) {
        this.getDataManager().set(SADDLE, saddled);
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

        this.updateHorseSlots();
    }

    @Override
    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        if (inventorySlot == 499) {
            if (this.hasChest() && itemStackIn.isEmpty()) {
                this.setChested(false);
                this.initBeetleChest();
                return true;
            }

            if (!this.hasChest() && itemStackIn.getItem() == Items.CHEST) {
                this.setChested(true);
                this.initBeetleChest();
                return true;
            }
        }

        return super.replaceItemInInventory(inventorySlot, itemStackIn);
    }

    private int getInventorySize() {
        if (hasChest())
        {
            return 64;
        }
        else return 1;
    }

    private boolean canBeSaddled() {
        return true;
    }

    private void updateHorseSlots() {
        if (!this.world.isRemote) {
            this.setSaddled(!this.beetleChest.getStackInSlot(0).isEmpty() && this.canBeSaddled());
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (this.isTamed() && player.isSneaking()) {
            this.openGUI(player);
            return true;
        }
        if (!itemstack.isEmpty()) {
            if (!this.isTamed() || itemstack.getItem() == Items.NAME_TAG) {
                if (itemstack.getItem() == Items.SLIME_BALL) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    if (!this.world.isRemote) {
                        if (this.rand.nextInt(3) == 0 && !EventFactory.onMonsterTame(this, player)) {
                            this.playTameEffect(true);
                            this.setTamedBy(player);
                            this.navigator.clearPath();
                            this.setAttackTarget((LivingEntity) null);
                            this.setHealth(24.0F);
                            this.world.setEntityState(this, (byte) 7);
                        } else {
                            this.playTameEffect(false);
                            this.world.setEntityState(this, (byte) 6);
                        }
                    }

                    return true;
                }

                if (itemstack.interactWithEntity(player, this, hand)) {
                    return true;
                }
            }

            if (!this.world.isRemote) {
                if (!this.hasChest() && itemstack.getItem() == Items.CHEST) {
                    this.setChested(true);
                    this.playChestEquipSound();
                    this.initBeetleChest();
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    return true;
                }
            }

            if (!this.isChild() && !this.hasSaddle() && itemstack.getItem() == Items.SADDLE) {
                this.openGUI(player);
                return true;
            }
        }
        return super.processInteract(player, hand);
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new BeetleInventoryContainer(i, playerInventory, beetleChest, this.getEntityId());
    }

    private void openGUI(PlayerEntity playerEntity) {
        if (!this.world.isRemote && (!this.isBeingRidden() || this.isPassenger(playerEntity)) && this.isTamed()) {
            NetworkHooks.openGui((ServerPlayerEntity)playerEntity, this, packetBuffer -> {
                packetBuffer.writeInt(this.getEntityId());
                packetBuffer.writeInt(getInventorySize());
            });
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
        this.updateHorseSlots();
        this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.beetleChest));
    }

    private void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            if (!this.world.isRemote) {
                this.entityDropItem(Blocks.CHEST);
            }

            this.setChested(false);
        }
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {
        boolean flag = this.hasSaddle();
        this.updateHorseSlots();
        if (this.ticksExisted > 20 && !flag && this.hasSaddle()) {
            this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
        }
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

}

