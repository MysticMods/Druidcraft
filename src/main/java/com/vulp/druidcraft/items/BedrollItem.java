package com.vulp.druidcraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.BedItem;
import net.minecraft.item.DyeColor;

public class BedrollItem extends BedItem {

    public final DyeColor color;

    public BedrollItem(DyeColor color, Block blockIn, Properties properties) {
        super(blockIn, properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return this.color;
    }

}
