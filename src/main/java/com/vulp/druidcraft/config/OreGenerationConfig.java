package com.vulp.druidcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OreGenerationConfig
{
    public static ForgeConfigSpec.IntValue amber_size;
    public static ForgeConfigSpec.IntValue amber_weight;
    public static ForgeConfigSpec.IntValue moonstone_size;
    public static ForgeConfigSpec.IntValue moonstone_weight;
    public static ForgeConfigSpec.IntValue fiery_glass_size;
    public static ForgeConfigSpec.IntValue fiery_glass_weight;
    public static ForgeConfigSpec.IntValue rockroot_size;
    public static ForgeConfigSpec.IntValue rockroot_weight;
    public static ForgeConfigSpec.BooleanValue generate_ores;

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
    {
        server.comment("Ore Generation Config");

        amber_size = server.comment("Determines the size of an amber vein.").defineInRange("oregeneration.amber_size", 3, 1, 16);
        amber_weight = server.comment("Determines the rarity of amber veins.").defineInRange("oregeneration.amber_weight", 200, 1, 200);
        moonstone_size = server.comment("Determines the size of a moonstone vein.").defineInRange("oregeneration.moonstone_size", 8, 1, 16);
        moonstone_weight = server.comment("Determines the rarity of moonstone veins.").defineInRange("oregeneration.moonstone_weight", 200, 1, 200);
        fiery_glass_size = server.comment("Determines the size of a fiery glass vein.").defineInRange("oregeneration.fiery_glass_size", 12, 1, 200);
        fiery_glass_weight = server.comment("Determines the rarity of fiery glass veins.").defineInRange("oregeneration.fiery_glass_weight", 200, 1, 200);
        rockroot_size = server.comment("Determines the size of a rockroot vein.").defineInRange("oregeneration.rockroot_size", 3, 1, 16);
        rockroot_weight = server.comment("Determines the rarity of rockroot veins.").defineInRange("oregeneration.rockroot_weight", 200, 1, 200);
        generate_ores = server.comment("Whether to have ores from this mod spawn at all.").define("oregeneration.generate_ores", true);
    }
}