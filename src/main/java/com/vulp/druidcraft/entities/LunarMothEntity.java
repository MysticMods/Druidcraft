package com.vulp.druidcraft.entities;

import com.mojang.datafixers.util.Pair;
import com.vulp.druidcraft.entities.AI.goals.*;
import com.vulp.druidcraft.items.LunarMothJarItem;
import com.vulp.druidcraft.pathfinding.ImprovedFlyingPathNavigator;
import com.vulp.druidcraft.registry.ItemRegistry;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.SoundEventRegistry;
import com.vulp.druidcraft.util.VectorUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class LunarMothEntity extends AnimalEntity {
    private static final DataParameter<Boolean> RESTING = EntityDataManager.createKey(LunarMothEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Direction> IDLING = EntityDataManager.createKey(LunarMothEntity.class, DataSerializers.DIRECTION);
    public static final DataParameter<Integer> COLOR = EntityDataManager.createKey(LunarMothEntity.class, DataSerializers.VARINT);
    private static final EntityPredicate entityPredicate = (new EntityPredicate()).setDistance(4.0D).allowFriendlyFire();
    private BlockPos spawnPosition;
    public LunarMothEntity(EntityType<? extends LunarMothEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new FlyingMovementController(this, 10, false);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.TORCH, Items.REDSTONE_TORCH, Items.LANTERN, ItemRegistry.fiery_torch, ItemRegistry.ceramic_lantern, ItemRegistry.growth_lamp), false));
        this.goalSelector.addGoal(5, new FollowParentLunarMothGoal(this, 1.25D));
