package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Random;

public class HempBlock extends DynamicCropBlock implements IGrowable {
    private static MethodHandle METHODgetGrowthChance = null;

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public HempBlock(Properties properties) {
        super(properties);
    }

    private float growthChance (Block blockIn, IBlockReader worldIn, BlockPos pos) {
        if (METHODgetGrowthChance == null) {
           MethodHandles.Lookup lookup = MethodHandles.lookup();
           Method growth = ObfuscationReflectionHelper.findMethod(CropsBlock.class, "func_180672_a");
           growth.setAccessible(true);
           try {
               METHODgetGrowthChance = lookup.unreflect(growth);
           } catch (IllegalAccessException e) {
               Druidcraft.LOGGER.error("Unable to unreflect growthChance", e);
               return 1;
           }
        }
        try {
            return (float) METHODgetGrowthChance.invokeExact(blockIn, worldIn, pos);
        } catch (Throwable e) {
            Druidcraft.LOGGER.error("Unable to invoke methodhandle for growthChance", e);
            return 1;
        }
    }


    @Override
    public IntegerProperty getAgeProperty () {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    /**
     * Determines if this state is valid. i.e., not a bottom above a bottom, a top below a bottom, or a top above a top.
     * @param state Current state.
     * @param world World.
     * @param pos Current position.
     * @return true if a bottom block with no hemp above or below, true if a top block with a bottom block below, true if a bottom block of full age with a hemp block above, otherwise false.
     */
    public boolean validState (BlockState state, IBlockReader world, BlockPos pos) {
        BlockState down = world.getBlockState(pos.down());
        if (down.getBlock() == this) {
            if (state.get(HALF) != Half.TOP || down.get(HALF) != Half.BOTTOM) {
                return false;
            }
        }
        BlockState up = world.getBlockState(pos.up());
        if (up.getBlock() == this) {
            return isMaxAge(state) && state.get(HALF) == Half.BOTTOM && up.get(HALF) == Half.TOP;
        }

        // This may cause invalidity failure.
        return state.get(HALF) == Half.BOTTOM;

        //return true;
    }

    @Override
    public boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        if (!validState(state, world, pos)) {
            return false;
        }

        // Otherwise fall back on the super.
        return super.isValidGround(state, world, pos);
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return !isMaxAge(state) && validState(state, world, pos);
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random, BlockPos blockPos, BlockState state) {
        // Handle hovering hemp.
        if (!validState(state, serverWorld, blockPos)) {
            serverWorld.destroyBlock(blockPos, true);
            return;
        }

        int i = this.getAge(state) + this.getBonemealAgeIncrease(serverWorld);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        if (this.getAge(state) != j) {
            serverWorld.setBlockState(blockPos, this.withAge(i), 2);
        }
        else if (this.getAge(state) == j) {
            BlockState up = serverWorld.getBlockState(blockPos.up());
            if (state.get(HALF) == Half.BOTTOM && (up.getMaterial().isReplaceable() || up.isAir(serverWorld, blockPos.up()))) {
                serverWorld.setBlockState(blockPos.up(), this.getDefaultState().with(HALF, Half.TOP));
            }
        }
    }


    @Override
    // Override random tick instead of tick
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Standard unloaded vanilla chunk check
        if (!world.isAreaLoaded(pos, 1)) {
            return;
        }

        // I'm not sure we need to check ground type here.
        if (!validState(state, world, pos)) {
            world.destroyBlock(pos, true);
        }

        // Use vanilla logic to handle the first half
        super.randomTick(state, world, pos, random);
        state = world.getBlockState(pos);

        // Only place the top half if we're a bottom
        if (isMaxAge(state) && state.get(HALF) == Half.BOTTOM) {
            BlockState up = world.getBlockState(pos.up());
            if (up.getBlock() != this && up.getMaterial().isReplaceable()) {
                float f = growthChance(state.getBlock(), world, pos.up());
                if (ForgeHooks.onCropsGrowPre(world, pos, state, random.nextInt((int)(25f/f)+1) == 0)) {
                    BlockState upState = this.getDefaultState().with(HALF, Half.TOP);
                    world.setBlockState(pos.up(), upState);
                    ForgeHooks.onCropsGrowPost(world, pos.up(), upState);
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        if (plantable == this && state.getBlock() == this) {
            return state.get(HALF) == Half.BOTTOM;
        }
        return super.canSustainPlant(state, world, pos, facing, plantable);
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return ItemRegistry.hemp_seeds;
    }

    @Override
    protected int getBonemealAgeIncrease(World worldIn) {
        return worldIn.rand.nextInt(1) + 1;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!validState(stateIn, worldIn, currentPos)) {
            // Is this sufficient or should we be destroying?
            return Blocks.AIR.getDefaultState();
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.CROP;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext selectionContext) {
        return Block.makeCuboidShape(4, 0, 4, 12.0d, 4.0d * (state.get(getAgeProperty()) + 1), 12.0d);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }

}