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
    public static final RegistryObject<Biome> darkwood_forest_key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(Druidcraft.MODID, "darkwood_forest"));
    public static Biome darkwood_forest;

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Druidcraft.MODID);

    public static final RegistryObject<Biome> DARKWOOD_FOREST = BIOMES.register("darkwood_forest", DarkwoodForest::new);

    public static Biome registerBiome(IForgeRegistry<Biome> registry, RegistryKey<Biome> biomeKey, Biome biome, String name, int weight, boolean spawn, BiomeManager.BiomeType type, BiomeDictionary.Type... types)
    {
        registry.register(biome.setRegistryName(Druidcraft.MODID, name));
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biomeKey, weight));
        BiomeDictionary.addTypes(biomeKey, types);
        return biome;
    }
}
