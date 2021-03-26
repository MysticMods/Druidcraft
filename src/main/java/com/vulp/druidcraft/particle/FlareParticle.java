package com.vulp.druidcraft.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.FlareTorchBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlareParticle extends SpriteTexturedParticle {

    private float pitch;
    private float yaw;
    private final Quaternion rotation = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
    private final IAnimatedSprite spriteSet;
    private int ageOverrider;

    public FlareParticle(ClientWorld world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, IAnimatedSprite sprite) {
        super(world, posX, posY, posZ);
        this.maxAge = 9;
        Random rand = new Random();
        this.age = rand.nextInt(9);
        this.ageOverrider = 0;
        this.particleScale = 6.0F;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.spriteSet = sprite;
        this.selectSpriteWithAge(sprite);
    }

    public void tick() {
        this.ageOverrider += 1;
        this.age = this.ageOverrider;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.selectSpriteWithAge(this.spriteSet);
        if (this.ageOverrider >= this.maxAge) {
            BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
            BlockState state = this.world.getBlockState(pos);
            if (!(state.getBlock() instanceof FlareTorchBlock)) {
                this.setExpired();
            }
            this.ageOverrider = 0;
        }
    }

    private void setRotation(float pitchIn, float yawIn) {
        this.pitch = yawIn;
        this.yaw = pitchIn;
        this.rotation.set(0.0F, 0.0F, 0.0F, 1.0F);
        this.rotation.multiply(Vector3f.YP.rotationDegrees(-pitchIn));
    }

    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        Vector3d vector3d = renderInfo.getProjectedView();
        float f = (float)(MathHelper.lerp((double)partialTicks, this.prevPosX, this.posX) - vector3d.getX());
        float f1 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosY, this.posY) - vector3d.getY());
        float f2 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosZ, this.posZ) - vector3d.getZ());
        Quaternion quaternion;
        if (this.particleAngle == 0.0F) {
            quaternion = this.rotation;
        } else {
            quaternion = new Quaternion(this.rotation);
            float f3 = MathHelper.lerp(partialTicks, this.prevParticleAngle, this.particleAngle);
            quaternion.multiply(Vector3f.ZP.rotation(f3));
        }

        Entity entity = renderInfo.getRenderViewEntity();
        this.setRotation(entity.getYaw(partialTicks), entity.getPitch(partialTicks));
        if (renderInfo.isThirdPerson()) {
            if (renderInfo.thirdPersonReverse) {
                this.setRotation(this.yaw + 180.0F, -this.pitch);
            }
        } else if (entity instanceof LivingEntity && ((LivingEntity)entity).isSleeping()) {
            Direction direction = ((LivingEntity)entity).getBedDirection();
            this.setRotation(direction != null ? direction.getHorizontalAngle() - 180.0F : 0.0F, 0.0F);
        }

        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        vector3f1.transform(quaternion);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f4 = this.getScale(partialTicks);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }

        float f7 = this.getMinU();
        float f8 = this.getMaxU();
        float f5 = this.getMinV();
        float f6 = this.getMaxV();
        int j = this.getBrightnessForRender(partialTicks);

        buffer.pos((double)avector3f[0].getX(), (double)avector3f[0].getY(), (double)avector3f[0].getZ()).tex(f8, f6).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        buffer.pos((double)avector3f[1].getX(), (double)avector3f[1].getY(), (double)avector3f[1].getZ()).tex(f8, f5).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        buffer.pos((double)avector3f[2].getX(), (double)avector3f[2].getY(), (double)avector3f[2].getZ()).tex(f7, f5).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        buffer.pos((double)avector3f[3].getX(), (double)avector3f[3].getY(), (double)avector3f[3].getZ()).tex(f7, f6).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        float f = 1.0F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getBrightnessForRender(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return ICustomParticleRender.PARTICLE_SHEET_TRANSLUCENT_GLOW;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FlareParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    @Override
    public boolean shouldCull() {
        return false;
    }
}
