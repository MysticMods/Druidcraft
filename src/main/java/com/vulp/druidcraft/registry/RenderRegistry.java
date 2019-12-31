package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.blocks.tileentities.LunarMothJarTileEntity;
import com.vulp.druidcraft.blocks.tileentities.lunarmothjar.LunarMothJarTileEntityTurquoise;
import com.vulp.druidcraft.client.gui.screen.inventory.BeetleInventoryScreen;
import com.vulp.druidcraft.client.renders.BeetleEntityRender;
import com.vulp.druidcraft.client.renders.DreadfishEntityRender;
import com.vulp.druidcraft.client.renders.LunarMothEntityRender;
import com.vulp.druidcraft.client.renders.LunarMothJarTileEntityRender;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.DreadfishEntity;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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

        // TILE ENTITIES
        ClientRegistry.bindTileEntitySpecialRenderer(LunarMothJarTileEntityTurquoise.class, new LunarMothJarTileEntityRender());

        // SCREENS
        ScreenManager.registerFactory(GUIRegistry.beetle_inv, BeetleInventoryScreen::new);
    }
}
