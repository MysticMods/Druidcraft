package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;

public class EntityRegistry
{
    public static EntityType<?> DREADFISH;

    public static void registerEntitySpawnEgg(EntityType<?> type, int colour1, int colour2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, colour1, colour2, new Item.Properties().group(Druidcraft.DRUIDCRAFT));
        item.setRegistryName(DruidcraftRegistry.location(name));
    }
}
