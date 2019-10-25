package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.particle.MagicRisingSparkParticle;
import com.vulp.druidcraft.particle.MagicSmokeParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ParticleRegistry {
    public static final BasicParticleType magic_smoke = new BasicParticleType(false);
    public static final BasicParticleType magic_rising_spark = new BasicParticleType(false);

    @OnlyIn(Dist.CLIENT)
    public static void registerFactories() {
        ParticleManager particles = Minecraft.getInstance().particles;

        particles.registerFactory(magic_smoke, MagicSmokeParticle.Factory::new);
        particles.registerFactory(magic_rising_spark, MagicRisingSparkParticle.Factory::new);
    }
}
