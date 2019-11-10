package com.vulp.druidcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;

public class EntitySpawnConfig {

    public static ForgeConfigSpec.BooleanValue dreadfish_spawn;
    public static ForgeConfigSpec.IntValue dreadfish_weight;
    public static ForgeConfigSpec.IntValue dreadfish_min_group;
    public static ForgeConfigSpec.IntValue dreadfish_max_group;
    public static ForgeConfigSpec.ConfigValue<String> dreadfish_biome_types;

    public static ForgeConfigSpec.BooleanValue beetle_spawn;
    public static ForgeConfigSpec.IntValue beetle_weight;
    public static ForgeConfigSpec.IntValue beetle_min_group;
    public static ForgeConfigSpec.IntValue beetle_max_group;
    public static ForgeConfigSpec.ConfigValue<String> beetle_biome_types;

    public EntitySpawnConfig() {
    }
        public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
        {

            server.comment("Mob Spawn Config");

            dreadfish_spawn = server.comment("Allow dreadfish to spawn?").define("entityspawning.dreadfish_spawn", true);
            dreadfish_weight = server.comment("Determines the rarity of the dreadfish.").defineInRange("entityspawning.dreadfish_weight", 24, 1, 100);
            dreadfish_min_group = server.comment("Minimum size of the group when spawned.").defineInRange("entityspawning.dreadfish_min_group_size", 1, 1, 5);
            dreadfish_max_group = server.comment("Maximum size of the group when spawned.").defineInRange("entityspawning.dreadfish_max_group_size", 3, 1, 10);
            dreadfish_biome_types = server.comment("List of biome types from the biome dictionary that the dreadfish can spawn in. Separate types with a '|' between them.").define("entityspawning.dreadfish_biomes", "SNOWY|COLD");

            beetle_spawn = server.comment("Allow beetles to spawn?").define("entityspawning.beetle_spawn", true);
            beetle_weight = server.comment("Determines the rarity of the beetle.").defineInRange("entityspawning.beetle_weight", 5, 1, 200);
            beetle_min_group = server.comment("Minimum size of the group when spawned.").defineInRange("entityspawning.beetle_min_group_size", 1, 1, 200);
            beetle_max_group = server.comment("Maximum size of the group when spawned.").defineInRange("entityspawning.beetle_max_group_size", 2, 1, 200);
            beetle_biome_types = server.comment("List of biome types from the biome dictionary that the beetle can spawn in. Separate types with a '|' between them.").define("entityspawning.beetle_biomes", "CONIFEROUS|FOREST|JUNGLE|DENSE");
    }
}
