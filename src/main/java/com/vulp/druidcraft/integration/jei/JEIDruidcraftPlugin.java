package com.vulp.druidcraft.integration.jei;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.WoodcutterContainer;
import com.vulp.druidcraft.recipes.RecipeSerializers;
import com.vulp.druidcraft.recipes.WoodcuttingRecipe;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.RecipeRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.RecipeMatcher;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIDruidcraftPlugin implements IModPlugin {

    private static final ResourceLocation UID = new ResourceLocation(Druidcraft.MODID, "main");
    public static final List<WoodcuttingRecipe> WOODCUTTER_LIST = new ArrayList<>();

    @Override
    public ResourceLocation getPluginUid() {
        return null;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new WoodcutterRecipeCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(WOODCUTTER_LIST, WoodcutterRecipeCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.woodcutter), WoodcutterRecipeCategory.UID);
    }

}
