package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public enum ArmorMaterialRegistry implements IArmorMaterial
{
    bone("bone", 1, new int[] {195, 225, 240, 165}, 18, Items.BONE, "item.armor.equip.gold", 0.0f);

    private static final int[] max_damage_array = new int[]{1, 4, 5, 2};
    private String name, equipSound;
    private int durability, enchantability;
    private Item repairItem;
    private int[] damageReductionAmount;
    private float toughness;

    private ArmorMaterialRegistry(String name, int durability, int[] damageReductionAmount, int enchantability, Item repairItem, String equipSound, float toughness)
    {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItem = repairItem;
        this.damageReductionAmount = damageReductionAmount;
        this.toughness = toughness;
    }

    @Override
    public int getDurability(EquipmentSlotType slot) {
        return this.damageReductionAmount[slot.getIndex()];
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slot) {
        return max_damage_array[slot.getIndex()] * this.durability;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return new SoundEvent(new ResourceLocation(equipSound));
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(this.repairItem);
    }

    @Override
    public String getName() {
        return Druidcraft.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }
}
