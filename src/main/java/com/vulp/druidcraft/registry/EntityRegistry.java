package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.config.EntitySpawnConfig;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.DreadfishEntity;
import com.vulp.druidcraft.entities.LunarMothEntity;
import com.vulp.druidcraft.entities.projectiles.DreadfishFireBurst;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.network.FMLPlayMessages;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class EntityRegistry
{

    public EntityRegistry() {
    }
    public static final EntityType<DreadfishEntity> dreadfish_entity = createEntity(DreadfishEntity::new, EntityClassification.MONSTER, "dreadfish", 0.8f, 0.4f);
    public static final EntityType<BeetleEntity> beetle_entity = createEntity(BeetleEntity::new, EntityClassification.MONSTER, "beetle", 1.5f, 1.5f);
    public static final EntityType<LunarMothEntity> lunar_moth_entity = createEntity(LunarMothEntity::new, EntityClassification.CREATURE, "lunar_moth", 0.5f, 0.5f);

    public static final EntityType<DreadfishFireBurst> dreadfish_fire_burst = createEntity(DreadfishFireBurst::new, EntityClassification.MISC, "dreadfish_fire_burst", 0.5f, 0.5f);

    private static <T extends Entity> EntityType<T> createEntity(EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height) {
        return createEntity(factory, entityClassification, name, width, height, -1, null);
    }

    private static <T extends Entity> EntityType<T> createEntity(EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height, int trackingRange,  BiFunction<FMLPlayMessages.SpawnEntity, World, T> customClientFactory) {
        ResourceLocation location = new ResourceLocation(Druidcraft.MODID, name);

        EntityType.Builder<T> builder = EntityType.Builder.create(factory, entityClassification)
                .size(width, height)
                .setShouldReceiveVelocityUpdates(true)
                .setUpdateInterval(3);

        if (customClientFactory != null) {
            builder.setCustomClientFactory(customClientFactory);
        }
        if (trackingRange != -1) {
            builder.setTrackingRange(trackingRange);
        }

        EntityType<T> entity = builder.build(location.toString());

        entity.setRegistryName(location);

        return entity;
    }

    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll
                (
                        ItemRegistry.dreadfish_spawn_egg = registerEntitySpawnEgg(dreadfish_entity, 0xE2E2D9, 0xA775FF, "dreadfish_spawn_egg"),
                        ItemRegistry.beetle_spawn_egg = registerEntitySpawnEgg(beetle_entity, 0x57E5DC, 0x227589, "beetle_spawn_egg"),
                        ItemRegistry.lunar_moth_spawn_egg = registerEntitySpawnEgg(lunar_moth_entity, 0x4AFFC2, 0x00CE89, "lunar_moth_spawn_egg")
                );

    }

    public static void registerEntityWorldSpawns()
    {
        registerEntityWorldSpawn(EntitySpawnConfig.dreadfish_spawn.get(), dreadfish_entity, EntityClassification.MONSTER, EntitySpawnConfig.dreadfish_weight.get(), EntitySpawnConfig.dreadfish_min_group.get(), EntitySpawnConfig.dreadfish_max_group.get(), EntitySpawnConfig.dreadfish_biome_types.get(), EntitySpawnConfig.dreadfish_biome_exclusions.get());
        registerEntityWorldSpawn(EntitySpawnConfig.beetle_spawn.get(), beetle_entity, EntityClassification.MONSTER, EntitySpawnConfig.beetle_weight.get(), EntitySpawnConfig.beetle_min_group.get(), EntitySpawnConfig.beetle_max_group.get(), EntitySpawnConfig.beetle_biome_types.get(), EntitySpawnConfig.beetle_biome_exclusions.get());
        registerEntityWorldSpawn(EntitySpawnConfig.lunar_moth_spawn.get(), lunar_moth_entity, EntityClassification.CREATURE, EntitySpawnConfig.lunar_moth_weight.get(), EntitySpawnConfig.lunar_moth_min_group.get(), EntitySpawnConfig.lunar_moth_max_group.get(), EntitySpawnConfig.lunar_moth_biome_types.get(), EntitySpawnConfig.lunar_moth_biome_exclusions.get());

        EntitySpawnPlacementRegistry.register(beetle_entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeetleEntity::placement);
        EntitySpawnPlacementRegistry.register(dreadfish_entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DreadfishEntity::placement);
    }

    public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(DruidcraftRegistry.DRUIDCRAFT));
        item.setRegistryName(DruidcraftRegistry.location(name));
        return item;
    }

    public static void registerEntityWorldSpawn(boolean spawnEnabled, EntityType<?> entity, EntityClassification classification, int weight, int minGroupCountIn, int maxGroupCountIn, List<String> biomes, List<String> exclusions) {
        Set<Biome> biomeSet = new HashSet<>();

        if (spawnEnabled) {
            for (String biomeName : biomes) {
                biomeSet.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeName)));
            }
            Set<BiomeDictionary.Type> exclusionTypes = exclusions.stream().filter(o -> !o.isEmpty()).map(BiomeDictionary.Type::getType).collect(Collectors.toCollection(HashSet::new));
            biomeSet.forEach(o -> {
                    for (BiomeDictionary.Type type : exclusionTypes) {
                        if (BiomeDictionary.hasType(o, type)) {
                            return;
                        }
                    }
                    o.getSpawns(classification).add(new Biome.SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
                });
        }

        biomeSet.clear();
    }
}