/*
package com.vulp.druidcraft.recipes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.inventory.container.FluidCraftingTableContainer;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class FluidCraftingRecipe extends SpecialRecipe {
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;

    private final int recipeWidth;
    private final int recipeHeight;
    private final Fluid fluid;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;

    private static final Field eventHandlerField = ObfuscationReflectionHelper.findField(CraftingInventory.class, "field_70465_c");
    private static final Field containerPlayerPlayerField = ObfuscationReflectionHelper.findField(PlayerContainer.class, "field_82862_h");
    private static final Field slotCraftingPlayerField = ObfuscationReflectionHelper.findField(CraftingResultSlot.class, "field_75238_b");

    public FluidCraftingRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, Fluid fluidIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn) {
        super(idIn);
        this.id = idIn;
        this.group = groupIn;
        this.recipeWidth = recipeWidthIn;
        this.recipeHeight = recipeHeightIn;
        this.fluid = fluidIn;
        this.recipeItems = recipeItemsIn;
        this.recipeOutput = recipeOutputIn;
    }

    @Nullable
    private static PlayerEntity findPlayer(CraftingInventory inventory) {
        try {
            Container container = (Container) eventHandlerField.get(inventory);
            if (container instanceof PlayerContainer) {
                return (PlayerEntity) containerPlayerPlayerField.get(container);
            } else if (container instanceof WorkbenchContainer || container instanceof FluidCraftingTableContainer) {
                return (PlayerEntity) slotCraftingPlayerField.get(container.getSlot(0));
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializers.fluid_crafting;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public Fluid getFluid() {
        return this.fluid;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= this.recipeWidth && height >= this.recipeHeight;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        for(int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i) {
            for(int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j) {
                if (this.checkMatch(inv, i, j, true)) {
                    return true;
                }

                if (this.checkMatch(inv, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkMatch(CraftingInventory craftingInventory, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
        for (int i = 0; i < craftingInventory.getWidth(); ++i) {
            for (int j = 0; j < craftingInventory.getHeight(); ++j) {
                int k = i - p_77573_2_;
                int l = j - p_77573_3_;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
                    if (p_77573_4_) {
                        ingredient = this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
                    } else {
                        ingredient = this.recipeItems.get(k + l * this.recipeWidth);
                    }
                }

                if (!ingredient.test(craftingInventory.getStackInSlot(i + j * craftingInventory.getWidth()))) {
                    return false;
                }
            }
        }

        PlayerEntity player = findPlayer(craftingInventory);
        if (player != null) {
            FluidCraftingTableContainer container = checkFluidCraftingTable(craftingInventory);
            if (container != null) {
                return container.getFluid() == this.fluid;
            } else if (this.fluid == Fluids.WATER || this.fluid == Fluids.FLOWING_WATER) {
                return player.isInWater();
            } else return player.getBlockState().getBlock() == this.fluid.getDefaultState().getBlockState().getBlock();
        } else return false;
    }

    private FluidCraftingTableContainer checkFluidCraftingTable(CraftingInventory inv) {
        try {
            Container container = (Container) eventHandlerField.get(inv);
            if (container instanceof FluidCraftingTableContainer) {
                return (FluidCraftingTableContainer) container;
            } else return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getWidth() {
        return this.recipeWidth;
    }

    public int getHeight() {
        return this.recipeHeight;
    }

    private static NonNullList<Ingredient> deserializeIngredients(String[] pattern, Map<String, Ingredient> keys, int patternWidth, int patternHeight) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(patternWidth * patternHeight, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(keys.keySet());
        set.remove(" ");

        for(int i = 0; i < pattern.length; ++i) {
            for(int j = 0; j < pattern[i].length(); ++j) {
                String s = pattern[i].substring(j, j + 1);
                Ingredient ingredient = keys.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + patternWidth * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    @VisibleForTesting
    static String[] shrink(String... toShrink) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int i1 = 0; i1 < toShrink.length; ++i1) {
            String s = toShrink[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (toShrink.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[toShrink.length - l - k];

            for(int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = toShrink[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    private static int firstNonSpace(String str) {
        int i;
        for(i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
            ;
        }

        return i;
    }

    private static int lastNonSpace(String str) {
        int i;
        for(i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
            ;
        }

        return i;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return this.getRecipeOutput().copy();
    }

    private static String[] patternFromJson(JsonArray jsonArr) {
        String[] astring = new String[jsonArr.size()];
        if (astring.length > MAX_HEIGHT) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < astring.length; ++i) {
                String s = JSONUtils.getString(jsonArr.get(i), "pattern[" + i + "]");
                if (s.length() > MAX_WIDTH) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    private static Map<String, Ingredient> deserializeKey(JsonObject json) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.deserialize(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static ItemStack deserializeItem(JsonObject p_199798_0_) {
        String s = JSONUtils.getString(p_199798_0_, "item");
        if (p_199798_0_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getInt(p_199798_0_, "count", 1);
            return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(p_199798_0_, true);
        }
    }

    public static Fluid deserializeFluid(JsonObject object) {
        String s = JSONUtils.getString(object, "fluid");
        return Registry.FLUID.getOptional(new ResourceLocation(s)).orElseThrow(() -> new JsonSyntaxException("Unknown fluid '" + s + "'"));
    }

    public static Fluid readFluid(PacketBuffer buffer) {
        if (!buffer.readBoolean()) {
            return Fluids.EMPTY;
        } else {
            int i = buffer.readVarInt();
            return Registry.FLUID.getByValue(i);
        }
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<FluidCraftingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation(Druidcraft.MODID, "water_crafting");
        @Override
        public FluidCraftingRecipe read(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getString(json, "group", "");
            Fluid fluid = deserializeFluid(JSONUtils.getJsonObject(json, "fluid_ingredient"));
            Map<String, Ingredient> map = FluidCraftingRecipe.deserializeKey(JSONUtils.getJsonObject(json, "key"));
            String[] astring = FluidCraftingRecipe.shrink(FluidCraftingRecipe.patternFromJson(JSONUtils.getJsonArray(json, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            NonNullList<Ingredient> nonnulllist = FluidCraftingRecipe.deserializeIngredients(astring, map, i, j);
            ItemStack itemstack = FluidCraftingRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new FluidCraftingRecipe(recipeId, s, i, j, fluid, nonnulllist, itemstack);
        }

        @Override
        public FluidCraftingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int i = buffer.readVarInt();
            int j = buffer.readVarInt();
            Fluid fluid = readFluid(buffer);
            String s = buffer.readString(32767);
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);
            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.read(buffer));
            }
            ItemStack itemstack = buffer.readItemStack();
            return new FluidCraftingRecipe(recipeId, s, i, j, fluid, nonnulllist, itemstack);
        }

        @Override
        public void write(PacketBuffer buffer, FluidCraftingRecipe recipe) {
            buffer.writeVarInt(recipe.recipeWidth);
            buffer.writeVarInt(recipe.recipeHeight);
            buffer.writeString(recipe.group);
            buffer.writeVarInt(Registry.FLUID.getId(recipe.fluid));
            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.write(buffer);
            }
            buffer.writeItemStack(recipe.recipeOutput);
        }
    }

}*/
