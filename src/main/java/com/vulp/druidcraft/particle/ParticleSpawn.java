package com.vulp.druidcraft.particle;

import com.vulp.druidcraft.util.IParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public enum ParticleSpawn {
    MAGIC_SMOKE,
    MAGIC_RISING_SPARK;

    ParticleSpawn() {}

    @OnlyIn(Dist.CLIENT)
    public Particle create(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... params) {
        return getFactory().makeParticle(world, x, y, z, velocityX, velocityY, velocityZ, params);
    }

    @OnlyIn(Dist.CLIENT)
    public IParticle getFactory() {
        switch (this) {
            case MAGIC_SMOKE:
                return new MagicSmokeParticle.Factory();
            case MAGIC_RISING_SPARK:
                return new MagicRisingSparkParticle.Factory();
        }
        return this.getDefaultParticle().getFactory();
    }

    public ParticleSpawn getDefaultParticle() {
        return MAGIC_RISING_SPARK;
    }

    public void spawn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... params) {
        if (world.isRemote) {
            spawn(create(world, x, y, z, velocityX, velocityY, velocityZ, params));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void spawn(Particle particle) {
        Minecraft.getInstance().particles.addEffect(particle);
    }
}