package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.blocks.CrateBlock;
import com.vulp.druidcraft.blocks.CrateTempBlock;
import com.vulp.druidcraft.inventory.OctoSidedInventory;
import com.vulp.druidcraft.inventory.QuadSidedInventory;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.SoundEventRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.ArrayList;

public class CrateTileEntity extends LockableLootTileEntity implements ITickableTileEntity {
    private NonNullList<ItemStack> contents = NonNullList.withSize(27, ItemStack.EMPTY);
    private ArrayList<BlockPos> neighbors;
    private int numPlayersUsing;
    private int ticksSinceSync;
    private net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandlerModifiable> crateHandler;

    private CrateTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
        this.neighbors = CrateBlock.getBlockPositions(world, pos);
    }

    public CrateTileEntity() {
        this(TileEntityRegistry.crate);
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ArrayList<Integer> x = null;
        ArrayList<Integer> y = null;
        ArrayList<Integer> z = null;
        for (int i = 0; i < this.neighbors.size(); i++) {
            x.add(this.neighbors.get(i).getX());
            y.add(this.neighbors.get(i).getY());
            z.add(this.neighbors.get(i).getZ());
        }
        compound.putIntArray("CoordX", x);
        compound.putIntArray("CoordY", y);
        compound.putIntArray("CoordZ", z);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.contents);
        }

        return compound;
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        ArrayList<BlockPos> neighborArray = new ArrayList<>();
        for (int i = 0; compound.getIntArray("CoordX").length > i; i++) {
            neighborArray.add(new BlockPos(compound.getIntArray("CoordX")[i], compound.getIntArray("CoordY")[i], compound.getIntArray("CoordZ")[i]));
        }
        this.neighbors = neighborArray;
        this.contents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, this.contents);
        }

    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return 27;
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.contents) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index) {
        return this.contents.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.contents, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.contents, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.contents.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

    }

    public void clear() {
        this.contents.clear();
    }

    protected NonNullList<ItemStack> getItems() {
        return this.contents;
    }

    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.contents = itemsIn;
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.druidcraft.crate");
    }

    protected Container createMenu(int id, PlayerInventory player) {
        return ChestContainer.createGeneric9X3(id, player, this);
    }

    public void tick() {
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        ++this.ticksSinceSync;
        this.numPlayersUsing = calculatePlayersUsingSync(this.world, this, this.ticksSinceSync, i, j, k, this.numPlayersUsing, this.neighbors.size() > 2);
    }

    public static int calculatePlayersUsingSync(World world, LockableTileEntity lockableTileEntity, int ticksSinceSync, int posX, int posY, int posZ, int numPlayersUsing, boolean isQuadOrOcto) {
        if (!world.isRemote && numPlayersUsing != 0 && (ticksSinceSync + posX + posY + posZ) % 200 == 0) {
            numPlayersUsing = calculatePlayersUsing(world, lockableTileEntity, posX, posY, posZ, isQuadOrOcto);
        }

        return numPlayersUsing;
    }

    public static int calculatePlayersUsing(World world, LockableTileEntity lockableTileEntity, int posX, int posY, int posZ, boolean isQuadOrOcto) {
        int i = 0;
        float f = 5.0F;

        for(PlayerEntity playerentity : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double)((float)posX - f), (double)((float)posY - f), (double)((float)posZ - f), (double)((float)(posX + 1) + f), (double)((float)(posY + 1) + f), (double)((float)(posZ + 1) + f)))) {
            if (playerentity.openContainer instanceof CrateContainer && isQuadOrOcto) {
                IInventory iinventory = ((CrateContainer)playerentity.openContainer).getLowerCrateInventory();
                if ((iinventory == lockableTileEntity || iinventory instanceof QuadSidedInventory && ((QuadSidedInventory)iinventory).isPartOfQuadCrate(lockableTileEntity)) || (iinventory == lockableTileEntity || iinventory instanceof OctoSidedInventory && ((OctoSidedInventory)iinventory).isPartOfOctoCrate(lockableTileEntity))) {
                    ++i;
                }
            }
            if (playerentity.openContainer instanceof ChestContainer && !isQuadOrOcto) {
                IInventory iinventory = ((ChestContainer)playerentity.openContainer).getLowerChestInventory();
                if (iinventory == lockableTileEntity || iinventory instanceof DoubleSidedInventory && ((DoubleSidedInventory)iinventory).isPartOfLargeChest(lockableTileEntity)) {
                    ++i;
                }
            }
        }

        return i;
    }

    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            BlockState blockstate = this.getBlockState();
            boolean flag = blockstate.get(CrateTempBlock.PROPERTY_OPEN);
            if (!flag) {
                this.func_213965_a(blockstate, SoundEventRegistry.open_crate);
                this.playSoundOpen(blockstate, true);
            }

            this.onOpenOrClose();
        }

    }

    private void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof CrateBlock) {
            this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, block);
        }

    }

    public static int getPlayersUsing(IBlockReader reader, BlockPos posIn) {
        BlockState blockstate = reader.getBlockState(posIn);
        if (blockstate.hasTileEntity()) {
            TileEntity tileentity = reader.getTileEntity(posIn);
            if (tileentity instanceof CrateTileEntity) {
                return ((CrateTileEntity)tileentity).numPlayersUsing;
            }
        }

        return 0;
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        if (this.crateHandler != null) {
            this.crateHandler.invalidate();
            this.crateHandler = null;
        }
    }

    public void func_213962_h() {
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        this.numPlayersUsing = ChestTileEntity.calculatePlayersUsing(this.world, this, i, j, k);
        if (this.numPlayersUsing > 0) {
            this.onOpenOrClose();
        } else {
            BlockState blockstate = this.getBlockState();
            if (blockstate.getBlock() != BlockRegistry.crate_temp) {
                this.remove();
                return;
            }

            boolean flag = blockstate.get(CrateTempBlock.PROPERTY_OPEN);
            if (flag) {
                this.func_213965_a(blockstate, SoundEventRegistry.close_crate);
                this.playSoundOpen(blockstate, false);
            }
        }

    }

    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, @javax.annotation.Nullable net.minecraft.util.Direction side) {
        if (!this.removed && cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (this.crateHandler == null) {
                this.crateHandler = net.minecraftforge.common.util.LazyOptional.of(this::createHandler);
            }
            return this.crateHandler.cast();
        }
        return super.getCapability(cap);
    }

    private net.minecraftforge.items.IItemHandlerModifiable createHandler() {
        int size = this.neighbors.size() + 1;
        for (int i = 0; i > this.neighbors.size(); i++) {
            if (!(this.world.getBlockState(this.neighbors.get(i)).getBlock() instanceof CrateBlock) || size < 2) {
                return new net.minecraftforge.items.wrapper.InvWrapper(this);
            }
            if (!(this.getWorld().getTileEntity(this.neighbors.get(i)) instanceof CrateTileEntity)) {
                return new net.minecraftforge.items.wrapper.InvWrapper(this);
            }
        }
        IInventory inven1 = (IInventory)this.getWorld().getTileEntity(this.neighbors.get(0));
        IInventory inven2 = (IInventory)this.getWorld().getTileEntity(this.neighbors.get(1));
        IInventory inven3 = (IInventory)this.getWorld().getTileEntity(this.neighbors.get(2));
        IInventory inven4 = (IInventory)this.getWorld().getTileEntity(this.neighbors.get(3));
        IInventory inven5 = (IInventory)this.getWorld().getTileEntity(this.neighbors.get(4));
        IInventory inven6 = (IInventory)this.getWorld().getTileEntity(this.neighbors.get(5));
        IInventory inven7 = (IInventory)this.getWorld().getTileEntity(this.neighbors.get(6));
        IInventory inven8 = (IInventory)this.getWorld().getTileEntity(this.neighbors.get(7));
        if (size == 2) {
            return new net.minecraftforge.items.wrapper.CombinedInvWrapper(
                    new net.minecraftforge.items.wrapper.InvWrapper(inven1),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven2));
        }
        if (size == 4) {
            return new net.minecraftforge.items.wrapper.CombinedInvWrapper(
                    new net.minecraftforge.items.wrapper.InvWrapper(inven1),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven2),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven3),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven4));
        }
        if (size == 8) {
            return new net.minecraftforge.items.wrapper.CombinedInvWrapper(
                    new net.minecraftforge.items.wrapper.InvWrapper(inven1),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven2),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven3),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven4),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven5),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven6),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven7),
                    new net.minecraftforge.items.wrapper.InvWrapper(inven8));
        }
        return new net.minecraftforge.items.wrapper.InvWrapper(this);
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void remove() {
        super.remove();
        if (crateHandler != null)
            crateHandler.invalidate();
    }

    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
        }
    }

    private void playSoundOpen(BlockState state, boolean isOpening) {
        this.world.setBlockState(this.getPos(), state.with(CrateBlock.PROPERTY_OPEN, Boolean.valueOf(isOpening)), 3);
    }

    private void func_213965_a(BlockState p_213965_1_, SoundEvent p_213965_2_) {
        double d0 = (double)this.pos.getX() + 0.5D;
        double d1 = (double)this.pos.getY() + 0.5D;
        double d2 = (double)this.pos.getZ() + 0.5D;
        this.world.playSound((PlayerEntity)null, d0, d1, d2, p_213965_2_, SoundCategory.BLOCKS, 0.65F, this.world.rand.nextFloat() * 0.1F + 0.9F);
    }
}