package com.vulp.druidcraft.blocks;

import com.google.common.collect.Maps;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.ConnectionType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;

public class RopeBlock extends SixWayBlock {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });


    protected RopeBlock(float apothem, Properties properties) {
        super(0.0625F, properties);
        this.setDefaultState(this.getDefaultState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(UP, false)
                .with(DOWN, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {
        return getState(getDefaultState(), ctx.getWorld(), ctx.getPos());
    }

    private boolean hasNode(World world, BlockPos pos, BlockState state, Direction direction) {
        if (getDirection() != BlockDirection.NONE && state.get(getDirection().getProperty()).getOpposite() == direction) {
            return false;
        }

        TileEntity tile = world.getTileEntity(pos);
        if (tile == null) {
            return false;
        }

        return tile.getCapability(NetworkNodeProxyCapability.NETWORK_NODE_PROXY_CAPABILITY, direction).isPresent();
    }

    private BlockState getState(BlockState currentState, World world, BlockPos pos) {
        boolean north = hasNode(world, pos.offset(Direction.NORTH), currentState, Direction.SOUTH);
        boolean east = hasNode(world, pos.offset(Direction.EAST), currentState, Direction.WEST);
        boolean south = hasNode(world, pos.offset(Direction.SOUTH), currentState, Direction.NORTH);
        boolean west = hasNode(world, pos.offset(Direction.WEST), currentState, Direction.EAST);
        boolean up = hasNode(world, pos.offset(Direction.UP), currentState, Direction.DOWN);
        boolean down = hasNode(world, pos.offset(Direction.DOWN), currentState, Direction.UP);

        return currentState
                .with(NORTH, north)
                .with(EAST, east)
                .with(SOUTH, south)
                .with(WEST, west)
                .with(UP, up)
                .with(DOWN, down);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (worldIn.getBlockState(facingPos) == BlockRegistry.rope.getDefaultState())

        BooleanProperty property = FACING_TO_PROPERTY_MAP.get(facing);
        return stateIn.with(property, createConnection(worldIn, facingPos));
    }
}
