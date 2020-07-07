package com.vulp.druidcraft.client.renders;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.BeetleEntityModel;
import com.vulp.druidcraft.client.models.LunarMothEntityModel;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.LunarMothColors;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class LunarMothEntityRender extends MobRenderer<LunarMothEntity, LunarMothEntityModel<LunarMothEntity>>
{
    private static final ResourceLocation MOTH_TURQUOISE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_turquoise.png");
    private static final ResourceLocation MOTH_WHITE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_white.png");
    private static final ResourceLocation MOTH_LIME = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_lime.png");
    private static final ResourceLocation MOTH_ORANGE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_orange.png");
    private static final ResourceLocation MOTH_PINK = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_pink.png");
    private static final ResourceLocation MOTH_YELLOW = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_yellow.png");
    private static final RenderType RENDER_TYPE_TURQUOISE = RenderTypeDictionary.getEntityGlow(MOTH_TURQUOISE);
    private static final RenderType RENDER_TYPE_WHITE = RenderTypeDictionary.getEntityGlow(MOTH_WHITE);
    private static final RenderType RENDER_TYPE_LIME = RenderTypeDictionary.getEntityGlow(MOTH_LIME);
    private static final RenderType RENDER_TYPE_ORANGE = RenderTypeDictionary.getEntityGlow(MOTH_ORANGE);
    private static final RenderType RENDER_TYPE_PINK = RenderTypeDictionary.getEntityGlow(MOTH_PINK);
    private static final RenderType RENDER_TYPE_YELLOW = RenderTypeDictionary.getEntityGlow(MOTH_YELLOW);

    public LunarMothEntityRender(EntityRendererManager manager)
    {
        super(manager, new LunarMothEntityModel<>(), 0.2f);
    }

    @Override
    public ResourceLocation getEntityTexture(LunarMothEntity entity) {
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

    @Nullable
    @Override
    protected RenderType func_230042_a_(LunarMothEntity entity, boolean p_230042_2_, boolean p_230042_3_) {
        switch (entity.getColor()) {
            case WHITE:
                return RENDER_TYPE_WHITE;
            case LIME:
                return RENDER_TYPE_LIME;
            case ORANGE:
                return RENDER_TYPE_ORANGE;
            case PINK:
                return RENDER_TYPE_PINK;
            case YELLOW:
                return RENDER_TYPE_YELLOW;
            default:
                return RENDER_TYPE_TURQUOISE;
        }
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
