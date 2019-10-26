package com.vulp.druidcraft.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public interface ICustomParticleRender extends IParticleRenderType {

    IParticleRenderType PARTICLE_SHEET_TRANSLUCENT_GLOW = new IParticleRenderType() {
        public void beginRender(BufferBuilder buffer, TextureManager textureManager) {
            RenderHelper.disableStandardItemLighting();
            GlStateManager.depthMask(true);
            textureManager.bindTexture(AtlasTexture.LOCATION_PARTICLES_TEXTURE);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            GlStateManager.alphaFunc(516, 0.003921569F);
            buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        }
        public void finishRender(Tessellator tess) {
            tess.draw();
        }
        public String toString() {
            return "PARTICLE_SHEET_TRANSLUCENT_GLOW";
        }
    };
}
