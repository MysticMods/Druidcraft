package com.vulp.druidcraft.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.Druidcraft;
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

public class FlareParticle extends SpriteTexturedParticle {

    private final float rotSpeed;
    private float pitch;
    private float yaw;
    private final Quaternion rotation = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);

    public FlareParticle(ClientWorld world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, IAnimatedSprite sprite) {
        super(world, posX, posY, posZ);
        this.maxAge = this.rand.nextInt(50) + 80;
        this.particleGravity = 3.0E-6F;
        this.motionX = motionX;
        this.motionY = motionY + (double)(this.rand.nextFloat() / 500.0F);
        this.motionZ = motionZ;
        this.particleScale *= 1.5F;
        this.rotSpeed = ((float)Math.random() - 0.5F) * 0.05F;
        this.selectSpriteRandomly(sprite);
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevParticleAngle = this.particleAngle;
        this.particleAngle += 3.1415927F * this.rotSpeed * 2.0F;
        if (this.age++ < this.maxAge && (this.particleAlpha > 0.0F || this.particleScale > 0.0F)) {
            this.motionY -= this.particleGravity;
            if (this.particleScale > 0.002F) {
                this.particleScale -= 0.002F;
            } else {
                this.setExpired();
            }
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.age >= this.maxAge - 60 && this.particleAlpha > 0.01F) {
                this.particleAlpha -= 0.015F;
            }
        } else {
            this.setExpired();
        }
    }

    private void setRotation(float pitchIn, float yawIn) {
        this.pitch = yawIn;
        this.yaw = pitchIn;
        this.rotation.set(0.0F, 0.0F, 0.0F, 1.0F);
        this.rotation.multiply(Vector3f.XP.rotationDegrees(yawIn));
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

        buffer.pos((double)avector3f[0].getX(), this.posY, (double)avector3f[0].getZ()).tex(f8, f6).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        buffer.pos((double)avector3f[1].getX(), this.posY, (double)avector3f[1].getZ()).tex(f8, f5).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        buffer.pos((double)avector3f[2].getX(), this.posY, (double)avector3f[2].getZ()).tex(f7, f5).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        buffer.pos((double)avector3f[3].getX(), this.posY, (double)avector3f[3].getZ()).tex(f7, f6).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
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

}
