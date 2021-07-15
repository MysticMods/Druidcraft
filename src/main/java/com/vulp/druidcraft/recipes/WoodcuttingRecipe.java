package com.vulp.druidcraft.recipes;

import com.vulp.druidcraft.integration.jei.JEIDruidcraftPlugin;
import com.vulp.druidcraft.registry.BlockRegistry;
import io.netty.handler.codec.http2.AbstractHttp2StreamFrame;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WoodcuttingRecipe extends SingleItemRecipe {

    public WoodcuttingRecipe(ResourceLocation p_i50021_1_, String p_i50021_2_, Ingredient p_i50021_3_, ItemStack p_i50021_4_) {
        super(IModdedRecipeType.woodcutting, RecipeSerializers.woodcutting, p_i50021_1_, p_i50021_2_, p_i50021_3_, p_i50021_4_);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

}