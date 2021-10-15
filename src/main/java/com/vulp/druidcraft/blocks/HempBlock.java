package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class HempBlock extends CropsBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;

    public HempBlock(Properties properties) {
        super(properties);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return ItemRegistry.hemp_seeds;
    }

    private static VoxelShape[] SHAPES = new VoxelShape[]{
            Block.makeCuboidShape(4, 0, 4, 12.0d, 4.0d * 1, 12.0d),
            Block.makeCuboidShape(4, 0, 4, 12.0d, 4.0d * 2, 12.0d),
            Block.makeCuboidShape(4, 0, 4, 12.0d, 4.0d * 3, 12.0d),
            Block.makeCuboidShape(4, 0, 4, 12.0d, 4.0d * 4, 12.0d)
    };

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext selectionContext) {
        return SHAPES[state.get(getAgeProperty())];
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}