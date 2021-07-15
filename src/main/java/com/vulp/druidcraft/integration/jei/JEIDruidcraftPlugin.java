package com.vulp.druidcraft.integration.jei;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.client.gui.screen.inventory.FluidCraftingTableScreen;
import com.vulp.druidcraft.client.gui.screen.inventory.HellkilnScreen;
import com.vulp.druidcraft.client.gui.screen.inventory.MortarAndPestleScreen;
import com.vulp.druidcraft.client.gui.screen.inventory.SmithingWorkbenchScreen;
import com.vulp.druidcraft.inventory.container.FluidCraftingTableContainer;
import com.vulp.druidcraft.inventory.container.HellkilnContainer;
import com.vulp.druidcraft.inventory.container.MortarAndPestleContainer;
import com.vulp.druidcraft.inventory.container.SmithingWorkbenchContainer;
import com.vulp.druidcraft.recipes.AdvancedSmithingRecipe;
import com.vulp.druidcraft.recipes.FluidCraftingRecipe;
import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.registry.BlockRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.helpers.IStackHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.SmithingTableContainer;
import net.minecraft.inventory.container.WorkbenchContainer;
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
                new WoodcuttingCategory(registry.getJeiHelpers().getGuiHelper()),
                new FluidCraftingCategory(registry.getJeiHelpers().getGuiHelper()),
                new AdvancedSmithingCategory(registry.getJeiHelpers().getGuiHelper()),
                new MortarAndPestleCategory(registry.getJeiHelpers().getGuiHelper()),
                new HellkilnCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        World world = Minecraft.getInstance().world;
        registration.addRecipes(IModdedRecipeType.getRecipes(world, IModdedRecipeType.woodcutting).values(), WoodcuttingCategory.UID);
        registration.addRecipes(setupFluidCraftingRecipes(IModdedRecipeType.getRecipes(world, IRecipeType.CRAFTING).values()), FluidCraftingCategory.UID);
        registration.addRecipes(updateAdvSmithingList(IModdedRecipeType.getRecipes(world, IModdedRecipeType.advanced_smithing).values(), IModdedRecipeType.getRecipes(world, IRecipeType.SMITHING).values()), AdvancedSmithingCategory.UID);
        registration.addRecipes(IModdedRecipeType.getRecipes(world, IModdedRecipeType.mortar_and_pestle).values(), MortarAndPestleCategory.UID);
        registration.addRecipes(IModdedRecipeType.getRecipes(world, IModdedRecipeType.hellkiln_smelting).values(), HellkilnCategory.UID);
        world.getRecipeManager().getRecipes();
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(FluidCraftingTableContainer.class, FluidCraftingCategory.UID, 1, 9, 10, 36);
        registration.addRecipeTransferHandler(SmithingWorkbenchContainer.class, AdvancedSmithingCategory.UID, 0, 6, 8, 36);
        registration.addRecipeTransferHandler(HellkilnContainer.class, HellkilnCategory.UID, 0, 2, 3, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.woodcutter), WoodcuttingCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.smithing_workbench), AdvancedSmithingCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.mortar_and_pestle), MortarAndPestleCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.hellkiln), HellkilnCategory.UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SmithingWorkbenchScreen.class, 101, 41, 22, 15, AdvancedSmithingCategory.UID);
        registration.addRecipeClickArea(MortarAndPestleScreen.class, 77, 35, 23, 15, MortarAndPestleCategory.UID);
        registration.addRecipeClickArea(HellkilnScreen.class, 71, 31, 34, 31, HellkilnCategory.UID);
        registration.addRecipeClickArea(FluidCraftingTableScreen.class, 88, 32, 28, 23, FluidCraftingCategory.UID);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {

    }

    // Not needed but keeping just in case it is again.
    public <C extends IInventory, T extends IRecipe<C>> List<T> eraseDuplicates(Collection<T> collection) {
        HashSet<T> set = new HashSet<>(collection);
        List<T> list = new ArrayList<>(Collections.emptyList());
        list.addAll(set);
        return list;
    }

    private List<IRecipe<CraftingInventory>> setupFluidCraftingRecipes(Collection<IRecipe<CraftingInventory>> recList) {
        List<IRecipe<CraftingInventory>> finalList = new ArrayList<>(Collections.emptyList());
        for (IRecipe<CraftingInventory> rec : recList) {
            if (rec instanceof FluidCraftingRecipe) {
                finalList.add(rec);
            }
        }
        return finalList;
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
