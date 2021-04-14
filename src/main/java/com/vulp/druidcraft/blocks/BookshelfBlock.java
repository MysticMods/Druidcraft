package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class BookshelfBlock extends Block {

    private final int enchPower;

    public BookshelfBlock(int enchantPower, Properties properties) {
        super(properties);
        this.enchPower = enchantPower;
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos) {
        return this.enchPower;
    }
}
