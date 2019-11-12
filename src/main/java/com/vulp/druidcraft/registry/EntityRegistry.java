package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.config.EntitySpawnConfig;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.DreadfishEntity;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;

import java.lang.reflect.Array;
import java.util.*;

public class EntityRegistry
{

    public EntityRegistry() {
    }
    public static final EntityType<DreadfishEntity> dreadfish_entity = createEntity(DreadfishEntity::new, EntityClassification.MONSTER, "dreadfish", 0.8f, 0.4f);
    public static final EntityType<BeetleEntity> beetle_entity = createEntity(BeetleEntity::new, EntityClassification.MONSTER, "beetle", 1.5f, 1.5f);

    private static <T extends Entity> EntityType<T> createEntity(EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height) {
        ResourceLocation location = new ResourceLocation(Druidcraft.MODID, name);

        EntityType<T> entity = EntityType.Builder.create(factory, entityClassification)
                .size(width, height).setTrackingRange(64)
                .setShouldReceiveVelocityUpdates(true)
                .setUpdateInterval(3)
                .build(location.toString());

        entity.setRegistryName(location);

        return entity;
    }

    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll
                (
                        ItemRegistry.dreadfish_spawn_egg = registerEntitySpawnEgg(dreadfish_entity, 0xE2E2D9, 0xA775FF, "dreadfish_spawn_egg"),
                        ItemRegistry.beetle_spawn_egg = registerEntitySpawnEgg(beetle_entity, 0x57E5DC, 0x227589, "beetle_spawn_egg")
                );

    }

    public static List<String> getBiomes(ForgeConfigSpec.ConfigValue<String> biomes) {
        String values = biomes.get();
        return Arrays.asList(values.split("|"));
    }

    public static void registerEntityWorldSpawns()
    {
        registerEntityWorldSpawn(EntitySpawnConfig.dreadfish_spawn.get(), dreadfish_entity, EntityClassification.MONSTER, EntitySpawnConfig.dreadfish_weight.get(), EntitySpawnConfig.dreadfish_min_group.get(), EntitySpawnConfig.dreadfish_max_group.get(), getBiomes(EntitySpawnConfig.dreadfish_biome_types));
        registerEntityWorldSpawn(EntitySpawnConfig.beetle_spawn.get(), beetle_entity, EntityClassification.MONSTER, EntitySpawnConfig.beetle_weight.get(), EntitySpawnConfig.beetle_min_group.get(), EntitySpawnConfig.beetle_max_group.get(), getBiomes(EntitySpawnConfig.beetle_biome_types));

        EntitySpawnPlacementRegistry.register(beetle_entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeetleEntity::placement);
        EntitySpawnPlacementRegistry.register(dreadfish_entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DreadfishEntity::placement);
    }

    public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(DruidcraftRegistry.DRUIDCRAFT));
        item.setRegistryName(DruidcraftRegistry.location(name));
        return item;
    }

    public static void registerEntityWorldSpawn(boolean spawnEnabled, EntityType<?> entity, EntityClassification classification, int weight, int minGroupCountIn, int maxGroupCountIn, List<String> biomes) {
        Set<Biome> biomeSet = new HashSet<>();

        if (spawnEnabled) {
            for (String biomeName : biomes) {
                biomeSet.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeName)));
            }
            biomeSet.forEach(biome -> biome.getSpawns(classification).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn)));
        }

        biomeSet.clear();
    }
}