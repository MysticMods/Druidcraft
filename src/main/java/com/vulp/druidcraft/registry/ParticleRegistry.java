package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.particle.FieryGlowParticle;
import com.vulp.druidcraft.particle.FierySparkParticle;
import com.vulp.druidcraft.particle.MagicSmokeParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleRegistry {
    public static final BasicParticleType magic_smoke = new BasicParticleType(false);
    public static final BasicParticleType fiery_glow = new BasicParticleType(false);
    public static final BasicParticleType fiery_spark = new BasicParticleType(false);

    @OnlyIn(Dist.CLIENT)
    public static void registerFactories() {
        ParticleManager particles = Minecraft.getInstance().particles;

        particles.registerFactory(magic_smoke, MagicSmokeParticle.Factory::new);
        particles.registerFactory(fiery_glow, FieryGlowParticle.Factory::new);
        particles.registerFactory(fiery_spark, FierySparkParticle.Factory::new);
    }
}
