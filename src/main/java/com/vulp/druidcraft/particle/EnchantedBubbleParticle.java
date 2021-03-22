package com.vulp.druidcraft.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnchantedBubbleParticle extends SpriteTexturedParticle {

    private final IAnimatedSprite spriteSet;
    private int fakeAge = 0;
    private int fakeMaxAge;

    public EnchantedBubbleParticle(ClientWorld world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, IAnimatedSprite sprite) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.spriteSet = sprite;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.particleGravity = 0.002f;
        this.maxAge = 10;
        this.fakeMaxAge = this.rand.nextInt(10) + 30;
        this.selectSpriteWithAge(sprite);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return ICustomParticleRender.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ <= this.maxAge) {
            this.motionX += this.rand.nextFloat() / 5000.0F * (float)(this.rand.nextBoolean() ? 1 : -1);
            this.motionZ += this.rand.nextFloat() / 5000.0F * (float)(this.rand.nextBoolean() ? 1 : -1);
            this.motionY -= this.rand.nextFloat() / 5000.0F;
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.fakeAge++ <= this.fakeMaxAge) {
                this.age = 0;
                this.fakeAge++;
            } else {
                this.selectSpriteWithAge(spriteSet);
            }
        } else {
            this.setExpired();
        }
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

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new EnchantedBubbleParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
