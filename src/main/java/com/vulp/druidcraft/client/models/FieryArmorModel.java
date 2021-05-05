package com.vulp.druidcraft.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.vulp.druidcraft.Druidcraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class FieryArmorModel<T extends LivingEntity> extends BipedModel<T> {

    public final ModelRenderer armorset;
    public final ModelRenderer helmet;
    public final ModelRenderer chestplate;
    public final ModelRenderer rightarm;
    public final ModelRenderer leftarm;
    public final ModelRenderer leggings;
    public final ModelRenderer rightleg;
    public final ModelRenderer leftleg;
    public final ModelRenderer boots;
    public final ModelRenderer leftboot;
    public final ModelRenderer rightboot;

    public FieryArmorModel() {
        super(0.0F);
        textureWidth = 64;
        textureHeight = 64;

        armorset = new ModelRenderer(this);
        armorset.setRotationPoint(0.0F, 24.0F, 0.0F);


        helmet = new ModelRenderer(this);
        helmet.setRotationPoint(0.0F, -24.0F, 0.0F);
        armorset.addChild(helmet);
        helmet.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.75F, false);

        ModelRenderer horns = new ModelRenderer(this);
        horns.setRotationPoint(5.5F, -7.0F, -0.5F);
        helmet.addChild(horns);
        setRotationAngle(horns, 0.3927F, 0.0F, 0.0F);
        horns.setTextureOffset(0, 0).addBox(-1.5F, -4.0F, -0.5F, 2.0F, 4.0F, 2.0F, 0.25F, true);
        horns.setTextureOffset(0, 0).addBox(-11.5F, -4.0F, -0.5F, 2.0F, 4.0F, 2.0F, 0.25F, false);

        chestplate = new ModelRenderer(this);
        chestplate.setRotationPoint(0.0F, -18.0F, 0.0F);
        armorset.addChild(chestplate);
        chestplate.setTextureOffset(16, 16).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, 1.0F, false);

        rightarm = new ModelRenderer(this);
        rightarm.setRotationPoint(-4.0F, -4.0F, 0.0F);
        chestplate.addChild(rightarm);
        rightarm.setTextureOffset(32, 5).addBox(-6.0F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F, 0.0F, false);
        rightarm.setTextureOffset(40, 16).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.5F, false);

        leftarm = new ModelRenderer(this);
        leftarm.setRotationPoint(4.0F, -4.0F, 0.0F);
        chestplate.addChild(leftarm);
        leftarm.setTextureOffset(32, 5).addBox(1.0F, -3.0F, -3.0F, 5.0F, 5.0F, 6.0F, 0.0F, true);
        leftarm.setTextureOffset(40, 16).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.5F, true);

        leggings = new ModelRenderer(this);
        leggings.setRotationPoint(0.0F, -13.0F, 0.0F);
        armorset.addChild(leggings);
        leggings.setTextureOffset(16, 48).addBox(-4.0F, -11.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.6F, false);

        rightleg = new ModelRenderer(this);
        rightleg.setRotationPoint(-2.0F, 0.0F, 0.0F);
        leggings.addChild(rightleg);
        rightleg.setTextureOffset(0, 48).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.5F, false);
        rightleg.setTextureOffset(40, 48).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.9F, false);

        leftleg = new ModelRenderer(this);
        leftleg.setRotationPoint(2.0F, 0.0F, 0.0F);
        leggings.addChild(leftleg);
        leftleg.setTextureOffset(0, 48).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.5F, true);
        leftleg.setTextureOffset(40, 48).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.9F, true);

        boots = new ModelRenderer(this);
        boots.setRotationPoint(0.0F, -13.0F, 0.0F);
        armorset.addChild(boots);


        leftboot = new ModelRenderer(this);
        leftboot.setRotationPoint(2.0F, 0.0F, 0.0F);
        boots.addChild(leftboot);
        leftboot.setTextureOffset(0, 16).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.9F, true);

        rightboot = new ModelRenderer(this);
        rightboot.setRotationPoint(-2.0F, 0.0F, 0.0F);
        boots.addChild(rightboot);
        rightboot.setTextureOffset(0, 16).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.9F, false);

    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        setRotationAngle(this.helmet, this.bipedHead.rotateAngleX, this.bipedHead.rotateAngleY, this.bipedHead.rotateAngleZ);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        this.helmet.showModel = this.bipedHead.showModel;
        this.chestplate.showModel = this.bipedBody.showModel;
        this.leggings.showModel = this.bipedLeftLeg.showModel;
        this.boots.showModel = this.bipedRightLeg.showModel;

        armorset.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setVisible(boolean visible) {
        this.helmet.showModel = visible;
        this.chestplate.showModel = visible;
        this.leggings.showModel = visible;
        this.boots.showModel = visible;

    }

}