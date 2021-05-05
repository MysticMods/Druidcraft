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
    bone("bone", 15, new int[] {1, 4, 5, 2}, 18, Items.BONE, "item.armor.equip.gold", 0.0f, 0.0F),
    chitin("chitin", 24, new int[] {3, 5, 7, 3}, 12, ItemRegistry.chitin, "item.armor.equip.leather", 1.0f, 0.0F),
    moonstone("moonstone", 40, new int[] {3, 7, 9, 4}, 15, ItemRegistry.moonstone, "item.armor.equip.diamond", 3.0f, 0.0F),
    fiery("fiery", 35, new int[] {3, 6, 8, 3}, 20, ItemRegistry.tempered_fiery_glass, "item.armor.equip.diamond", 2.0f, 0.0F);


    private static final int[] max_damage_array = new int[]{13, 15, 16, 11};
    private String name, equipSound;
    private int durability, enchantability;
    private Item repairItem;
    private int[] damageReductionAmount;
    private float toughness;
    private float knockbackResistance;

    ArmorMaterialRegistry(String name, int durability, int[] damageReductionAmount, int enchantability, Item repairItem, String equipSound, float toughness, float knockbackResistance)
    {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItem = repairItem;
        this.damageReductionAmount = damageReductionAmount;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDurability(EquipmentSlotType slot) {
        return max_damage_array[slot.getIndex()] * durability;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slot) {
        return damageReductionAmount[slot.getIndex()];
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

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
