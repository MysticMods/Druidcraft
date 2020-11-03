package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.world.biomes.BiomeFeatures;
import com.vulp.druidcraft.world.features.ElderTreeFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class FeatureRegistry {

    public static BlockClusterFeatureConfig blueberry_bush_feature;
    public static BlockClusterFeatureConfig lavender_feature;
    public static Feature<BaseTreeFeatureConfig> elder_tree_feature;

    public static <V extends R, R extends IForgeRegistryEntry<R>> V register(IForgeRegistry<R> registry, V feature, String name) {
        ResourceLocation id = new ResourceLocation(Druidcraft.MODID, name);
        feature.setRegistryName(id);
        registry.register(feature);
        return feature;
    }

}
