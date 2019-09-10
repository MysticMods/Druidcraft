package com.vulp.druidcraft.registry;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

public class VanillaIntegrationRegistry {

    public static void addStrippable(Block unstrippedBlock, Block strippedBlock)
    {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        AxeItem.BLOCK_STRIPPING_MAP.put(unstrippedBlock, strippedBlock);
    }

    public static void setup() {
        addStrippable(BlockRegistry.darkwood_log, BlockRegistry.stripped_darkwood_log);
        addStrippable(BlockRegistry.darkwood_wood, BlockRegistry.stripped_darkwood_wood);
    }

}
