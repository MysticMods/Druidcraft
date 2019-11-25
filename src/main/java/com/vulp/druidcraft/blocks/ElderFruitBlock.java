package com.vulp.druidcraft.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.vulp.druidcraft.api.CropLifeStageType;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.server.permission.context.WorldContext;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;


public class ElderFruitBlock extends CropBlock implements IGrowable {

    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;
    public static final EnumProperty<CropLifeStageType> LIFE_STAGE = EnumProperty.create("life_stage", CropLifeStageType.class);

    public ElderFruitBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(this.getAgeProperty(), 0).with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape voxelshape = VoxelShapes.empty();
        Vec3d vec3d = state.getOffset(worldIn, pos);

        if (state.get(FACING) == Direction.UP) {
            voxelshape = VoxelShapes.or(voxelshape, Block.makeCuboidShape(2.0D, 15.0D, 2.0D, 14.0D, 16.0D, 14.0D));
        }
        if (state.get(FACING) == Direction.DOWN) {
            voxelshape = VoxelShapes.or(voxelshape, Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D));
        }
        if (state.get(FACING) == Direction.NORTH) {
            voxelshape = VoxelShapes.or(voxelshape, Block.makeCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 1.0D));
        }
        if (state.get(FACING) == Direction.EAST) {
            voxelshape = VoxelShapes.or(voxelshape, Block.makeCuboidShape(15.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D));
        }
        if (state.get(FACING) == Direction.SOUTH) {
            voxelshape = VoxelShapes.or(voxelshape, Block.makeCuboidShape(2.0D, 2.0D, 15.0D, 14.0D, 14.0D, 16.0D));
        }
        if (state.get(FACING) == Direction.WEST) {
            voxelshape = VoxelShapes.or(voxelshape, Block.makeCuboidShape(0.0D, 2.0D, 2.0D, 1.0D, 14.0D, 14.0D));
        }
        return voxelshape.withOffset(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public Vec3d getOffset(BlockState state, IBlockReader worldIn, BlockPos pos) {
        long i = MathHelper.getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
        return new Vec3d(
                !(state.get(FACING) == Direction.EAST || state.get(FACING) == Direction.WEST) ?(((i & 15L) / 15.0F) - 0.5D) * 0.5D : 0.0D,
                !(state.get(FACING) == Direction.UP || state.get(FACING) == Direction.DOWN) ? (((i >> 4 & 15L) / 15.0F) - 1.0D) * 0.2D : 0.0D,
                !(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) ? (((i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D : 0.0D);
    }

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        for (Direction direction : context.getNearestLookingDirections()) {
            BlockState blockstate = this.getDefaultState().with(FACING, Direction.NORTH);;
            if (direction == Direction.UP) {
                blockstate = this.getDefaultState().with(FACING, Direction.UP);
            } if (direction == Direction.DOWN) {
                blockstate = this.getDefaultState().with(FACING, Direction.DOWN);
            } if (direction == Direction.NORTH) {
                blockstate = this.getDefaultState().with(FACING, Direction.NORTH);
            } if (direction == Direction.SOUTH) {
                blockstate = this.getDefaultState().with(FACING, Direction.SOUTH);
            } if (direction == Direction.EAST) {
                blockstate = this.getDefaultState().with(FACING, Direction.EAST);
            } if (direction == Direction.WEST) {
                blockstate = this.getDefaultState().with(FACING, Direction.WEST);
            }

            if (isValidPosition(blockstate, context.getWorld(), context.getPos())) {
                return blockstate;
            }
        }
        return null;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 5;
    }

    @Override
    protected int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    @Override
    public BlockState withAge(int age) {
        return this.getDefaultState().with(this.getAgeProperty(), Integer.valueOf(age));
    }

    @Override
    public boolean isMaxAge(BlockState state) {
        return state.get(this.getAgeProperty()) >= this.getMaxAge();
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightSubtracted(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, worldIn, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0)) {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }


    }

    public static CropLifeStageType checkCropLife(World world) {
        double timeValue = world.getWorldInfo().getGameTime() % 48000;
        double twoDayDecimal = timeValue - Math.floor(timeValue);
        if (twoDayDecimal >= 0.375) {
            if (twoDayDecimal >= 0.75) {
                return CropLifeStageType.BERRY;
            }
            return CropLifeStageType.FLOWER;
        }
        else return CropLifeStageType.NONE;
    }

    @Override
    public void grow(World worldIn, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        worldIn.setBlockState(pos, this.withAge(i), 2);
    }

    @Override
    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.nextInt(worldIn.rand, 2, 5);
    }

    protected static float getGrowthChance(Block blockIn, IBlockReader worldIn, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos = pos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
                if (blockstate.canSustainPlant(worldIn, blockpos.add(i, 0, j), net.minecraft.util.Direction.UP, (net.minecraftforge.common.IPlantable)blockIn)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(worldIn, blockpos.add(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock();
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    public Boolean isOnLeaves(IWorldReader world, BlockPos pos) {
        Direction[] var3 = Direction.values();

        for (Direction direction : var3) {
            if (world.getBlockState(pos.offset(direction.getOpposite())).getBlock() == Blocks.BIRCH_LEAVES) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return isOnLeaves(worldIn, pos);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof RavagerEntity && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entityIn)) {
            worldIn.destroyBlock(pos, true);
        }

        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    /**
     * Whether this IGrowable can grow
     */
    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        this.grow(worldIn, pos, state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, FACING);
    }

}
