package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.gui.screen.inventory.BeetleInventoryScreen;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class GUIRegistry {
    private static final List<ContainerType<?>> CONTAINER_TYPE = new ArrayList<>();

//    public static final ContainerType<ExampleContainer> EXAMPLE = createContainer("example_container", (IContainerFactory<ExampleContainer>) (windowId, playerInventory, data) -> {
//        ExampleTileEntity tileEntity = (ExampleTileEntity) playerInventory.player.world.getTileEntity(data.readBlockPos());
//        return new ExampleContainer(windowId, playerInventory, tileEntity);
//    });

    private static <T extends Container> ContainerType<T> createContainer(String name, ContainerType.IFactory<T> factory) {
        ResourceLocation location = new ResourceLocation(Druidcraft.MODID, name);
        ContainerType<T> container = new ContainerType<>(factory);
        container.setRegistryName(location);
        CONTAINER_TYPE.add(container);

        return container;
    }

    @SubscribeEvent
    public static void registerTypes(final RegistryEvent.Register<ContainerType<?>> event)
    {
        CONTAINER_TYPE.forEach(type -> event.getRegistry().register(type));
        CONTAINER_TYPE.clear();
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreenFactories()
    {

    }
}
