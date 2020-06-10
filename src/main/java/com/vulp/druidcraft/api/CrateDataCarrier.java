package com.vulp.druidcraft.api;

public class CrateDataCarrier {

    private CrateType type;
    private int position;
    private boolean north;
    private boolean south;
    private boolean east;
    private boolean west;
    private boolean up;
    private boolean down;

    public CrateDataCarrier(boolean north, boolean east, boolean south, boolean west, boolean up, boolean down) {
        this.type = CrateType.SINGLE;
        this.position = 1;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.up = up;
        this.down = down;
    }

    public CrateType getType() {
        return this.type;
    }

    public int getPosition() {
        return position;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isEast() {
        return east;
    }

    public boolean isWest() {
        return west;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public void setType(CrateType type) {
        this.type = type;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCardinals(boolean north, boolean east, boolean south, boolean west, boolean up, boolean down) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.up = up;
        this.down = down;
    }

    public void setNorth(boolean north) {
        this.north = north;
    }

    public void setSouth(boolean south) {
        this.south = south;
    }

    public void setEast(boolean east) {
        this.east = east;
    }

    public void setWest(boolean west) {
        this.west = west;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

}
