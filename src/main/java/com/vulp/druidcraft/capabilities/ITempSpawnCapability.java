package com.vulp.druidcraft.capabilities;

import net.minecraft.util.math.BlockPos;

public interface ITempSpawnCapability {

    void setTempSpawn(BlockPos spawnPos);
    BlockPos getTempSpawn();

}
