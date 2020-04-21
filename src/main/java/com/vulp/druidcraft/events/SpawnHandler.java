package com.vulp.druidcraft.events;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.BedrollBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpawnHandler {

    @SubscribeEvent
    public void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
/*            if (!event.getPlayer().world.isRemote()) {
            Druidcraft.LOGGER.debug("Bed");
            PlayerEntity player = event.getPlayer();
            BlockPos pos = event.getNewSpawn();
            if (player.getEntityWorld().getBlockState(pos).getBlock() instanceof BedrollBlock) {
                CompoundNBT playerData = player.getPersistentData();
                playerData.putInt("TempSpawnX", pos.getX());
                playerData.putInt("TempSpawnY", pos.getY());
                playerData.putInt("TempSpawnZ", pos.getZ());
                event.isCanceled();
            }
        } else {*/
            PlayerEntity player = event.getPlayer();
            BlockPos pos = event.getNewSpawn();
            if (player.getEntityWorld().getBlockState(pos).getBlock() instanceof BedrollBlock) {
                event.isCanceled();
            }
//        }
    }

    // Does trigger! Something is wrong. Place a bunch more debugs to see what path is being taken on respawn.
    // The setting of spawns is triggering the above SetSpawnEvent. Look into whether that's what is breaking the code.
/*    @SubscribeEvent
    public void onSpawn(PlayerEvent.PlayerRespawnEvent event) {
        World world = event.getPlayer().world;
        if (!world.isRemote()) {
            Druidcraft.LOGGER.debug("----------");
            Druidcraft.LOGGER.debug("Spawn Event");
            Druidcraft.LOGGER.debug("----------");
            PlayerEntity player = event.getPlayer();
            CompoundNBT playerData = player.getPersistentData();
            BlockPos mainSpawn = player.getBedLocation(player.dimension);
            if (playerData.contains("TempSpawnX")) {
                Druidcraft.LOGGER.debug("Initiating...");
                int tempX = playerData.getInt("TempSpawnX");
                int tempY = playerData.getInt("TempSpawnY");
                int tempZ = playerData.getInt("TempSpawnZ");
                BlockPos tempPos = new BlockPos(tempX, tempY, tempZ);
                if (tempPos != mainSpawn && playerData.contains("HolderSpawnX")) {
                    int holderX = playerData.getInt("HolderSpawnX");
                    int holderY = playerData.getInt("HolderSpawnY");
                    int holderZ = playerData.getInt("HolderSpawnZ");
                    BlockPos holderSpawn = new BlockPos(holderX, holderY, holderZ);
                    if (!holderSpawn.equals(mainSpawn)) {
                        Druidcraft.LOGGER.debug("Detected change in main spawn. Spawn holder modified.");
                        playerData.putInt("HolderSpawnX", mainSpawn.getX());
                        playerData.putInt("HolderSpawnY", mainSpawn.getY());
                        playerData.putInt("HolderSpawnZ", mainSpawn.getZ());
                    }
                }
                if (event.getPlayer().world.getBlockState(tempPos).getBlock() instanceof BedrollBlock) {
                    Druidcraft.LOGGER.debug("Detected existing bedroll.");
                    // Spawn holder created every time I die with an existing bedroll. Should only happen once.
                    if (!playerData.contains("HolderSpawnX")) {
                        Druidcraft.LOGGER.debug("Spawn holder created.");
                        playerData.putInt("HolderSpawnX", mainSpawn.getX());
                        playerData.putInt("HolderSpawnY", mainSpawn.getY());
                        playerData.putInt("HolderSpawnZ", mainSpawn.getZ());
                    }
                    player.setSpawnPoint(tempPos, true, player.dimension);
                } else {
                    Druidcraft.LOGGER.debug("Bedroll not existent.");
                    if (playerData.contains("HolderSpawnX")) {
                        Druidcraft.LOGGER.debug("Main spawn restored.");
                        int holderX = playerData.getInt("HolderSpawnX");
                        int holderY = playerData.getInt("HolderSpawnY");
                        int holderZ = playerData.getInt("HolderSpawnZ");
                        BlockPos holderSpawn = new BlockPos(holderX, holderY, holderZ);
                        player.setSpawnPoint(holderSpawn, false, player.dimension);
                        playerData.remove("HolderSpawnX");
                        playerData.remove("HolderSpawnY");
                        playerData.remove("HolderSpawnZ");
                    }
                    playerData.remove("TempSpawnX");
                    playerData.remove("TempSpawnY");
                    playerData.remove("TempSpawnZ");
                }
            }
        }
    }*/

/*    @SubscribeEvent
    public void onCloned(PlayerEvent.Clone event) {
        if (!event.getPlayer().world.isRemote()) {
            CompoundNBT oldData = event.getOriginal().getPersistentData();
            CompoundNBT newData = event.getPlayer().getPersistentData();
            if (oldData.contains("TempSpawnX")) {
                newData.putInt("TempSpawnX", oldData.getInt("TempSpawnX"));
                newData.putInt("TempSpawnY", oldData.getInt("TempSpawnY"));
                newData.putInt("TempSpawnZ", oldData.getInt("TempSpawnZ"));
            }
            if (oldData.contains("HolderSpawnX")) {
                newData.putInt("HolderSpawnX", oldData.getInt("HolderSpawnX"));
                newData.putInt("HolderSpawnY", oldData.getInt("HolderSpawnY"));
                newData.putInt("HolderSpawnZ", oldData.getInt("HolderSpawnZ"));
            }
        }
    }*/
}
