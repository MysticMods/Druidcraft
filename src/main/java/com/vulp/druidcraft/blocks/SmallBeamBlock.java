package com.vulp.druidcraft.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class SmallBeamBlock extends Block implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty X_AXIS = BooleanProperty.create("x_axis");
    public static final BooleanProperty Y_AXIS = BooleanProperty.create("y_axis");
    public static final BooleanProperty Z_AXIS = BooleanProperty.create("z_axis");
    public static final IntegerProperty CONNECTIONS = IntegerProperty.create("connections", 0, 3);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Direction.Axis> DEFAULT_AXIS = BlockStateProperties.AXIS;

    public SmallBeamBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState()
                .with(X_AXIS, false)
                .with(Y_AXIS, false)
                .with(Z_AXIS, false)
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(UP, false)
                .with(DOWN, false)
                .with(CONNECTIONS, 0)
                .with(WATERLOGGED, false)
                .with(DEFAULT_AXIS, Direction.Axis.Y));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape voxelshape = VoxelShapes.empty();

        if (state.get(X_AXIS)) {
            voxelshape = VoxelShapes.or(voxelshape, Block.makeCuboidShape(0.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D));
        }
        if (state.get(Y_AXIS)) {
            voxelshape = VoxelShapes.or(voxelshape, Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D));
        }
        if (state.get(Z_AXIS)) {
            voxelshape = VoxelShapes.or(voxelshape, Block.makeCuboidShape(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 16.0D));
        }
        if (!state.get(X_AXIS) && !state.get(Y_AXIS) && !state.get(Z_AXIS)) {
            voxelshape = VoxelShapes.fullCube();
        }

        return voxelshape;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(X_AXIS, Y_AXIS, Z_AXIS, NORTH, SOUTH, EAST, WEST, UP, DOWN, CONNECTIONS, WATERLOGGED, DEFAULT_AXIS);
    }

    public BlockState ropeConnectionCalculations(boolean onPlaced, IWorld world, BlockState state, BlockPos pos) {
        BlockState newState = state.with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false);
        if (world.getBlockState(pos.north()).getBlock() instanceof RopeBlock && !state.get(Z_AXIS)) {
            if (onPlaced || world.getBlockState(pos.north()).get(RopeBlock.SOUTH))
                newState = newState.with(NORTH, true);
        }
        if (world.getBlockState(pos.south()).getBlock() instanceof RopeBlock && !state.get(Z_AXIS)) {
            if (onPlaced || world.getBlockState(pos.south()).get(RopeBlock.NORTH))
                newState = newState.with(SOUTH, true);
        }
        if (world.getBlockState(pos.east()).getBlock() instanceof RopeBlock && !state.get(X_AXIS)) {
            if (onPlaced || world.getBlockState(pos.east()).get(RopeBlock.WEST))
                newState = newState.with(EAST, true);
        }
        if (world.getBlockState(pos.west()).getBlock() instanceof RopeBlock && !state.get(X_AXIS)) {
            if (onPlaced || world.getBlockState(pos.west()).get(RopeBlock.EAST))
                newState = newState.with(WEST, true);
        }
        if (world.getBlockState(pos.up()).getBlock() instanceof RopeBlock && !state.get(Y_AXIS)) {
            if (onPlaced || world.getBlockState(pos.up()).get(RopeBlock.DOWN))
                newState = newState.with(UP, true);
        }
        if (world.getBlockState(pos.down()).getBlock() instanceof RopeBlock && !state.get(Y_AXIS)) {
            if (onPlaced || world.getBlockState(pos.down()).get(RopeBlock.UP))
                newState = newState.with(DOWN, true);
        }
        return newState;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return this.ropeConnectionCalculations(true, context.getWorld(), calculateState(getDefaultState(), context.getWorld(), context.getPos(), context.getFace().getAxis()).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER), context.getPos());
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    private BlockState calculateState (BlockState currentState, IWorld world, BlockPos pos, Direction.Axis defaultAxis) {

        boolean xBool = defaultAxis == Direction.Axis.X;
        boolean yBool = defaultAxis == Direction.Axis.Y;
        boolean zBool = defaultAxis == Direction.Axis.Z;

        BlockState northState = world.getBlockState(pos.offset(Direction.NORTH));
        BlockState eastState = world.getBlockState(pos.offset(Direction.EAST));
        BlockState southState = world.getBlockState(pos.offset(Direction.SOUTH));
        BlockState westState = world.getBlockState(pos.offset(Direction.WEST));
        BlockState upState = world.getBlockState(pos.offset(Direction.UP));
        BlockState downState = world.getBlockState(pos.offset(Direction.DOWN));

        if (eastState.getBlock() == this) {
            if (eastState.get(X_AXIS)) {
                xBool = true;
            }
        } else if (westState.getBlock() == this) {
            if (westState.get(X_AXIS)) {
                xBool = true;
            }
        }

        if (upState.getBlock() == this) {
            if (upState.get(Y_AXIS)) {
                yBool = true;
            }
        } else if (downState.getBlock() == this) {
            if (downState.get(Y_AXIS)) {
                yBool = true;
            }
        }

        if (northState.getBlock() == this) {
            if (northState.get(Z_AXIS)) {
                zBool = true;
            }
        } else if (southState.getBlock() == this) {
            if (southState.get(Z_AXIS)) {
                zBool = true;
            }
        }

        int count = 0;
        if (xBool) {
            count++;
        }
        if (yBool) {
            count++;
        }
        if (zBool) {
            count++;
        }

        return currentState.with(X_AXIS, xBool).with(Y_AXIS, yBool).with(Z_AXIS, zBool).with(CONNECTIONS, count).with(DEFAULT_AXIS, defaultAxis);
    }



    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidState) {
        if (!state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state) {
        if (state.get(WATERLOGGED)) {
            worldIn.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.get(WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {

        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return ropeConnectionCalculations(false, world, calculateState(state, world, currentPos, state.get(DEFAULT_AXIS)), currentPos);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
