/*
package com.vulp.druidcraft.recipes;

import com.google.gson.JsonObject;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Collections;

public class MortarAndPestleRecipe implements IRecipe<IInventory> {

    private final Ingredient ingredient;
    private final ItemStack result;
    private final ResourceLocation recipeId;

    public MortarAndPestleRecipe(ResourceLocation recipeId, Ingredient ingredient, ItemStack result) {
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.result = result;
    }

    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    public boolean canFit(int width, int height) {
        return width * height >= 1;
    }

    public ItemStack getRecipeOutput() {
        return this.result;
    }

    public ItemStack getIcon() {
        return new ItemStack(BlockRegistry.mortar_and_pestle);
    }

    public ResourceLocation getId() {
        return this.recipeId;
    }

    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializers.mortar_and_pestle;
    }

    public IRecipeType<?> getType() {
        return IModdedRecipeType.mortar_and_pestle;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MortarAndPestleRecipe> {
        public MortarAndPestleRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new MortarAndPestleRecipe(recipeId, ingredient, itemstack);
        }

        public MortarAndPestleRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.read(buffer);
            ItemStack itemstack = buffer.readItemStack();
            return new MortarAndPestleRecipe(recipeId, ingredient, itemstack);
        }

        public void write(PacketBuffer buffer, MortarAndPestleRecipe recipe) {
            recipe.ingredient.write(buffer);
            buffer.writeItemStack(recipe.result);
        }

    }

}
*/
