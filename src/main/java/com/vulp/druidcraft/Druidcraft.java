package com.vulp.druidcraft;

import com.vulp.druidcraft.config.Configuration;
import com.vulp.druidcraft.events.BiomeLoadEventHandler;
import com.vulp.druidcraft.events.EventHandler;
import com.vulp.druidcraft.network.PacketHandler;
import com.vulp.druidcraft.registry.*;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
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

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::doClientStuff);
        bus.addListener(this::doParticleStuff);

        Configuration.loadConfig(Configuration.server_config, FMLPaths.CONFIGDIR.get().resolve("druidcraft-server.toml").toString());
        Configuration.loadConfig(Configuration.client_config, FMLPaths.CONFIGDIR.get().resolve("druidcraft-client.toml").toString());
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();

        event.enqueueWork(() -> {
                BiomeRegistry.registerBiomes();
                VanillaIntegrationRegistry.setup();
            });

        // Deferred to static methods
        /*MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new BiomeLoadEventHandler());*/
        LOGGER.info("Setup method registered.");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderRegistry.registryRenders(event);
        // RenderHandler.registerColors();
        LOGGER.info("Client registry method registered.");
    }

    private void doParticleStuff(final ParticleFactoryRegisterEvent event) {
        ParticleRegistry.registerFactories();
        LOGGER.info("Particle registry method registered.");
    }

/*    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }*/
}
