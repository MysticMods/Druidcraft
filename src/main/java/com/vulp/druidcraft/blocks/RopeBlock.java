package com.vulp.druidcraft.blocks;

import com.google.common.collect.Maps;
import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.api.IKnifeable;
import com.vulp.druidcraft.api.RopeConnectionType;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;

@SuppressWarnings("deprecation")
public class RopeBlock extends SixWayBlock implements IKnifeable {
    private static final Direction[] FACING_VALUES = Direction.values();
    public static final EnumProperty<RopeConnectionType> NORTH = EnumProperty.create("north", RopeConnectionType.class);
    public static final EnumProperty<RopeConnectionType> EAST = EnumProperty.create("east", RopeConnectionType.class);
    public static final EnumProperty<RopeConnectionType> SOUTH = EnumProperty.create("south", RopeConnectionType.class);
    public static final EnumProperty<RopeConnectionType> WEST = EnumProperty.create("west", RopeConnectionType.class);
    public static final EnumProperty<RopeConnectionType> UP = EnumProperty.create("up", RopeConnectionType.class);
    public static final EnumProperty<RopeConnectionType> DOWN = EnumProperty.create("down", RopeConnectionType.class);
    public static final BooleanProperty KNOTTED = BooleanProperty.create("knotted");
    protected final VoxelShape[] collisionShapes;
    public static final Map<Direction, EnumProperty<RopeConnectionType>> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });

    public RopeBlock(Properties properties) {
        super(0.12F, properties);
        this.collisionShapes = this.makeCollisionShapes(0.125F);
        this.setDefaultState(this.getDefaultState()
                .with(NORTH, RopeConnectionType.NONE)
                .with(EAST, RopeConnectionType.NONE)
                .with(SOUTH, RopeConnectionType.NONE)
                .with(WEST, RopeConnectionType.NONE)
                .with(UP, RopeConnectionType.NONE)
                .with(DOWN, RopeConnectionType.NONE)
                .with(KNOTTED, false));
    }

    @Override
    public ActionResultType onKnife(ItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);
        Vec3d relative = context.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());
        Druidcraft.LOGGER.debug("onKnife: {}", relative);

        Direction side = getClickedConnection(relative);
        if (side != null) {
            if (!(world.getBlockState(pos.offset(side)).getBlock() instanceof RopeBlock )) {
                BlockState state1 = cycleProperty(state, FACING_TO_PROPERTY_MAP.get(side), context);
                world.setBlockState(pos, state1, 18);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> BlockState cycleProperty(BlockState state, IProperty<T> propertyIn, ItemUseContext context) {
        T value = getAdjacentValue(propertyIn.getAllowedValues(), state.get(propertyIn));
        if (value != RopeConnectionType.NONE) {
            return calculateKnot(state.with(propertyIn, (T) RopeConnectionType.NONE));
        } else if (!state.get(KNOTTED) && context.getPlayer().isSneaking()) {
            return state.with(KNOTTED, true);
        } else return calculateState(state, context.getWorld(), context.getPos());
    }

    private static <T> T getAdjacentValue(Iterable<T> p_195959_0_, @Nullable T p_195959_1_) {
        return Util.getElementAfter(p_195959_0_, p_195959_1_);
    }

    @Nullable
    private static Direction getClickedConnection(Vec3d relative) {
        if (relative.x < 0.25)
            return Direction.WEST;
        if (relative.x > 0.75)
            return Direction.EAST;
        if (relative.y < 0.25)
            return Direction.DOWN;
        if (relative.y > 0.75)
            return Direction.UP;
        if (relative.z < 0.25)
            return Direction.NORTH;
        if (relative.z > 0.75)
            return Direction.SOUTH;
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes[this.getShapeIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.collisionShapes[this.getShapeIndex(state)];
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    private VoxelShape[] makeCollisionShapes(float apothem) {
        float f = 0.5F - apothem;
        float f1 = 0.5F + apothem;
        VoxelShape voxelshape = Block.makeCuboidShape((double)(f * 16.0F), (double)(f * 16.0F), (double)(f * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F));
        VoxelShape[] avoxelshape = new VoxelShape[FACING_VALUES.length];

        for(int i = 0; i < FACING_VALUES.length; ++i) {
            Direction direction = FACING_VALUES[i];
            avoxelshape[i] = VoxelShapes.create(0.5D + Math.min((double)(-apothem), (double)direction.getXOffset() * 0.5D), 0.5D + Math.min((double)(-apothem), (double)direction.getYOffset() * 0.5D), 0.5D + Math.min((double)(-apothem), (double)direction.getZOffset() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getXOffset() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getYOffset() * 0.5D), 0.5D + Math.max((double)apothem, (double)direction.getZOffset() * 0.5D));
        }

        VoxelShape[] avoxelshape1 = new VoxelShape[64];

        for(int k = 0; k < 64; ++k) {
            VoxelShape voxelshape1 = voxelshape;

            for(int j = 0; j < FACING_VALUES.length; ++j) {
                if ((k & 1 << j) != 0) {
                    voxelshape1 = VoxelShapes.or(voxelshape1, avoxelshape[j]);
                }
            }

            avoxelshape1[k] = voxelshape1;
        }

        return avoxelshape1;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, KNOTTED);
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return calculateState(getDefaultState(), context.getWorld(), context.getPos());
    }

    private BlockState calculateKnot (BlockState currentState) {
        int count = 0;

        for (Direction dir : Direction.values()) {
            EnumProperty<RopeConnectionType> prop = FACING_TO_PROPERTY_MAP.get(dir);
            if (prop != null) {
                if (currentState.get(prop) != RopeConnectionType.NONE) {
                count++;
                }
            }
        }

        boolean doKnot = count >= 3 || count == 0;
        return currentState.with(KNOTTED, doKnot);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        return calculateState(state, world, currentPos);
    }

    public static RopeConnectionType beamChecker(BlockState directionState, Direction direction) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            if (directionState.get(SmallBeamBlock.X_AXIS)) {
                if (!directionState.get(SmallBeamBlock.Z_AXIS)) {
                    return RopeConnectionType.TIED_BEAM_2;
                } else return RopeConnectionType.REGULAR;
            } if (directionState.get(SmallBeamBlock.Y_AXIS)) {
                if (!directionState.get(SmallBeamBlock.Z_AXIS)) {
                    return RopeConnectionType.TIED_BEAM_1;
                } else return RopeConnectionType.REGULAR;
            } if (directionState.get(SmallBeamBlock.Z_AXIS)) {
                return RopeConnectionType.REGULAR;
            }
        }

        if (direction == Direction.EAST || direction == Direction.WEST) {
            if (directionState.get(SmallBeamBlock.X_AXIS)) {
                return RopeConnectionType.REGULAR;
            } if (directionState.get(SmallBeamBlock.Y_AXIS)) {
                if (!directionState.get(SmallBeamBlock.X_AXIS)) {
                    return RopeConnectionType.TIED_BEAM_1;
                } else return RopeConnectionType.REGULAR;
            } if (directionState.get(SmallBeamBlock.Z_AXIS)) {
                if (!directionState.get(SmallBeamBlock.X_AXIS)) {
                    return RopeConnectionType.TIED_BEAM_2;
                } else return RopeConnectionType.REGULAR;
            }
        }

        if (direction == Direction.UP || direction == Direction.DOWN) {
            if (directionState.get(SmallBeamBlock.X_AXIS)) {
                if (!directionState.get(SmallBeamBlock.Y_AXIS)) {
                    return RopeConnectionType.TIED_BEAM_2;
                } else return RopeConnectionType.REGULAR;
            } if (directionState.get(SmallBeamBlock.Y_AXIS)) {
                return RopeConnectionType.REGULAR;
            } if (directionState.get(SmallBeamBlock.Z_AXIS)) {
                if (!directionState.get(SmallBeamBlock.Y_AXIS)) {
                    return RopeConnectionType.TIED_BEAM_1;
                } else return RopeConnectionType.REGULAR;
            }
        }

        return RopeConnectionType.NONE;
    }

    private BlockState calculateState(BlockState currentState, IWorld world, BlockPos pos) {

        RopeConnectionType northType = RopeConnectionType.NONE;
        BlockState northState = world.getBlockState(pos.offset(Direction.NORTH));
        if (northState.func_224755_d(world, pos.offset(Direction.NORTH), Direction.NORTH.getOpposite()) || northState.getBlock() == this) {
            northType = RopeConnectionType.REGULAR;
        } else if (northState.getBlock().isIn(BlockTags.FENCES)) {
            northType = RopeConnectionType.TIED_FENCE;
        } else if (northState.getBlock() instanceof SmallBeamBlock) {
            northType = beamChecker(northState, Direction.NORTH);
        }

        RopeConnectionType eastType = RopeConnectionType.NONE;
        BlockState eastState = world.getBlockState(pos.offset(Direction.EAST));
        if (eastState.func_224755_d(world, pos.offset(Direction.EAST), Direction.EAST.getOpposite()) || eastState.getBlock() == this) {
            eastType = RopeConnectionType.REGULAR;
        } else if (eastState.getBlock().isIn(BlockTags.FENCES)) {
            eastType = RopeConnectionType.TIED_FENCE;
        } else if (eastState.getBlock() instanceof SmallBeamBlock) {
            eastType = beamChecker(eastState, Direction.EAST);
        }


        RopeConnectionType southType = RopeConnectionType.NONE;
        BlockState southState = world.getBlockState(pos.offset(Direction.SOUTH));
        if (southState.func_224755_d(world, pos.offset(Direction.SOUTH), Direction.SOUTH.getOpposite()) || southState.getBlock() == this) {
            southType = RopeConnectionType.REGULAR;
        } else if (southState.getBlock().isIn(BlockTags.FENCES)) {
            southType = RopeConnectionType.TIED_FENCE;
        } else if (southState.getBlock() instanceof SmallBeamBlock) {
            southType = beamChecker(southState, Direction.SOUTH);
        }

        RopeConnectionType westType = RopeConnectionType.NONE;
        BlockState westState = world.getBlockState(pos.offset(Direction.WEST));
        if (westState.func_224755_d(world, pos.offset(Direction.WEST), Direction.WEST.getOpposite()) || westState.getBlock() == this) {
            westType = RopeConnectionType.REGULAR;
        } else if (westState.getBlock().isIn(BlockTags.FENCES)) {
            westType = RopeConnectionType.TIED_FENCE;
        } else if (westState.getBlock() instanceof SmallBeamBlock) {
            westType = beamChecker(westState, Direction.WEST);
        }

        RopeConnectionType upType = RopeConnectionType.NONE;
        BlockState upState = world.getBlockState(pos.offset(Direction.UP));
        if (upState.func_224755_d(world, pos.offset(Direction.UP), Direction.UP.getOpposite()) || upState.getBlock() == this || upState.getBlock().isIn(BlockTags.FENCES)) {
            upType = RopeConnectionType.REGULAR;
        } else if (upState.getBlock() instanceof SmallBeamBlock) {
            upType = beamChecker(upState, Direction.UP);
        }

        RopeConnectionType downType = RopeConnectionType.NONE;
        BlockState downState = world.getBlockState(pos.offset(Direction.DOWN));
        if (downState.func_224755_d(world, pos.offset(Direction.DOWN), Direction.DOWN.getOpposite()) || downState.getBlock() == this || downState.getBlock().isIn(BlockTags.FENCES) || downState.getBlock() instanceof RopeLanternBlock || ((downState.getBlock() instanceof GrowthLampBlock || (downState.getBlock() instanceof RopeableLanternBlock) && downState.get(RopeableLanternBlock.HANGING) && downState.get(RopeableLanternBlock.ROPED)))) {
            downType = RopeConnectionType.REGULAR;
        } else if (downState.getBlock() instanceof SmallBeamBlock) {
            downType = beamChecker(downState, Direction.DOWN);
        }

        return calculateKnot(currentState
                .with(NORTH, northType)
                .with(EAST, eastType)
                .with(SOUTH, southType)
                .with(WEST, westType)
                .with(UP, upType)
                .with(DOWN, downType));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        Item item = itemstack.getItem();

        if (item == Items.LANTERN && hit.getFace() == Direction.DOWN && worldIn.getBlockState(pos.down()).getMaterial().isReplaceable()) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }
            worldIn.setBlockState(pos.down(), BlockRegistry.rope_lantern.getDefaultState());
            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LANTERN_PLACE, SoundCategory.BLOCKS, 1.0F, 0.88F, true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected int getShapeIndex(BlockState state) {
        int i = 0;

        for(int j = 0; j < Direction.values().length; ++j) {
            if (state.get(FACING_TO_PROPERTY_MAP.get(Direction.values()[j])) != RopeConnectionType.NONE) {
                i |= 1 << j;
            }
        }

        return i;
    }

    public static RopeConnectionType getConnection(BlockState state, Direction side) {
        return state.get(FACING_TO_PROPERTY_MAP.get(side));
    }

}
