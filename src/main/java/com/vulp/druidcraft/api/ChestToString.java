package com.vulp.druidcraft.api;

import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;

public enum ChestToString {

    DARKWOOD(BlockRegistry.darkwood_chest, BlockRegistry.trapped_darkwood_chest, "darkwood");

    private final Block chest;
    private final Block trapped_chest;
    private final String name;

    ChestToString(Block chest, Block trappedChest, String name) {
        this.chest = chest;
        this.trapped_chest = trappedChest;
        this.name = name;
    }

    public static ChestToString getEnumFromChest(Block chest) {
        for (ChestToString index : ChestToString.values()) {
            if (index.getChest() == chest || index.getTrappedChest() == chest) {
                return index;
            }
        }
        return null;
    }

    public Block getChest() {
        return chest;
    }

    public Block getTrappedChest() {
        return trapped_chest;
    }

    public String getString() {
        return name;
    }

}
