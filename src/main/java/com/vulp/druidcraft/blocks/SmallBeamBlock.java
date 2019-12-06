package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.api.IKnifeable;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SmallBeamBlock extends Block implements IBucketPickupHandler, ILiquidContainer, IKnifeable {

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
                .with(CONNECTIONS, 0)
                .with(WATERLOGGED, false)
                .with(DEFAULT_AXIS, Direction.Axis.Y));
    }

    @Nullable
    private static Direction.Axis getClickedConnection(Vec3d relative) {
        if (relative.x < 0.25 || relative.x > 0.75)
            return Direction.Axis.X;
        if (relative.y < 0.25 || relative.y < 0.75)
            return Direction.Axis.Y;
        if (relative.z < 0.25 || relative.z > 0.75)
            return Direction.Axis.Z;
        return null;
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
        return voxelshape;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(X_AXIS, Y_AXIS, Z_AXIS, CONNECTIONS, WATERLOGGED, DEFAULT_AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        if (context.getFace().getAxis() == Direction.Axis.X) {
            return this.calculateState(getDefaultState(), context.getWorld(), context.getPos(), false).with(X_AXIS, true).with(DEFAULT_AXIS, Direction.Axis.X).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else if (context.getFace().getAxis() == Direction.Axis.Z) {
            return this.calculateState(getDefaultState(), context.getWorld(), context.getPos(), false).with(Z_AXIS, true).with(DEFAULT_AXIS, Direction.Axis.Z).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
        } else return this.calculateState(getDefaultState(), context.getWorld(), context.getPos(), false).with(Y_AXIS, true).with(DEFAULT_AXIS, Direction.Axis.Y).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    private BlockState calculateState (BlockState currentState, World world, BlockPos pos, boolean isUpdate) {
        boolean xBool = false;
        boolean yBool = false;
        boolean zBool = false;
        if (!isUpdate) {
            xBool = currentState.get(X_AXIS);
            yBool = currentState.get(Y_AXIS);
            zBool = currentState.get(Z_AXIS);
        }

        Direction.Axis defaultAxis = currentState.get(DEFAULT_AXIS);
        Direction.Axis newAxis = Direction.Axis.Y;
        if (!currentState.get(X_AXIS) && ((world.getBlockState(pos.offset(Direction.EAST)).getBlock() == this && world.getBlockState(pos.offset(Direction.EAST)).get(X_AXIS)) || (world.getBlockState(pos.offset(Direction.WEST)).getBlock() == this && world.getBlockState(pos.offset(Direction.WEST)).get(X_AXIS)))) {
            xBool = true;
        } else if (currentState.get(DEFAULT_AXIS) == Direction.Axis.X) {
            defaultAxis = Direction.Axis.Y;
        }
        if (!currentState.get(Y_AXIS) && ((world.getBlockState(pos.offset(Direction.UP)).getBlock() == this && world.getBlockState(pos.offset(Direction.UP)).get(Y_AXIS)) || (world.getBlockState(pos.offset(Direction.DOWN)).getBlock() == this && world.getBlockState(pos.offset(Direction.DOWN)).get(Y_AXIS)))) {
            yBool = true;
        } else if (currentState.get(DEFAULT_AXIS) == Direction.Axis.Y) {
            defaultAxis = Direction.Axis.Z;
        }
        if (!currentState.get(Z_AXIS) && ((world.getBlockState(pos.offset(Direction.NORTH)).getBlock() == this && world.getBlockState(pos.offset(Direction.NORTH)).get(Z_AXIS)) || (world.getBlockState(pos.offset(Direction.SOUTH)).getBlock() == this && world.getBlockState(pos.offset(Direction.SOUTH)).get(Z_AXIS)))) {
            zBool = true;
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

        if (count == 0) {
            newAxis = defaultAxis;
        }

        xBool = newAxis == Direction.Axis.X;
        yBool = newAxis == Direction.Axis.Y;
        zBool = newAxis == Direction.Axis.Z;

        return currentState.with(X_AXIS, xBool).with(Y_AXIS, yBool).with(Z_AXIS, zBool).with(CONNECTIONS, count).with(DEFAULT_AXIS, newAxis);
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
            worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(false)), 3);
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

        return calculateState(state, (World) world, currentPos, true);
    }

    @Override
    public ActionResultType onKnife(ItemUseContext context) {
        return null;
    }
}
