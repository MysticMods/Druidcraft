package com.vulp.druidcraft.recipes;

import com.google.gson.JsonObject;
import com.vulp.druidcraft.blocks.Hellkiln;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class HellkilnRecipe implements IRecipe<IInventory> {
    protected final ResourceLocation id;
    protected final Ingredient ingredient1;
    protected final Ingredient ingredient2;
    protected final ItemStack result;

    public HellkilnRecipe(ResourceLocation idIn, Ingredient ingredient1, Ingredient ingredient2, ItemStack resultIn) {
        this.id = idIn;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.result = resultIn;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return (this.ingredient1.test(inv.getStackInSlot(0)) && this.ingredient2.test(inv.getStackInSlot(1))) || (this.ingredient1.test(inv.getStackInSlot(1)) && this.ingredient2.test(inv.getStackInSlot(0)));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    private NonNullList<Ingredient> ingredients = null;

    public NonNullList<Ingredient> getIngredients() {
        if (ingredients == null) {
            ingredients = NonNullList.create();
            ingredients.add(this.ingredient1);
            ingredients.add(this.ingredient2);
        }
        return ingredients;
    }

    public boolean isIngredient (ItemStack stack) {
        return this.ingredient1.test(stack) || this.ingredient2.test(stack);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializers.hellkiln_smelting;
    }

    @Override
    public IRecipeType<?> getType() {
        return IModdedRecipeType.hellkiln_smelting;
    }

    public ItemStack getIcon() {
        return new ItemStack(BlockRegistry.fiery_glass_block);
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<HellkilnRecipe> {
        @Override
        public HellkilnRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient1"));
            Ingredient ingredient1 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient2"));
            ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new HellkilnRecipe(recipeId, ingredient, ingredient1, itemstack);
        }

        @Override
        public HellkilnRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient1 = Ingredient.read(buffer);
            Ingredient ingredient2 = Ingredient.read(buffer);
            ItemStack itemstack = buffer.readItemStack();
            return new HellkilnRecipe(recipeId, ingredient1, ingredient2, itemstack);
        }

        @Override
        public void write(PacketBuffer buffer, HellkilnRecipe recipe) {
            recipe.ingredient1.write(buffer);
            recipe.ingredient2.write(buffer);
            buffer.writeItemStack(recipe.result);
        }
    }

}
