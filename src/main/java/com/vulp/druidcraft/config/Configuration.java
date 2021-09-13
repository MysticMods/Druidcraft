package com.vulp.druidcraft.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

@Mod.EventBusSubscriber
public class Configuration
{
    private static final ForgeConfigSpec.Builder common_builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec common_config;

    private static final ForgeConfigSpec.Builder client_builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec client_config;

    static
    {
        OreConfig.init(common_builder, client_builder);
        DropRateConfig.init(common_builder, client_builder);
        HarvestConfig.init(common_builder, client_builder);
        EntitySpawnConfig.init(common_builder, client_builder);

        common_config = common_builder.build();
        client_config = client_builder.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path)
    {
        //Druidcraft.LOGGER.info("Loading Config: " + path);
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        //Druidcraft.LOGGER.info("Built Config: " + path);
        file.load();
        //Druidcraft.LOGGER.info("Loaded Config: " + path);
        config.setConfig(file);
    }
}