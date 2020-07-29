package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.CrateType;
import com.vulp.druidcraft.blocks.CrateBlock;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.SoundEventRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class CrateTileEntity extends TileEntity {
    private ItemStackHandler inventory = new ItemStackHandler(27);
    private ArrayList<BlockPos> neighbors;
    private int numPlayersUsing;
    private LazyOptional<IItemHandlerModifiable> crateHandler;
    private UUID crateId;

    public CrateTileEntity() {
        super(TileEntityRegistry.crate);
        this.crateId = UUID.randomUUID();
    }

    private List<BlockPos> getNeighbors() {
        return this.neighbors;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putIntArray("CoordX", this.neighbors.stream().map(Vec3i::getX).collect(Collectors.toList()));
        compound.putIntArray("CoordY", this.neighbors.stream().map(Vec3i::getY).collect(Collectors.toList()));
        compound.putIntArray("CoordZ", this.neighbors.stream().map(Vec3i::getZ).collect(Collectors.toList()));
        compound.put("inventory", inventory.serializeNBT());
        compound.putUniqueId("uuid", crateId);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.neighbors = new ArrayList<>();
        for (int i = 0; i < compound.getIntArray("CoordX").length; i++) {
            this.neighbors.add(new BlockPos(compound.getIntArray("CoordX")[i], compound.getIntArray("CoordY")[i], compound.getIntArray("CoordZ")[i]));
        }
        if (compound.contains("Items", Constants.NBT.TAG_COMPOUND)) {
            NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(compound, items);
            this.inventory = new ItemStackHandler(items);
        } else {
            this.inventory.deserializeNBT(compound.getCompound("inventory"));
        }
        if (compound.hasUniqueId("uuid")) {
            this.crateId = compound.getUniqueId("uuid");
        } else if (this.crateId == null) {
            this.crateId = UUID.randomUUID();
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.neighbors == null) {
            this.neighbors = CrateBlock.getBlockPositions(world, pos);
        }
        for (int i = 0; i < this.neighbors.size(); i++) {
            Druidcraft.LOGGER.debug("onLoad() : " + this.neighbors.get(i));
        }
    }

    public void crateTick() {
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        BlockState blockState = world.getBlockState(new BlockPos(i, j, k));
        this.numPlayersUsing = calculatePlayersUsing(this.world, i, j, k); //(blockState.get(CrateBlock.INDEX).getType() == CrateType.QUAD_X || blockState.get(CrateBlock.INDEX).getType() == CrateType.QUAD_Y || blockState.get(CrateBlock.INDEX).getType() == CrateType.QUAD_Z || blockState.get(CrateBlock.INDEX).getType() == CrateType.OCTO));
        if (this.numPlayersUsing > 0) {
            this.scheduleTick();
        } else {
            BlockState blockstate = this.getBlockState();
            if (blockstate.getBlock() != BlockRegistry.crate) {
                this.remove();
                return;
            }

            boolean flag = blockstate.get(CrateBlock.PROPERTY_OPEN);
            if (flag) {
                if (blockState.get(CrateBlock.INDEX).isParent()) {
                    this.playSound(blockState, SoundEventRegistry.close_crate);
                }
                this.setCrateState(blockstate, false);
            }
        }

        for (int b = 0; b < this.neighbors.size(); b++) {
            Druidcraft.LOGGER.debug("crateTick() : " + this.neighbors.get(b));
        }

    }

    @Deprecated
    public static int calculatePlayersUsing(World world, int posX, int posY, int posZ) {
        int i = 0;
        float f = 6.0F;

        Set<UUID> inventoryIds = null; //getInventoryIds();
        for(PlayerEntity playerentity : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double)((float)posX - f), (double)((float)posY - f), (double)((float)posZ - f), (double)((float)(posX + 1) + f), (double)((float)(posY + 1) + f), (double)((float)(posZ + 1) + f)))) {
            if (playerentity.openContainer instanceof CrateContainer) {
                CrateContainer crate = (CrateContainer) playerentity.openContainer;
/*                if (inventoryIds.contains(crate.getCrateId())) {
                    i++;
                }*/
            }
/*            if (playerentity.openContainer instanceof ChestContainer && !isQuadOrOcto) {
                IInventory iinventory = ((ChestContainer)playerentity.openContainer).getLowerChestInventory();
                if (iinventory == lockableTileEntity || iinventory instanceof DoubleSidedInventory && ((DoubleSidedInventory)iinventory).isPartOfLargeChest(lockableTileEntity)) {
                    ++i;
                }
            }*/
        }

        return i;
    }

