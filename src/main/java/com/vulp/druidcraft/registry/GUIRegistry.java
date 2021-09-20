package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.*;
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
 /*   public static ContainerType<HellkilnContainer> hellkiln = (ContainerType<HellkilnContainer>) new ContainerType<>(HellkilnContainer::new).setRegistryName(Druidcraft.MODID, "hellkiln");*/
    public static ContainerType<HellkilnIgniterContainer> hellkiln_igniter = (ContainerType<HellkilnIgniterContainer>) new ContainerType<>(HellkilnIgniterContainer::new).setRegistryName(Druidcraft.MODID, "hellkiln_igniter");
    public static ContainerType<TravelPackContainer> travel_pack = (ContainerType<TravelPackContainer>) new ContainerType<>(TravelPackContainer::createClientContainer).setRegistryName(Druidcraft.MODID, "travel_pack");
/*    public static ContainerType<SmithingWorkbenchContainer> smithing_workbench = (ContainerType<SmithingWorkbenchContainer>) new ContainerType<>(SmithingWorkbenchContainer::new).setRegistryName(Druidcraft.MODID, "smithing_workbench");*/
/*    public static ContainerType<MortarAndPestleContainer> mortar_and_pestle = (ContainerType<MortarAndPestleContainer>) new ContainerType<>(MortarAndPestleContainer::new).setRegistryName(Druidcraft.MODID, "mortar_and_pestle");*/
/*    public static ContainerType<FluidCraftingTableContainer> fluid_crafting_table = (ContainerType<FluidCraftingTableContainer>) new ContainerType<>(FluidCraftingTableContainer::new).setRegistryName(Druidcraft.MODID, "fluid_crafting_table");*/


}