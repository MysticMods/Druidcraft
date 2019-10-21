package com.vulp.druidcraft.config;

import net.minecraft.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HarvestConfig {

        public static ForgeConfigSpec.ConfigValue<List<? extends String>> sickle_block_breaking;

        public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
        {
            server.comment("World Generation Config");

            sickle_block_breaking = server.comment("Decides what blocks are allowed and not allowed to be affected by the sickle.",
                    "Adding '+' to the start of the block will add it, and '-' will remove it.")
                    .defineList("toolcontrollers.sickle_block_list", new ArrayList<>(), k -> k instanceof String);
        }
    }