package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.gui.screen.inventory.BeetleInventoryScreen;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import com.vulp.druidcraft.inventory.container.TravelPackContainer;
import com.vulp.druidcraft.inventory.container.WoodcutterContainer;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;

// TODO: Improve on this
@SuppressWarnings("unchecked")
public class GUIRegistry {

    public static ContainerType<BeetleInventoryContainer> beetle_inv = (ContainerType<BeetleInventoryContainer>) IForgeContainerType.create((windowId, inv, data) -> {
        int id = data.readInt();
        int size = data.readInt();
        return new BeetleInventoryContainer(windowId, inv, new Inventory(size), id);
    }).setRegistryName(Druidcraft.MODID, "beetle_inv");

    public static ContainerType<CrateContainer> generic_9X3 = (ContainerType<CrateContainer>) new ContainerType<>(CrateContainer::createGeneric9X3).setRegistryName(Druidcraft.MODID, "generic_9x3");
    public static ContainerType<CrateContainer> generic_9X6 = (ContainerType<CrateContainer>) new ContainerType<>(CrateContainer::createGeneric9X6).setRegistryName(Druidcraft.MODID, "generic_9x6");
    public static ContainerType<CrateContainer> generic_9X12 = (ContainerType<CrateContainer>) new ContainerType<>(CrateContainer::createGeneric9X12).setRegistryName(Druidcraft.MODID, "generic_9x12");
    public static ContainerType<CrateContainer> generic_9X24 = (ContainerType<CrateContainer>) new ContainerType<>(CrateContainer::createGeneric9X24).setRegistryName(Druidcraft.MODID, "generic_9x24");
    public static ContainerType<WoodcutterContainer> woodcutter = (ContainerType<WoodcutterContainer>) new ContainerType<>(WoodcutterContainer::new).setRegistryName(Druidcraft.MODID, "woodcutter");
    public static ContainerType<TravelPackContainer> travel_pack = (ContainerType<TravelPackContainer>) new ContainerType<>(TravelPackContainer::createClientContainer).setRegistryName(Druidcraft.MODID, "travel_pack");
}