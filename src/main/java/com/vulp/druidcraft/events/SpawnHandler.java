package com.vulp.druidcraft.events;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.BedrollBlock;
import com.vulp.druidcraft.capabilities.ITempSpawnCapability;
import com.vulp.druidcraft.capabilities.TempSpawnCapability;
import com.vulp.druidcraft.capabilities.TempSpawnProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpawnHandler {

    @SubscribeEvent
    public void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
        Druidcraft.LOGGER.debug("Bed");
        PlayerEntity player = event.getPlayer();
        BlockPos pos = event.getNewSpawn();
        if(player != null && pos != null) {
            if(player.getEntityWorld().getBlockState(pos).getBlock() instanceof BedrollBlock) {
                LazyOptional<ITempSpawnCapability> lazyTempSpawn = player.getCapability(TempSpawnProvider.TEMP_SPAWN);
                ITempSpawnCapability tempSpawn = lazyTempSpawn.orElse(new TempSpawnCapability());
                tempSpawn.setTempSpawn(pos);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onSpawn(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEntity player = event.getPlayer();
        LazyOptional<ITempSpawnCapability> lazyTempSpawn = player.getCapability(TempSpawnProvider.TEMP_SPAWN);
        ITempSpawnCapability tempSpawn = lazyTempSpawn.orElse(new TempSpawnCapability());
        BlockPos spawnPos = player.getBedLocation();
        BlockPos tempSpawnPos = tempSpawn.getTempSpawn();
        Druidcraft.LOGGER.debug("Real Spawn = " + spawnPos);
        Druidcraft.LOGGER.debug("Temp Spawn = " + tempSpawnPos);
/*        if (tempSpawnPos != null) {
            if ()
                if (!(player.getEntityWorld().getBlockState(tempSpawnPos).getBlock() instanceof BedrollBlock)) {
                    tempSpawn.setTempSpawn(null);
                } else {
                    player.respawnPlayer();
                }

        }*/
    }
}
