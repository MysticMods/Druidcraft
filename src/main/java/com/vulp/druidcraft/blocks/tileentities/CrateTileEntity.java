package com.vulp.druidcraft.blocks.tileentities;

import com.google.common.collect.Sets;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.CrateType;
import com.vulp.druidcraft.blocks.CrateBlock;
import com.vulp.druidcraft.inventory.container.CrateContainer;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.GUIRegistry;
import com.vulp.druidcraft.registry.SoundEventRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class CrateTileEntity extends TileEntity implements INamedContainerProvider {
    private ItemStackHandler inventory = new ItemStackHandler(27);
    @SuppressWarnings("FieldCanBeLocal")
    private int numPlayersUsing;
    private UUID crateId;
    private ITextComponent displayName;
    private LazyOptional<IItemHandler> empty = null;
    private LazyOptional<IItemHandler> combined = null;
    private Set<BlockPos> combinedArray = null;

    private LazyOptional<IItemHandler> getEmpty () {
        if (empty == null || !empty.isPresent()) {
            empty = LazyOptional.of(() -> new ItemStackHandler(27));
        }
        return empty;
    }

    public CrateTileEntity() {
        super(TileEntityRegistry.crate);
        this.crateId = UUID.randomUUID();
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        if (empty != null) {
            empty.invalidate();
        }
        combinedArray = null;
        if (combined != null) {
            combined.invalidate();
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        compound = super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putUniqueId("uuid", crateId);

        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("Items", Constants.NBT.TAG_LIST)) {
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

    public void crateTick() {
        if (this.world == null) {
            return;
        }

        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        BlockState blockState = world.getBlockState(new BlockPos(i, j, k));
        this.numPlayersUsing = calculatePlayersUsing(this.world, i, j, k);
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
    }

    private static int calculatePlayersUsing(World world, int posX, int posY, int posZ) {
        int i = 0;
        float f = 6.0F;

        Set<UUID> inventoryIds = new HashSet<>();
        CrateBlock.getBlockPositions(world, new BlockPos(posX, posY, posZ)).forEach(o -> {
            TileEntity te = world.getTileEntity(o);
            if (te instanceof CrateTileEntity) {
                inventoryIds.add(((CrateTileEntity) te).getCrateId());
            }
        });
        for(PlayerEntity playerentity : world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double)((float)posX - f), (double)((float)posY - f), (double)((float)posZ - f), (double)((float)(posX + 1) + f), (double)((float)(posY + 1) + f), (double)((float)(posZ + 1) + f)))) {
            if (playerentity.openContainer instanceof CrateContainer) {
                CrateContainer crate = (CrateContainer) playerentity.openContainer;
                CrateTileEntity tile = crate.getCrate();
                if (tile != null) {
                    if (inventoryIds.contains(tile.getCrateId())) {
                        i++;
                    }
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
            boolean flag = blockstate.get(CrateBlock.PROPERTY_OPEN);
            if (!flag) {
                if (blockstate.get(CrateBlock.INDEX).isParent()) {
                    this.playSound(blockstate, SoundEventRegistry.open_crate);
                }
                this.setCrateState(blockstate, true);
            }

            this.scheduleTick();
        }

    }

    private void scheduleTick() {
        if (this.world != null) {
            Block block = this.getBlockState().getBlock();
            this.world.getPendingBlockTicks().scheduleTick(this.getPos(), block, 5);
            this.world.notifyNeighborsOfStateChange(this.pos, block);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (world == null || world.isRemote) {
            return getEmpty().cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
        Set<BlockPos> positions = Sets.newHashSet(CrateBlock.getBlockPositions(world, pos));
            if (this.combinedArray == null) {
                combinedArray = Sets.newHashSet(positions);
                combined = null;
            } else {
                if (combinedArray != positions) {
                    combined = null;
                    combinedArray = positions;
                }
            }

            if (combined == null || !combined.isPresent()) {
                combined = LazyOptional.of(this::getFullInventory);
            }
            return combined.cast();
        }
        return super.getCapability(cap, side);
    }

    @Nonnull
    public ItemStackHandler getInventory () {
        return inventory;
    }

    @Nonnull
    private IItemHandlerModifiable getFullInventory () {
        if (this.world == null) {
            return inventory;
        }

        List<BlockPos> positions = CrateBlock.getBlockPositions(world, pos);
        int size = positions.size();
        List<TileEntity> tiles;
        if (size == 2 || size == 4 || size == 8) {
            tiles = positions.stream().map(o -> world.getTileEntity(o)).collect(Collectors.toList());
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

    private UUID getCrateId() {
        return crateId;
    }

    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
        }
    }

    private void setCrateState(BlockState state, boolean open) {
        if (this.world != null) {
            this.world.setBlockState(this.getPos(), state.with(CrateBlock.PROPERTY_OPEN, open), 3);
        }
    }

    private float checkCrateShape(BlockState state) {
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
        if (this.world != null) {
            double d0 = (double) this.pos.getX() + 0.5D;
            double d1 = (double) this.pos.getY() + 0.5D;
            double d2 = (double) this.pos.getZ() + 0.5D;
            this.world.playSound(null, d0, d1, d2, p_213965_2_, SoundCategory.BLOCKS, 0.65F, this.world.rand.nextFloat() * 0.1F + checkCrateShape(state));
        }
    }

    public void setDisplayName (ITextComponent name) {
        this.displayName = name;
    }

    @Override
    public ITextComponent getDisplayName() {
        return displayName == null ? new TranslationTextComponent("block.druidcraft.crate") : displayName;
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        IItemHandler inventory = getFullInventory();
        ContainerType<?> type;
        int numRows = inventory.getSlots() / 9;
        switch (numRows) {
            default:
            case 3:
                type = GUIRegistry.generic_9X3;
                break;
            case 6:
                type = GUIRegistry.generic_9X6;
                break;
            case 12:
                type = GUIRegistry.generic_9X12;
                break;
            case 24:
                type = GUIRegistry.generic_9X24;
                break;
        }
        return new CrateContainer(type, i, playerInventory, inventory, numRows, this);
    }
}