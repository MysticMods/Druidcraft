/*
package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.blocks.tileentities.FluidCraftingTableTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.pipeline.LightUtil;

import javax.annotation.Nonnull;

public class FluidCraftingTableTileEntityRenderer extends TileEntityRenderer<FluidCraftingTableTileEntity> {

    public FluidCraftingTableTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    public float pixelToUnits(float i) {
        return 1F / 256F * i;
    }

    @Override
    public void render(@Nonnull FluidCraftingTableTileEntity table, float pticks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {
        Fluid fluid = table.getFluid();
        if (fluid != null && fluid != Fluids.EMPTY) {
            ms.push();
            float i = 1F / 16F * 2F;
            ms.translate(i, 1.5, i);
            float s = pixelToUnits(12F);
            ms.translate(0F, -0.5F - 1F / 16F, 0F);

            ms.rotate(Vector3f.XP.rotationDegrees(90));
            ms.scale(s, s, s);

            TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getBlockModelShapes().getModel(fluid.getDefaultState().getBlockState()).getParticleTexture();
            int color = fluid.getAttributes().getColor(table.getWorld(), table.getPos());
            IVertexBuilder buffer = buffers.getBuffer(Atlases.getSolidBlockType());
            renderIcon(ms, buffer, sprite, color, 1.0F, overlay, fluid.getAttributes().getLuminosity() > 0 ? 0xF000F0 : WorldRenderer.getPackedLightmapCoords(table.getWorld(), table.getBlockState(), table.getPos().offset(Direction.UP)));
            ms.pop();
        }
    }

    private void renderIcon(MatrixStack ms, IVertexBuilder builder, TextureAtlasSprite sprite, int color, float alpha, int overlay, int light) {
        int red = ((color >> 16) & 0xFF);
        int green = ((color >> 8) & 0xFF);
        int blue = (color & 0xFF);
        Matrix4f mat = ms.getLast().getMatrix();
        builder.pos(mat, 0, 16, 0).color(red, green, blue, (int) (alpha * 255F)).tex(sprite.getMinU(), sprite.getMaxV()).overlay(overlay).lightmap(light).normal(0, 0, 1).endVertex();
        builder.pos(mat, 16, 16, 0).color(red, green, blue, (int) (alpha * 255F)).tex(sprite.getMaxU(), sprite.getMaxV()).overlay(overlay).lightmap(light).normal(0, 0, 1).endVertex();
        builder.pos(mat, 16, 0, 0).color(red, green, blue, (int) (alpha * 255F)).tex(sprite.getMaxU(), sprite.getMinV()).overlay(overlay).lightmap(light).normal(0, 0, 1).endVertex();
        builder.pos(mat, 0, 0, 0).color(red, green, blue, (int) (alpha * 255F)).tex(sprite.getMinU(), sprite.getMinV()).overlay(overlay).lightmap(light).normal(0, 0, 1).endVertex();
    }

}
*/
