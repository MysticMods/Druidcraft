package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.world.biomes.BiomeBuilder;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;

import java.util.HashSet;
import java.util.Set;

public class BiomeRegistry {
    private static final Set<Biome> BIOMES = new HashSet<>();

    public static class BiomeKeys {

        public static final RegistryKey<Biome> darkwood_forest = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, DruidcraftRegistry.location("darkwood_forest"));
        public static final RegistryKey<Biome> torrid_jungle = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, DruidcraftRegistry.location("glowing_jungle"));

    }

    public static Biome register(String name, Biome biome) {
        biome.setRegistryName(DruidcraftRegistry.location(name));
        BIOMES.add(biome);
        return biome;
    }

    public static Biome silentRegister(String name, Biome biome) {
        biome.setRegistryName(DruidcraftRegistry.location(name));
        return biome;
    }

    public static void register (RegistryEvent.Register<Biome> event) {

        register("darkwood_forest", BiomeBuilder.makeDarkwoodForest());
        register("glowing_jungle", BiomeBuilder.makeGlowingJungle());

        event.getRegistry().registerAll(BIOMES.toArray(new Biome[0]));
        registerBiomes();
    }

    public static void registerBiomes() {
        // TODO: Make weight of darkwood forest configurable
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(BiomeKeys.darkwood_forest, 1));
        BiomeDictionary.addTypes(BiomeKeys.darkwood_forest, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL);

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BiomeKeys.torrid_jungle, 10));
        BiomeDictionary.addTypes(BiomeKeys.torrid_jungle, BiomeDictionary.Type.HOT, BiomeDictionary.Type.JUNGLE, BiomeDictionary.Type.DRY, BiomeDictionary.Type.NETHER);
    }
}
