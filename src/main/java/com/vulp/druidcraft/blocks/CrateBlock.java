package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.entities.tileentities.CrateTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class CrateBlock extends ContainerBlock {
    public static final BooleanProperty PROPERTY_OPEN;

    public CrateBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_OPEN, false));
    }

    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof CrateTileEntity) {
                player.openContainer((CrateTileEntity)tileentity);
            }

            return true;
        }
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }

    }

    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof CrateTileEntity) {
            ((CrateTileEntity)tileentity).func_213962_h();
        }

    }

    @Nullable
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CrateTileEntity();
    }

    /** @deprecated */
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof CrateTileEntity) {
                ((CrateTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }

    }

    /** @deprecated */
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    /** @deprecated */
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PROPERTY_OPEN);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return (BlockState)this.getDefaultState();
    }

    static {
        PROPERTY_OPEN = BlockStateProperties.OPEN;
    }
}