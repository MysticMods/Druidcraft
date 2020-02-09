package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.items.LunarMothJarItem;
import com.vulp.druidcraft.registry.ItemRegistry;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.SoundEventRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class LunarMothEntity extends AnimalEntity {
    private static final DataParameter<Boolean> RESTING = EntityDataManager.createKey(LunarMothEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Direction> IDLING = EntityDataManager.createKey(LunarMothEntity.class, DataSerializers.DIRECTION);
    public static final DataParameter<Integer> COLOR = EntityDataManager.createKey(LunarMothEntity.class, DataSerializers.VARINT);
    private static final EntityPredicate entityPredicate = (new EntityPredicate()).setDistance(4.0D).allowFriendlyFire();
    public int timeUntilDropGlowstone;
    private BlockPos spawnPosition;
    public LunarMothEntity(EntityType<? extends LunarMothEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.timeUntilDropGlowstone = this.rand.nextInt(10000) + 10000;
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(RESTING, false);
        this.dataManager.register(IDLING, Direction.NORTH);
        this.dataManager.register(COLOR, LunarMothColors.colorToInt(LunarMothColors.randomColor(rand)));
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (item == Items.GLASS_BOTTLE) {
            player.getEntityWorld().playSound(player, player.posX, player.posY, player.posZ, SoundEventRegistry.fill_bottle, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            this.bottleToMothJar(itemstack, player);
            remove();
            return true;
        }
        return super.processInteract(player, hand);
    }

    protected void bottleToMothJar(ItemStack itemstack, PlayerEntity player) {
        itemstack.shrink(1);

        ItemStack stack = LunarMothJarItem.getItemStackByColor(getColor());
        CompoundNBT entityData = new CompoundNBT();
        writeAdditional(entityData);
        stack.getOrCreateTag().put("EntityTag", entityData);

        if (!player.inventory.addItemStackToInventory(stack)) {
            player.dropItem(stack, false);
        }
    }

    public boolean getMothResting() {
        return (this.dataManager.get(RESTING));
    }

    public LunarMothColors getColor() {
        return LunarMothColors.colorArray().get(this.dataManager.get(COLOR));
    }

    public Direction getMothIdleDirection() {
        return (this.dataManager.get(IDLING));
    }

    public void setMothResting(boolean resting) {
        this.dataManager.set(RESTING, resting);
    }

    public void setMothIdleDirection(Direction direction) {
        this.dataManager.set(IDLING, direction);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getMothResting()) {
            this.setMotion(Vec3d.ZERO);
        } else {
            this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
        }

    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.up();
        if (this.getMothResting()) {
            if (this.world.getBlockState(blockpos1).isNormalCube(this.world, blockpos)) {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = (float)this.rand.nextInt(360);
                }

                if (this.world.getClosestPlayer(entityPredicate, this) != null) {
                    this.setMothResting(false);
                    this.world.playEvent(null, 1025, blockpos, 0);
                }
            } else {
                this.setMothResting(false);
                this.world.playEvent(null, 1025, blockpos, 0);
            }
        } else {
            if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
            }

            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.withinDistance(this.getPositionVec(), 2.0D)) {
                this.spawnPosition = new BlockPos(this.posX + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7), this.posY + (double)this.rand.nextInt(6) - 2.0D, this.posZ + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7));
            }

            double d0 = (double)this.spawnPosition.getX() + 0.5D - this.posX;
            double d1 = (double)this.spawnPosition.getY() + 0.1D - this.posY;
            double d2 = (double)this.spawnPosition.getZ() + 0.5D - this.posZ;
            Vec3d vec3d = this.getMotion();
            Vec3d vec3d1 = vec3d.add((Math.signum(d0) * 0.5D - vec3d.x) * 0.10000000149011612D, (Math.signum(d1) * 0.699999988079071D - vec3d.y) * 0.10000000149011612D, (Math.signum(d2) * 0.5D - vec3d.z) * 0.10000000149011612D);
            this.setMotion(vec3d1);
            float f = (float)(MathHelper.atan2(vec3d1.z, vec3d1.x) * 57.2957763671875D) - 90.0F;
            float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += f1;
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.world.isRemote && this.getMothResting()) {
                this.setMothResting(false);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(RESTING, compound.getBoolean("MothResting"));
        this.dataManager.set(IDLING, Direction.byIndex(compound.getByte("MothIdleDirection")));
        this.dataManager.set(COLOR, compound.getInt("Color"));
        if (compound.contains("GlowstoneDropTime")) {
            this.timeUntilDropGlowstone = compound.getInt("GlowstoneDropTime");
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("MothResting", this.dataManager.get(RESTING));
        compound.putByte("MothIdleDirection", (byte) this.dataManager.get(IDLING).getIndex());
        compound.putInt("Color", this.dataManager.get(COLOR));
        compound.putInt("GlowstoneDropTime", this.timeUntilDropGlowstone);
    }

    public static boolean placement(EntityType<LunarMothEntity> entity, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getBlockState(pos.down()).getBlock() != Blocks.AIR;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.isAlive() && --this.timeUntilDropGlowstone <= 0) {
            if (rand.nextInt(7) == 0) {

                ItemStack eggItem;
                int eggColor;


                if (getColor() == LunarMothColors.LIME) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_lime);
                    eggColor = 2;
                }
                else if (getColor() == LunarMothColors.ORANGE) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_orange);
                    eggColor = 4;
                }
                else if (getColor() == LunarMothColors.YELLOW) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_yellow);
                    eggColor = 3;
                }
                else if (getColor() == LunarMothColors.PINK) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_pink);
                    eggColor = 5;
                }
                else if (getColor() == LunarMothColors.WHITE) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_white);
                    eggColor = 1;
                }
                else {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_turquoise);
                    eggColor = 0;
                }

                CompoundNBT tag = eggItem.getOrCreateTag();
                CompoundNBT entityData = new CompoundNBT();
                entityData.putInt("Color", eggColor);
                tag.put("EntityData", entityData);

                this.entityDropItem(eggItem);

            }
            else {
                this.entityDropItem(Items.GLOWSTONE_DUST);
            }
            this.timeUntilDropGlowstone = this.rand.nextInt(10000) + 10000;
        }
        if (this.world.isRemote) {
            int red = 1;
            int green = 1;
            int blue = 1;
            if (getColor() == LunarMothColors.TURQUOISE) {
                red = 85;
                green = 255;
                blue = 160;
            }
            if (getColor() == LunarMothColors.PINK) {
                red = 255;
                green = 200;
                blue = 240;
            }
            if (getColor() == LunarMothColors.LIME) {
                red = 180;
                green = 255;
                blue = 110;
            }
            if (getColor() == LunarMothColors.ORANGE) {
                red = 255;
                green = 200;
                blue = 80;
            }
            if (getColor() == LunarMothColors.WHITE) {
                red = 255;
                green = 250;
                blue = 240;
            }
            if (getColor() == LunarMothColors.YELLOW) {
                red = 255;
                green = 255;
                blue = 140;
            }

            float colorMod;

            if (rand.nextInt(3) == 3) {
                colorMod = 1.1f;
            }
            else if (rand.nextInt(3) == 3) {
                colorMod = 0.9f;
            }
            else {
                colorMod = 1.0f;
            }

            if (rand.nextBoolean()) {
                world.addParticle(ParticleRegistry.magic_glitter, false, this.posX + (((rand.nextDouble() - 0.5)) / 3), this.posY + ((rand.nextDouble() - 0.5) / 3) + 0.2, this.posZ + (((rand.nextDouble() - 0.5)) / 3), (red / 255.f) * colorMod, (green / 255.f) * colorMod, (blue / 255.f) * colorMod);
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height / 2.0F;
    }

}
