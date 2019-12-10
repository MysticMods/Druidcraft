package com.vulp.druidcraft.particle;

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MagicMistParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;

    public MagicMistParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, IAnimatedSprite sprite) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.spriteSet = sprite;
        this.motionX = this.motionX * 0.009999999776482582d;
        this.motionY = this.motionY * 0.009999999776482582d;
        this.motionZ = this.motionZ * 0.009999999776482582d;
        this.particleScale = 0.3f;
        this.particleGravity = 0.002f;
        this.particleRed = (float) motionX;
        this.particleGreen = (float) motionY;
        this.particleBlue = (float) motionZ;
        this.maxAge = this.rand.nextInt(20) + 10;
        this.selectSpriteRandomly(sprite);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ < this.maxAge && this.particleAlpha > 1.0F) {
            this.particleAlpha = (float)this.maxAge - (float)this.age / (float)this.maxAge;
            this.motionX += this.rand.nextFloat() / 7500.0F * (float)(this.rand.nextBoolean() ? 1 : -1);
            this.motionZ += this.rand.nextFloat() / 7500.0F * (float)(this.rand.nextBoolean() ? 1 : -1);
            this.motionY -= this.rand.nextFloat() / 2000.0F * (float)(this.rand.nextBoolean() ? 1 : -1);
            this.move(this.motionX, this.motionY, this.motionZ);
        } else {
            this.setExpired();
        }

    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        float f = ((float)this.age + partialTick) / (float)this.maxAge;
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
        public Particle makeParticle(BasicParticleType typeIn, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MagicMistParticle particle = new MagicMistParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            return particle;
        }
    }
}