package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.client.gui.screen.inventory.BeetleInventoryScreen;
import com.vulp.druidcraft.client.gui.screen.inventory.CrateScreen;
import com.vulp.druidcraft.client.renders.BeetleEntityRender;
import com.vulp.druidcraft.client.renders.DreadfishEntityRender;
import com.vulp.druidcraft.client.renders.LunarMothEntityRender;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.DreadfishEntity;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry
{
    public static void registryRenders()
    {
        // ENTITIES
        RenderingRegistry.registerEntityRenderingHandler(DreadfishEntity.class, new DreadfishEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(BeetleEntity.class, new BeetleEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(LunarMothEntity.class, new LunarMothEntityRender.RenderFactory());

        // SCREENS
        ScreenManager.registerFactory(GUIRegistry.beetle_inv, BeetleInventoryScreen::new);
        ScreenManager.registerFactory(GUIRegistry.generic_9X9, CrateScreen::new);
        ScreenManager.registerFactory(GUIRegistry.generic_9X12, CrateScreen::new);
        ScreenManager.registerFactory(GUIRegistry.generic_9X15, CrateScreen::new);
        ScreenManager.registerFactory(GUIRegistry.generic_9X18, CrateScreen::new);
        ScreenManager.registerFactory(GUIRegistry.generic_9X21, CrateScreen::new);
        ScreenManager.registerFactory(GUIRegistry.generic_9X24, CrateScreen::new);

    }
}
