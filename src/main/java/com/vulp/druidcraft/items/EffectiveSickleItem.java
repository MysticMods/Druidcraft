package com.vulp.druidcraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.Set;

public interface EffectiveSickleItem {
    Set<Block> getEffectiveBlocks ();
    Set<Material> getEffectiveMaterials ();
}
