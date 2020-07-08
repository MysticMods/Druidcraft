package com.vulp.druidcraft.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.entities.LunarMothEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LunarMothEntityModel<T extends LunarMothEntity> extends EntityModel<T> {
    private final ModelRenderer main;
    private final ModelRenderer body;
    private final ModelRenderer antennae;
    private final ModelRenderer wing1;
    private final ModelRenderer wing2;

    public LunarMothEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 21.0F, 0.0F);

        body = new ModelRenderer(this, 0, 0);
        body.setRotationPoint(0.0F, -1.0F, 0.0F);
        main.addChild(body);
        body.addBox(-1.0F, -1.0F, -3.0F, 2, 2, 6, 0.0F, false);

        antennae = new ModelRenderer(this, 4, 8);
        antennae.setRotationPoint(0.0F, -2.0F, -3.0F);
        main.addChild(antennae);
        antennae.addBox(-2.5F, 0.0F, -3.0F, 2, 0, 3, 0.0F, false);
        antennae.addBox(0.5F, 0.0F, -3.0F, 2, 0, 3, 0.0F, true);

        wing1 = new ModelRenderer(this, 0, 8);
        wing1.setRotationPoint(-1.0F, -2.0F, 1.5F);
        main.addChild(wing1);
        wing1.addBox(-6.0F, 0.0F, -5.5F, 6, 0, 14, 0.0F, false);

        wing2 = new ModelRenderer(this, 0, 8);
        wing2.setRotationPoint(1.0F, -2.0F, 1.5F);
        main.addChild(wing2);
        wing2.addBox(0.0F, 0.0F, -5.5F, 6, 0, 14, 0.0F, true);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.main.render(matrixStack, iVertexBuilder, packedLight, packedOverlay);
    }



    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(LunarMothEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.setRotationAngle(main, -0.3491F, 0.0F, 0.0F);
        setRotationAngle(antennae, -0.6109F, 0.0F, 0.0F);
        setRotationAngle(wing1, 0.0F, 0.0F, MathHelper.cos(ageInTicks * 1.3F) * 3.1415927F * 0.25F);
        setRotationAngle(wing2, 0.0F, 0.0F, -(MathHelper.cos(ageInTicks * 1.3F) * 3.1415927F * 0.25F));
    }
}