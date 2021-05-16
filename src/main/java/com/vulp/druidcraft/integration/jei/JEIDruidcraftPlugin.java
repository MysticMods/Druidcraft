package com.vulp.druidcraft.integration.jei;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.gui.screen.inventory.SmithingWorkbenchScreen;
import com.vulp.druidcraft.recipes.AdvancedSmithingRecipe;
import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.recipes.WoodcuttingRecipe;
import com.vulp.druidcraft.registry.BlockRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.SmithingTableScreen;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.*;

@JeiPlugin
public class JEIDruidcraftPlugin implements IModPlugin {

    private static final ResourceLocation UID = new ResourceLocation(Druidcraft.MODID, "main");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new WoodcutterRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new AdvancedSmithingCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        World world = Minecraft.getInstance().world;
        registration.addRecipes(IModdedRecipeType.getRecipes(world, IModdedRecipeType.woodcutting).values(), WoodcutterRecipeCategory.UID);
        registration.addRecipes(updateAdvSmithingList(IModdedRecipeType.getRecipes(world, IModdedRecipeType.advanced_smithing).values(), IModdedRecipeType.getRecipes(world, IRecipeType.SMITHING).values()), AdvancedSmithingCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.woodcutter), WoodcutterRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.smithing_workbench), AdvancedSmithingCategory.UID);
    }

    public <C extends IInventory, T extends IRecipe<C>> List<T> wipeDuplicates(Collection<T> collection) {
        HashSet<T> set = new HashSet<>(collection);
        List<T> list = new ArrayList<>(Collections.emptyList());
        list.addAll(set);
        return list;
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SmithingWorkbenchScreen.class, 101, 41, 22, 15, AdvancedSmithingCategory.UID);
    }


    private <C extends IInventory, T extends IRecipe<C>> List<AdvancedSmithingRecipe> updateAdvSmithingList(Collection<T> advRecList, Collection<T> recList) {
        List<AdvancedSmithingRecipe> finalList = new ArrayList<>(Collections.emptyList());
        finalList.addAll((Collection<? extends AdvancedSmithingRecipe>) advRecList);
        for (SmithingRecipe smithingRecipe : ((Collection<? extends SmithingRecipe>) recList)) {
            NonNullList<Ingredient> ingredientList = NonNullList.create();
            ingredientList.add(smithingRecipe.addition);
            finalList.add(new AdvancedSmithingRecipe(smithingRecipe.getId(), smithingRecipe.base, ingredientList, smithingRecipe.getRecipeOutput()));
        }
        return finalList;
    }

}
