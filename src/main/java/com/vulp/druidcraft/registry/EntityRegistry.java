package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.DreadfishEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;

public class EntityRegistry
{
    public static final EntityType<DreadfishEntity> dreadfish_entity = createEntity(DreadfishEntity.class, DreadfishEntity::new, EntityClassification.MONSTER, "dreadfish", 0.8f, 0.4f);
    public static final EntityType<BeetleEntity> beetle_entity = createEntity(BeetleEntity.class, BeetleEntity::new, EntityClassification.MONSTER, "beetle", 1.5f, 1.5f);


    private static <T extends Entity> EntityType<T> createEntity(Class<T> entityClass, EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height) {
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

    public static void registerEntityWorldSpawns()
    {
        registerEntityWorldSpawn(dreadfish_entity, 35, 1, 3, Biomes.SNOWY_TUNDRA, Biomes.ICE_SPIKES, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.FROZEN_RIVER, Biomes.SNOWY_BEACH, Biomes.MOUNTAINS, Biomes.GRAVELLY_MOUNTAINS, Biomes.WOODED_MOUNTAINS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS);
        registerEntityWorldSpawn(beetle_entity, 6, 1, 2, BiomeRegistry.darkwood_forest, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.WOODED_MOUNTAINS, Biomes.TAIGA, Biomes.TAIGA_MOUNTAINS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA, Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.TALL_BIRCH_FOREST, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.JUNGLE, Biomes.MODIFIED_JUNGLE, Biomes.JUNGLE_EDGE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.BAMBOO_JUNGLE, Biomes.SAVANNA, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.BADLANDS_PLATEAU, Biomes.SAVANNA_PLATEAU, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.SHATTERED_SAVANNA_PLATEAU);
    }

    public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(DruidcraftRegistry.DRUIDCRAFT));
        item.setRegistryName(DruidcraftRegistry.location(name));
        return item;
    }

    public static void registerEntityWorldSpawn(EntityType<?> entity, int weight, int minGroupCountIn, int maxGroupCountIn, Biome... biomes)
    {
            for (Biome biome : biomes) {
                if (biome != null) {
                    biome.getSpawns(entity.getClassification()).add(new SpawnListEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
            }
        }
    }
}