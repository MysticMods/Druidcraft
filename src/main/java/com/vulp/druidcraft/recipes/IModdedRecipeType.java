package com.vulp.druidcraft.recipes;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Optional;

public interface IModdedRecipeType<T extends IRecipe<?>> {

    IRecipeType<WoodcuttingRecipe> woodcutting = register("woodcutting");
    IRecipeType<HellkilnRecipe> hellkiln_smelting = register("hellkiln_smelting");
    IRecipeType<InfernalLanternFuellingRecipe> infernal_lantern_fuelling = register("infernal_lantern_fuelling");
    IRecipeType<AdvancedSmithingRecipe> advanced_smithing = register("advanced_smithing");
    IRecipeType<MortarAndPestleRecipe> mortar_and_pestle = register("mortar_and_pestle");

    static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(Druidcraft.MODID, key), new IRecipeType<T>() {
            public String toString() {
                return key;
            }
        });
    }

    default <C extends IInventory> Optional<T> matches(IRecipe<C> recipe, World worldIn, C inv) {
        return recipe.matches(inv, worldIn) ? Optional.of((T)recipe) : Optional.empty();
    }

    static <C extends IInventory, T extends IRecipe<C>> Map<ResourceLocation, IRecipe<C>> getRecipes(World world, IRecipeType<T> type) {
        return world.getRecipeManager().getRecipes(type);
    }

}
