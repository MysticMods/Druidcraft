package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.api.StackPart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherrackBlock;
import net.minecraft.block.NyliumBlock;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import java.util.function.ToIntFunction;

public class GaseousGrowthBlock extends HangingBlock {

    public static final VoxelShape MAIN = Block.makeCuboidShape(5.0F, 0.0F, 5.0F, 11.0F, 16.0F, 11.0F);
    public static final VoxelShape SINGLE = Block.makeCuboidShape(5.0F, 0.0F, 5.0F, 11.0F, 16.0F, 11.0F);
    public static final VoxelShape BULB = Block.makeCuboidShape(4.0F, 4.0F, 4.0F, 12.0F, 12.0F, 12.0F);
    public static final VoxelShape BOTTOM = VoxelShapes.or(BULB, Block.makeCuboidShape(5.0F, 12.0F, 5.0F, 11.0F, 16.0F, 11.0F));

    public GaseousGrowthBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean allowsMovement(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return true;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        Block aboveBlock = world.getBlockState(pos.up()).getBlock();
        if (aboveBlock == this || aboveBlock instanceof NyliumBlock || aboveBlock instanceof NetherrackBlock) {
            return super.isValidPosition(state, world, pos);
        }
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
        return state.get(PART) == StackPart.BOTTOM ? BULB : VoxelShapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
        StackPart part = state.get(PART);
        if (part == StackPart.SINGLE) {
            return SINGLE;
        } else return state.get(PART) == StackPart.BOTTOM ? BOTTOM : MAIN;
    }

    public static ToIntFunction<BlockState> getLightValue(int lightValue) {
        return (state) -> state.get(PART) == StackPart.BOTTOM ? lightValue : 0;
    }

}
