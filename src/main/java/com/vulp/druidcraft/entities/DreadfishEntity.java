package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.registry.EntityRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

@SuppressWarnings("unchecked")
public class DreadfishEntity extends MonsterEntity
{
    public DreadfishEntity(EntityType<? extends MonsterEntity> type, World worldIn)
    {
        super((EntityType<? extends MonsterEntity>) EntityRegistry.dreadfish_entity, worldIn);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new AttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new TargetGoal(this, PlayerEntity.class));
        this.targetSelector.addGoal(3, new TargetGoal(this, IronGolemEntity.class));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0d);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.1F;
    }

    static class TargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        TargetGoal(DreadfishEntity dreadfishEntity, Class<T> classTarget) {
            super(dreadfishEntity, classTarget, true);
        }

        public boolean shouldExecute() {
            float f = this.goalOwner.getBrightness();
            return !(f >= 0.5F) && super.shouldExecute();
        }
    }

    static class AttackGoal extends MeleeAttackGoal {
        AttackGoal(DreadfishEntity dreadfish) {
            super(dreadfish, 1.0D, true);
        }

        public boolean shouldExecute() {
            return super.shouldExecute() && !this.attacker.isBeingRidden();
        }

        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();
            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget((LivingEntity)null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(3.0F + attackTarget.getWidth());
        }
    }
}