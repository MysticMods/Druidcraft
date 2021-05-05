package com.vulp.druidcraft.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class FieryShieldModel extends Model {

    private final ModelRenderer main;
    private final ModelRenderer left;
    private final ModelRenderer right;

    public FieryShieldModel() {
        super(RenderType::getEntitySolid);

        textureWidth = 64;
        textureHeight = 64;

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 24.0F, 0.0F);
        main.setTextureOffset(0, 25).addBox(-1.0F, -27.0F, -1.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
        main.setTextureOffset(16, 25).addBox(-3.0F, -29.0F, -2.0F, 6.0F, 10.0F, 1.0F, 0.0F, false);

        left = new ModelRenderer(this);
        left.setRotationPoint(0.0F, -24.0F, -3.0F);
        main.addChild(left);
        setRotationAngle(left, 0.0F, -0.2182F, 0.0F);
        left.setTextureOffset(16, 0).addBox(0.0F, -12.0F, 0.0F, 7.0F, 24.0F, 1.0F, 0.0F, false);
        left.setTextureOffset(30, 30).addBox(7.0F, -12.0F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
        left.setTextureOffset(30, 25).addBox(7.0F, 8.0F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);

        right = new ModelRenderer(this);
        right.setRotationPoint(0.0F, -24.0F, -3.0F);
        main.addChild(right);
        setRotationAngle(right, 0.0F, 0.2182F, 0.0F);
        right.setTextureOffset(0, 0).addBox(-7.0F, -12.0F, 0.0F, 7.0F, 24.0F, 1.0F, 0.0F, false);
        right.setTextureOffset(10, 25).addBox(-9.0F, -12.0F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
        right.setTextureOffset(0, 25).addBox(-9.0F, 8.0F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}
