package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.gui.screen.inventory.BeetleInventoryScreen;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class GUIRegistry {
    private static final List<ContainerType<?>> CONTAINER_TYPE = new ArrayList<>();

    public static ContainerType beetle_inv = IForgeContainerType.create((windowId, inv, data) -> {
        int id = data.readInt();
        int size = data.readInt();
        return new BeetleInventoryContainer(windowId, inv, new Inventory(size), id);
    }).setRegistryName(Druidcraft.MODID, "beetle_inv");

    @SubscribeEvent
    public static void registerTypes(final RegistryEvent.Register<ContainerType<?>> event)
    {
        CONTAINER_TYPE.forEach(type -> event.getRegistry().register(type));
        CONTAINER_TYPE.clear();
    }
}