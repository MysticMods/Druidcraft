package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.api.StackPart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HangingBlock extends Block {

    public static final EnumProperty<StackPart> PART = StackPart.getEnumProperty();

    public HangingBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(PART, StackPart.MIDDLE));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        return this.isValidPosition(state, world, currentPos) ? updatePart(super.updatePostPlacement(state, direction, facingState, world, currentPos, facingPos), (World) world, currentPos) : Blocks.AIR.getDefaultState();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        return updatePart(this.getDefaultState(), world, pos);
    }

    public BlockState updatePart(BlockState state, World world, BlockPos pos) {
        BlockState above = world.getBlockState(pos.up());
        BlockState below = world.getBlockState(pos.down());
        if (above.getBlock() == this) {
            if (below.getBlock() == this) {
                return state.with(PART, StackPart.MIDDLE);
            } else return state.with(PART, StackPart.BOTTOM);
        } else if (below.getBlock() == this) {
            return state.with(PART, StackPart.TOP);
        } else return state.with(PART, StackPart.SINGLE);
    }

    @Override
    public BlockRenderType getRenderType(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }
}
