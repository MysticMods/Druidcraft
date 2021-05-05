package com.vulp.druidcraft.client.renders.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import javax.annotation.Nullable;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.FieryArmorModel;
import com.vulp.druidcraft.items.FieryArmorItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FieryArmorLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation ARMOR_TEXTURE = new ResourceLocation(Druidcraft.MODID, "textures/models/armor/fiery_layer.png");
    private final A armorModel;

    public FieryArmorLayer(IEntityRenderer<T, M> entityRenderer, A armorModel) {
        super(entityRenderer);
        this.armorModel = armorModel;
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.renderArmorPart(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.CHEST, packedLightIn, this.armorModel);
        this.renderArmorPart(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.LEGS, packedLightIn, this.armorModel);
        this.renderArmorPart(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.FEET, packedLightIn, this.armorModel);
        this.renderArmorPart(matrixStackIn, bufferIn, entitylivingbaseIn, EquipmentSlotType.HEAD, packedLightIn, this.armorModel);
    }

    private void renderArmorPart(MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, T livingEntity, EquipmentSlotType slotType, int packedLight, A armorModel) {
        ItemStack itemstack = livingEntity.getItemStackFromSlot(slotType);
        if (itemstack.getItem() instanceof FieryArmorItem) {
            FieryArmorItem armoritem = (FieryArmorItem)itemstack.getItem();
            if (armoritem.getEquipmentSlot() == slotType) {
                this.getEntityModel().setModelAttributes(armorModel);
                this.setModelSlotVisible(armorModel, slotType);
                boolean flag = itemstack.hasEffect();
                IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(renderTypeBuffer, RenderType.getArmorCutoutNoCull(ARMOR_TEXTURE), false, flag);
                armorModel.render(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    protected void setModelSlotVisible(A modelIn, EquipmentSlotType slotIn) {
        modelIn.setVisible(false);
        switch(slotIn) {
            case HEAD:
                modelIn.bipedHead.showModel = true;
                break;
            case CHEST:
                modelIn.bipedBody.showModel = true;
                break;
            case LEGS:
                modelIn.bipedLeftLeg.showModel = true;
                break;
            case FEET:
                modelIn.bipedRightLeg.showModel = true;
        }
    }

}
