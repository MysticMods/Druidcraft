package com.vulp.druidcraft.api;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;

public enum CrateIndex implements IStringSerializable {
    // | TYPE | NUM | NORTH | SOUTH | EAST | WEST | UP | DOWN |
    CRATE00_0(CrateType.SINGLE, 1, true, true, true, true, true, true),
    CRATE00_1(CrateType.SINGLE, 1, false, false, false, false, false, false),
    CRATE01(CrateType.DOUBLE_X, 1, true, true, true, false, true, true),
    CRATE02(CrateType.DOUBLE_X, 2, true, true, false, true, true, true),
    CRATE03(CrateType.DOUBLE_Y, 1, true, true, true, true, true, false),
    CRATE04(CrateType.DOUBLE_Y, 2, true, true, true, true, false, true),
    CRATE05(CrateType.DOUBLE_Z, 1, false, true, true, true, true, true),
    CRATE06(CrateType.DOUBLE_Z, 2, true, false, true, true, true, true),
    CRATE_07(CrateType.QUAD_X, 1, false, true, true, true, true, false),
    CRATE_08(CrateType.QUAD_X, 2, true, false, true, true, true, false),
    CRATE_09(CrateType.QUAD_X, 3, false, true, true, true, false, true),
    CRATE_10(CrateType.QUAD_X, 4, true, false, true, true, false, true),
    CRATE_11(CrateType.QUAD_Y, 1, false, true, true, false, true, true),
    CRATE_12(CrateType.QUAD_Y, 2, false, true, false, true, true, true),
    CRATE_13(CrateType.QUAD_Y, 3, true, false, true, false, true, true),
    CRATE_14(CrateType.QUAD_Y, 4, true, false, false, true, true, true),
    CRATE_15(CrateType.QUAD_Z, 1, true, true, false, true, true, false),
    CRATE_16(CrateType.QUAD_Z, 2, true, true, true, false, true, false),
    CRATE_17(CrateType.QUAD_Z, 3, true, true, false, true, false, true),
    CRATE_18(CrateType.QUAD_Z, 4, true, true, true, false, false, true),
    CRATE_19(CrateType.OCTO, 1, false, true, false, true, true, false),
    CRATE_20(CrateType.OCTO, 2, false, true, true, false, true, false),
    CRATE_21(CrateType.OCTO, 3, true, false, false, true, true, false),
    CRATE_22(CrateType.OCTO, 4, true, false, true, false, true, false),
    CRATE_23(CrateType.OCTO, 5, false, true, false, true, false, true),
    CRATE_24(CrateType.OCTO, 6, false, true, true, false, false, true),
    CRATE_25(CrateType.OCTO, 7, true, false, false, true, false, true),
    CRATE_26(CrateType.OCTO, 8, true, false, true, false, false, true);

    private final CrateType type;
    private final int posNumber;
    private final boolean north;
    private final boolean south;
    private final boolean east;
    private final boolean west;
    private final boolean up;
    private final boolean down;

    CrateIndex(CrateType type, int posNumber, boolean north, boolean south, boolean east, boolean west, boolean up, boolean down) {
        this.type = type;
        this.posNumber = posNumber;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.up = up;
        this.down = down;
    }

    @Nullable
    public static CrateIndex matchCrateIndex(CrateType type, int posNumber, boolean north, boolean south, boolean east, boolean west, boolean up, boolean down) {
        for (CrateIndex index : CrateIndex.values()) {
            if (type == index.getType() && posNumber == index.getPosNumber()) {
                if (north == index.isNorth() && south == index.isSouth() && east == index.isEast() && west == index.isWest() && up == index.isUp() && down == index.isDown()) {
                    return index;
                }
            }
        }
        return null;
    }

    public boolean isParent() {
        return getPosNumber() == 1;
    }

    public CrateType getType() {
        return this.type;
    }

    public int getPosNumber() {
        return this.posNumber;
    }

    public boolean isNorth() {
        return this.north;
    }

    public boolean isSouth() {
        return this.south;
    }

    public boolean isEast() {
        return this.east;
    }

    public boolean isWest() {
        return this.west;
    }

    public boolean isUp() {
        return this.up;
    }

    public boolean isDown() {
        return this.down;
    }

    @Override
    public String getName() {
        return name();
    }

}
