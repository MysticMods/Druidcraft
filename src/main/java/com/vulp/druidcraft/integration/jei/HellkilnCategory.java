package com.vulp.druidcraft.integration.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.recipes.HellkilnRecipe;
import com.vulp.druidcraft.registry.BlockRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.List;

public class HellkilnCategory implements IRecipeCategory<HellkilnRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Druidcraft.MODID, "hellkilning");

    private static final List<Integer> inputSlot = Arrays.asList(0, 1);
    private static final int outputSlot = 2;
    private final List<Integer> cookTimes;
    private int igniters;
    private int timer;

    public static final int width = 68;
    public static final int height = 43;

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;

    private final LoadingCache<Integer, IDrawableAnimated> cachedFlame;
    private final LoadingCache<Integer, IDrawableStatic> cachedIgniters;

    public HellkilnCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(Druidcraft.MODID, "textures/gui/jei/jei_backgrounds.png");
        this.background = guiHelper.createDrawable(location, 0, 86, width, height);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BlockRegistry.hellkiln));
        this.localizedName = "container.druidcraft.hellkiln";
        // TODO: Make below values configurable.
        this.cookTimes = Arrays.asList(2400, 1200, 800);
        this.igniters = 0;
        this.timer = 0;
        this.cachedFlame = CacheBuilder.newBuilder().maximumSize(25L).build(new CacheLoader<Integer, IDrawableAnimated>() {
            public IDrawableAnimated load(Integer cookTime) {
                return guiHelper.drawableBuilder(location, 68, 86, 16, 23).buildAnimated(cookTime, IDrawableAnimated.StartDirection.BOTTOM, false);
            }
        });
        this.cachedIgniters = CacheBuilder.newBuilder().maximumSize(25L).build(new CacheLoader<Integer, IDrawableStatic>() {
            public IDrawableStatic load(Integer count) {
                return guiHelper.drawableBuilder(location, 68, 109, getPixelsFromCount(count), 17).build();
            }
            public int getPixelsFromCount(int count) {
                if (count == 0) {
                    return 5;
                } else if (count == 1) {
                    return 11;
                } else {
                    return 16;
                }
            }
        });
    }

    protected IDrawableAnimated getFlame() {
        return this.cachedFlame.getUnchecked(200);
    }

    protected IDrawableStatic getIgniters() {
        return this.cachedIgniters.getUnchecked(this.igniters);
    }

    protected void drawCookTime(MatrixStack matrixStack) {
        int cookTime = this.cookTimes.get(this.igniters);
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            FontRenderer fontRenderer = minecraft.fontRenderer;
            int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
            fontRenderer.func_243248_b(matrixStack, timeString, (float)(this.background.getWidth() - stringWidth), 34.0F, -8355712);
        }
    }

    public void draw(HellkilnRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        this.getFlame().draw(matrixStack, 23, 1);
        this.getIgniters().draw(matrixStack, 24, 26);
        this.drawCookTime(matrixStack);
        if (this.timer >= 200) {
            if (this.igniters > 1) {
                this.igniters = 0;
            } else {
                this.igniters++;
            }
            this.timer = 0;
        } else {
            this.timer++;
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends HellkilnRecipe> getRecipeClass() {
        return HellkilnRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.format(this.localizedName);
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(HellkilnRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, HellkilnRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(inputSlot.get(0), true, 0, 2);
        guiItemStacks.init(inputSlot.get(1), true, 0, 23);
        guiItemStacks.init(outputSlot, false, 48, 12);
        guiItemStacks.set(ingredients);
    }

}