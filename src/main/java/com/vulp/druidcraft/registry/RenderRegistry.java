package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.client.renders.BeetleEntityRender;
import com.vulp.druidcraft.client.renders.DreadfishEntityRender;
import com.vulp.druidcraft.client.renders.LunarMothEntityRender;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.DreadfishEntity;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry
{
    public static void registryEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(DreadfishEntity.class, new DreadfishEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(BeetleEntity.class, new BeetleEntityRender.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(LunarMothEntity.class, new LunarMothEntityRender.RenderFactory());
    }
}
