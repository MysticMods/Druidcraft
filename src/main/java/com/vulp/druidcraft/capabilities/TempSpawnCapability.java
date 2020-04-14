package com.vulp.druidcraft.capabilities;

import net.minecraft.util.math.BlockPos;

public class TempSpawnCapability implements ITempSpawnCapability {

    private BlockPos spawnPos;

    @Override
    public void setTempSpawn(BlockPos spawnPos) {
        this.spawnPos = spawnPos;
    }

    @Override
    public BlockPos getTempSpawn() {
        return this.spawnPos;
    }

}
