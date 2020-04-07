package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class GUIRegistry {

    public static ContainerType beetle_inv = IForgeContainerType.create((windowId, inv, data) -> {
        int id = data.readInt();
        int size = data.readInt();
        return new BeetleInventoryContainer(windowId, inv, new Inventory(size), id);
    }).setRegistryName(Druidcraft.MODID, "beetle_inv");

    public static ContainerType generic_9X12 = new ContainerType<>(CrateContainer::createGeneric9X12).setRegistryName(Druidcraft.MODID, "generic_9x12");
    public static ContainerType generic_9X24 = new ContainerType<>(CrateContainer::createGeneric9X24).setRegistryName(Druidcraft.MODID, "generic_9x24");
}