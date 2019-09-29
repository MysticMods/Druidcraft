package com.vulp.druidcraft.events;

import com.vulp.druidcraft.config.DropRateConfig;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LootHandler {

    @SubscribeEvent
    public void onGrassBroken(BreakEvent event) {
        if (!event.getWorld().isRemote()) {
            if ((event.getPlayer().getHeldItemMainhand().getItem() != Items.SHEARS) || (!event.getPlayer().isCreative())) {
                if (event.getState().getBlock() == Blocks.GRASS || event.getState().getBlock() == Blocks.TALL_GRASS || event.getState().getBlock() == Blocks.FERN) {
                    if (Math.random() <= (double) DropRateConfig.hemp_seed_drops.get() / 100) {
                        event.getWorld().setBlockState(event.getPos(), Blocks.AIR.getDefaultState(), 2);
                        event.getWorld().addEntity(new ItemEntity((World) event.getWorld(), event.getPos().getX(),
                                event.getPos().getY(), event.getPos().getZ(), new ItemStack(ItemRegistry.hemp_seeds, 1)));
                    }
                }
            }
        }
    }
}
