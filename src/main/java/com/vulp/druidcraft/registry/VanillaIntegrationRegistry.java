package com.vulp.druidcraft.registry;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vulp.druidcraft.Druidcraft;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.AxeItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VanillaIntegrationRegistry {

    private static void addStrippable(Block unstrippedBlock, Block strippedBlock) {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        AxeItem.BLOCK_STRIPPING_MAP.put(unstrippedBlock, strippedBlock);
    }

    public static void setup() {
        // STRIPPED LOGS
        addStrippable(BlockRegistry.darkwood_log, BlockRegistry.stripped_darkwood_log);
        addStrippable(BlockRegistry.darkwood_wood, BlockRegistry.stripped_darkwood_wood);

        // COMPOSTER ITEMS
        ComposterBlock.CHANCES.put(ItemRegistry.hemp_seeds, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.darkwood_sapling, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.darkwood_leaves, 0.3f);
    }
}