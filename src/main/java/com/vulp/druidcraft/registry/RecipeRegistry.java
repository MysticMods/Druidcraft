package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.recipes.RecipeSerializers;
import com.vulp.druidcraft.recipes.WaterDependentRecipe;
import com.vulp.druidcraft.recipes.WoodcuttingRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

import java.util.function.Supplier;

public enum RecipeRegistry {
    water_crafting(WaterDependentRecipe.Serializer::new, IRecipeType.CRAFTING),
    ;

    public static IRecipeSerializer<?> serializer;
    public Supplier<IRecipeSerializer<?>> supplier;
    public IRecipeType<? extends IRecipe<? extends IInventory>> type;

    RecipeRegistry(Supplier<IRecipeSerializer<?>> supplier, IRecipeType<? extends IRecipe<? extends IInventory>> type) {
        this.supplier = supplier;
        this.type = type;
    }

    public static IRecipeSerializer<?> getSerializer(RecipeRegistry recipeRegistry) {
        return recipeRegistry.supplier.get();
    }

    public static void register(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        for (RecipeRegistry recipe : RecipeRegistry.values()) {
            recipe.serializer = recipe.supplier.get();
            ResourceLocation location = new ResourceLocation(Druidcraft.MODID, recipe.name().toLowerCase());
            event.getRegistry().register(recipe.serializer.setRegistryName(location));
        }
    }
}