package com.vulp.druidcraft.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;

public class RecipeSerializers {
    public static final IRecipeSerializer<WoodcuttingRecipe> woodcutting = new SingleItemRecipe.Serializer<WoodcuttingRecipe>(WoodcuttingRecipe::new) {};
    public static final IRecipeSerializer<HellkilnRecipe> hellkiln_smelting = new HellkilnRecipe.Serializer() {};
    public static final IRecipeSerializer<InfernalLanternFuellingRecipe> infernal_lantern_fuelling = new InfernalLanternFuellingRecipe.Serializer() {};
    public static final IRecipeSerializer<AdvancedSmithingRecipe> advanced_smithing = new AdvancedSmithingRecipe.Serializer() {};
}
