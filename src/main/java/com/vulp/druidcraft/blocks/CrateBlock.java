package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.api.CrateType;
import com.vulp.druidcraft.blocks.tileentities.CrateTileEntity;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class CrateBlock extends ContainerBlock {
    //public static final BooleanProperty PROPERTY_OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty TYPE = EnumProperty.create("type", CrateType.class);
    public static final IntegerProperty POS_NUM = IntegerProperty.create("pos_num", 0, 7);
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty FORCE_SINGLE = BooleanProperty.create("force_single");
    //private static final CrateBlock.InventoryFactory<IInventory> field_220109_i;
    //private static final CrateBlock.InventoryFactory<INamedContainerProvider> field_220110_j;

    public CrateBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState()
    //            .with(PROPERTY_OPEN, false)
                .with(TYPE, CrateType.SINGLE)
                .with(POS_NUM, 0)
                .with(NORTH, true)
                .with(EAST, true)
                .with(SOUTH, true)
                .with(WEST, true)
                .with(UP, true)
                .with(DOWN, true));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, POS_NUM, NORTH, EAST, SOUTH, WEST, UP, DOWN, FORCE_SINGLE);
    }


    private void crateGeometry(BlockState state, World worldIn, BlockPos pos) {
        BlockState north = worldIn.getBlockState(pos.north());
        BlockState south = worldIn.getBlockState(pos.south());
        BlockState east = worldIn.getBlockState(pos.east());
        BlockState west = worldIn.getBlockState(pos.west());
        BlockState up = worldIn.getBlockState(pos.up());
        BlockState down = worldIn.getBlockState(pos.down());
        BlockState north_east = worldIn.getBlockState(pos.north().east());
        BlockState north_west = worldIn.getBlockState(pos.north().west());
        BlockState south_east = worldIn.getBlockState(pos.north().east());
        BlockState south_west = worldIn.getBlockState(pos.north().west());
        BlockState up_north = worldIn.getBlockState(pos.north().up());
        BlockState up_south = worldIn.getBlockState(pos.south().up());
        BlockState up_east = worldIn.getBlockState(pos.east().up());
        BlockState up_west = worldIn.getBlockState(pos.west().up());
        BlockState down_north = worldIn.getBlockState(pos.north().down());
        BlockState down_south = worldIn.getBlockState(pos.south().down());
        BlockState down_east = worldIn.getBlockState(pos.east().down());
        BlockState down_west = worldIn.getBlockState(pos.west().down());
        BlockState up_north_east = worldIn.getBlockState(pos.north().up().east());
        BlockState up_north_west = worldIn.getBlockState(pos.north().up().west());
        BlockState up_south_east = worldIn.getBlockState(pos.south().up().east());
        BlockState up_south_west = worldIn.getBlockState(pos.south().up().west());
        BlockState down_north_east = worldIn.getBlockState(pos.north().down().east());
        BlockState down_north_west = worldIn.getBlockState(pos.north().down().west());
        BlockState down_south_east = worldIn.getBlockState(pos.south().down().east());
        BlockState down_south_west = worldIn.getBlockState(pos.south().down().west());

        if (crateCheckOcto(state, north, east, north_east, up, up_north, up_east, up_north_east)) {
            if (faceCheck(north, 3, Direction.SOUTH, Direction.EAST, Direction.UP) && faceCheck(east, 3, Direction.NORTH, Direction.UP, Direction.WEST)
                    && faceCheck(north_east, 3, Direction.SOUTH, Direction.WEST, Direction.UP) && faceCheck(up, 3, Direction.NORTH, Direction.EAST, Direction.DOWN)
                    && faceCheck(up_east, 3, Direction.DOWN, Direction.WEST, Direction.NORTH) && faceCheck(up_north, 3, Direction.DOWN, Direction.SOUTH, Direction.EAST)
                    && faceCheck(up_north_east, 3, Direction.DOWN, Direction.SOUTH, Direction.WEST))
                worldIn.setBlockState(pos.north(), north.with(POS_NUM, 0).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north().east(), north_east.with(POS_NUM, 1).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos, state.with(POS_NUM, 2).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.east(), east.with(POS_NUM, 3).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north().up(), up_north.with(POS_NUM, 4).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north().east().up(), up_north_east.with(POS_NUM, 5).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up(), up.with(POS_NUM, 6).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.east().up(), up_east.with(POS_NUM, 7).with(TYPE, CrateType.OCTO));
        }
        if (crateCheckOcto(state, north, west, north_west, up, up_north, up_west, up_north_west)) {
            if (faceCheck(west, 3, Direction.EAST, Direction.NORTH, Direction.UP) && faceCheck(north, 3, Direction.SOUTH, Direction.WEST, Direction.UP)
                    && faceCheck(north_west, 3, Direction.UP, Direction.SOUTH, Direction.EAST) && faceCheck(up, 3, Direction.DOWN, Direction.NORTH, Direction.WEST)
                    && faceCheck(up_west, 3, Direction.DOWN, Direction.EAST, Direction.NORTH) && faceCheck(up_north, 3, Direction.SOUTH, Direction.DOWN, Direction.WEST)
                    && faceCheck(up_north_west, 3, Direction.DOWN, Direction.SOUTH, Direction.EAST))
                worldIn.setBlockState(pos.north().west(), north_west.with(POS_NUM, 0).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north(), north.with(POS_NUM, 1).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.west(), west.with(POS_NUM, 2).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos, state.with(POS_NUM, 3).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north().east().up(), up_north_east.with(POS_NUM, 4).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north().up(), up_north.with(POS_NUM, 5).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.west().up(), up_west.with(POS_NUM, 6).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up(), up.with(POS_NUM, 7).with(TYPE, CrateType.OCTO));
        }
        if (crateCheckOcto(state, south, east, south_east, up, up_south, up_east, up_south_east)) {
            if (faceCheck(south, 3, Direction.NORTH, Direction.UP, Direction.EAST) && faceCheck(east, 3, Direction.WEST, Direction.UP, Direction.SOUTH)
                    && faceCheck(south_east, 3, Direction.NORTH, Direction.WEST, Direction.UP) && faceCheck(up, 3, Direction.SOUTH, Direction.EAST, Direction.DOWN)
                    && faceCheck(up_south, 3, Direction.DOWN, Direction.NORTH, Direction.EAST) && faceCheck(up_east, 3, Direction.WEST, Direction.DOWN, Direction.SOUTH)
                    && faceCheck(up_south_east, 3, Direction.DOWN, Direction.WEST, Direction.NORTH))
                worldIn.setBlockState(pos, state.with(POS_NUM, 0).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.east(), east.with(POS_NUM, 1).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.south(), south.with(POS_NUM, 2).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.south().east(), south_east.with(POS_NUM, 3).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up(), up.with(POS_NUM, 4).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up().east(), up_east.with(POS_NUM, 5).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up().south(), up_south.with(POS_NUM, 6).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up().south().east(), up_south_east.with(POS_NUM, 7).with(TYPE, CrateType.OCTO));
        }
        if (crateCheckOcto(state, south, west, south_west, up, up_south, up_west, up_south_west)) {
            if (faceCheck(south, 3, Direction.NORTH, Direction.WEST, Direction.UP) && faceCheck(west, 3, Direction.EAST, Direction.UP, Direction.SOUTH)
                    && faceCheck(south_west, 3, Direction.NORTH, Direction.EAST, Direction.UP) && faceCheck(up, 3, Direction.SOUTH, Direction.WEST, Direction.DOWN)
                    && faceCheck(up_south, 3, Direction.NORTH, Direction.DOWN, Direction.WEST) && faceCheck(up_west, 3, Direction.SOUTH, Direction.EAST, Direction.DOWN)
                    && faceCheck(up_south_west, 3, Direction.NORTH, Direction.EAST, Direction.DOWN))
                worldIn.setBlockState(pos.west(), west.with(POS_NUM, 0).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos, state.with(POS_NUM, 1).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.south().west(), south_west.with(POS_NUM, 2).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.south(), south.with(POS_NUM, 3).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up().west(), up_west.with(POS_NUM, 4).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up(), up.with(POS_NUM, 5).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up().south().west(), up_south_west.with(POS_NUM, 6).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.up().south(), up_south.with(POS_NUM, 7).with(TYPE, CrateType.OCTO));
        }
        if (crateCheckOcto(state, north, east, north_east, down, down_north, down_east, down_north_east)) {
            if (faceCheck(north, 3, Direction.SOUTH, Direction.EAST, Direction.DOWN) && faceCheck(east, 3, Direction.NORTH, Direction.DOWN, Direction.WEST)
                    && faceCheck(north_east, 3, Direction.SOUTH, Direction.WEST, Direction.DOWN) && faceCheck(down, 3, Direction.NORTH, Direction.EAST, Direction.UP)
                    && faceCheck(down_east, 3, Direction.UP, Direction.WEST, Direction.NORTH) && faceCheck(down_north, 3, Direction.UP, Direction.SOUTH, Direction.EAST)
                    && faceCheck(down_north_east, 3, Direction.UP, Direction.SOUTH, Direction.WEST))
                worldIn.setBlockState(pos.down().north(), down_south.with(POS_NUM, 0).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down().north().east(), down_north_east.with(POS_NUM, 1).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down(), down.with(POS_NUM, 2).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down().east(), down_east.with(POS_NUM, 3).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north(), north.with(POS_NUM, 4).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north().east(), north_east.with(POS_NUM, 5).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos, state.with(POS_NUM, 6).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.east(), east.with(POS_NUM, 7).with(TYPE, CrateType.OCTO));
        }
        if (crateCheckOcto(state, north, west, north_west, down, down_north, down_west, down_north_west)) {
            if (faceCheck(west, 3, Direction.EAST, Direction.NORTH, Direction.DOWN) && faceCheck(north, 3, Direction.SOUTH, Direction.WEST, Direction.DOWN)
                    && faceCheck(north_west, 3, Direction.DOWN, Direction.SOUTH, Direction.EAST) && faceCheck(down, 3, Direction.UP, Direction.NORTH, Direction.WEST)
                    && faceCheck(down_west, 3, Direction.UP, Direction.EAST, Direction.NORTH) && faceCheck(down_north, 3, Direction.SOUTH, Direction.UP, Direction.WEST)
                    && faceCheck(down_north_west, 3, Direction.UP, Direction.SOUTH, Direction.EAST))
                worldIn.setBlockState(pos.down().north().west(), down_north_west.with(POS_NUM, 0).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down().north(), down_north.with(POS_NUM, 1).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down().west(), down_west.with(POS_NUM, 2).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down(), down.with(POS_NUM, 3).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north().west(), north_west.with(POS_NUM, 4).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.north(), north.with(POS_NUM, 5).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.west(), west.with(POS_NUM, 6).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos, state.with(POS_NUM, 7).with(TYPE, CrateType.OCTO));
        }
        if (crateCheckOcto(state, south, east, south_east, down, down_south, down_east, down_south_east)) {
            if (faceCheck(south, 3, Direction.NORTH, Direction.DOWN, Direction.EAST) && faceCheck(east, 3, Direction.WEST, Direction.DOWN, Direction.SOUTH)
                    && faceCheck(south_east, 3, Direction.NORTH, Direction.WEST, Direction.DOWN) && faceCheck(down, 3, Direction.SOUTH, Direction.EAST, Direction.UP)
                    && faceCheck(down_south, 3, Direction.UP, Direction.NORTH, Direction.EAST) && faceCheck(down_east, 3, Direction.WEST, Direction.UP, Direction.SOUTH)
                    && faceCheck(down_south_east, 3, Direction.UP, Direction.WEST, Direction.NORTH))
                worldIn.setBlockState(pos.down(), down.with(POS_NUM, 0).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down().east(), down_east.with(POS_NUM, 1).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down().south(), down_south.with(POS_NUM, 2).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down().south().east(), down_south_east.with(POS_NUM, 3).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos, state.with(POS_NUM, 4).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.east(), east.with(POS_NUM, 5).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.south(), south.with(POS_NUM, 6).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.south().east(), south_east.with(POS_NUM, 7).with(TYPE, CrateType.OCTO));
        }
        if (crateCheckOcto(state, south, west, south_west, down, down_south, down_west, down_south_west)) {
            if (faceCheck(south, 3, Direction.NORTH, Direction.WEST, Direction.DOWN) && faceCheck(west, 3, Direction.EAST, Direction.DOWN, Direction.SOUTH)
                    && faceCheck(south_west, 3, Direction.NORTH, Direction.EAST, Direction.DOWN) && faceCheck(down, 3, Direction.SOUTH, Direction.WEST, Direction.UP)
                    && faceCheck(down_south, 3, Direction.NORTH, Direction.UP, Direction.WEST) && faceCheck(down_west, 3, Direction.SOUTH, Direction.EAST, Direction.UP)
                    && faceCheck(down_south_west, 3, Direction.NORTH, Direction.EAST, Direction.UP))
                worldIn.setBlockState(pos.down().west(), down_west.with(POS_NUM, 0).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down(), down.with(POS_NUM, 1).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down().south().west(), down_south_west.with(POS_NUM, 2).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.down().south(), down_south.with(POS_NUM, 3).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.west(), west.with(POS_NUM, 4).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos, state.with(POS_NUM, 5).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.south().west(), south_west.with(POS_NUM, 6).with(TYPE, CrateType.OCTO));
            worldIn.setBlockState(pos.south(), south.with(POS_NUM, 7).with(TYPE, CrateType.OCTO));
        }

        if (crateCheckQuad(state, north, east, north_east, Direction.Axis.Y)) {
            if (faceCheck(north, 2, Direction.SOUTH, Direction.EAST, null) && faceCheck(east, 2, Direction.NORTH, Direction.WEST, null)
                    && faceCheck(north_east, 2, Direction.SOUTH, Direction.WEST, null))
                worldIn.setBlockState(pos.north(), north.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos.north().east(), north_east.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos, state.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos.east(), east.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_Y));
        }
        if (crateCheckQuad(state, north, west, north_west, Direction.Axis.Y)) {
            if (faceCheck(north, 2, Direction.SOUTH, Direction.WEST, null) && faceCheck(west, 2, Direction.NORTH, Direction.EAST, null)
                    && faceCheck(north_west, 2, Direction.SOUTH, Direction.EAST, null))
                worldIn.setBlockState(pos.north().west(), north_west.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos.north(), north.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos.west(), west.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos, state.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_Y));
        }
        if (crateCheckQuad(state, south, east, south_east, Direction.Axis.Y)) {
            if (faceCheck(south, 2, Direction.NORTH, Direction.EAST, null) && faceCheck(east, 2, Direction.WEST, Direction.SOUTH, null)
                    && faceCheck(south_east, 2, Direction.NORTH, Direction.WEST, null))
                worldIn.setBlockState(pos, state.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos.east(), east.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos.south(), south.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos.south().east(), south_east.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_Y));
        }
        if (crateCheckQuad(state, south, west, south_west, Direction.Axis.Y)) {
            if (faceCheck(west, 2, Direction.EAST, Direction.SOUTH, null) && faceCheck(south, 2, Direction.NORTH, Direction.WEST, null)
                    && faceCheck(south_west, 2, Direction.NORTH, Direction.EAST, null))
                worldIn.setBlockState(pos.west(), west.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos, state.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos.south().west(), south_west.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_Y));
            worldIn.setBlockState(pos.south(), south.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_Y));
        }
        if (crateCheckQuad(state, east, up, up_east, Direction.Axis.Z)) {
            if (faceCheck(east, 2, Direction.WEST, Direction.UP, null) && faceCheck(up, 2, Direction.EAST, Direction.DOWN, null)
                    && faceCheck(up_east, 2, Direction.DOWN, Direction.WEST, null))
                worldIn.setBlockState(pos.up(), up.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos.up().east(), up_east.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos, state.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos.east(), east.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_Z));
        }
        if (crateCheckQuad(state, west, up, up_west, Direction.Axis.Z)) {
            if (faceCheck(west, 2, Direction.UP, Direction.EAST, null) && faceCheck(up, 2, Direction.WEST, Direction.DOWN, null)
                    && faceCheck(up_west, 2, Direction.DOWN, Direction.EAST, null))
                worldIn.setBlockState(pos.up().west(), up_west.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos.up(), up.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos.west(), west.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos, state.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_Z));
        }
        if (crateCheckQuad(state, east, down, down_east, Direction.Axis.Z)) {
            if (faceCheck(east, 2, Direction.WEST, Direction.DOWN, null) && faceCheck(down, 2, Direction.EAST, Direction.UP, null)
                    && faceCheck(down_east, 2, Direction.UP, Direction.WEST, null))
                worldIn.setBlockState(pos, state.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos.east(), east.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos.down(), down.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos.down().east(), down_east.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_Z));
        }
        if (crateCheckQuad(state, west, down, down_west, Direction.Axis.Z)) {
            if (faceCheck(west, 2, Direction.EAST, Direction.DOWN, null) && faceCheck(down, 2, Direction.WEST, Direction.UP, null)
                    && faceCheck(down_west, 2, Direction.UP, Direction.EAST, null))
                worldIn.setBlockState(pos.west(), west.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos, state.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos.down().west(), down_west.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_Z));
            worldIn.setBlockState(pos.down(), down.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_Z));
        }
        if (crateCheckQuad(state, north, up, up_north, Direction.Axis.X)) {
            if (faceCheck(north, 2, Direction.UP, Direction.SOUTH, null) && faceCheck(up, 2, Direction.NORTH, Direction.DOWN, null)
                    && faceCheck(up_north, 2, Direction.SOUTH, Direction.DOWN, null))
                worldIn.setBlockState(pos.up().north(), up_north.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos.up(), up.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos.north(), north.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos, state.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_X));
        }
        if (crateCheckQuad(state, south, up, up_south, Direction.Axis.X)) {
            if (faceCheck(south, 2, Direction.NORTH, Direction.UP, null) && faceCheck(up, 2, Direction.SOUTH, Direction.DOWN, null)
                    && faceCheck(up_south, 2, Direction.DOWN, Direction.NORTH, null))
                worldIn.setBlockState(pos.up(), up.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos.up().south(), up_south.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos, state.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos.south(), south.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_X));
        }
        if (crateCheckQuad(state, north, down, down_north, Direction.Axis.X)) {
            if (faceCheck(north, 2, Direction.DOWN, Direction.SOUTH, null) && faceCheck(down, 2, Direction.NORTH, Direction.UP, null)
                    && faceCheck(down_north, 2, Direction.UP, Direction.SOUTH, null))
                worldIn.setBlockState(pos.north(), north.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos, state.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos.down().north(), down_north.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos.down(), down.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_X));
        }
        if (crateCheckQuad(state, south, down, down_south, Direction.Axis.X)) {
            if (faceCheck(south, 2, Direction.DOWN, Direction.NORTH, null) && faceCheck(down, 2, Direction.UP, Direction.SOUTH, null)
                    && faceCheck(down_south, 2, Direction.UP, Direction.NORTH, null))
                worldIn.setBlockState(pos, state.with(POS_NUM, 0).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos.south(), south.with(POS_NUM, 1).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos.down(), down.with(POS_NUM, 2).with(TYPE, CrateType.QUAD_X));
            worldIn.setBlockState(pos.down().south(), down_south.with(POS_NUM, 3).with(TYPE, CrateType.QUAD_X));
        }

        if (crateCheckDuo(state, east)) {
            if (faceCheck(east, 1, Direction.WEST, null, null))
                worldIn.setBlockState(pos, state.with(POS_NUM, 0).with(TYPE, CrateType.DOUBLE_X));
            worldIn.setBlockState(pos.east(), east.with(POS_NUM, 1).with(TYPE, CrateType.DOUBLE_X));
        }
        if (crateCheckDuo(state, west)) {
            if (faceCheck(west, 1, Direction.EAST, null, null))
                worldIn.setBlockState(pos.west(), west.with(POS_NUM, 0).with(TYPE, CrateType.DOUBLE_X));
            worldIn.setBlockState(pos, state.with(POS_NUM, 1).with(TYPE, CrateType.DOUBLE_X));
        }
        if (crateCheckDuo(state, north)) {
            if (faceCheck(north, 1, Direction.SOUTH, null, null))
                worldIn.setBlockState(pos.north(), north.with(POS_NUM, 0).with(TYPE, CrateType.DOUBLE_Z));
            worldIn.setBlockState(pos, state.with(POS_NUM, 1).with(TYPE, CrateType.DOUBLE_Z));
        }
        if (crateCheckDuo(state, south)) {
            if (faceCheck(south, 1, Direction.NORTH, null, null))
                worldIn.setBlockState(pos, state.with(POS_NUM, 0).with(TYPE, CrateType.DOUBLE_Z));
            worldIn.setBlockState(pos.south(), south.with(POS_NUM, 1).with(TYPE, CrateType.DOUBLE_Z));
        }
        if (crateCheckDuo(state, down)) {
            if (faceCheck(down, 1, Direction.UP, null, null))
                worldIn.setBlockState(pos, state.with(POS_NUM, 0).with(TYPE, CrateType.DOUBLE_Y));
            worldIn.setBlockState(pos.down(), down.with(POS_NUM, 1).with(TYPE, CrateType.DOUBLE_Y));
        }
        if (crateCheckDuo(state, up)) {
            if (faceCheck(up, 1, Direction.DOWN, null, null))
                worldIn.setBlockState(pos.up(), up.with(POS_NUM, 0).with(TYPE, CrateType.DOUBLE_Y));
            worldIn.setBlockState(pos, state.with(POS_NUM, 1).with(TYPE, CrateType.DOUBLE_Y));
        }

    }

    private static void convertShapeId(int shapeId) {
        switch (shapeId) {
            case 1:

        }
    }

    private boolean faceCheck (BlockState state, int faces, Direction dir1, Direction dir2, Direction dir3) {
        int counter = 0;
        if (dir3 != null && dir2 != null) {
            if (dir1 == Direction.NORTH || dir2 == Direction.NORTH || dir3 == Direction.NORTH) {
                if (state.get(NORTH)) {
                    counter++;
                }
            }
            if (dir1 == Direction.SOUTH || dir2 == Direction.SOUTH || dir3 == Direction.SOUTH) {
                if (state.get(SOUTH)) {
                    counter++;
                }
            }
            if (dir1 == Direction.EAST || dir2 == Direction.EAST || dir3 == Direction.EAST) {
                if (state.get(EAST)) {
                    counter++;
                }
            }
            if (dir1 == Direction.WEST || dir2 == Direction.WEST || dir3 == Direction.WEST) {
                if (state.get(WEST)) {
                    counter++;
                }
            }
            if (dir1 == Direction.UP || dir2 == Direction.UP || dir3 == Direction.UP) {
                if (state.get(UP)) {
                    counter++;
                }
            }
            if (dir1 == Direction.DOWN || dir2 == Direction.DOWN || dir3 == Direction.DOWN) {
                if (state.get(DOWN)) {
                    counter++;
                }
            }
        }
        if (dir3 == null && dir2 != null) {
            if (dir1 == Direction.NORTH || dir2 == Direction.NORTH) {
                if (state.get(NORTH)) {
                    counter++;
                }
            }
            if (dir1 == Direction.SOUTH || dir2 == Direction.SOUTH) {
                if (state.get(SOUTH)) {
                    counter++;
                }
            }
            if (dir1 == Direction.EAST || dir2 == Direction.EAST) {
                if (state.get(EAST)) {
                    counter++;
                }
            }
            if (dir1 == Direction.WEST || dir2 == Direction.WEST) {
                if (state.get(WEST)) {
                    counter++;
                }
            }
            if (dir1 == Direction.UP || dir2 == Direction.UP) {
                if (state.get(UP)) {
                    counter++;
                }
            }
            if (dir1 == Direction.DOWN || dir2 == Direction.DOWN) {
                if (state.get(DOWN)) {
                    counter++;
                }
            }
        }
        if (dir3 == null && dir2 == null) {
            if (dir1 == Direction.NORTH) {
                if (state.get(NORTH)) {
                    counter++;
                }
            }
            if (dir1 == Direction.SOUTH) {
                if (state.get(SOUTH)) {
                    counter++;
                }
            }
            if (dir1 == Direction.EAST) {
                if (state.get(EAST)) {
                    counter++;
                }
            }
            if (dir1 == Direction.WEST) {
                if (state.get(WEST)) {
                    counter++;
                }
            }
            if (dir1 == Direction.UP) {
                if (state.get(UP)) {
                    counter++;
                }
            }
            if (dir1 == Direction.DOWN) {
                if (state.get(DOWN)) {
                    counter++;
                }
            }
        }
        return counter >= faces;
    }

    private boolean crateCheckOcto (BlockState currentBlock, BlockState state1, BlockState state2, BlockState state3, BlockState state4, BlockState state5, BlockState state6, BlockState state7) {
        if (state1.getBlock() instanceof CrateBlock
                && state2.getBlock() instanceof CrateBlock
                && state3.getBlock() instanceof CrateBlock
                && state4.getBlock() instanceof CrateBlock
                && state5.getBlock() instanceof CrateBlock
                && state6.getBlock() instanceof CrateBlock
                && state7.getBlock() instanceof CrateBlock
                && currentBlock.getBlock() instanceof CrateBlock) {
            return true;
        }
        return false;
    }

    private boolean crateCheckQuad (BlockState currentBlock, BlockState state1, BlockState state2, BlockState state3, Direction.Axis axis) {
        if (state1.getBlock() instanceof CrateBlock && (state1.get(TYPE) != CrateType.QUAD_X && state1.get(TYPE) != CrateType.QUAD_Y && state1.get(TYPE) != CrateType.QUAD_Z)
                && state2.getBlock() instanceof CrateBlock && (state2.get(TYPE) != CrateType.QUAD_X && state2.get(TYPE) != CrateType.QUAD_Y && state2.get(TYPE) != CrateType.QUAD_Z)
                && state3.getBlock() instanceof CrateBlock && (state3.get(TYPE) != CrateType.QUAD_X && state3.get(TYPE) != CrateType.QUAD_Y && state3.get(TYPE) != CrateType.QUAD_Z)
                && currentBlock.getBlock() instanceof CrateBlock) {
            if (((axis == Direction.Axis.X && state1.get(TYPE) != CrateType.DOUBLE_X) || (axis == Direction.Axis.Y && state1.get(TYPE) != CrateType.DOUBLE_Y) || (axis == Direction.Axis.Z && state1.get(TYPE) != CrateType.DOUBLE_Z) || state1.get(TYPE) == CrateType.SINGLE)
                    && ((axis == Direction.Axis.X && state2.get(TYPE) != CrateType.DOUBLE_X) || (axis == Direction.Axis.Y && state2.get(TYPE) != CrateType.DOUBLE_Y) || (axis == Direction.Axis.Z && state2.get(TYPE) != CrateType.DOUBLE_Z) || state2.get(TYPE) == CrateType.SINGLE)
                    && ((axis == Direction.Axis.X && state3.get(TYPE) != CrateType.DOUBLE_X) || (axis == Direction.Axis.Y && state3.get(TYPE) != CrateType.DOUBLE_Y) || (axis == Direction.Axis.Z && state3.get(TYPE) != CrateType.DOUBLE_Z) || state3.get(TYPE) == CrateType.SINGLE))
            return true;
        }
        return false;
    }

    private boolean crateCheckDuo (BlockState currentBlock, BlockState state1) {
        if (state1.getBlock() instanceof CrateBlock && (state1.get(TYPE) != CrateType.QUAD_X && state1.get(TYPE) != CrateType.QUAD_Y && state1.get(TYPE) != CrateType.QUAD_Z)
                && currentBlock.getBlock() instanceof CrateBlock && (state1.get(TYPE) != CrateType.DOUBLE_Z && state1.get(TYPE) != CrateType.DOUBLE_X && state1.get(TYPE) != CrateType.DOUBLE_Y)) {
            return true;
        }
        return false;
    }

    private BlockState calculateSize(BlockState state, World worldIn, BlockPos pos) {
    }

    /*public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof CrateTileEntity) {
                player.openContainer((CrateTileEntity)tileentity);
            }

            return true;
        }
    }*/

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }

    }

    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof CrateTileEntity) {
            ((CrateTileEntity)tileentity).func_213962_h();
        }

    }

    @Nullable
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CrateTileEntity();
    }

    /** @deprecated */
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof CrateTileEntity) {
                ((CrateTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }

    }

    /** @deprecated */
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    /** @deprecated */
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return (BlockState)this.getDefaultState();
    }
}