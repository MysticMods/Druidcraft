package com.vulp.druidcraft;

import com.vulp.druidcraft.blocks.OreBlock;
import com.vulp.druidcraft.config.Configuration;
import com.vulp.druidcraft.registry.*;
import com.vulp.druidcraft.world.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("druidcraft")
public class Druidcraft {
    public static Druidcraft INSTANCE;
    public static final String MODID = "druidcraft";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final ItemGroup DRUIDCRAFT = new DruidcraftItemGroup();

    public Druidcraft() {
        INSTANCE = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configuration.server_config);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Configuration.client_config);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        Configuration.loadConfig(Configuration.server_config, FMLPaths.CONFIGDIR.get().resolve("druidcraft-server.toml").toString());
        Configuration.loadConfig(Configuration.client_config, FMLPaths.CONFIGDIR.get().resolve("druidcraft-client.toml").toString());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        OreGeneration.setupOreGeneration();
        LOGGER.info("Setup method registered.");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Client registry method registered.", event.getMinecraftSupplier().get().gameSettings);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

    }
}
