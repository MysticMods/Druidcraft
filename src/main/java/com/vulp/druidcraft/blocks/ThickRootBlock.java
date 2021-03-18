package com.vulp.druidcraft.blocks;

import com.google.common.collect.Maps;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Map;

public class ThickRootBlock extends Block implements IBucketPickupHandler, ILiquidContainer {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final float thicknessMod;
    public static final Map<Direction, BooleanProperty> DIR_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });
    
    public ThickRootBlock(float thicknessModifier, Properties properties) {
        super(properties);
        this.thicknessMod = thicknessModifier;
        this.setDefaultState(this.getDefaultState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(UP, false)
                .with(DOWN, false)
                .with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        float j = 3.0F - this.thicknessMod;
        float k = 13.0F + this.thicknessMod;
        VoxelShape shape = Block.makeCuboidShape(j, j, j, k, k, k);
        if (state.get(NORTH)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(j, j, 0.0F, k, k, 3.0F));
        }
        if (state.get(SOUTH)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(j, j, 13.0F, k, k, 16.0F));
        }
        if (state.get(WEST)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(0.0F, j, j, 3.0F, k, k));
        }
        if (state.get(EAST)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(13.0F, j, j, 16.0F, k, k));
        }
        if (state.get(DOWN)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(j, 0.0F, j, k, 3.0F, k));
        }
        if (state.get(UP)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(j, 13.0F, j, k, 16.0F, k));
        }
        return shape;
    }

    public static BlockState sideChecker(World world, BlockState state, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            state = state.with(DIR_TO_PROPERTY_MAP.get(dir), (world.getBlockState(pos.offset(dir)).getBlock() == BlockRegistry.brambleroot || Block.hasEnoughSolidSide(world, pos.offset(dir), dir.getOpposite())));
        }
        return state;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        return sideChecker(context.getWorld(), getDefaultState(), context.getPos()).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return sideChecker((World) world, state, currentPos);
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
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return fluidIn == Fluids.WATER;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (fluidStateIn.getFluid() == Fluids.WATER) {
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, state.with(WATERLOGGED, true), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            }
            return true;
        }
        return false;
    }

}
