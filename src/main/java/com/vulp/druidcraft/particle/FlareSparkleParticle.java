package com.vulp.druidcraft.particle;

import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlareSparkleParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;
    private final float rotSpeed;
    private int fizzleAge;
    private float fizzleScale;
    private boolean fizzleTriggered;
    private int sparkleTimer;

    public FlareSparkleParticle(ClientWorld world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, IAnimatedSprite sprite) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.spriteSet = sprite;
        this.motionX = this.motionX * 0.009999999776482582D + motionX;
        this.motionY = this.motionY * 0.009999999776482582D + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582D + motionZ;
        this.posX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
        this.posY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
        this.posZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
        this.maxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
        this.particleScale *= 5.0F;
        this.rotSpeed = ((float) Math.random() - 0.5F) * 0.05F;
        this.fizzleAge = 0;
        this.fizzleScale = 0;
        this.fizzleTriggered = false;
        this.particleAngle *= (float) Math.random() * 6.2831855F;
        this.sparkleTimer = this.rand.nextInt(8);
        this.selectSpriteRandomly(spriteSet);
    }

    @Override
    public float getScale(float p_217561_1_) {
        double f;
        if ((float)age / (float)maxAge <= 0.3 || !(this.fizzleAge == 0)) {
            f = MathHelper.cos((float)age / 2) * -0.035 + 0.125;
        } else {
            f = this.getFizzleScale() * (((float)age / (float)maxAge) / this.getFizzleAge());
        }
        return (float) f;
    }

    public void setFizzleScale(float fizzleScale) {
        this.fizzleScale = fizzleScale;
    }

    public float getFizzleScale() {
        return this.fizzleScale;
    }

    public void setFizzleAge(int fizzleAge) {
        this.fizzleAge = fizzleAge;
    }

    public int getFizzleAge() {
        return this.fizzleAge;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return ICustomParticleRender.PARTICLE_SHEET_TRANSLUCENT_ADDITIVE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.sparkleTimer <= 0) {
            this.selectSpriteRandomly(spriteSet);
            this.sparkleTimer = this.rand.nextInt(8);
        }
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
            this.prevParticleAngle = this.particleAngle;
            this.particleAngle += 3.1415927F * this.rotSpeed * 2.0F;
            if ((float)this.age / (float)this.maxAge <= 0.3 && !fizzleTriggered) {
                fizzleTriggered = true;
                this.setFizzleAge(this.age);
                this.setFizzleScale(this.particleScale);
            }
        }

    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        float f = ((float) this.age + partialTick) / (float) this.maxAge;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getBrightnessForRender(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int) (f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FlareSparkleParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

}
