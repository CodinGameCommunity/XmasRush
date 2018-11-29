package com.codingame.game.Utils;

/**
 * Vector2 is a mutable type representing a position in x,y coordinates.
 */
public class Vector2 {
    private int x;
    private int y;

    /**
     * Constants to be used when sending item positions as input,
     * MINUS_ONE for item on player's tile, MINUS_TWO for item on opponent's tile.
     */
    public static final Vector2 MINUS_ONE = new Vector2(-1, -1);
    public static final Vector2 MINUS_TWO = new Vector2(-2, -2);

    /**
     * Constants to be used when moving on the map.
     */
    public static final Vector2 UP = new Vector2(0, -1);
    public static final Vector2 DOWN = new Vector2(0, 1);
    public static final Vector2 LEFT = new Vector2(-1, 0);
    public static final Vector2 RIGHT = new Vector2(1, 0);

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Adds all attributes of a given Vector2 object to the current one and wraps around map borders when required.
     * @param other the Vector2 object to add to the current one.
     */
    public void wrap(Vector2 other) {
        this.x = (this.x + other.x + Constants.MAP_WIDTH) % Constants.MAP_WIDTH;
        this.y = (this.y + other.y + Constants.MAP_HEIGHT) % Constants.MAP_HEIGHT;
    }

    public String toString() {
        return x + " " + y;
    }

    public String toTooltip() {
        return "(" + x + ", " + y + ")";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2))
            return false;
        Vector2 other = (Vector2) obj;
        return this.x == other.x && this.y == other.y;
    }
    public static Vector2 fromMapSpaceToViewSpace(Vector2 pos) {
        int x = Constants.MAP_POS_X + pos.getX() * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        int y = Constants.MAP_POS_Y + pos.getY() * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        return new Vector2(x,y);
    }
    public static Vector2 fromViewSpaceToMapSpace(Vector2 pos) {
        int x = (pos.getX() - Constants.MAP_POS_X) / (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        int y = (pos.getY() - Constants.MAP_POS_Y) / (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        return new Vector2(x,y);
    }
}
