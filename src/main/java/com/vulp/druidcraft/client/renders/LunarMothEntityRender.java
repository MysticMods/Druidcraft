package com.vulp.druidcraft.client.renders;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.LunarMothEntityModel;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraft.block.HopperBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.inventory.container.HopperContainer;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class LunarMothEntityRender extends MobRenderer<LunarMothEntity, LunarMothEntityModel<LunarMothEntity>> {

    public static final ResourceLocation MOTH_TURQUOISE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_turquoise.png");
    public static final ResourceLocation MOTH_WHITE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_white.png");
    public static final ResourceLocation MOTH_LIME = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_lime.png");
    public static final ResourceLocation MOTH_ORANGE = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_orange.png");
    public static final ResourceLocation MOTH_PINK = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_pink.png");
    public static final ResourceLocation MOTH_YELLOW = new ResourceLocation(Druidcraft.MODID, "textures/entity/lunar_moth/lunar_moth_yellow.png");

    private static final RenderType RENDER_TYPE_TURQUOISE = RenderTypeDictionary.getEntityGlow(MOTH_TURQUOISE);
    private static final RenderType RENDER_TYPE_WHITE = RenderTypeDictionary.getEntityGlow(MOTH_WHITE);
    private static final RenderType RENDER_TYPE_LIME = RenderTypeDictionary.getEntityGlow(MOTH_LIME);
    private static final RenderType RENDER_TYPE_ORANGE = RenderTypeDictionary.getEntityGlow(MOTH_ORANGE);
    private static final RenderType RENDER_TYPE_PINK = RenderTypeDictionary.getEntityGlow(MOTH_PINK);
    private static final RenderType RENDER_TYPE_YELLOW = RenderTypeDictionary.getEntityGlow(MOTH_YELLOW);

    public LunarMothEntityRender(EntityRendererManager manager) {
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
    protected RenderType func_230496_a_(LunarMothEntity entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        if (!entity.isResting()) {
            return getTrueRenderType(entity);
        }
        return super.func_230496_a_(entity, p_230496_2_, p_230496_3_, p_230496_4_);
    }

    public RenderType getTrueRenderType(LunarMothEntity entity) {
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

    public static class RenderFactory implements IRenderFactory<LunarMothEntity> {
        @Override
        public EntityRenderer<? super LunarMothEntity> createRenderFor(EntityRendererManager manager)
        {
            return new LunarMothEntityRender(manager);
        }
    }
}
