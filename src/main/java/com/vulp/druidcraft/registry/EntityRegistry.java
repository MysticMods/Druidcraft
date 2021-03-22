package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.config.EntitySpawnConfig;
import com.vulp.druidcraft.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.RegistryKey;
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
    public static final EntityType<DuragemProtectionEntity> duragem_protection_entity = createEntity(DuragemProtectionEntity::new, EntityClassification.MISC, "duragem_protection", 1.0F, 1.0F);
    public static final EntityType<GaseousBombEntity> gaseous_bomb_entity = createEntity(GaseousBombEntity::new, EntityClassification.MISC, "gaseous_bomb", 0.35F, 0.35F);

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

    public static void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(dreadfish_entity, DreadfishEntity.bakeAttributes().create());
        GlobalEntityTypeAttributes.put(beetle_entity, BeetleEntity.bakeAttributes().create());
        GlobalEntityTypeAttributes.put(lunar_moth_entity, LunarMothEntity.bakeAttributes().create());
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

    public static void registerEntityPlacements()
    {
        EntitySpawnPlacementRegistry.register(beetle_entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeetleEntity::placement);
        EntitySpawnPlacementRegistry.register(dreadfish_entity, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DreadfishEntity::placement);
    }

    public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(DruidcraftRegistry.DRUIDCRAFT));
        item.setRegistryName(DruidcraftRegistry.location(name));
        return item;
    }
}