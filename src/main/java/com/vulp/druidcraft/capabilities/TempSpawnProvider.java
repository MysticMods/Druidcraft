package com.vulp.druidcraft.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

public class TempSpawnProvider implements ICapabilitySerializable<INBT>
{
    @CapabilityInject(ITempSpawnCapability.class)
    public static final Capability<ITempSpawnCapability> TEMP_SPAWN = null;

    private LazyOptional<ITempSpawnCapability> instance = LazyOptional.of(TEMP_SPAWN::getDefaultInstance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing)
    {
        return capability == TEMP_SPAWN ? this.instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT()
    {
        return TEMP_SPAWN.getStorage().writeNBT(TEMP_SPAWN, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt)
    {
        TEMP_SPAWN.getStorage().readNBT(TEMP_SPAWN, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty")), null, nbt);
    }
}
