package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.BlockInhabitingEntityModel;
import com.vulp.druidcraft.entities.DuragemProtectionEntity;
import com.vulp.druidcraft.entities.FieryGlassGlowEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class FieryGlassGlowEntityRenderer extends EntityRenderer<FieryGlassGlowEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Druidcraft.MODID, "textures/entity/blockentity/fiery_glass_glow.png");
    protected final BlockInhabitingEntityModel model = new BlockInhabitingEntityModel();

    protected FieryGlassGlowEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(FieryGlassGlowEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        float visibility = entityIn.getVisibility();
        if (visibility > 0.0F) {
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, -0.501D, 0.0D);
            matrixStackIn.scale(1.001F, 1.001F, 1.001F);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderTypeDictionary.getEntityTransparentGlow(TEXTURE));
            model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, visibility);
            matrixStackIn.pop();
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(FieryGlassGlowEntity entity) {
        return TEXTURE;
    }

    public static class RenderFactory implements IRenderFactory<FieryGlassGlowEntity> {
        @Override
        public EntityRenderer<? super FieryGlassGlowEntity> createRenderFor(EntityRendererManager manager)
        {
            return new FieryGlassGlowEntityRenderer(manager);
        }
    }

}
