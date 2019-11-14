package com.vulp.druidcraft.blocks;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

@SuppressWarnings("deprecation")
public class RopeBlock extends SixWayBlock implements IWaterLoggable {
    public static final BooleanProperty KNOTTED = BooleanProperty.create("knotted");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RopeBlock(Properties properties) {
        super(0.0625F, properties);
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(KNOTTED))
            return VoxelShapes.or(Block.makeCuboidShape(6.0d, 6.0d, 6.0d, 10.0d, 10.0d, 10.0d), this.shapes[this.getShapeIndex(state)]);
        return this.shapes[this.getShapeIndex(state)];
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, KNOTTED, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return calculateState(getDefaultState(), context.getWorld(), context.getPos());
    }

    private BlockState calculateKnot (BlockState currentState) {
      int count = 0;

      for (Direction dir : Direction.values()) {
        BooleanProperty prop = FACING_TO_PROPERTY_MAP.get(dir);
        if (prop != null) {
          if (currentState.get(prop)) {
            count++;
          }
        }
      }

      return currentState.with(KNOTTED, count >= 3);
    }

  @Override
  public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return calculateState(state, world, facingPos);
  }

  private BlockState calculateState(BlockState currentState, IWorld world, BlockPos pos) {
        BlockState northState = world.getBlockState(pos.offset(Direction.NORTH));
        boolean north = northState.func_224755_d(world, pos.offset(Direction.NORTH), Direction.NORTH.getOpposite()) || northState.getBlock() == this;

        BlockState eastState = world.getBlockState(pos.offset(Direction.EAST));
        boolean east = eastState.func_224755_d(world, pos.offset(Direction.EAST), Direction.EAST.getOpposite()) || eastState.getBlock() == this;

        BlockState southState = world.getBlockState(pos.offset(Direction.SOUTH));
        boolean south = southState.func_224755_d(world, pos.offset(Direction.SOUTH), Direction.SOUTH.getOpposite()) || southState.getBlock() == this;

        BlockState westState = world.getBlockState(pos.offset(Direction.WEST));
        boolean west = westState.func_224755_d(world, pos.offset(Direction.WEST), Direction.WEST.getOpposite()) || westState.getBlock() == this;

        BlockState upState = world.getBlockState(pos.offset(Direction.UP));
        boolean up = upState.func_224755_d(world, pos.offset(Direction.UP), Direction.UP.getOpposite()) || upState.getBlock() == this;

        BlockState downState = world.getBlockState(pos.offset(Direction.DOWN));
        boolean down = downState.func_224755_d(world, pos.offset(Direction.DOWN), Direction.DOWN.getOpposite()) || downState.getBlock() == this;

        return calculateKnot(currentState
                .with(NORTH, north)
                .with(EAST, east)
                .with(SOUTH, south)
                .with(WEST, west)
                .with(UP, up)
                .with(DOWN, down));
    }
}
