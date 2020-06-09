package com.vulp.druidcraft.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChitinShieldModel extends Model {
    private final ModelRenderer bb_main;
    private final ModelRenderer chitin_bot_left;
    private final ModelRenderer chitin_bot_right;
    private final ModelRenderer chitin_top_left;
    private final ModelRenderer chitin_top_right;

    public ChitinShieldModel() {
        super(RenderType::getEntitySolid);
        textureWidth = 64;
        textureHeight = 64;

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(32, 17).addBox(-1.0F, -27.0F, -1.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
        bb_main.setTextureOffset(32, 0).addBox(-6.0F, -32.0F, -2.0F, 12.0F, 16.0F, 1.0F, 0.0F, false);

        chitin_bot_left = new ModelRenderer(this);
        chitin_bot_left.setRotationPoint(-0.5F, 21.5F, -2.0F);
        setRotationAngle(chitin_bot_left, 0.0873F, 0.0873F, 0.0F);
        chitin_bot_left.setTextureOffset(0, 10).addBox(-6.9826F, -20.9374F, 0.6326F, 7.0F, 9.0F, 1.0F, 0.0F, false);

        chitin_bot_right = new ModelRenderer(this);
        chitin_bot_right.setRotationPoint(0.5F, 21.5F, -2.0F);
        setRotationAngle(chitin_bot_right, 0.0873F, -0.0873F, 0.0F);
        chitin_bot_right.setTextureOffset(16, 10).addBox(-0.0174F, -20.9374F, 0.6326F, 7.0F, 9.0F, 1.0F, 0.0F, false);

        chitin_top_left = new ModelRenderer(this);
        chitin_top_left.setRotationPoint(-0.5F, 26.5F, -2.0F);
        setRotationAngle(chitin_top_left, -0.0873F, 0.0873F, 0.0F);
        chitin_top_left.setTextureOffset(0, 0).addBox(-6.9826F, -35.8798F, -3.5525F, 7.0F, 9.0F, 1.0F, 0.0F, false);

        chitin_top_right = new ModelRenderer(this);
        chitin_top_right.setRotationPoint(0.5F, 26.5F, -2.0F);
        setRotationAngle(chitin_top_right, -0.0873F, -0.0873F, 0.0F);
        chitin_top_right.setTextureOffset(16, 0).addBox(-0.0174F, -35.8798F, -3.5525F, 7.0F, 9.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        chitin_bot_left.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        chitin_bot_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        chitin_top_left.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        chitin_top_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}