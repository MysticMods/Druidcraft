package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.client.gui.screen.inventory.BeetleInventoryScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenRegistry
{
    @SuppressWarnings("unchecked")
    public static void registryScreenRenders()
    {
        ScreenManager.registerFactory(GUIRegistry.beetle_inv, BeetleInventoryScreen::new);
    }
}
