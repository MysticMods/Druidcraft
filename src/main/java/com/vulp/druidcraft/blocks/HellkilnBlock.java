package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.blocks.tileentities.HellkilnIgniterTileEntity;
import com.vulp.druidcraft.blocks.tileentities.HellkilnTileEntity;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class HellkilnBlock extends ContainerBlock {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

    public HellkilnBlock(Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, false).with(BOTTOM, true));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            if (!state.get(BOTTOM)) {
                pos = pos.down();
            }
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof HellkilnTileEntity) {
                player.openContainer((INamedContainerProvider) tileentity);
            }
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof HellkilnTileEntity) {
                ((HellkilnTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }
        if (!worldIn.isRemote) {
            BlockPos blockpos = pos.up();
            worldIn.setBlockState(blockpos, state.with(BOTTOM, false), 3);
            worldIn.func_230547_a_(pos, Blocks.AIR);
            state.updateNeighbours(worldIn, pos, 3);
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof HellkilnTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (HellkilnTileEntity)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos().up();
        return context.getWorld().getBlockState(pos).isReplaceable(context) ? this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()) : null;
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(BOTTOM) ? Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D) : VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.makeCuboidShape(3.0D, 4.0D, 3.0D, 13.0D, 16.0D, 13.0D));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        TileEntity tileEntity = worldIn.getTileEntity(currentPos);
        if (tileEntity instanceof HellkilnTileEntity) {
            ((HellkilnTileEntity)tileEntity).refreshIgniterList();
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote) {
            boolean bottom = state.get(BOTTOM);
            if (bottom) {
                BlockPos blockpos = pos.up();
                BlockState blockstate = worldIn.getBlockState(blockpos);
                if (blockstate.getBlock() == this && !blockstate.get(BOTTOM)) {
                    worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
                    worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
                }
            }
            if (!bottom) {
                BlockPos blockpos = pos.down();
                BlockState blockstate = worldIn.getBlockState(blockpos);
                if (blockstate.getBlock() == this && blockstate.get(BOTTOM)) {
                    worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
                    worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
                }
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new HellkilnTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, BOTTOM);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            if (!stateIn.get(BOTTOM)) {
                for (int i = 0; i < 3; ++i) {
                    double d0 = (double) pos.getX() + 0.5D;
                    double d1 = (double) pos.getY() + 1.0D;
                    double d2 = (double) pos.getZ() + 0.5D;
                    worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            } else {
                double d0 = (double) pos.getX() + 0.5F;
                double d1 = (double) pos.getY();
                double d2 = (double) pos.getZ() + 0.5F;
                Direction direction = stateIn.get(FACING);
                Direction.Axis direction$axis = direction.getAxis();
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * 0.52D : d4;
                double d6 = rand.nextDouble() * 6.0D / 16.0D;
                double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * 0.52D : d4;
                worldIn.addParticle(ParticleRegistry.fiery_spark, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                worldIn.addParticle(ParticleRegistry.fiery_spark, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                if (rand.nextDouble() < 0.1D) {
                    worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }
            }
        }
    }

}
