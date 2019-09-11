package com.vulp.druidcraft.events;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Druidcraft.MODID)
public class LootHandler {

    private static ResourceLocation grass_table = new ResourceLocation("minecraft", "blocks/grass");

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        if (event.getName().equals(grass_table)) {
            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(Druidcraft.MODID, "blocks/grass_druidcraft_seeds"))).build());
        }
    }
}
