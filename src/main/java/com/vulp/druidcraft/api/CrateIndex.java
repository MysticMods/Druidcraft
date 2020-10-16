package com.vulp.druidcraft.api;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.Locale;

public enum CrateIndex implements IStringSerializable {
    // | TYPE | NUM | NORTH | SOUTH | EAST | WEST | UP | DOWN |
    CRATE00_0(CrateType.SINGLE, 1, false, false, false, false, false, false),
    CRATE00_1(CrateType.SINGLE, 1, true, true, true, true, true, true),
    CRATE01(CrateType.DOUBLE_X, 1, true, true, true, false, true, true),
    CRATE02(CrateType.DOUBLE_X, 2, true, true, false, true, true, true),
    CRATE03(CrateType.DOUBLE_Y, 1, true, true, true, true, true, false),
    CRATE04(CrateType.DOUBLE_Y, 2, true, true, true, true, false, true),
    CRATE05(CrateType.DOUBLE_Z, 1, false, true, true, true, true, true),
    CRATE06(CrateType.DOUBLE_Z, 2, true, false, true, true, true, true),
    CRATE07(CrateType.QUAD_X, 1, false, true, true, true, true, false),
    CRATE08(CrateType.QUAD_X, 2, true, false, true, true, true, false),
    CRATE09(CrateType.QUAD_X, 3, false, true, true, true, false, true),
    CRATE10(CrateType.QUAD_X, 4, true, false, true, true, false, true),
    CRATE11(CrateType.QUAD_Y, 1, false, true, true, false, true, true),
    CRATE12(CrateType.QUAD_Y, 2, false, true, false, true, true, true),
    CRATE13(CrateType.QUAD_Y, 3, true, false, true, false, true, true),
    CRATE14(CrateType.QUAD_Y, 4, true, false, false, true, true, true),
    CRATE15(CrateType.QUAD_Z, 1, true, true, true, false, true, false),
    CRATE16(CrateType.QUAD_Z, 2, true, true, false, true, true, false),
    CRATE17(CrateType.QUAD_Z, 3, true, true, true, false, false, true),
    CRATE18(CrateType.QUAD_Z, 4, true, true, false, true, false, true),
    CRATE19(CrateType.OCTO, 1, false, true, true, false, true, false),
    CRATE20(CrateType.OCTO, 2, false, true, false, true, true, false),
    CRATE21(CrateType.OCTO, 3, true, false, true, false, true, false),
    CRATE22(CrateType.OCTO, 4, true, false, false, true, true, false),
    CRATE23(CrateType.OCTO, 5, false, true, true, false, false, true),
    CRATE24(CrateType.OCTO, 6, false, true, false, true, false, true),
    CRATE25(CrateType.OCTO, 7, true, false, true, false, false, true),
    CRATE26(CrateType.OCTO, 8, true, false, false, true, false, true);

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

    public static CrateIndex matchCrateIndex(CrateType type, int posNumber, boolean north, boolean south, boolean east, boolean west, boolean up, boolean down) {
        for (CrateIndex index : CrateIndex.values()) {
            if (type == index.getType() && posNumber == index.getPosNumber()) {
                if (north == index.isNorth() && south == index.isSouth() && east == index.isEast() && west == index.isWest() && up == index.isUp() && down == index.isDown()) {
                    return index;
                }
            }
        }
        return CrateIndex.CRATE00_0;
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
    public String getString() {
        return name().toLowerCase(Locale.ROOT);
    }

}
