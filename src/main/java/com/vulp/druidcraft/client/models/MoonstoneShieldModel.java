package com.vulp.druidcraft.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonstoneShieldModel extends Model {

    private final ModelRenderer main;
    private final ModelRenderer plate_left;
    private final ModelRenderer plate_right;

    public MoonstoneShieldModel() {
        super(RenderType::getEntitySolid);
        textureWidth = 64;
        textureHeight = 64;

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 24.0F, 0.0F);
        main.setTextureOffset(30, 6).addBox(-1.0F, -27.0F, -1.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
        main.setTextureOffset(0, 0).addBox(-3.0F, -35.0F, -2.0F, 6.0F, 22.0F, 1.0F, 0.0F, false);

        plate_left = new ModelRenderer(this);
        plate_left.setRotationPoint(-3.0F, 24.0F, -2.0F);
        setRotationAngle(plate_left, 0.0F, 0.3491F, 0.0F);
        plate_left.setTextureOffset(14, 0).addBox(-3.0F, -35.0F, 0.0F, 3.0F, 22.0F, 1.0F, 0.0F, false);
        plate_left.setTextureOffset(30, 3).addBox(-4.0F, -15.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        plate_left.setTextureOffset(30, 0).addBox(-4.0F, -35.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        plate_right = new ModelRenderer(this);
        plate_right.setRotationPoint(3.0F, 24.0F, -2.0F);
        setRotationAngle(plate_right, 0.0F, -0.3491F, 0.0F);
        plate_right.setTextureOffset(22, 0).addBox(0.0F, -35.0F, 0.0F, 3.0F, 22.0F, 1.0F, 0.0F, false);
        plate_right.setTextureOffset(34, 3).addBox(3.0F, -15.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        plate_right.setTextureOffset(34, 0).addBox(3.0F, -35.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        plate_left.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        plate_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}