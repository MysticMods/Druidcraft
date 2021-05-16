package com.vulp.druidcraft.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AdvancedSmithingRecipe implements IRecipe<IInventory> {

    public static final List<AdvancedSmithingRecipe> SMITHING_LIST = new ArrayList<>();
    public final Ingredient base;
    public final NonNullList<Ingredient> additions;
    private final ItemStack result;
    private final ResourceLocation recipeId;

    public AdvancedSmithingRecipe(ResourceLocation recipeId, Ingredient base, NonNullList<Ingredient> additions, ItemStack result) {
        this.recipeId = recipeId;
        this.base = base;
        this.additions = additions;
        this.result = result;
        SMITHING_LIST.add(this);
    }

    public boolean matches(IInventory inv, World worldIn) {

        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for(int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack = inv.getStackInSlot(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }

        if (this.base.test(inv.getStackInSlot(0))) {
            inputs.remove(0);
            return i == this.additions.size() + 1 && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.additions) != null;
        }
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.base);
        list.addAll(this.additions);
        return list;
    }

    public ItemStack getCraftingResult(IInventory inv) {
        ItemStack itemstack = this.result.copy();
        CompoundNBT compoundnbt = inv.getStackInSlot(0).getTag();
        if (compoundnbt != null) {
            itemstack.setTag(compoundnbt.copy());
        }

        return itemstack;
    }

    public boolean canFit(int width, int height) {
        return width * height >= 7;
    }

    public ItemStack getRecipeOutput() {
        return this.result;
    }

    public boolean isValidAdditionItem(ItemStack addition) {
        for (Ingredient ingredient : this.additions) {
            if (ingredient.test(addition)) {
                return true;
            }
        }
        return false;
    }

    public ItemStack getIcon() {
        return new ItemStack(Blocks.SMITHING_TABLE);
    }

    public ResourceLocation getId() {
        return this.recipeId;
    }

    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializers.advanced_smithing;
    }

    public IRecipeType<?> getType() {
        return IModdedRecipeType.advanced_smithing;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AdvancedSmithingRecipe> {
        public AdvancedSmithingRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.deserialize(JSONUtils.getJsonObject(json, "base"));
            NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "additions"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No additions for advanced smithing recipe");
            } else if (nonnulllist.size() > 6) {
                throw new JsonParseException("Too many additions for advanced smithing recipe the max is 6");
            } else {
                ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
                return new AdvancedSmithingRecipe(recipeId, base, nonnulllist, itemstack);
            }
        }

        public AdvancedSmithingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient base = Ingredient.read(buffer);
            int i = buffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.read(buffer));
            }

            ItemStack itemstack = buffer.readItemStack();
            return new AdvancedSmithingRecipe(recipeId, base, nonnulllist, itemstack);
        }

        public void write(PacketBuffer buffer, AdvancedSmithingRecipe recipe) {
            recipe.base.write(buffer);
            buffer.writeVarInt(recipe.additions.size());

            for(Ingredient ingredient : recipe.additions) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.result);
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.deserialize(ingredientArray.get(i));
                if (!ingredient.hasNoMatchingItems()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

    }

}
