package com.vulp.druidcraft.client.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.blocks.tileentities.SmallBeamTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)
public class SmallBeamTileEntityRenderer extends TileEntityRenderer<SmallBeamTileEntity> {

    public static final ResourceLocation texture = DruidcraftRegistry.location("entity/small_beam/rope");
    private final SmallBeamRopeModel model = new SmallBeamRopeModel();

    public SmallBeamTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(SmallBeamTileEntity smallBeamTileEntity, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        ArrayList<Boolean> list = smallBeamTileEntity.getDirections();
        Direction.Axis axis = smallBeamTileEntity.getRotation();
        matrixStack.push();
        if (axis != null) {
            this.model.north.showModel = list.get(0);
            this.model.east.showModel = list.get(1);
            this.model.south.showModel = list.get(2);
            this.model.west.showModel = list.get(3);
            this.model.up.showModel = list.get(4);
            this.model.down.showModel = list.get(5);
            this.model.center_x.showModel = axis == Direction.Axis.X;
            this.model.center_y.showModel = axis == Direction.Axis.Y;
            this.model.center_z.showModel = axis == Direction.Axis.Z;
        }
        matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        Material material = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, texture);
        SmallBeamRopeModel model = this.model;
        model.getClass();
        IVertexBuilder iVertexBuilder = material.getBuffer(iRenderTypeBuffer, model::getRenderType);
        this.model.render(matrixStack, iVertexBuilder, i, i1, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
    }

    @OnlyIn(Dist.CLIENT)
    public class SmallBeamRopeModel extends Model {
        private final ModelRenderer center_y;
        private final ModelRenderer center_z;
        private final ModelRenderer center_x;
        private final ModelRenderer down;
        private final ModelRenderer up;
        private final ModelRenderer north;
        private final ModelRenderer south;
        private final ModelRenderer east;
        private final ModelRenderer west;

        public SmallBeamRopeModel() {
            super(RenderType::getEntityCutoutNoCull);
            textureWidth = 64;
            textureHeight = 64;

            center_y = new ModelRenderer(this);
            center_y.setRotationPoint(0.0F, 16.0F, 0.0F);
            center_y.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
            center_y.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.2F, false);

            center_z = new ModelRenderer(this);
            center_z.setRotationPoint(0.0F, 16.0F, 0.0F);
            setRotationAngle(center_z, -1.5708F, 0.0F, 0.0F);
            center_z.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
            center_z.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.2F, false);

            center_x = new ModelRenderer(this);
            center_x.setRotationPoint(0.0F, 16.0F, 0.0F);
            setRotationAngle(center_x, 0.0F, 0.0F, 1.5708F);
            center_x.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
            center_x.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.2F, false);

            down = new ModelRenderer(this);
            down.setRotationPoint(0.0F, 16.0F, 0.0F);
            down.setTextureOffset(0, 22).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
            down.setTextureOffset(8, 22).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.2F, false);

            up = new ModelRenderer(this);
            up.setRotationPoint(0.0F, 16.0F, 0.0F);
            up.setTextureOffset(0, 16).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
            up.setTextureOffset(8, 16).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.2F, false);

            north = new ModelRenderer(this);
            north.setRotationPoint(0.0F, 16.0F, 0.0F);
            north.setTextureOffset(40, 16).addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
            north.setTextureOffset(52, 16).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 3.0F, 0.2F, false);

            south = new ModelRenderer(this);
            south.setRotationPoint(0.0F, 16.0F, 0.0F);
            south.setTextureOffset(40, 22).addBox(-1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
            south.setTextureOffset(52, 22).addBox(-1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 4.0F, 0.2F, false);

            east = new ModelRenderer(this);
            east.setRotationPoint(0.0F, 16.0F, 0.0F);
            east.setTextureOffset(16, 16).addBox(-8.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
            east.setTextureOffset(28, 16).addBox(-7.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.2F, false);

            west = new ModelRenderer(this);
            west.setRotationPoint(0.0F, 16.0F, 0.0F);
            west.setTextureOffset(16, 20).addBox(4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
            west.setTextureOffset(28, 20).addBox(4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 0.2F, false);
        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            center_y.render(matrixStack, buffer, packedLight, packedOverlay);
            center_z.render(matrixStack, buffer, packedLight, packedOverlay);
            center_x.render(matrixStack, buffer, packedLight, packedOverlay);
            down.render(matrixStack, buffer, packedLight, packedOverlay);
            up.render(matrixStack, buffer, packedLight, packedOverlay);
            north.render(matrixStack, buffer, packedLight, packedOverlay);
            south.render(matrixStack, buffer, packedLight, packedOverlay);
            east.render(matrixStack, buffer, packedLight, packedOverlay);
            west.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }

    }

}
