package com.vulp.druidcraft.api;

import com.vulp.druidcraft.blocks.BedrollBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public enum BedrollDyeColorIndex {
    WHITE(0, DyeColor.WHITE, ItemRegistry.white_bedroll, BlockRegistry.white_bedroll),
    ORANGE(1, DyeColor.ORANGE, ItemRegistry.orange_bedroll, BlockRegistry.orange_bedroll),
    MAGENTA(2, DyeColor.MAGENTA, ItemRegistry.magenta_bedroll, BlockRegistry.magenta_bedroll),
    LIGHT_BLUE(3, DyeColor.LIGHT_BLUE, ItemRegistry.light_blue_bedroll, BlockRegistry.light_blue_bedroll),
    YELLOW(4, DyeColor.YELLOW, ItemRegistry.yellow_bedroll, BlockRegistry.yellow_bedroll),
    LIME(5, DyeColor.LIME, ItemRegistry.lime_bedroll, BlockRegistry.lime_bedroll),
    PINK(6, DyeColor.PINK, ItemRegistry.pink_bedroll, BlockRegistry.pink_bedroll),
    GRAY(7, DyeColor.GRAY, ItemRegistry.gray_bedroll, BlockRegistry.gray_bedroll),
    LIGHT_GRAY(8, DyeColor.LIGHT_GRAY, ItemRegistry.light_gray_bedroll, BlockRegistry.light_gray_bedroll),
    CYAN(9, DyeColor.CYAN, ItemRegistry.cyan_bedroll, BlockRegistry.cyan_bedroll),
    PURPLE(10, DyeColor.PURPLE, ItemRegistry.purple_bedroll, BlockRegistry.purple_bedroll),
    BLUE(11, DyeColor.BLUE, ItemRegistry.blue_bedroll, BlockRegistry.blue_bedroll),
    BROWN(12, DyeColor.BROWN, ItemRegistry.brown_bedroll, BlockRegistry.brown_bedroll),
    GREEN(13, DyeColor.GREEN, ItemRegistry.green_bedroll, BlockRegistry.green_bedroll),
    RED(14, DyeColor.RED, ItemRegistry.red_bedroll, BlockRegistry.red_bedroll),
    BLACK(15, DyeColor.BLACK, ItemRegistry.black_bedroll, BlockRegistry.black_bedroll);

    private static final BedrollDyeColorIndex[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(BedrollDyeColorIndex::getIndex)).toArray(BedrollDyeColorIndex[]::new);

    private final int index;
    private final DyeColor color;
    private final Item bedrollItem;
    private final Block bedrollBlock;

    BedrollDyeColorIndex(int index, DyeColor color, Item bedrollItem, Block bedrollBlock) {
        this.index = index;
        this.color = color;
        this.bedrollItem = bedrollItem;
        this.bedrollBlock = bedrollBlock;
    }

    public int getIndex() {
        return this.index;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public Item getBedrollItem() {
        return this.bedrollItem;
    }

    public Block getBedrollBlock() {
        return this.bedrollBlock;
    }

    @Nullable
    public static BedrollDyeColorIndex byBlock(BedrollBlock block) {
        for (BedrollDyeColorIndex index : BedrollDyeColorIndex.values()) {
            if (block == index.getBedrollBlock()) {
                return index;
            }
        }
        return null;
    }

    public static BedrollDyeColorIndex byIndex(int index) {
        if (index < 0 || index >= VALUES.length) {
            index = 0;
        }

        return VALUES[index];
    }


}
