package com.vulp.druidcraft.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;

import javax.annotation.Nullable;

public class BedrollBlock extends BedBlock implements IBucketPickupHandler, ILiquidContainer {
    private final DyeColor color;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BedrollBlock(DyeColor colorIn, Properties properties) {
        super(colorIn, properties);
        this.color = colorIn;
        this.setDefaultState(this.stateContainer.getBaseState().with(PART, BedPart.FOOT).with(OCCUPIED, Boolean.valueOf(false)).with(WATERLOGGED, false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, PART, OCCUPIED, WATERLOGGED);
    }

    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        } else {
            if (state.get(PART) != BedPart.HEAD) {
                pos = pos.offset(state.get(HORIZONTAL_FACING));
                state = worldIn.getBlockState(pos);
                if (state.getBlock() != this) {
                    return true;
                }
            }

            net.minecraftforge.common.extensions.IForgeDimension.SleepResult sleepResult = worldIn.dimension.canSleepAt(player, pos);
            if (sleepResult != net.minecraftforge.common.extensions.IForgeDimension.SleepResult.BED_EXPLODES) {
                if (sleepResult == net.minecraftforge.common.extensions.IForgeDimension.SleepResult.DENY) return true;
                if (state.get(OCCUPIED)) {
                    player.sendStatusMessage(new TranslationTextComponent("block.druidcraft.bedroll.occupied"), true);
                    return true;
                } else {
                    player.trySleep(pos).ifLeft((p_220173_1_) -> {
                        if (p_220173_1_ != null) {
                            player.sendStatusMessage(p_220173_1_.getMessage(), true);
                        }

                    });
                    return true;
                }
            } else {
                worldIn.removeBlock(pos, false);
                BlockPos blockpos = pos.offset(state.get(HORIZONTAL_FACING).getOpposite());
                if (worldIn.getBlockState(blockpos).getBlock() == this) {
                    worldIn.removeBlock(blockpos, false);
                }

                worldIn.createExplosion((Entity)null, DamageSource.netherBedExplosion(), (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
                return true;
            }
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

    @SuppressWarnings("deprecation")
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return fluidIn == Fluids.WATER;
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
        if (fluidStateIn.getFluid() == Fluids.WATER) {
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean water = context.getWorld().getBlockState(context.getPos()).getBlock() == Blocks.WATER;
        return super.getStateForPlacement(context).with(WATERLOGGED, water);
    }

    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.fall(fallDistance, 1.0F);
    }

    public void onLanded(IBlockReader worldIn, Entity entityIn) {
        entityIn.setMotion(entityIn.getMotion().mul(1.0D, 0.0D, 1.0D));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(PART) == BedPart.FOOT) {
            Direction direction = state.get(HORIZONTAL_FACING);
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    return Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 2.0D, 16.0D);
                } else {
                    return Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 16.0D, 2.0D, 15.0D);
                }
            } else {
            Direction direction = state.get(HORIZONTAL_FACING).getOpposite();
            VoxelShape matNS = Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 2.0D, 16.0D);
            VoxelShape matEW = Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 16.0D, 2.0D, 15.0D);
            switch(direction) {
                case NORTH:
                    return VoxelShapes.or(VoxelShapes.or(Block.makeCuboidShape(2.0D, 0.0D, 9.0D, 14.0D, 4.0D, 15.0D), Block.makeCuboidShape(0.0D, 0.0D, 2.0D, 16.0D, 3.0D, 8.0D)), matNS);
                case SOUTH:
                    return VoxelShapes.or(VoxelShapes.or(Block.makeCuboidShape(2.0D, 2.0D, 1.0D, 14.0D, 4.0D, 7.0D), Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 3.0D, 14.0D)), matNS);
                case WEST:
                    return VoxelShapes.or(VoxelShapes.or(Block.makeCuboidShape(9.0D, 0.0D, 2.0D, 15.0D, 4.0D, 14.0D), Block.makeCuboidShape(2.0D, 0.0D, 0.0D, 8.0D, 3.0D, 16.0D)), matEW);
                default:
                    return VoxelShapes.or(VoxelShapes.or(Block.makeCuboidShape(1.0D, 0.0D, 2.0D, 7.0D, 4.0D, 14.0D), Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 14.0D, 3.0D, 16.0D)), matEW);
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch (direction) {
            case NORTH:
            case SOUTH:
                return Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 2.0D, 16.0D);
            default:
                return Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 16.0D, 2.0D, 15.0D);
        }
    }


    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }
}