/*        this.goalSelector.addGoal(5, new RestGoal(this));
        this.goalSelector.addGoal(5, new HoverAtLight(this));*/
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new SwimGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.6);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(RESTING, false);
        this.dataManager.register(IDLING, Direction.NORTH);
        this.dataManager.register(COLOR, LunarMothColors.colorToInt(LunarMothColors.randomColor(rand)));
    }

    public boolean isResting() {
        return (this.dataManager.get(RESTING));
    }

    public void setResting(boolean resting) {
        this.dataManager.set(RESTING, resting);
    }

    public Direction getRestingDirection() {
        return (this.dataManager.get(IDLING));
    }

    public void setRestingDirection(Direction direction) {
        this.dataManager.set(IDLING, direction);
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (item == Items.GLASS_BOTTLE) {
            player.getEntityWorld().playSound(player, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEventRegistry.fill_bottle, SoundCategory.NEUTRAL, 1.0F, 1.0F);
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


    public LunarMothColors getColor() {
        return LunarMothColors.colorArray().get(this.dataManager.get(COLOR));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isResting()) {
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
        if (this.isResting()) {
            if (this.world.getBlockState(blockpos1).isNormalCube(this.world, blockpos)) {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = (float)this.rand.nextInt(360);
                }

                if (this.world.getClosestPlayer(entityPredicate, this) != null) {
                    this.setResting(false);
                    this.world.playEvent(null, 1025, blockpos, 0);
                }
            } else {
                this.setResting(false);
                this.world.playEvent(null, 1025, blockpos, 0);
            }
        } else {
            if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
            }

            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.withinDistance(this.getPositionVec(), 2.0D)) {
                this.spawnPosition = new BlockPos(this.getPosX() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7), this.getPosY() + (double)this.rand.nextInt(6) - 2.0D, this.getPosZ() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7));
            }

            double d0 = (double)this.spawnPosition.getX() + 0.5D - this.getPosX();
            double d1 = (double)this.spawnPosition.getY() + 0.1D - this.getPosY();
            double d2 = (double)this.spawnPosition.getZ() + 0.5D - this.getPosZ();
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
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean onLivingFall(float p_225503_1_, float p_225503_2_) {
        return false;
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
            if (!this.world.isRemote && this.isResting()) {
                this.setResting(false);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        ImprovedFlyingPathNavigator flyingpathnavigator = new ImprovedFlyingPathNavigator(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanSwim(false);
        flyingpathnavigator.setCanEnterDoors(true);
        flyingpathnavigator.canEntityStandOnPos(null);
        return flyingpathnavigator;
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(RESTING, compound.getBoolean("MothResting"));
        this.dataManager.set(IDLING, Direction.byIndex(compound.getInt("MothIdleDirection")));
        this.dataManager.set(COLOR, compound.getInt("Color"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("MothResting", this.dataManager.get(RESTING));
        compound.putInt("MothIdleDirection", this.dataManager.get(IDLING).getIndex());
        compound.putInt("Color", this.dataManager.get(COLOR));
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
        if (this.world.isRemote) {
            int red = 1;
            int green = 1;
            int blue = 1;
            if (getColor() == LunarMothColors.TURQUOISE) {
                red = 85;
                green = 255;
                blue = 160;
            } if (getColor() == LunarMothColors.PINK) {
                red = 255;
                green = 200;
                blue = 240;
            } if (getColor() == LunarMothColors.LIME) {
                red = 180;
                green = 255;
                blue = 110;
            } if (getColor() == LunarMothColors.ORANGE) {
                red = 255;
                green = 200;
                blue = 80;
            } if (getColor() == LunarMothColors.WHITE) {
                red = 255;
                green = 250;
                blue = 240;
            } if (getColor() == LunarMothColors.YELLOW) {
                red = 255;
                green = 255;
                blue = 140;
            }
            float colorMod;
            if (rand.nextInt(3) == 3) {
                colorMod = 1.1f;
            } else if (rand.nextInt(3) == 3) {
                colorMod = 0.9f;
            } else {
                colorMod = 1.0f;
            }
            if (rand.nextBoolean()) {
                world.addParticle(ParticleRegistry.magic_glitter, false, this.getPosX() + (((rand.nextDouble() - 0.5)) / 3), this.getPosY() + ((rand.nextDouble() - 0.5) / 3) + 0.2, this.getPosZ() + (((rand.nextDouble() - 0.5)) / 3), (red / 255.f) * colorMod, (green / 255.f) * colorMod, (blue / 255.f) * colorMod);
            }
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height / 2.0F;
    }

    class RestOnWallGoal extends MoveToBlockGoal {

        private int sleepChance = LunarMothEntity.this.rand.nextInt(140);

        RestOnWallGoal(LunarMothEntity creatureIn, double speed, int searchLength) {
            super(creatureIn, speed, searchLength);
        }

        @Override
        public void resetTask() {
            super.resetTask();
        }

        @Override
        public boolean shouldExecute() {
            if (isResting()) {
                return true;
            } else return !world.isNightTime() && super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (!world.isNightTime()) {
                return super.shouldContinueExecuting();
            }
            return false;
        }

        @Override
        public double getTargetDistanceSq() {
            return 1.14D;
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader iWorldReader, BlockPos blockPos) {
            return false;
        }

        @Override
        public void tick() {
            Vec3d vec3d = getPositionVec();
            Vec3d vec3d1 = new Vec3d(destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ());
            Direction direction = VectorUtil.getDirection(vec3d, vec3d1);
            if (world.getBlockState(destinationBlock).isSolidSide(world, destinationBlock, direction)) {

            }
            super.tick();
        }
    }


    class FollowParentLunarMothGoal extends FollowParentGoal {

        FollowParentLunarMothGoal(AnimalEntity child, double moveSpeed) {
            super(child, moveSpeed);
        }

        @Override
        public boolean shouldExecute() {
            if (this.childAnimal.getGrowingAge() >= 0) {
                return false;
            } else {
                List<AnimalEntity> lvt_1_1_ = this.childAnimal.world.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
                AnimalEntity lvt_2_1_ = null;
                double lvt_3_1_ = 1.7976931348623157E308D;

                for (AnimalEntity lvt_6_1_ : lvt_1_1_) {
                    if (lvt_6_1_.getGrowingAge() >= 0) {
                        double lvt_7_1_ = this.childAnimal.getDistanceSq(lvt_6_1_);
                        if (lvt_7_1_ <= lvt_3_1_) {
                            lvt_3_1_ = lvt_7_1_;
                            lvt_2_1_ = lvt_6_1_;
                        }
                    }
                }

                if (lvt_2_1_ == null) {
                    return false;
                } else if (lvt_3_1_ < 9.0D) {
                    return false;
                } else {
                    this.parentAnimal = lvt_2_1_;
                    LunarMothEntity entity = (LunarMothEntity)this.parentAnimal;
                    if (entity.isResting()) {
                        return false;
                    } else return true;
                }
            }
        }

        public boolean shouldContinueExecuting() {
            if (this.childAnimal.getGrowingAge() >= 0) {
                return false;
            }
            LunarMothEntity entity = (LunarMothEntity)this.parentAnimal;
            if (!this.parentAnimal.isAlive() || entity.isResting()) {
                return false;
            } else {
                double lvt_1_1_ = this.childAnimal.getDistanceSq(this.parentAnimal);
                return lvt_1_1_ >= 9.0D && lvt_1_1_ <= 256.0D;
            }
        }

    }

}
