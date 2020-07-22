package com.vulp.druidcraft.registry;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;

import java.util.Arrays;

public class VanillaIntegrationRegistry {

    public static void setup() {
        // STRIPPED LOGS
        addStrippable(BlockRegistry.darkwood_log, BlockRegistry.stripped_darkwood_log);
        addStrippable(BlockRegistry.darkwood_wood, BlockRegistry.stripped_darkwood_wood);
        addStrippable(BlockRegistry.elder_log, BlockRegistry.stripped_elder_log);
        addStrippable(BlockRegistry.elder_wood, BlockRegistry.stripped_elder_wood);

        // COMPOSTER ITEMS
        ComposterBlock.CHANCES.put(ItemRegistry.hemp_seeds, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.hemp, 0.65f);
        ComposterBlock.CHANCES.put(ItemRegistry.darkwood_sapling, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.darkwood_leaves, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.elder_sapling, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.elder_leaves, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.lavender, 0.65f);
        ComposterBlock.CHANCES.put(ItemRegistry.blueberries, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.elderberries, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.elderflower, 0.3f);
        ComposterBlock.CHANCES.put(ItemRegistry.blueberry_muffin, 0.85f);
        ComposterBlock.CHANCES.put(ItemRegistry.apple_elderberry_crumble, 1.0f);

        // FLAMMABLES
        addFlammables(BlockRegistry.hemp_crop, 60, 100);
        addFlammables(BlockRegistry.darkwood_log, 5, 5);
        addFlammables(BlockRegistry.darkwood_wood, 5, 5);
        addFlammables(BlockRegistry.stripped_darkwood_log, 5, 5);
        addFlammables(BlockRegistry.stripped_darkwood_wood, 5, 5);
        addFlammables(BlockRegistry.darkwood_leaves, 30, 60);
        addFlammables(BlockRegistry.darkwood_planks, 5, 20);
        addFlammables(BlockRegistry.darkwood_slab, 5, 20);
        addFlammables(BlockRegistry.darkwood_stairs, 5, 20);
        addFlammables(BlockRegistry.darkwood_fence, 5, 20);
        addFlammables(BlockRegistry.darkwood_fence_gate, 5, 20);
        addFlammables(BlockRegistry.darkwood_button, 5, 20);
        addFlammables(BlockRegistry.darkwood_door, 5, 20);
        addFlammables(BlockRegistry.darkwood_pressure_plate, 5, 20);
        addFlammables(BlockRegistry.darkwood_trapdoor, 5, 20);
        addFlammables(BlockRegistry.elder_log, 5, 5);
        addFlammables(BlockRegistry.elder_wood, 5, 5);
        addFlammables(BlockRegistry.stripped_elder_log, 5, 5);
        addFlammables(BlockRegistry.stripped_elder_wood, 5, 5);
        addFlammables(BlockRegistry.elder_leaves, 30, 60);
        addFlammables(BlockRegistry.elder_fruit, 30, 60);
        addFlammables(BlockRegistry.elder_planks, 5, 20);
        addFlammables(BlockRegistry.elder_slab, 5, 20);
        addFlammables(BlockRegistry.elder_stairs, 5, 20);
        addFlammables(BlockRegistry.elder_fence, 5, 20);
        addFlammables(BlockRegistry.elder_fence_gate, 5, 20);
        addFlammables(BlockRegistry.elder_button, 5, 20);
        addFlammables(BlockRegistry.elder_door, 5, 20);
        addFlammables(BlockRegistry.elder_pressure_plate, 5, 20);
        addFlammables(BlockRegistry.elder_trapdoor, 5, 20);
        addFlammables(BlockRegistry.birch_beam, 5, 5);
        addFlammables(BlockRegistry.acacia_beam, 5, 5);
        addFlammables(BlockRegistry.dark_oak_beam, 5, 5);
        addFlammables(BlockRegistry.darkwood_beam, 5, 5);
        addFlammables(BlockRegistry.jungle_beam, 5, 5);
        addFlammables(BlockRegistry.elder_beam, 5, 5);
        addFlammables(BlockRegistry.oak_beam, 5, 5);
        addFlammables(BlockRegistry.spruce_beam, 5, 5);
        addFlammables(BlockRegistry.oak_panels, 5, 20);
        addFlammables(BlockRegistry.acacia_panels, 5, 20);
        addFlammables(BlockRegistry.birch_panels, 5, 20);
        addFlammables(BlockRegistry.dark_oak_panels, 5, 20);
        addFlammables(BlockRegistry.darkwood_panels, 5, 20);
        addFlammables(BlockRegistry.elder_panels, 5, 20);
        addFlammables(BlockRegistry.jungle_panels, 5, 20);
        addFlammables(BlockRegistry.spruce_panels, 5, 20);
        addFlammables(BlockRegistry.oak_small_beam, 5, 15);
        addFlammables(BlockRegistry.birch_small_beam, 5, 15);
        addFlammables(BlockRegistry.spruce_small_beam, 5, 15);
        addFlammables(BlockRegistry.dark_oak_small_beam, 5, 15);
        addFlammables(BlockRegistry.darkwood_small_beam, 5, 15);
        addFlammables(BlockRegistry.acacia_small_beam, 5, 15);
        addFlammables(BlockRegistry.jungle_small_beam, 5, 15);
        addFlammables(BlockRegistry.elder_small_beam, 5, 15);
        addFlammables(BlockRegistry.lavender, 60, 100);
        addFlammables(BlockRegistry.blueberry_bush, 60, 100);
        addFlammables(BlockRegistry.rope, 30, 60);

        // Handle Chicken tameables
        ChickenEntity.TEMPTATION_ITEMS = Ingredient.merge(Arrays.asList(ChickenEntity.TEMPTATION_ITEMS, Ingredient.fromItems(ItemRegistry.hemp_seeds)));
    }

    public static void addFlammables(Block blockIn, int encouragement, int flammability)
    {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(blockIn, encouragement, flammability);
    }

    private static void addStrippable(Block unstrippedBlock, Block strippedBlock) {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        AxeItem.BLOCK_STRIPPING_MAP.put(unstrippedBlock, strippedBlock);
    }
}