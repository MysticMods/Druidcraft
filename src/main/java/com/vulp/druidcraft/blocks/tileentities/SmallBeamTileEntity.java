package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.blocks.RopeBlock;
import com.vulp.druidcraft.blocks.SmallBeamBlock;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SmallBeamTileEntity extends TileEntity {

    private Boolean NORTH = false;
    private Boolean EAST = false;
    private Boolean SOUTH = false;
    private Boolean WEST = false;
    private Boolean UP = false;
    private Boolean DOWN = false;
    private Direction.Axis ROT = Direction.Axis.X;

    public SmallBeamTileEntity() {
        super(TileEntityRegistry.small_beam);
    }

    public SmallBeamTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.world != null) {
            ropeConnectionCalculations(this.world, getBlockState(), this.pos);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putBoolean("North", this.NORTH);
        compound.putBoolean("East", this.EAST);
        compound.putBoolean("South", this.SOUTH);
        compound.putBoolean("West", this.WEST);
        compound.putBoolean("Up", this.UP);
        compound.putBoolean("Down", this.DOWN);
        compound.putInt("Rotation", axisToInt(this.ROT));
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.NORTH = compound.getBoolean("North");
        this.EAST = compound.getBoolean("East");
        this.SOUTH = compound.getBoolean("South");
        this.WEST = compound.getBoolean("West");
        this.UP = compound.getBoolean("Up");
        this.DOWN = compound.getBoolean("Down");
        this.ROT = intToAxis(compound.getInt("Rotation"));
    }

    public int axisToInt(Direction.Axis axis) {
        if (axis == Direction.Axis.X)
            return 1;
        else if (axis == Direction.Axis.Y)
            return 2;
        else return 3;
    }

    @Nullable
    public Direction.Axis intToAxis(int i) {
        if (i == 1)
            return Direction.Axis.X;
        else if (i == 2)
            return Direction.Axis.Y;
        else return Direction.Axis.Z;
    }

    public ArrayList<Boolean> getDirections() {
        ArrayList<Boolean> list = new ArrayList<>();
        list.add(this.NORTH);
        list.add(this.EAST);
        list.add(this.SOUTH);
        list.add(this.WEST);
        list.add(this.UP);
        list.add(this.DOWN);
        return list;
    }

    public Direction.Axis getRotation() {
        return this.ROT;
    }

    public void ropeConnectionCalculations(IWorld world, BlockState state, BlockPos pos) {
        boolean xAxis = state.get(SmallBeamBlock.X_AXIS);
        boolean yAxis = state.get(SmallBeamBlock.Y_AXIS);
        boolean zAxis = state.get(SmallBeamBlock.Z_AXIS);
        this.NORTH = world.getBlockState(pos.north()).getBlock() instanceof RopeBlock && !zAxis;
        this.SOUTH = world.getBlockState(pos.south()).getBlock() instanceof RopeBlock && !zAxis;
        this.EAST = world.getBlockState(pos.east()).getBlock() instanceof RopeBlock && !xAxis;
        this.WEST = world.getBlockState(pos.west()).getBlock() instanceof RopeBlock && !xAxis;
        this.UP = world.getBlockState(pos.up()).getBlock() instanceof RopeBlock && !yAxis;
        this.DOWN = world.getBlockState(pos.down()).getBlock() instanceof RopeBlock && !yAxis;
        if (xAxis)
            this.ROT = Direction.Axis.X;
        else if (yAxis)
            this.ROT = Direction.Axis.Y;
        else this.ROT = Direction.Axis.Z;
    }

}
