package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.IForgeRegistry;

public class BiomeRegistry
{
    public static Biome darkwood_forest;

    public static Biome registerBiome(IForgeRegistry<Biome> registry, Biome biome, String name, int weight, boolean spawn, BiomeManager.BiomeType type, BiomeDictionary.Type... types)
    {
        registry.register(biome.setRegistryName(Druidcraft.MODID, name));
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, weight));
        if (spawn == true) {
            BiomeManager.addSpawnBiome(biome);
        }
        BiomeDictionary.addTypes(biome, types);
        return biome;
    }
}
