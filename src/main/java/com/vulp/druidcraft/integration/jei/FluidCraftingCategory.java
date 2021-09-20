/*
package com.vulp.druidcraft.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.recipes.FluidCraftingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FluidCraftingCategory implements IRecipeCategory<FluidCraftingRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Druidcraft.MODID, "fluid_crafting");

    private static final int outputSlot = 9;

    public static final int width = 128;
    public static final int height = 64;

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;
    private final IGuiHelper helper;
    private final List<ITextComponent> questionMarkText;

    public FluidCraftingCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(Druidcraft.MODID, "textures/gui/jei/jei_backgrounds.png");
        this.helper = guiHelper;
        background = guiHelper.createDrawable(location, 0, 129, width, height);
        icon = guiHelper.createDrawableIngredient(new ItemStack(Items.BUCKET));
        localizedName = "container.druidcraft.fluid_crafting";
        questionMarkText = Collections.singletonList(new TranslationTextComponent("container.druidcraft.fluid_crafting.description"));
    }

    @Override
    public void draw(FluidCraftingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        IDrawable liquid = helper.createDrawableIngredient(new FluidStack(recipe.getFluid(), 1000));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                liquid.draw(matrixStack, i * 16, j * 16);
            }
        }
        background.draw(matrixStack);
    }

    @Override
    public List<ITextComponent> getTooltipStrings(FluidCraftingRecipe recipe, double mouseX, double mouseY) {
        if (mouseX > 111 && mouseX < 122) {
            if (mouseY > 4 && mouseY < 16) {
                return questionMarkText;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends FluidCraftingRecipe> getRecipeClass() {
        return FluidCraftingRecipe.class;
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
    public void setIngredients(FluidCraftingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(Collections.singletonList(new FluidStack(recipe.getFluid(), 1000))));
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidCraftingRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup guiFluidStacks1 = recipeLayout.getFluidStacks();
        IGuiFluidStackGroup guiFluidStacks2 = recipeLayout.getFluidStacks();
        guiFluidStacks1.init(9, true, 63, 4, 45, 13, 1000, false, null);
        guiFluidStacks2.init(10, true, 63, 47, 60, 13, 1000, false, null);
        guiFluidStacks1.set(9, new FluidStack(recipe.getFluid(), 1000));
        guiFluidStacks2.set(10, new FluidStack(recipe.getFluid(), 1000));
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        int width = recipe.getWidth();
        int height = recipe.getHeight();
        int i = -1;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if (j > height && k > width) {
                    guiItemStacks.init(i, false, 5 + (k * 18), 5 + (j * 18));
                } else {
                    i++;
                    guiItemStacks.init(i, true, 5 + (k * 18), 5 + (j * 18));
                }
            }
        }
        guiItemStacks.init(outputSlot, false, 101, 23);
        guiItemStacks.set(ingredients);
    }

}*/
