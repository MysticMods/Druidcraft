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
import net.minecraftforge.event.RegistryEvent;

public class EntityRegistry
{
    public static final EntityType<DreadfishEntity> dreadfish_entity = createEntity(DreadfishEntity.class, DreadfishEntity::new, EntityClassification.MONSTER, "dreadfish", 0.8f, 0.4f);
    public static final EntityType<BeetleEntity> beetle_entity = createEntity(BeetleEntity.class, BeetleEntity::new, EntityClassification.MONSTER, "beetle", 0.8f, 0.4f);


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
        registerEntityWorldSpawn(dreadfish_entity);
        registerEntityWorldSpawn(beetle_entity);
    }

    public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(DruidcraftRegistry.DRUIDCRAFT));
        item.setRegistryName(DruidcraftRegistry.location(name));
        return item;
    }

    public static void registerEntityWorldSpawn(EntityType<?> entity, Biome... biomes)
    {
        for(Biome biome : biomes)
        {
            if(biome != null)
            {
                biome.getSpawns(entity.getClassification()).add(new SpawnListEntry(entity, 10, 1, 10));
            }
        }
    }
}