package com.vulp.druidcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class HarvestConfig {

        public static ForgeConfigSpec.ConfigValue<List<? extends String>> sickle_block_breaking;

        public static void init(ForgeConfigSpec.Builder common, ForgeConfigSpec.Builder client)
        {
            common.comment("World Generation Config");

            sickle_block_breaking = common.comment("Decides what blocks are allowed and not allowed to be affected by the sickle.",
                    "Adding '+' to the start of the block will add it, and '-' will remove it.")
                    .defineList("toolcontrollers.sickle_block_list", new ArrayList<>(), k -> k instanceof String);
        }
    }