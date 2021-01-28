package com.vulp.druidcraft.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.items.InfernalLanternItem;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class InfernalLanternFuellingRecipe extends SpecialRecipe {

    private final Ingredient fuel;
    private final int amount;

    public InfernalLanternFuellingRecipe(ResourceLocation idIn, Ingredient fuel, int amount) {
        super(idIn);
        this.amount = amount;
        this.fuel = fuel;
    }

    public int isFuel(ItemStack stack) {
        return this.fuel.test(stack) ? this.amount : 0;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(CraftingInventory inv, World worldIn) {
        int fuel = 0;
        int lantern = 0;
        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                if (isFuel(itemstack) > 0) {
                    fuel++;
                } else if (itemstack.getItem() == ItemRegistry.infernal_lantern) {
                    lantern++;
                } else return false;
            }
        }
        if (fuel > 0 && lantern == 1) {
            return true;
        }
        return false;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(CraftingInventory inv) {
        int fuelItems = 0;
        int lantern = 0;
        int addedFuel = 0;
        ItemStack infernal_lantern = null;
        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                int fuel = isFuel(itemstack);
                if (fuel > 0) {
                    fuelItems++;
                    addedFuel += fuel;
                } else if (itemstack.getItem() == ItemRegistry.infernal_lantern) {
                    lantern++;
                    infernal_lantern = itemstack;
                } else return ItemStack.EMPTY;
            }
        }
        if (fuelItems > 0 || lantern == 1) {
            if (infernal_lantern.getItem() instanceof InfernalLanternItem) {
                return refuelLantern(infernal_lantern, addedFuel);
            }
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack refuelLantern(ItemStack stack, int amount) {
        if (stack.getItem() instanceof InfernalLanternItem) {
            CompoundNBT nbt = stack.getOrCreateTag();
            int fuel = nbt.getInt("fuel");
            nbt.putInt("fuel", fuel + amount);
            nbt.putInt("old_fuel", fuel + amount);
        }
        return stack;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializers.infernal_lantern_fuelling;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<InfernalLanternFuellingRecipe> {
        @Override
        public InfernalLanternFuellingRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient fuel = Ingredient.deserialize(JSONUtils.getJsonObject(json, "fuel"));
            int amount = JSONUtils.getInt(json, "amount");
            return new InfernalLanternFuellingRecipe(recipeId, fuel, amount);
        }

        @Override
        public InfernalLanternFuellingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient fuel = Ingredient.read(buffer);
            int amount = buffer.readInt();
            return new InfernalLanternFuellingRecipe(recipeId, fuel, amount);
        }

        @Override
        public void write(PacketBuffer buffer, InfernalLanternFuellingRecipe recipe) {
            recipe.fuel.write(buffer);
            buffer.writeInt(recipe.amount);
        }

    }

}
