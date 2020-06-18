package com.vulp.druidcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;

public class SmeltableBlockItem extends BlockItem implements IForgeItem {

    private final int burnTime;

    public SmeltableBlockItem(Block blockIn, int burnTime, Properties builder) {
        super(blockIn, builder);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return this.burnTime;
    }
}
