package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.world.carvers.CustomNetherCaveCarver;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.NetherCaveCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class CarverRegistry {

    private static Set<WorldCarver<?>> CARVERS = new HashSet<>();

    public static final WorldCarver<ProbabilityConfig> CUSTOM_NETHER_CAVE = register("custom_nether_cave", new CustomNetherCaveCarver(ProbabilityConfig.CODEC));

    public static <C extends ICarverConfig, F extends WorldCarver<C>> F register(String name, F carver) {
        ResourceLocation id = new ResourceLocation(Druidcraft.MODID, name);
        carver.setRegistryName(id);
        CARVERS.add(carver);
        return carver;
    }

    @SubscribeEvent
    public static void register (RegistryEvent.Register<WorldCarver<?>> event) {
        event.getRegistry().registerAll(CARVERS.toArray(new WorldCarver[0]));
    }

}
