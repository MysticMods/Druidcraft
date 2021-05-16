package com.vulp.druidcraft.integration.jei;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.recipes.AdvancedSmithingRecipe;
import com.vulp.druidcraft.recipes.WoodcuttingRecipe;
import com.vulp.druidcraft.registry.BlockRegistry;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Blocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class AdvancedSmithingCategory implements IRecipeCategory<AdvancedSmithingRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Druidcraft.MODID, "advanced_smithing");

    private static final List<Integer> inputSlot = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
    private static final int outputSlot = 7;

    public static final int width = 126;
    public static final int height = 64;

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;

    public AdvancedSmithingCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(Druidcraft.MODID, "textures/gui/jei/jei_backgrounds.png");
        background = guiHelper.createDrawable(location, 0, 0, width, height);
        icon = guiHelper.createDrawableIngredient(new ItemStack(BlockRegistry.smithing_workbench));
        localizedName = "container.druidcraft.advanced_smithing";
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends AdvancedSmithingRecipe> getRecipeClass() {
        return AdvancedSmithingRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.format(localizedName);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(AdvancedSmithingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AdvancedSmithingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(inputSlot.get(0), true, 23, 23);
        guiItemStacks.init(inputSlot.get(1), true, 23, 0);
        guiItemStacks.init(inputSlot.get(2), true, 46, 11);
        guiItemStacks.init(inputSlot.get(3), true, 46, 35);
        guiItemStacks.init(inputSlot.get(4), true, 23, 46);
        guiItemStacks.init(inputSlot.get(5), true, 0, 35);
        guiItemStacks.init(inputSlot.get(6), true, 0, 11);
        guiItemStacks.init(outputSlot, false, 106, 23);
        guiItemStacks.set(ingredients);
    }

}