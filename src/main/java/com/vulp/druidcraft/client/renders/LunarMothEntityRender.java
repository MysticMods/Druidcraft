package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.BeetleEntityModel;
import com.vulp.druidcraft.client.models.LunarMothEntityModel;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.LunarMothColors;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class LunarMothEntityRender extends MobRenderer<LunarMothEntity, LunarMothEntityModel<LunarMothEntity>>
{
    private static final ResourceLocation MOTH_TURQUOISE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_turquoise.png");
    private static final ResourceLocation MOTH_WHITE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_white.png");
    private static final ResourceLocation MOTH_LIME = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_lime.png");
    private static final ResourceLocation MOTH_ORANGE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_orange.png");
    private static final ResourceLocation MOTH_PINK = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_pink.png");
    private static final ResourceLocation MOTH_YELLOW = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_yellow.png");

    public LunarMothEntityRender(EntityRendererManager manager)
    {
        super(manager, new LunarMothEntityModel<>(), 0.2f);
    }

    @Override
    protected ResourceLocation getEntityTexture(LunarMothEntity entity) {
        switch (entity.getColor()) {
            case TURQUOISE:
                return MOTH_TURQUOISE;
            case WHITE:
                return MOTH_WHITE;
            case LIME:
                return MOTH_LIME;
            case ORANGE:
                return MOTH_ORANGE;
            case PINK:
                return MOTH_PINK;
            case YELLOW:
                return MOTH_YELLOW;
        }
        return MOTH_WHITE;
    }

    public static class RenderFactory implements IRenderFactory<LunarMothEntity>
    {
        @Override
        public EntityRenderer<? super LunarMothEntity> createRenderFor(EntityRendererManager manager)
        {
            return new LunarMothEntityRender(manager);
        }
    }
}
