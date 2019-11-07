package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.entities.AI.goals.NonTamedTargetGoalMonster;
import com.vulp.druidcraft.entities.AI.goals.OwnerHurtByTargetGoalMonster;
import com.vulp.druidcraft.entities.AI.goals.OwnerHurtTargetGoalMonster;
import com.vulp.druidcraft.events.EventFactory;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class BeetleEntity extends TameableMonsterEntity implements IInventoryChangedListener, INamedContainerProvider {
    private static final DataParameter<Boolean> SADDLE = EntityDataManager.createKey(BeetleEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHEST = EntityDataManager.createKey(BeetleEntity.class, DataSerializers.BOOLEAN);
    private Inventory beetleChest;
    private LazyOptional<?> itemHandler = null;

    public BeetleEntity(EntityType<? extends BeetleEntity> type, World worldIn) {
        super(type, worldIn);
        this.experienceValue = 10;
        this.initBeetleChest();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SADDLE, false);
        this.dataManager.register(CHEST, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.2F));
        this.goalSelector.addGoal(4, new AttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoalMonster(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoalMonster(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NonTamedTargetGoalMonster<>(this, PlayerEntity.class, false, null));
        this.targetSelector.addGoal(5, new NonTamedTargetGoalMonster<>(this, IronGolemEntity.class, false));
    }


    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0d);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0d);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5d);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15d);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(1.5d);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0d);
    }

    public boolean hasSaddle() {
        return (boolean) this.dataManager.get(SADDLE);
    }

    private void setSaddled(boolean saddled) {
        this.dataManager.set(SADDLE, saddled);
    }

    public boolean hasChest() {
        return (boolean) this.dataManager.get(CHEST);
    }

    private void setChested(boolean chested) {
        this.dataManager.set(CHEST, chested);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.8F;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("saddled", this.hasSaddle());
        compound.putBoolean("chested", this.hasChest());
        if (this.hasChest()) {
            ListNBT listnbt = new ListNBT();

            for (int i = 2; i < this.beetleChest.getSizeInventory(); ++i) {
                ItemStack itemstack = this.beetleChest.getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    CompoundNBT compoundnbt = new CompoundNBT();
                    compoundnbt.putByte("Slot", (byte) i);
                    itemstack.write(compoundnbt);
                    listnbt.add(compoundnbt);
                }
            }

            compound.put("Items", listnbt);
        }

        if (!this.beetleChest.getStackInSlot(0).isEmpty()) {
            compound.put("SaddleItem", this.beetleChest.getStackInSlot(0).write(new CompoundNBT()));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSaddled(compound.getBoolean("saddled"));
        this.setChested(compound.getBoolean("chested"));
        if (this.hasChest()) {
            ListNBT listnbt = compound.getList("Items", 10);
            this.initBeetleChest();

            for (int i = 0; i < listnbt.size(); ++i) {
                CompoundNBT compoundnbt = listnbt.getCompound(i);
                int j = compoundnbt.getByte("Slot") & 255;
                if (j >= 2 && j < this.beetleChest.getSizeInventory()) {
                    this.beetleChest.setInventorySlotContents(j, ItemStack.read(compoundnbt));
                }
            }
        }

        if (compound.contains("SaddleItem", 10)) {
            ItemStack itemstack = ItemStack.read(compound.getCompound("SaddleItem"));
            if (itemstack.getItem() == Items.SADDLE) {
                this.beetleChest.setInventorySlotContents(0, itemstack);
            }
        }

        this.updateBeetleSlots();
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
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
        if (hasChest()) {
            return 65;
        } else return 1;
    }

    private boolean canBeSaddled() {
        return true;
    }

    @Override
    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity)) {
            if (target instanceof TameableMonsterEntity) {
                TameableMonsterEntity monsterEntity = (TameableMonsterEntity) target;
                if (monsterEntity.isTamed() && monsterEntity.getOwner() == this.getOwner()) {
                    return false;
                }
            }

            if (target instanceof TameableEntity) {
                TameableEntity tameableEntity = (TameableEntity) target;
                if (tameableEntity.isTamed() && tameableEntity.getOwner() == this.getOwner()) {
                    return false;
                }
            }

            if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity) target).isTame()) {
                return false;
            } else {
                return !(target instanceof CatEntity) || !((CatEntity) target).isTamed();
            }
        } else {
            return false;
        }
    }

    private void updateBeetleSlots() {
        if (!this.world.isRemote) {
            this.setSaddled(!this.beetleChest.getStackInSlot(0).isEmpty() && this.canBeSaddled());
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (this.isTamed() && player.isSneaking()) {
            this.openGUI(player);
            return true;
        }
        if (!itemstack.isEmpty()) {
            if (item == Items.APPLE && (Float) this.getHealth() < this.getMaxHealth()) {
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                this.heal(8f);
                return true;
            }
            if (!this.isTamed() || itemstack.getItem() == Items.NAME_TAG) {
                if (itemstack.getItem() == Items.GOLDEN_APPLE) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    if (!this.world.isRemote) {
                        if (this.rand.nextInt(3) == 0 && !EventFactory.onMonsterTame(this, player)) {
                            this.playTameEffect(true);
                            this.setTamedBy(player);
                            this.navigator.clearPath();
                            this.setAttackTarget((LivingEntity) null);
                            this.setHealth(32.0F);
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
            } else if (this.isTamed()) {
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

                    if (!this.hasSaddle() && itemstack.getItem() == Items.SADDLE) {
                        this.openGUI(player);
                        return true;
                    }
                }
            }
        } else {
              if (!this.world.isRemote) {
                  if (this.hasSaddle() && !this.isBeingRidden()) {
                      this.mountTo(player);
                      return true;
                  }
              }
        }
        return super.processInteract(player, hand);
    }

   private void mountTo(PlayerEntity player) {
      if (!this.world.isRemote) {
         player.rotationYaw = this.rotationYaw;
         player.rotationPitch = this.rotationPitch;
         player.startRiding(this);
      }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (passenger instanceof MobEntity) {
            MobEntity mobentity = (MobEntity)passenger;
            this.renderYawOffset = mobentity.renderYawOffset;
            passenger.setPosition(this.posX, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ);
        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BeetleInventoryContainer(i, playerInventory, beetleChest, this.getEntityId());
    }

    private void openGUI(PlayerEntity playerEntity) {
        if (!this.world.isRemote && (!this.isBeingRidden() || this.isPassenger(playerEntity)) && this.isTamed()) {
            NetworkHooks.openGui((ServerPlayerEntity) playerEntity, this, packetBuffer -> {
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

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    this.beetleChest.setInventorySlotContents(j, itemstack.copy());
                }
            }
        }

        this.beetleChest.addListener(this);
        this.updateBeetleSlots();
        this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.beetleChest));
    }

    private void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (!this.world.isRemote) {
            if (isTamed()) {
                if (this.hasChest()) {
                    this.entityDropItem(Blocks.CHEST);
                    this.setChested(false);
                    for (int i = 0; i < this.beetleChest.getSizeInventory(); ++i) {
                        ItemStack itemstack = this.beetleChest.getStackInSlot(i);
                        if (!itemstack.isEmpty()) {
                            this.entityDropItem(itemstack);
                        }
                    }
                }

                if (this.hasSaddle()) {
                    this.entityDropItem(Items.SADDLE);
                    this.setSaddled(false);
                }
            } else {
                int j = this.rand.nextInt(2);
                for (int k = 0; k <= j; ++k) {
                    this.entityDropItem(ItemRegistry.chitin);
                }
            }
        }
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {
        boolean flag = this.hasSaddle();
        this.updateBeetleSlots();
        if (this.ticksExisted > 20 && !flag && this.hasSaddle()) {
            this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
        }
    }

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean canBeSteered() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    @Override
    public double getMountedYOffset() {
        return (double) (this.getHeight() - 0.15d);
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public void travel(Vec3d p_213352_1_) {
        if (this.isAlive()) {
            if (this.isBeingRidden() && this.canBeSteered() && this.hasSaddle()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
                this.rotationYaw = livingentity.rotationYaw;
                this.prevRotationYaw = this.rotationYaw;
                this.rotationPitch = livingentity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                this.rotationYawHead = this.renderYawOffset;
                this.stepHeight = 1.0F;
                float f = livingentity.moveStrafing * 0.5F;
                float f1 = livingentity.moveForward * 1F;

                if (this.canPassengerSteer()) {
                    this.setAIMoveSpeed((float)this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                    super.travel(new Vec3d((double)f, p_213352_1_.y, (double)f1));
                } else if (livingentity instanceof PlayerEntity) {
                    this.setMotion(Vec3d.ZERO);
                }
            } else {
                this.stepHeight = 0.5F;
                super.travel(p_213352_1_);
            }
        }
    }

    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(BeetleEntity beetle) {
            super(beetle, 0.85D, true);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(4.0F + attackTarget.getWidth());
        }
    }
}

