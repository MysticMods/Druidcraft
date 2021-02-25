package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.blocks.tileentities.HellkilnIgniterTileEntity;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class HellkilnIgniterBlock extends ContainerBlock {

    public static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 10.0D, 12.0D, 8.0D, 16.0D);
    public static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 6.0D, 8.0D, 12.0D);
    public static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 8.0D, 6.0D);
    public static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(10.0D, 0.0D, 4.0D, 16.0D, 8.0D, 12.0D);
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public HellkilnIgniterBlock(Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, false));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof HellkilnIgniterTileEntity) {
                player.openContainer((INamedContainerProvider) tileentity);
            }
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof HellkilnIgniterTileEntity) {
                ((HellkilnIgniterTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(FACING);
        switch (direction) {
            case EAST:
                return EAST_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return NORTH_SHAPE;
        }
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof HellkilnIgniterTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (HellkilnIgniterTileEntity)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new HellkilnIgniterTileEntity(TileEntityRegistry.hellkiln_igniter);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            double d0 = (double) pos.getX() + 0.5F;
            double d1 = (double) pos.getY() + 0.5F;
            double d2 = (double) pos.getZ() + 0.5F;
            Direction direction = stateIn.get(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d4 = rand.nextDouble() * 0.4D - 0.2D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * 0.28D * -1 : d4;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * 0.28D * -1 : d4;
            worldIn.addParticle(ParticleRegistry.fiery_spark, d0 + d5, d1, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

}
