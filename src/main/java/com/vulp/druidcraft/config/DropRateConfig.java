package com.vulp.druidcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class DropRateConfig
{
    public static ForgeConfigSpec.IntValue hemp_seed_drops;
    public static ForgeConfigSpec.BooleanValue drop_seeds;

    public static void init(ForgeConfigSpec.Builder common, ForgeConfigSpec.Builder client)
    {
        common.comment("Drop Rate Config");

        hemp_seed_drops = common.comment("Rate of hemp seed drops from grass blocks.").defineInRange("droprate.hemp_seed_rate", 8, 0, 100);
        drop_seeds = common.comment("Whether to have seeds from this mod drop from grass at all.").define("droprate.drop_seeds", true);
    }
}