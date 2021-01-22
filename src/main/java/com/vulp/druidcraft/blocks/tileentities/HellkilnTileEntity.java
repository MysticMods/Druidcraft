package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.Hellkiln;
import com.vulp.druidcraft.blocks.HellkilnIgniter;
import com.vulp.druidcraft.inventory.container.HellkilnContainer;
import com.vulp.druidcraft.recipes.HellkilnRecipe;
import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.registry.GUIRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class HellkilnTileEntity extends LockableTileEntity implements ITickableTileEntity, IRecipeHolder, IRecipeHelperPopulator {

    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int burnTime;
    private int recipesUsed;
    private int cookTime;
    private int cookTimeTotal;
    private int igniterCount;
    private int speed;
    private int igniterFuelCountLeft;
    private int igniterFuelCountBack;
    private int igniterFuelCountRight;
    private ArrayList<BlockPos> igniterList;
    protected final IIntArray hellkilnData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return HellkilnTileEntity.this.burnTime;
                case 1:
                    return HellkilnTileEntity.this.recipesUsed;
                case 2:
                    return HellkilnTileEntity.this.cookTime;
                case 3:
                    return HellkilnTileEntity.this.cookTimeTotal;
                case 4:
                    return HellkilnTileEntity.this.igniterCount;
                case 5:
                    return HellkilnTileEntity.this.igniterFuelCountLeft;
                case 6:
                    return HellkilnTileEntity.this.igniterFuelCountBack;
                case 7:
                    return HellkilnTileEntity.this.igniterFuelCountRight;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    HellkilnTileEntity.this.burnTime = value;
                    break;
                case 1:
                    HellkilnTileEntity.this.recipesUsed = value;
                    break;
                case 2:
                    HellkilnTileEntity.this.cookTime = value;
                    break;
                case 3:
                    HellkilnTileEntity.this.cookTimeTotal = value;
                    break;
                case 4:
                    HellkilnTileEntity.this.igniterCount = value;
                    break;
                case 5:
                    HellkilnTileEntity.this.igniterFuelCountLeft = value;
                    break;
                case 6:
                    HellkilnTileEntity.this.igniterFuelCountBack = value;
                    break;
                case 7:
                    HellkilnTileEntity.this.igniterFuelCountRight = value;
            }
        }

        public int size() {
            return 8;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();
    protected final IRecipeType<? extends HellkilnRecipe> recipeType;

    public HellkilnTileEntity() {
        this(TileEntityRegistry.hellkiln, IModdedRecipeType.hellkiln_smelting);
    }

    public HellkilnTileEntity(TileEntityType<?> typeIn, IRecipeType<? extends HellkilnRecipe> recipeTypeIn) {
        super(typeIn);
        this.recipeType = recipeTypeIn;
    }


    // Change so that it checks the igniter count variable!
    public boolean hasFuel() {
        return this.igniterCount > 0;
    }



    // To be changed so that it checks for the size of the array for igniter blockstates. Make sure this writes to the igniter count variable!
    public int checkFuel() {
        return getStatesFromPositions(this.igniterList).size();
    }

    // Code called when fuel is to be deducted from the igniters. Should be at the same rate regardless of amount of igniters.
    public void useFuel(int amount) {
        ArrayList<HellkilnIgniterTileEntity> list = getTilesFromPositions(this.igniterList);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                list.get(i).deductFuel(amount);
            }
        }

    }

    // Checks whether or not the given block is an igniter, and then checks if the igniter is both active and pointed at this block.
    public static boolean isValidIgniter(BlockState state, TileEntity tile, Direction direction) {
        return state.getBlock() instanceof HellkilnIgniter && tile instanceof HellkilnIgniterTileEntity && state.get(HellkilnIgniter.FACING) == direction && state.get(HellkilnIgniter.LIT);
    }

    // Creates and refreshes the list of blockstates for ignitors, which can then be manipulated. Already gets called when an igniter is toggled.
    public void refreshIgniterList() {
        World world = this.world;
        BlockPos pos = this.getPos();
        Direction direction = world.getBlockState(pos).get(Hellkiln.FACING).rotateY();
        ArrayList<BlockPos> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (isValidIgniter(world.getBlockState(pos.offset(direction)), world.getTileEntity(pos.offset(direction)), direction)) {
                list.add(pos.offset(direction));
            } else {
                list.add(null);
            }
            direction = direction.rotateY();
        }
        this.igniterList = list;
    }

    public ArrayList<BlockState> getStatesFromPositions(ArrayList<BlockPos> posList) {
        World world = this.world;
        ArrayList<BlockState> stateList = new ArrayList<>();
        for (int i = 0; i < posList.size(); i++) {
            if (posList.get(i) != null) {
                stateList.add(world.getBlockState(posList.get(i)));
            }
        }
        return stateList;
    }

    public ArrayList<HellkilnIgniterTileEntity> getTilesFromPositions(ArrayList<BlockPos> posList) {
        World world = this.world;
        ArrayList<HellkilnIgniterTileEntity> tileList = new ArrayList<>();
        for (int i = 0; i < posList.size(); i++) {
            if (posList.get(i) != null) {
                tileList.add((HellkilnIgniterTileEntity) world.getTileEntity(posList.get(i)));
            } else {
                tileList.add(null);
            }
        }
        return tileList;
    }


    //
    //      Left you some notes above some methods! Most need to be reworked.
    //      Still need to create read and write code! Leave till last though, but keep this message. Reading will probably include the check for igniters as well. May fail due to load order of blocks and tile entities,
    //      so if it does not work you will have to refer to the beam tile entity.
    //      Also need to make sure that the container can backtrack the amount of fuel in all the igniters from this class. You should be able to determine orientation based on the nulls, and then check the tile entities of those blocks. If that fails, switch
    // the list from states to positions and create a function that will calculate states from the positions, making things easier for regular use. Could be combined with the de-nulling code?
    //      Only issue would be the container checking the tile entity. Research a way to track that. Ask Nooby potentially! The data handler being used already can be backtracked, maybe look deeper into that code?
    //