/*    @Override
    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            BlockState blockstate = this.getBlockState();
            boolean flag = blockstate.get(CrateBlock.PROPERTY_OPEN);
            if (!flag) {
                if (blockstate.get(CrateBlock.INDEX).isParent()) {
                    this.playSound(blockstate, SoundEventRegistry.open_crate);
                }
                this.setCrateState(blockstate, true);
            }

            this.scheduleTick();
        }

    }*/

    private void scheduleTick() {
        Block block = this.getBlockState().getBlock();
        this.world.getPendingBlockTicks().scheduleTick(this.getPos(), block, 5);
        this.world.notifyNeighborsOfStateChange(this.pos, block);
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        if (this.crateHandler != null) {
            // TODO: Invalidate after crate formation change
            this.crateHandler.invalidate();
/*            this.crateHandler = null;*/
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (this.crateHandler == null) {
                this.crateHandler = LazyOptional.of(this::createHandler);
            }
            return this.crateHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public ItemStackHandler getInventory () {
        return inventory;
    }

    private IItemHandlerModifiable getFullInventory () {
        int size = neighbors.size();
        List<TileEntity> tiles;
        if (size == 2 || size == 4 || size == 8) {
            tiles = neighbors.stream().map(o -> world.getTileEntity(o)).collect(Collectors.toList());
        } else {
            return getInventory();
        }
        List<CrateTileEntity> crates = new ArrayList<>();
        for (TileEntity te : tiles) {
            if (!(te instanceof CrateTileEntity)) {
                throw new IllegalStateException("Neighbouring tile entity of crate is not a crate tile entity.");
            }
            crates.add((CrateTileEntity) te);
        }
        crates.sort(Comparator.comparing(CrateTileEntity::getCrateId));
        ItemStackHandler[] handlers = crates.stream().map(CrateTileEntity::getInventory).toArray(ItemStackHandler[]::new);
        return new CombinedInvWrapper(handlers);
    }

    public UUID getCrateId () {
        return crateId;
    }

    @Override
    public void remove() {
        super.remove();
        if (crateHandler != null)
            crateHandler.invalidate();
    }

/*    @Override
    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
        }
    }*/

    private void setCrateState(BlockState state, boolean isOpening) {
        this.world.setBlockState(this.getPos(), state.with(CrateBlock.PROPERTY_OPEN, isOpening), 3);
    }

    public float checkCrateShape(BlockState state) {
        CrateType type = state.get(CrateBlock.INDEX).getType();
        if (type == CrateType.DOUBLE_X || type == CrateType.DOUBLE_Y || type == CrateType.DOUBLE_Z)
            return 0.8F;
        if (type == CrateType.QUAD_X || type == CrateType.QUAD_Y || type == CrateType.QUAD_Z)
            return 0.7F;
        if (type == CrateType.OCTO)
            return 0.6F;
        return 0.9F;
    }

    private void playSound(BlockState state, SoundEvent p_213965_2_) {
        double d0 = (double)this.pos.getX() + 0.5D;
        double d1 = (double)this.pos.getY() + 0.5D;
        double d2 = (double)this.pos.getZ() + 0.5D;
        this.world.playSound(null, d0, d1, d2, p_213965_2_, SoundCategory.BLOCKS, 0.65F, this.world.rand.nextFloat() * 0.1F + checkCrateShape(state));
    }
}