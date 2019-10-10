package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.events.EventFactory;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class BeetleEntity extends TameableMonster implements IInventoryChangedListener {
    private static final DataParameter<Integer> STATUS = EntityDataManager.createKey(BeetleEntity.class, DataSerializers.VARINT);
    private BeetleInventoryContainer beetleChest;
    public final PlayerEntity player;
    private PlayerInventory beetleInventory;

    public BeetleEntity(EntityType<? extends TameableMonster> type, World worldIn, PlayerEntity player, IInventory beetleInventory) {
        super(type, worldIn);
        this.player = player;
        this.initBeetleChest();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(STATUS, 0);
    }

    public boolean hasChest() {
        return (this.dataManager.get(STATUS) == 2 || this.dataManager.get(STATUS) == 3);
    }

    private void setChested(boolean chested) {
        if (chested) {
            if (this.dataManager.get(STATUS) == 1) {
                this.dataManager.set(STATUS, 3);
            } else {
                this.dataManager.set(STATUS, 2);
            }
        } else {
            if (this.dataManager.get(STATUS) == 3) {
                this.dataManager.set(STATUS, 1);
            } else {
                this.dataManager.set(STATUS, 0);
            }
        }
    }

    private boolean hasSaddle() {
        return (this.dataManager.get(STATUS) == 1 || this.dataManager.get(STATUS) == 3);
    }

    private void setSaddled(boolean saddled) {
        if (saddled) {
            if (this.dataManager.get(STATUS) == 2) {
                this.dataManager.set(STATUS, 3);
            } else {
                this.dataManager.set(STATUS, 1);
            }
        } else {
            if (this.dataManager.get(STATUS) == 3) {
                this.dataManager.set(STATUS, 2);
            } else {
                this.dataManager.set(STATUS, 0);
            }
        }
    }

    private void updateHorseSlots() {
        if (!this.world.isRemote) {
            if (!this.beetleChest.getSlot(0).getHasStack()) {
                this.setSaddled(true);
            }
        }
    }

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
                            this.setAttackTarget((LivingEntity)null);
                            this.sitGoal.setSitting(true);
                            this.setHealth(24.0F);
                            this.world.setEntityState(this, (byte)7);
                        } else {
                            this.playTameEffect(false);
                            this.world.setEntityState(this, (byte)6);
                        }
                    }

                    return true;
                }

                if (itemstack.interactWithEntity(player, this, hand)) {
                    return true;
                } else {
                    return true;
                }
            }

            if (!this.hasChest() && itemstack.getItem() == Blocks.CHEST.asItem()) {
                this.setChested(true);
                this.playChestEquipSound();
                this.initBeetleChest();
            }

            if (!this.isChild() && !this.hasSaddle() && itemstack.getItem() == Items.SADDLE) {
                this.openGUI(player);
                return true;
            }
        }
        return super.processInteract(player, hand);
    }


    private void openGUI(PlayerEntity player) {
        if (!this.world.isRemote && (!this.isBeingRidden() || this.isPassenger(player)) && this.isTamed()) {

        }
    }

    private void initBeetleChest() {
        BeetleInventoryContainer inventory = this.beetleChest;
        int windowID = inventory.windowId;
        this.beetleChest = new BeetleInventoryContainer(windowID, player.inventory, null, this.beetleInventory, this);
        if (inventory != null) {
            for(int j = 0; j < 100; ++j) {
                ItemStack itemstack = inventory.getSlot(j).getStack();
                if (!itemstack.isEmpty()) {
                    this.beetleChest.putStackInSlot(j, itemstack.copy());
                }
            }
        }
        this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.beetleInventory));
    }

    private void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
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

