package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import com.vulp.druidcraft.inventory.container.TravelPackContainer;
import com.vulp.druidcraft.inventory.container.WoodcutterContainer;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class GUIRegistry {

    public static ContainerType beetle_inv = IForgeContainerType.create((windowId, inv, data) -> {
        int id = data.readInt();
        int size = data.readInt();
        return new BeetleInventoryContainer(windowId, inv, new Inventory(size), id);
    }).setRegistryName(Druidcraft.MODID, "beetle_inv");

    public static ContainerType generic_9X3 = new ContainerType<>(CrateContainer::createGeneric9X3).setRegistryName(Druidcraft.MODID, "generic_9x3");
    public static ContainerType generic_9X6 = new ContainerType<>(CrateContainer::createGeneric9X6).setRegistryName(Druidcraft.MODID, "generic_9x6");
    public static ContainerType generic_9X12 = new ContainerType<>(CrateContainer::createGeneric9X12).setRegistryName(Druidcraft.MODID, "generic_9x12");
    public static ContainerType generic_9X24 = new ContainerType<>(CrateContainer::createGeneric9X24).setRegistryName(Druidcraft.MODID, "generic_9x24");
    public static ContainerType woodcutter = new ContainerType<>(WoodcutterContainer::new).setRegistryName(Druidcraft.MODID, "woodcutter");
    public static ContainerType travel_pack = new ContainerType<>(TravelPackContainer::createClientContainer).setRegistryName(Druidcraft.MODID, "travel_pack");
}