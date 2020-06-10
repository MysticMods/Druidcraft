package com.vulp.druidcraft.blocks;

import com.google.common.collect.Maps;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.IKnifeable;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;

public class RopeBlock extends Block implements IKnifeable {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty KNOTTED = BooleanProperty.create("knotted");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, EAST);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });

    public RopeBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(UP, false)
                .with(DOWN, false)
                .with(KNOTTED, false)
                .with(WATERLOGGED, false));
    }

    @Override
    public ActionResultType onKnife(ItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);
        Vec3d relative = context.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());
        Druidcraft.LOGGER.debug("onKnife: {}", relative);

        Direction side = getClickedConnection(relative);
        if (side != null) {
            if (!(world.getBlockState(pos.offset(side)).getBlock() instanceof RopeBlock )) {
                BlockState state1 = cycleProperty(state, side, context);
                world.setBlockState(pos, state1, 18);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }

    private BlockState cycleProperty(BlockState state, Direction sideUsed, ItemUseContext context) {
        BooleanProperty property = FACING_TO_PROPERTY_MAP.get(sideUsed);
        if (!context.getPlayer().isSneaking()) {
            if (state.get(property)) {
                return calculateKnot(state.with(property, false));
            } else {
                if (context.getWorld().getBlockState(context.getPos()).isSolidSide(context.getWorld(), context.getPos().offset(sideUsed), sideUsed)) {
                    return calculateKnot(state.with(property, true));
                }
            }
        }
        else if (context.getPlayer().isSneaking()) {
            if (!state.get(KNOTTED))
                return state.with(KNOTTED, true);
            else if (!calculateKnot(state).get(KNOTTED)) {
                return state.with(KNOTTED, false);
            }
        }
        return calculateState(state, context.getWorld(), context.getPos());
    }

    @Nullable
    private static Direction getClickedConnection(Vec3d relative) {
        if (relative.x < 0.25)
            return Direction.WEST;
        if (relative.x > 0.75)
            return Direction.EAST;
        if (relative.y < 0.25)
            return Direction.DOWN;
        if (relative.y > 0.75)
            return Direction.UP;
        if (relative.z < 0.25)
            return Direction.NORTH;
        if (relative.z > 0.75)
            return Direction.SOUTH;
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = Block.makeCuboidShape(6.0f, 6.0f, 6.0f, 10.0f, 10.0f, 10.0f);
        if (state.get(NORTH)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(6.0f, 6.0f, 0.0f, 10.0f, 10.0f, 6.0f));
        }
        if (state.get(SOUTH)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(6.0f, 6.0f, 10.0f, 10.0f, 10.0f, 16.0f));
        }
        if (state.get(WEST)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(0.0f, 6.0f, 6.0f, 6.0f, 10.0f, 10.0f));
        }
        if (state.get(EAST)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(10.0f, 6.0f, 6.0f, 16.0f, 10.0f, 10.0f));
        }
        if (state.get(DOWN)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(6.0f, 0.0f, 6.0f, 10.0f, 6.0f, 10.0f));
        }
        if (state.get(UP)) {
            shape = VoxelShapes.or(shape, Block.makeCuboidShape(6.0f, 10.0f, 6.0f, 10.0f, 16.0f, 10.0f));
        }
        return shape;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, KNOTTED, WATERLOGGED);
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return calculateState(getDefaultState(), context.getWorld(), context.getPos()).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    private BlockState calculateKnot (BlockState currentState) {
        int count = 0;

        if (currentState.get(NORTH))
            count++;
        if (currentState.get(SOUTH))
            count++;
        if (currentState.get(EAST))
            count++;
        if (currentState.get(WEST))
            count++;
        if (currentState.get(UP))
            count++;
        if (currentState.get(DOWN))
            count++;

        boolean doKnot = count > 2 || count == 0;
        return currentState.with(KNOTTED, doKnot);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return calculateState(state, world, currentPos);
    }

    private BlockState calculateState(BlockState currentState, IWorld world, BlockPos pos) {

        boolean northType = false;
        BlockState northState = world.getBlockState(pos.offset(Direction.NORTH));
        if (northState.isSolidSide(world, pos.offset(Direction.NORTH), Direction.NORTH.getOpposite()) || northState.getBlock() instanceof RopeBlock || northState.getBlock() instanceof SmallBeamBlock) {
            northType = true;
        }

        boolean eastType = false;
        BlockState eastState = world.getBlockState(pos.offset(Direction.EAST));
        if (eastState.isSolidSide(world, pos.offset(Direction.EAST), Direction.EAST.getOpposite()) || eastState.getBlock() instanceof RopeBlock || eastState.getBlock() instanceof SmallBeamBlock) {
            eastType = true;
        }

        boolean southType = false;
        BlockState southState = world.getBlockState(pos.offset(Direction.SOUTH));
        if (southState.isSolidSide(world, pos.offset(Direction.SOUTH), Direction.SOUTH.getOpposite()) || southState.getBlock() instanceof RopeBlock || southState.getBlock() instanceof SmallBeamBlock) {
            southType = true;
        }

        boolean westType = false;
        BlockState westState = world.getBlockState(pos.offset(Direction.WEST));
        if (westState.isSolidSide(world, pos.offset(Direction.WEST), Direction.WEST.getOpposite()) || westState.getBlock() instanceof RopeBlock || westState.getBlock() instanceof SmallBeamBlock) {
            westType = true;
        }

        boolean upType = false;
        BlockState upState = world.getBlockState(pos.offset(Direction.UP));
        if (upState.isSolidSide(world, pos.offset(Direction.UP), Direction.UP.getOpposite()) || upState.getBlock() instanceof RopeBlock || upState.getBlock() instanceof SmallBeamBlock) {
            upType = true;
        }

        boolean downType = false;
        BlockState downState = world.getBlockState(pos.offset(Direction.DOWN));
        if (downState.isSolidSide(world, pos.offset(Direction.DOWN), Direction.DOWN.getOpposite()) || downState.getBlock() instanceof RopeBlock || downState.getBlock() instanceof RopeLanternBlock || downState.getBlock() instanceof SmallBeamBlock || ((downState.getBlock() instanceof RopeableLanternBlock || downState.getBlock() instanceof GrowthLampBlock) && (downState.get(RopeableLanternBlock.HANGING) && downState.get(RopeableLanternBlock.ROPED)))) {
            downType = true;
        }

        BlockState finalState = calculateKnot(currentState
                .with(NORTH, northType)
                .with(EAST, eastType)
                .with(SOUTH, southType)
                .with(WEST, westType)
                .with(UP, upType)
                .with(DOWN, downType));

        if (finalState == currentState) {
            return currentState;
        } else {
            return finalState;
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();

        if (item == Items.LANTERN && hit.getFace() == Direction.DOWN && worldIn.getBlockState(pos.down()).getMaterial().isReplaceable()) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }
            worldIn.setBlockState(pos.down(), BlockRegistry.rope_lantern.getDefaultState());
            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LANTERN_PLACE, SoundCategory.BLOCKS, 1.0F, 0.88F, true);
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}