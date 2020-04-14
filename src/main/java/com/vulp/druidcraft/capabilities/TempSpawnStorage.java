package com.vulp.druidcraft.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TempSpawnStorage implements Capability.IStorage<ITempSpawnCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ITempSpawnCapability> capability, ITempSpawnCapability instance, Direction side) {
        return NBTUtil.writeBlockPos(instance.getTempSpawn());
    }

    @Override
    public void readNBT(Capability<ITempSpawnCapability> capability, ITempSpawnCapability instance, Direction side, INBT nbt) {
        instance.setTempSpawn((NBTUtil.readBlockPos((CompoundNBT)nbt)));
    }
}
