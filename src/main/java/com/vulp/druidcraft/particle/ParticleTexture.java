package com.vulp.druidcraft.particle;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Druidcraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
class ParticleTexture {

    static ResourceLocation[] MAGIC_SMOKE = new ResourceLocation[] {
            new ResourceLocation("druidcraft:textures/particle/magic_smoke_0.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_smoke_1.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_smoke_2.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_smoke_3.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_smoke_4.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_smoke_5.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_smoke_6.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_smoke_7.png")
    };

    static ResourceLocation[] MAGIC_RISING_SPARK = new ResourceLocation[] {
            new ResourceLocation("druidcraft:textures/particle/magic_rising_spark_0.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_rising_spark_1.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_rising_spark_2.png"),
            new ResourceLocation("druidcraft:textures/particle/magic_rising_spark_3.png")
    };
}