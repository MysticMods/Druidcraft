package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.world.biomes.DarkwoodForest;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class BiomeRegistry
{
    public static Biome darkwood_forest = new DarkwoodForest();

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
