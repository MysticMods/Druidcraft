package com.vulp.druidcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OreConfigs
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

    public static void init(ForgeConfigSpec.Builder common, ForgeConfigSpec.Builder client)
    {
        common.comment("World Generation Config");

        amber_size = common.comment("Determines the size of an amber vein.").defineInRange("oregeneration.amber_size", 4, 1, 32);
        amber_weight = common.comment("Determines the rarity of amber veins.").defineInRange("oregeneration.amber_weight", 2, 0, 200);
        moonstone_size = common.comment("Determines the size of a moonstone vein.").defineInRange("oregeneration.moonstone_size", 4, 1, 32);
        moonstone_weight = common.comment("Determines the rarity of moonstone veins.").defineInRange("oregeneration.moonstone_weight", 3, 0, 200);
        fiery_glass_size = common.comment("Determines the size of a fiery glass vein.").defineInRange("oregeneration.fiery_glass_size", 7, 1, 32);
        fiery_glass_weight = common.comment("Determines the rarity of fiery glass veins.").defineInRange("oregeneration.fiery_glass_weight", 5, 0, 200);
        rockroot_size = common.comment("Determines the size of a rockroot vein.").defineInRange("oregeneration.rockroot_size", 3, 1, 32);
        rockroot_weight = common.comment("Determines the rarity of rockroot veins.").defineInRange("oregeneration.rockroot_weight", 16, 0, 200);
        generate_ores = common.comment("Whether to have ores from this mod spawn at all.").define("oregeneration.generate_ores", true);
    }
}