/*      FURNACE
        0 = input
        1 = fuel
        2 = output

        HELLKILN
        0 = input 1
        1 = input 2
        2 = output

        Burn time:
        0 - forever
        1 - 12x furnace speed - 12 each - 2400 ticks
        2 - 6x furnace speed - 6 each - 1200 ticks
        3 - 4x furnace speed - 4 each - 600 ticks

        100 ticks each fuel, 100 ticks per 20 fuel.*/



    // Will probably need reworked soon, understanding of how this class works is coming along well.
    @Override
    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.igniterList != null) {
            ArrayList<HellkilnIgniterTileEntity> tileList = this.getTilesFromPositions(this.igniterList);
            this.igniterFuelCountLeft = tileList.get(0) != null ? tileList.get(0).getFuel() : -1;
            this.igniterFuelCountBack = tileList.get(1) != null ? tileList.get(1).getFuel() : -1;
            this.igniterFuelCountRight = tileList.get(2) != null ? tileList.get(2).getFuel() : -1;
        } else {
            refreshIgniterList();
        }

        if (this.isBurning()) {
            --this.burnTime;
        }
        if (!this.world.isRemote) {

            ItemStack itemstack1 = this.items.get(0);
            ItemStack itemstack2 = this.items.get(1);
            if (this.isBurning() || (!itemstack1.isEmpty() && !itemstack2.isEmpty()) && hasFuel()) {
                IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<HellkilnRecipe>)this.recipeType, this, this.world).orElse(null);
                if (!this.isBurning() && this.canSmelt(irecipe)) {
                    this.burnTime = this.getBurnTime();
                    this.speed = checkFuel();
                    this.recipesUsed = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (hasFuel()) {
                            useFuel(1);
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(irecipe)) {
                    for (int i = 0; i < this.speed; i++) {
                        ++this.cookTime;
                    }
                    if (this.cookTime >= this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTime();
                        this.smelt(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(Hellkiln.LIT, this.isBurning()), 3);
            }
        }

        if (flag1) {
            this.markDirty();
        }

    }

    protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
        if (!this.items.get(0).isEmpty() && !this.items.get(1).isEmpty() && recipeIn != null) {
            ItemStack itemstack = recipeIn.getRecipeOutput();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private void smelt(@Nullable IRecipe<?> recipe) {
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = this.items.get(1);
            ItemStack itemstack2 = recipe.getRecipeOutput();
            ItemStack itemstack3 = this.items.get(2);
            if (itemstack3.isEmpty()) {
                this.items.set(2, itemstack2.copy());
            } else if (itemstack3.getItem() == itemstack2.getItem()) {
                itemstack3.grow(itemstack2.getCount());
            }

            if (!this.world.isRemote) {
                this.setRecipeUsed(recipe);
            }

            itemstack.shrink(1);
            itemstack1.shrink(1);
        }
    }

    protected int getBurnTime() {
        if (!this.hasFuel()) {
            return 0;
        } else {
            return 5;
        }
    }

    protected int getCookTime() {
        return 2400;
    }

    public boolean isBurning() {
        return this.burnTime > 0 && !this.items.get(0).isEmpty() && !this.items.get(1).isEmpty();
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.druidcraft.hellkiln");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new HellkilnContainer(GUIRegistry.hellkiln, IModdedRecipeType.hellkiln_smelting, RecipeBookCategory.FURNACE, id, player, this, this.hellkilnData);
    }

    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipes.addTo(resourcelocation, 1);
        }

    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public int getSizeInventory() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.cookTimeTotal = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }

    }

    public void onCrafting(PlayerEntity player) {
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        for(ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }

    }
}
