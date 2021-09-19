/*
package com.vulp.druidcraft.integration.jei;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.recipes.MortarAndPestleRecipe;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MortarAndPestleCategory implements IRecipeCategory<MortarAndPestleRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Druidcraft.MODID, "mortar_and_pestle");

    private static final int inputSlot = 0;
    private static final int outputSlot = 1;

    public static final int width = 78;
    public static final int height = 22;

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;

    public MortarAndPestleCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(Druidcraft.MODID, "textures/gui/jei/jei_backgrounds.png");
        background = guiHelper.createDrawable(location, 0, 64, width, height);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemRegistry.mortar_and_pestle));
        localizedName = "container.druidcraft.mortar_and_pestle";
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends MortarAndPestleRecipe> getRecipeClass() {
        return MortarAndPestleRecipe.class;
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
    public void setIngredients(MortarAndPestleRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MortarAndPestleRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(inputSlot, true, 0, 2);
        guiItemStacks.init(outputSlot, false, 58, 2);
        guiItemStacks.set(ingredients);
    }

}
*/
