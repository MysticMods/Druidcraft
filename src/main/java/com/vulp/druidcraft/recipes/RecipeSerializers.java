package com.vulp.druidcraft.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SingleItemRecipe;

public class RecipeSerializers {
    public static final IRecipeSerializer<WoodcuttingRecipe> woodcutting = new SingleItemRecipe.Serializer<WoodcuttingRecipe>(WoodcuttingRecipe::new) {};
    public static final IRecipeSerializer<HellkilnRecipe> hellkiln_smelting = new HellkilnRecipe.Serializer() {};
}
