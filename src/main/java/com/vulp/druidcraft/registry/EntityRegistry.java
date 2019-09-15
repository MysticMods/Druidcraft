package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.entities.DreadfishEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.event.RegistryEvent;

public class EntityRegistry
{
    public static EntityType<?> dreadfish_entity = EntityType.Builder.create(DreadfishEntity::new, EntityClassification.AMBIENT).build(DruidcraftRegistry.MODID + ":dreadfish").setRegistryName(DruidcraftRegistry.location("dreadfish"));

    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll
                (
                        ItemRegistry.dreadfish_spawn_egg = registerEntitySpawnEgg(dreadfish_entity, 0xE2E2D9, 0xA775FF, "dreadfish_spawn_egg")
                );

    }

    public static void registerEntityWorldSpawns()
    {
        registerEntityWorldSpawn(dreadfish_entity);
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