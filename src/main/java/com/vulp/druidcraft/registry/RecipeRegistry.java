package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.recipes.WaterDependentRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

import java.util.function.Supplier;

public enum RecipeRegistry {
    WATER_CRAFTING(WaterDependentRecipe.Serializer::new, IRecipeType.CRAFTING),
    ;

    public static IRecipeSerializer<?> serializer;
    public Supplier<IRecipeSerializer<?>> supplier;
    public IRecipeType<? extends IRecipe<? extends IInventory>> type;

    private RecipeRegistry(Supplier<IRecipeSerializer<?>> supplier, IRecipeType<? extends IRecipe<? extends IInventory>> type) {
        this.supplier = supplier;
        this.type = type;
    }

    public static void register(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        for (RecipeRegistry recipe : RecipeRegistry.values()) {
            recipe.serializer = recipe.supplier.get();
            ResourceLocation location = new ResourceLocation(Druidcraft.MODID, recipe.name().toLowerCase());
            event.getRegistry().register(recipe.serializer.setRegistryName(location));
        }
    }
}