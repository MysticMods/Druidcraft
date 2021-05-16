package com.vulp.druidcraft.client.models;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FieryArmorModel extends BipedModel {

    public FieryArmorModel(float scale) {
        super(scale, 0.0F, 64, 64);

        textureWidth = 64;
        textureHeight = 64;

        ModelRenderer rightFauld = new ModelRenderer(this);
        rightFauld.setRotationPoint(-2.0F, 10.0F, 0.0F);
        rightFauld.setTextureOffset(22, 32).addBox(0.0F, -10.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.85F, false);

        ModelRenderer leftFauld = new ModelRenderer(this);
        leftFauld.setRotationPoint(2.0F, 10.0F, 0.0F);
        leftFauld.setTextureOffset(22, 32).addBox(-4.0F, -10.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.85F, true);

        ModelRenderer rightPauldron = new ModelRenderer(this);
        rightPauldron.setRotationPoint(2.0F, 2.0F, 0.0F);
        rightPauldron.setTextureOffset(0, 32).addBox(-6.0F, -5.0F, -3.0F, 5.0F, 5.0F, 6.0F, 1.0F, false);

        ModelRenderer leftPauldron = new ModelRenderer(this);
        leftPauldron.setRotationPoint(-2.0F, 2.0F, 0.0F);
        leftPauldron.setTextureOffset(0, 32).addBox(1.0F, -5.0F, -3.0F, 5.0F, 5.0F, 6.0F, 1.0F, true);

        ModelRenderer horns = new ModelRenderer(this);
        horns.setRotationPoint(5.5F, -7.0F, -0.5F);
        setRotationAngle(horns, 0.3927F, 0.0F, 0.0F);
        horns.setTextureOffset(0, 0).addBox(-1.5F, -4.0F, -0.5F, 2.0F, 4.0F, 2.0F, 0.25F, true);
        horns.setTextureOffset(0, 0).addBox(-11.5F, -4.0F, -0.5F, 2.0F, 4.0F, 2.0F, 0.25F, false);

        // Sync up body and armor animations.
        this.bipedHead.addChild(horns);
        this.bipedRightArm.addChild(rightPauldron);
        this.bipedLeftArm.addChild(leftPauldron);
        this.bipedRightLeg.addChild(rightFauld);
        this.bipedLeftLeg.addChild(leftFauld);

    }

    @Override
    public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn instanceof ArmorStandEntity) {
            ArmorStandEntity entityarmorstand = (ArmorStandEntity) entityIn;
            this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            this.bipedHeadwear.copyModelAngles(this.bipedHead);
        } else {
            super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }

    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}