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
public class BoneShieldModel extends Model {
    private final ModelRenderer cage_right;
    private final ModelRenderer bone2;
    private final ModelRenderer main;
    private final ModelRenderer bone;
    private final ModelRenderer cage_left;
    private final ModelRenderer bone3;

    public BoneShieldModel() {
        super(RenderType::getEntitySolid);
        textureWidth = 64;
        textureHeight = 64;

        cage_right = new ModelRenderer(this);
        cage_right.setRotationPoint(0.0F, 24.0F, -2.0F);
        setRotationAngle(cage_right, 0.0F, -0.1745F, 0.0F);
        cage_right.setTextureOffset(0, 0).addBox(0.0F, -31.0F, 0.0F, 6.0F, 10.0F, 1.0F, 0.0F, false);



        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(0.0F, 2.0F, 0.0F);
        cage_right.addChild(bone2);
        setRotationAngle(bone2, 0.1745F, 0.0F, 0.0F);
        bone2.setTextureOffset(0, 11).addBox(0.0F, -22.6506F, 3.9939F, 4.0F, 4.0F, 1.0F, 0.0F, false);

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 24.0F, 0.0F);
        main.setTextureOffset(20, 0).addBox(-1.0F, -27.0F, -1.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
        main.setTextureOffset(14, 0).addBox(-1.0F, -31.0F, -2.7F, 2.0F, 10.0F, 1.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 2.0F, -2.7F);
        main.addChild(bone);
        setRotationAngle(bone, 0.1745F, 0.0F, 0.0F);
        bone.setTextureOffset(14, 10).addBox(-1.0F, -22.6506F, 3.9939F, 2.0F, 6.0F, 1.0F, 0.0F, false);

        cage_left = new ModelRenderer(this);
        cage_left.setRotationPoint(0.0F, 24.0F, -2.0F);
        setRotationAngle(cage_left, 0.0F, 0.1745F, 0.0F);
        cage_left.setTextureOffset(0, 16).addBox(-6.0F, -31.0F, 0.0F, 6.0F, 10.0F, 1.0F, 0.0F, true);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, 2.0F, 0.0F);
        cage_left.addChild(bone3);
        setRotationAngle(bone3, 0.1745F, 0.0F, 0.0F);
        bone3.setTextureOffset(0, 11).addBox(-4.0F, -22.6506F, 3.9939F, 4.0F, 4.0F, 1.0F, 0.0F, true);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        cage_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        cage_left.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}