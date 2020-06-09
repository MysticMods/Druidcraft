package com.vulp.druidcraft.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BedrollTravelPackModel<T extends LivingEntity> extends BipedModel<T> {
    private final ModelRenderer pack;
    private final ModelRenderer straps;

    public BedrollTravelPackModel() {
        super(0.0F);
        textureWidth = 64;
        textureHeight = 64;

        pack = new ModelRenderer(this);
        pack.setRotationPoint(0.0F, 24.0F, 0.0F);
        pack.setTextureOffset(0, 16).addBox(-4.0F, -23.0F, 3.1F, 8, 10, 4, 0.0F, false);
        pack.setTextureOffset(24, 0).addBox(-5.5F, -27.0F, 3.1F, 11, 4, 4, 0.0F, false);
        pack.setTextureOffset(24, 16).addBox(-3.5F, -18.0F, 7.1F, 7, 4, 2, 0.0F, false);
        pack.setTextureOffset(12, 30).addBox(-6.0F, -18.0F, 3.1F, 2, 4, 4, 0.0F, true);
        pack.setTextureOffset(0, 30).addBox(4.0F, -18.0F, 3.1F, 2, 4, 4, 0.0F, false);
        pack.setTextureOffset(24, 22).addBox(-4.0F, -23.0F, 7.1F, 8, 4, 1, 0.0F, false);

        straps = new ModelRenderer(this);
        straps.setRotationPoint(0.0F, 0.0F, 0.0F);
        pack.addChild(straps);
        straps.setTextureOffset(0, 0).addBox(-4.0F, -24.0F, -2.0F, 8, 12, 4, 1.1F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.pack.render(matrixStack, iVertexBuilder, packedLight, packedOverlay);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.pack.rotateAngleX = this.isSneak ? 0.5F : 0.0F;
        this.pack.rotationPointZ = this.isSneak ? 0.7F : 0.0F;
        this.bipedBody.rotateAngleY = 0.0F;
        if (this.swingProgress > 0.0F) {
            HandSide lvt_11_1_ = this.getMainHand(entity);
            ModelRenderer lvt_12_1_ = this.getArmForSide(lvt_11_1_);
            float lvt_13_2_ = this.swingProgress;
            this.pack.rotateAngleY = MathHelper.sin(MathHelper.sqrt(lvt_13_2_) * 6.2831855F) * 0.2F;
            if (lvt_11_1_ == HandSide.LEFT) {
                ModelRenderer var10000 = this.pack;
                var10000.rotateAngleY *= -1.0F;
            }

            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.pack.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.pack.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.pack.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.pack.rotateAngleY) * 5.0F;
            ModelRenderer var10000 = this.bipedRightArm;
            var10000.rotateAngleY += this.pack.rotateAngleY;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleY += this.pack.rotateAngleY;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleX += this.pack.rotateAngleY;
            lvt_13_2_ = 1.0F - this.swingProgress;
            lvt_13_2_ *= lvt_13_2_;
            lvt_13_2_ *= lvt_13_2_;
            lvt_13_2_ = 1.0F - lvt_13_2_;
            float lvt_14_3_ = MathHelper.sin(lvt_13_2_ * 3.1415927F);
            float lvt_15_2_ = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
            lvt_12_1_.rotateAngleX = (float)((double)lvt_12_1_.rotateAngleX - ((double)lvt_14_3_ * 1.2D + (double)lvt_15_2_));
            lvt_12_1_.rotateAngleY += this.pack.rotateAngleY * 2.0F;
            lvt_12_1_.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
        }
    }

}
