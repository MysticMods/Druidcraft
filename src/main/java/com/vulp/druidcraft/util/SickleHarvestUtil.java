package com.vulp.druidcraft.util;

import com.vulp.druidcraft.items.EffectiveSickleItem;
import com.vulp.druidcraft.items.RadialToolItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;

import java.util.HashSet;
import java.util.Set;

public class SickleHarvestUtil {
    public static void breakNeighbours(ItemStack tool, World world, BlockPos pos, PlayerEntity player) {
        if (world.isRemote) return;

        int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
        int silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
        int blocksBroken = 0;

        for (BlockPos target : nearbyBlocks(tool, pos, world, player)) {
            BlockState state = world.getBlockState(target);

            EffectiveSickleItem sickleItem = (EffectiveSickleItem) tool.getItem();
            if (sickleItem.getEffectiveBlocks().contains(state.getBlock()) || sickleItem.getEffectiveMaterials().contains(state.getMaterial())) {
                world.destroyBlock(target, false);
                state.getBlock().harvestBlock(world, player, target, state, null, tool);
                ++blocksBroken;
            }
        }
        tool.damageItem(Math.round(blocksBroken/2), player, p -> p.sendBreakAnimation(Hand.MAIN_HAND));
    }

    public static Set<BlockPos> nearbyBlocks(ItemStack tool, BlockPos pos, World world, PlayerEntity player) {
        int radius = ((RadialToolItem) tool.getItem()).getRadius();

        Set<BlockPos> result = new HashSet<>();

        // Determines the height.
        for (int y = -1; y < 2; y++) {
            // Determines the width.
            for (int x = -radius; x < radius + 1; x++) {
                // Determines the length.
                for (int z = -radius; z < radius + 1; z++) {
                    if (x == z && y == z && z == 0) {
                        continue;
                    }

                    BlockPos potential;
                    potential = pos.add(x, y, z);
                    BlockState state = world.getBlockState(potential);
                    if (BlockTags.WITHER_IMMUNE.contains(state.getBlock())) {
                        continue;
                    }
                    if (!ForgeHooks.canHarvestBlock(state, player, world, potential)) {
                        continue;
                    }

                    EffectiveSickleItem sickleItem = (EffectiveSickleItem) tool.getItem();
                    if (sickleItem.getEffectiveBlocks().contains(state.getBlock()) || sickleItem.getEffectiveMaterials().contains(state.getMaterial())) {
                        result.add(potential);
                    }
                }
            }
        }
        return result;
    }
}
