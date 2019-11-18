package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class SmallBeamBlock extends RotatedPillarBlock {

    private final VoxelShape beamShapeX = Block.makeCuboidShape(0.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D);
    private final VoxelShape beamShapeY = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private final VoxelShape beamShapeZ = Block.makeCuboidShape(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 16.0D);

    public SmallBeamBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(AXIS)) {
            case X:
                return this.beamShapeX;
            case Z:
                return this.beamShapeZ;
            default:
                return this.beamShapeY;
        }
    }

}
