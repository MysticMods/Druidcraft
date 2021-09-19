/*
package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.blocks.HellkilnBlock;
import com.vulp.druidcraft.blocks.HellkilnIgniterBlock;
import com.vulp.druidcraft.inventory.container.HellkilnContainer;
import com.vulp.druidcraft.recipes.HellkilnRecipe;
import com.vulp.druidcraft.recipes.IModdedRecipeType;
import com.vulp.druidcraft.registry.GUIRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class HellkilnTileEntity extends LockableTileEntity implements ITickableTileEntity, IRecipeHolder, IRecipeHelperPopulator {

    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int burnTime;
    private int recipesUsed;
    private int cookTime = 0;
    private int cookTimeTotal = 0;
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

    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
        this.igniterCount = nbt.getInt("IgniterCount");
        this.burnTime = nbt.getInt("BurnTime");
        this.cookTime = nbt.getInt("CookTime");
        this.cookTimeTotal = nbt.getInt("CookTimeTotal");
        int[] fuelArray = nbt.getIntArray("IgniterFuelCounts");
        this.igniterFuelCountLeft = fuelArray[0];
        this.igniterFuelCountBack = fuelArray[1];
        this.igniterFuelCountRight = fuelArray[2];
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("IgniterCount", this.igniterCount);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("CookTime", this.cookTime);
        compound.putInt("CookTimeTotal", this.cookTimeTotal);
        compound.putIntArray("IgniterFuelCounts", new int[]{this.igniterFuelCountLeft, this.igniterFuelCountBack, this.igniterFuelCountRight});
        ItemStackHelper.saveAllItems(compound, this.items);
        CompoundNBT compoundnbt = new CompoundNBT();
        this.recipes.forEach((recipeId, craftedAmount) -> {
            compoundnbt.putInt(recipeId.toString(), craftedAmount);
        });
        return compound;
    }

    // Change so that it checks the igniter count variable!
    public boolean hasIgniters() {
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
            HellkilnIgniterTileEntity tile = list.get(i);
            if (tile != null) {
                tile.deductFuel(amount);
            }
        }

    }

    // Checks whether or not the given block is an igniter, and then checks if the igniter is both active and pointed at this block.
    public static boolean isValidIgniter(BlockState state, TileEntity tile, Direction direction) {
        return state.getBlock() instanceof HellkilnIgniterBlock && tile instanceof HellkilnIgniterTileEntity && state.get(HellkilnIgniterBlock.FACING) == direction && state.get(HellkilnIgniterBlock.LIT);
    }

    // Creates and refreshes the list of blockstates for ignitors, which can then be manipulated. Already gets called when an igniter is toggled.
    public void refreshIgniterList() {
        World world = this.world;
        BlockPos pos = this.getPos();
        Direction direction = world.getBlockState(pos).get(HellkilnBlock.FACING).rotateY();
        ArrayList<BlockPos> list = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < 3; i++) {
            if (isValidIgniter(world.getBlockState(pos.offset(direction)), world.getTileEntity(pos.offset(direction)), direction)) {
                list.add(pos.offset(direction));
                j++;
            } else {
                list.add(null);
            }
            direction = direction.rotateY();
        }
        this.igniterList = list;
        this.igniterCount = j;
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



*/
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

        100 ticks each fuel, 100 ticks per 20 fuel.*//*


    @Override
    public void tick() {
        // flag regards if the furnace is burning start of tick.
        boolean flag = this.isBurning();
        boolean flag1 = false;

        // Refreshes igniter list or gets the fuel count.
        if (this.igniterList != null) {
            ArrayList<HellkilnIgniterTileEntity> tileList = this.getTilesFromPositions(this.igniterList);
            this.igniterFuelCountLeft = tileList.get(0) != null ? tileList.get(0).getFuel() : -1;
            this.igniterFuelCountBack = tileList.get(1) != null ? tileList.get(1).getFuel() : -1;
            this.igniterFuelCountRight = tileList.get(2) != null ? tileList.get(2).getFuel() : -1;
        } else {
            refreshIgniterList();
        }
        // Checks if furnace is burning and deducts burntime.
        if (this.isBurning()) {
            --this.burnTime;
        }
        if (!this.world.isRemote) {

            ItemStack itemstack1 = this.items.get(0);
            ItemStack itemstack2 = this.items.get(1);
            // Checks if the furnace is meant to be burning this tick.
            if (this.isBurning() || (!itemstack1.isEmpty() && !itemstack2.isEmpty()) && hasIgniters()) {
                IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe(this.recipeType, this, this.world).orElse(null);
                // If the furnace hasn't previously been burning but has a valid recipe.
                if (!this.isBurning() && this.canSmelt(irecipe)) {
                    this.burnTime = this.getBurnTime();
                    this.speed = checkFuel();
                    this.recipesUsed = this.burnTime;
                    this.cookTimeTotal = this.getCookTime();
                    if (this.isBurning()) {
                        flag1 = true;
                        if (hasIgniters()) {
                            useFuel(1);
                        }
                    }
                }
                // If it was already burning and still has valid recipe.
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
                    // If the items in the slots don't match a recipe.
                } else {
                    this.cookTime = 0;
                }
                // If the furnace is not meant to be burning and there's still cooktime left.
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime -= 3;
            }
            // Checks to see if isBurning() has changed by the end of tick, so that furnace state can change.
            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(HellkilnBlock.LIT, this.isBurning()), 3);
                this.world.setBlockState(this.pos.up(), this.world.getBlockState(this.pos.up()).with(HellkilnBlock.LIT, this.isBurning()), 3);
            }
        }

        // If new recipe is started.
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
        if (!this.hasIgniters()) {
            return 0;
        } else {
            return 9;
        }
    }

    protected int getCookTime() {
        return 2400;
    }

    public boolean isBurning() {
        return this.burnTime > 0;
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
*/
