package com.vulp.druidcraft.items;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.models.FieryArmorModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class FieryArmorItem extends ArmorItem {

    public FieryArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties) {
        super(armorMaterial, slotType, properties);
    }

    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) (armorSlot == EquipmentSlotType.LEGS ? new FieryArmorModel(0.5F) : new FieryArmorModel(1.0F));
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return Druidcraft.MODID + ":textures/models/armor/" + ( slot == EquipmentSlotType.LEGS ? "fiery_layer_2" : "fiery_layer_1") + ".png";
    }

}
