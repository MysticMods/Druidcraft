package com.vulp.druidcraft;

import com.vulp.druidcraft.config.Configuration;
import com.vulp.druidcraft.config.DropRateConfig;
import com.vulp.druidcraft.events.LootHandler;
import com.vulp.druidcraft.registry.*;
import com.vulp.druidcraft.world.OreGeneration;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
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
        VanillaIntegrationRegistry.setup();

        MinecraftForge.EVENT_BUS.register(new GUIRegistry());
        if (DropRateConfig.drop_seeds.get()) {
            MinecraftForge.EVENT_BUS.register(new LootHandler());
        }

        LOGGER.info("Setup method registered.");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderRegistry.registryEntityRenders();
        ScreenRegistry.registryScreenRenders();
        LOGGER.info("Client registry method registered.");
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
