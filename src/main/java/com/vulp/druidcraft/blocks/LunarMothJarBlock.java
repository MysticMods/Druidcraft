package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.blocks.tileentities.LunarMothJarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class LunarMothJarBlock extends RopeableLanternBlock {

    public static IntegerProperty COLOR = IntegerProperty.create("color", 1, 6);

    public LunarMothJarBlock(Block.Properties properties, int mothColor) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HANGING, false).with(ROPED, false).with(WATERLOGGED, false).with(COLOR, mothColor));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape lantern_grounded = VoxelShapes.or(Block.makeCuboidShape(4.0f, 0.0f, 4.0f, 12.0f, 8.0f, 12.0f), Block.makeCuboidShape(5.0f, 8.0f, 5.0f, 11.0f, 10.0f, 11.0f));
        VoxelShape lantern_hanging = Block.makeCuboidShape(4.0f, 1.0f, 4.0f, 12.0f, 12.0f, 12.0f);

        if (state.get(HANGING)) {
            return VoxelShapes.or(lantern_hanging, VoxelShapes.or(Block.makeCuboidShape(5.0f, 14.0f, 5.0f, 14.0f, 14.0f, 11.0f), Block.makeCuboidShape(6.0f, 14.0f, 6.0f, 10.0f, 16.0f, 10.0f)));
        }
        return lantern_grounded;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LunarMothJarTileEntity(state.get(COLOR), state.get(HANGING));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HANGING, ROPED, WATERLOGGED, COLOR);
    }
}