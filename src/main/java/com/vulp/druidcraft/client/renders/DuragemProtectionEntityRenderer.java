package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.DuragemProtectionEntityModel;
import com.vulp.druidcraft.entities.DuragemProtectionEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class DuragemProtectionEntityRenderer extends EntityRenderer<DuragemProtectionEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Druidcraft.MODID, "textures/entity/misc/duragem_protection.png");
    protected final DuragemProtectionEntityModel model = new DuragemProtectionEntityModel();
    private float visibility;

    protected DuragemProtectionEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(DuragemProtectionEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.visibility = entityIn.getVisibility();
        if (this.visibility > 0.0F) {
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, -0.501D, 0.0D);
            matrixStackIn.scale(1.001F, 1.001F, 1.001F);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderTypeDictionary.getEntityTransparentGlow(TEXTURE));
            model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, this.visibility);
            matrixStackIn.pop();
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    @Override
    public ResourceLocation getEntityTexture(DuragemProtectionEntity entity) {
        return TEXTURE;
    }

    public static class RenderFactory implements IRenderFactory<DuragemProtectionEntity> {
        @Override
        public EntityRenderer<? super DuragemProtectionEntity> createRenderFor(EntityRendererManager manager)
        {
            return new DuragemProtectionEntityRenderer(manager);
        }
    }

}
