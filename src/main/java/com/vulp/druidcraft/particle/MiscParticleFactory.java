package com.vulp.druidcraft.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.UnderwaterParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class MiscParticleFactory {

    @OnlyIn(Dist.CLIENT)
    public static class OvergrownSporeFactory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public OvergrownSporeFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @OnlyIn(Dist.CLIENT)
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Random rand = worldIn.rand;
            double velX = rand.nextGaussian() * 12E-7D;
            double velY = (rand.nextDouble() - 0.2D) * 12E-5D;
            double velZ = rand.nextGaussian() * 12E-7D;
            UnderwaterParticle particle = new UnderwaterParticle(worldIn, x, y, z, velX, velY, velZ);
            particle.selectSpriteRandomly(this.spriteSet);
            particle.setColor(1.0F, 0.7F, 0.3F);
            particle.setSize(0.001F, 0.001F);
            return particle;
        }
    }

}
