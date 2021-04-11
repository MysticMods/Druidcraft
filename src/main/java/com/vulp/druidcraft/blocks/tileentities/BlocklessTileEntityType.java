package com.vulp.druidcraft.blocks.tileentities;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.Set;
import java.util.function.Supplier;

public class BlocklessTileEntityType<T extends TileEntity> extends TileEntityType<T> {

    public BlocklessTileEntityType(Supplier<? extends T> factoryIn, Type<?> dataFixerType) {
        super(factoryIn, null, dataFixerType);
    }

    @Override
    public boolean isValidBlock(Block blockIn) {
        return !(blockIn instanceof AirBlock);
    }

    public static final class Builder<T extends TileEntity> {
        private final Supplier<? extends T> factory;

        private Builder(Supplier<? extends T> factoryIn) {
            this.factory = factoryIn;
        }

        public static <T extends TileEntity> BlocklessTileEntityType.Builder<T> create(Supplier<? extends T> factoryIn) {
            return new BlocklessTileEntityType.Builder<>(factoryIn);
        }

        public BlocklessTileEntityType<T> build(Type<?> datafixerType) {
            return new BlocklessTileEntityType<>(this.factory, datafixerType);
        }
    }

}
