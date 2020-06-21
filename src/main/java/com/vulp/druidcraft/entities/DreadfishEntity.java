package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.api.ICooldownAttackMob;
import com.vulp.druidcraft.entities.AI.goals.*;
import com.vulp.druidcraft.events.EventFactory;
import com.vulp.druidcraft.pathfinding.ImprovedFlyingPathNavigator;
import com.vulp.druidcraft.registry.ParticleRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class DreadfishEntity extends TameableMonsterEntity implements IFlyingAnimal, IRangedAttackMob, ICooldownAttackMob
{
    private static final Predicate<LivingEntity> isPlayer;
    private static final DataParameter<Integer> SMOKE_COLOR = EntityDataManager.createKey(DreadfishEntity.class, DataSerializers.VARINT);
    private static final Map<DyeColor, int[]> DYE_COLOR_MAP = new HashMap<>();
    private DyeColor smokeColor = null;
    private final RangedAttackGoal breathAttack = new RangedAttackGoal(this, 1.5D, 10, 20.0F);
    private final MeleeAttackGoal meleeAttack = new MeleeAttackGoal(this, 3.0D, true);
    private int cooldown;


    static {
        DYE_COLOR_MAP.put(DyeColor.BLACK, new int[]{15, 15, 15});
        DYE_COLOR_MAP.put(DyeColor.RED, new int[]{255, 50, 40});
        DYE_COLOR_MAP.put(DyeColor.GREEN, new int[]{15, 150, 45});
        DYE_COLOR_MAP.put(DyeColor.BROWN, new int[]{130, 70, 45});
        DYE_COLOR_MAP.put(DyeColor.BLUE, new int[]{30, 60, 225});
        DYE_COLOR_MAP.put(DyeColor.PURPLE, new int[]{135, 45, 245});
        DYE_COLOR_MAP.put(DyeColor.CYAN, new int[]{20, 125, 130});
        DYE_COLOR_MAP.put(DyeColor.LIGHT_GRAY, new int[]{160, 160, 155});
        DYE_COLOR_MAP.put(DyeColor.GRAY, new int[]{90, 90, 90});
        DYE_COLOR_MAP.put(DyeColor.PINK, new int[]{255, 115, 170});
        DYE_COLOR_MAP.put(DyeColor.LIME, new int[]{135, 250, 35});
        DYE_COLOR_MAP.put(DyeColor.YELLOW, new int[]{240, 240, 50});
        DYE_COLOR_MAP.put(DyeColor.LIGHT_BLUE, new int[]{50, 200, 255});
        DYE_COLOR_MAP.put(DyeColor.MAGENTA, new int[]{230, 65, 170});
        DYE_COLOR_MAP.put(DyeColor.ORANGE, new int[]{240, 135, 30});
        DYE_COLOR_MAP.put(DyeColor.WHITE, new int[]{215, 215, 215});
    }

    public DreadfishEntity(EntityType<? extends TameableMonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new FlyingMovementController(this, 10, false);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, -1.0F);
        this.setTamed(false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.sitGoal = new SitGoalMonster(this);
        this.goalSelector.addGoal(1, this.sitGoal);
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.5D, 60, 20.0F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 3.0D, true));
        this.goalSelector.addGoal(5, new FollowOwnerGoalMonster(this, 2.0D, 5.0F, 1.0F));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomFlyingGoal(this, 1.0D));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoalMonster(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoalMonster(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NonTamedTargetGoalMonster(this, PlayerEntity.class, false, isPlayer));
        this.targetSelector.addGoal(5, new NonTamedTargetGoalMonster(this, IronGolemEntity.class, false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.3F);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.975d);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SMOKE_COLOR, DyeColor.PURPLE.getId());
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack itemstack = this.findAmmo(new ItemStack(Items.SPECTRAL_ARROW));
        AbstractArrowEntity abstractarrowentity = this.fireArrow(itemstack, distanceFactor);
        double d0 = target.getPosX() - this.getPosX();
        double d1 = target.getPosYHeight(0.3333333333333333D) - abstractarrowentity.getPosY();
        double d2 = target.getPosZ() - this.getPosZ();
        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        abstractarrowentity.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(abstractarrowentity);
        this.cooldown = 0;
    }

    protected AbstractArrowEntity fireArrow(ItemStack arrowStack, float distanceFactor) {
        return ProjectileHelper.fireArrow(this, arrowStack, distanceFactor);
    }

    @Override
    public boolean canUseAttack(LivingEntity entity) {
        return this.cooldown <= 50;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
    }

    public static boolean placement(EntityType<?> type, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(worldIn, pos, randomIn);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.2F;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    private static boolean isValidLightLevel(IWorld world, BlockPos pos, Random rand) {
        if (world.getLightFor(LightType.SKY, pos) > rand.nextInt(32)) {
            return false;
        } else {
            int i = world.getWorld().isThundering() ? world.getNeighborAwareLightSubtracted(pos, 10) : world.getLight(pos);
            return i <= rand.nextInt(8);
        }
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (this.sitGoal != null) {
                this.sitGoal.setSitting(false);
            }

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Hostile", this.isHostile());
        compound.putByte("SmokeColor", (byte)this.getSmokeColor().getId());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setHostile(compound.getBoolean("Hostile"));
        if (compound.contains("SmokeColor", 99)) {
            this.setSmokeColor(DyeColor.byId(compound.getInt("SmokeColor")));
        }
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
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
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        }
    }

    @Override
    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
        if (entitylivingbaseIn == null) {
            this.setHostile(false);
        } else if (!this.isTamed()) {
            this.setHostile(true);
        }

    }

    public int[] getSmokeColorArray () {
        return DYE_COLOR_MAP.getOrDefault(getSmokeColor(), new int[]{0, 0, 0});
    }

    public DyeColor getSmokeColor() {
        if (smokeColor == null) {
            smokeColor = DyeColor.byId(this.dataManager.get(SMOKE_COLOR));
        }
        return smokeColor;
    }

    public void setSmokeColor(DyeColor smokeColor) {
        this.dataManager.set(SMOKE_COLOR, smokeColor.getId());
        this.smokeColor = smokeColor;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (this.isTamed()) {
            if (!itemstack.isEmpty()) {
                if (item == Items.BONE && this.getHealth() < this.getMaxHealth()) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    this.heal(4f);
                    return true;
                }

                else if (item instanceof DyeItem) {
                    DyeColor dyecolor = ((DyeItem) item).getDyeColor();
                    if (dyecolor != this.getSmokeColor()) {
                        this.setSmokeColor(dyecolor);
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        return true;
                    }
                }
            }
            if (this.isOwner(player) && !this.world.isRemote) {
                this.sitGoal.setSitting(!this.isSitting());
                this.getNavigator().clearPath();
                this.setAttackTarget(null);
            }
        }
        else if (item == Items.PRISMARINE_CRYSTALS) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0 && !EventFactory.onMonsterTame(this, player)) {
                    this.playTameEffect(true);
                    this.setTamedBy(player);
                    this.getNavigator().clearPath();
                    this.setAttackTarget(null);
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

        return super.processInteract(player, hand);
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

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return !this.isHostile() && !this.isTamed();
    }

    static {
        isPlayer = (type) -> {
            EntityType<?> entitytype = type.getType();
            return entitytype == EntityType.PLAYER;
        };
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity entityIn) {
        if (!(entityIn instanceof PlayerEntity) && !(entityIn instanceof DreadfishEntity)) {
            super.collideWithEntity(entityIn);
        }
    }


    public boolean isHostile() {
        return (this.dataManager.get(TAMED) & 2) != 0;
    }

    public void setHostile(boolean hostile) {
        byte b0 = this.dataManager.get(TAMED);
        if (hostile) {
            this.dataManager.set(TAMED, (byte)(b0 | 2));
        } else {
            this.dataManager.set(TAMED, (byte)(b0 & -3));
        }

    }

    public void particle() {
        int[] color = getSmokeColorArray();
        world.addParticle(ParticleRegistry.magic_smoke, false, this.getPosX(), this.getPosY() + (((rand.nextDouble() - 0.5) + 0.2) / 4) + 0.1F, this.getPosZ() + (((rand.nextDouble() - 0.5) + 0.2) / 3), color[0] / 255.f, color[1] / 255.f, color[2] / 255.f);
    }

    @Override
    public void livingTick() {

        super.livingTick();
        if (this.world.isRemote) {
            if ((int)(this.limbSwing * 10) > 0 && rand.nextInt((int)(this.limbSwing * 10)) != 0) {
                if (rand.nextInt(3) == 0) {
                    particle();
                }
            }
            else {
                if (rand.nextInt(8) == 0) {
                    particle();
                }
            }
        }

        if (this.cooldown < 50) {
            this.cooldown++;
        }

        if (!this.world.isRemote && this.getAttackTarget() == null && this.isHostile()) {
            this.setHostile(false);
        }
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

}