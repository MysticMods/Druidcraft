/*
package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.blocks.tileentities.FluidCraftingTableTileEntity;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FluidCraftingTableBlock extends ContainerBlock {

    public static final BooleanProperty EMPTY = BooleanProperty.create("empty");

    public FluidCraftingTableBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(EMPTY, true));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(EMPTY);
    }

    private void setEmpty(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(EMPTY, true));
    }

    private void setFull(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(EMPTY, false));
    }

    private void setFluid(World world, BlockPos pos, Fluid fluid) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof FluidCraftingTableTileEntity) {
            ((FluidCraftingTableTileEntity) tile).setFluid(fluid);
        }
    }

    @Nullable
    private Fluid getFluid(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof FluidCraftingTableTileEntity) {
            return ((FluidCraftingTableTileEntity) tile).getFluid();
        }
        return null;
    }

    private boolean checkFull(BlockState state) {
        return !state.get(EMPTY);
    }

    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        ItemStack heldStack = player.getHeldItem(hand);
        if (!heldStack.isEmpty()) {
            boolean isFull = checkFull(state);
            Item heldItem = heldStack.getItem();
            if (heldItem instanceof BucketItem) {
                if (!isFull && heldItem != Items.BUCKET && !world.isRemote) {
                    this.setFull(world, pos, state);
                    this.setFluid(world, pos, ((BucketItem) heldItem).getFluid());
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (!player.abilities.isCreativeMode) {
                        player.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    }
                    return ActionResultType.CONSUME;
                }

            } if (heldItem == Items.BUCKET) {
                if (isFull && !world.isRemote) {
                    Fluid fluid = getFluid(world, pos);
                    this.setEmpty(world, pos, state);
                    this.setFluid(world, pos, Fluids.EMPTY);
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (!player.abilities.isCreativeMode) {
                        heldStack.shrink(1);
                        Item bucketItem = fluid != null ? fluid.getFilledBucket() : Items.BUCKET;
                        if (heldStack.isEmpty()) {
                            player.setHeldItem(hand, new ItemStack(bucketItem));
                        } else if (!player.inventory.addItemStackToInventory(new ItemStack(bucketItem))) {
                            player.dropItem(new ItemStack(bucketItem), false);
                        }
                    }
                    return ActionResultType.CONSUME;
                }
            }
        }

        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof FluidCraftingTableTileEntity) {
                player.openContainer((INamedContainerProvider) tileentity);
            }
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof FluidCraftingTableTileEntity) {
            return ((FluidCraftingTableTileEntity) tileentity).getFluid().getDefaultState().getBlockState().getLightValue();
        }
        return super.getLightValue(state, world, pos);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof FluidCraftingTableTileEntity) {
                worldIn.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new FluidCraftingTableTileEntity(TileEntityRegistry.fluid_crafting_table);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
*/
