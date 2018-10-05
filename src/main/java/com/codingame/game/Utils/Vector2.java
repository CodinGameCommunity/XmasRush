package com.codingame.game.Utils;

/**
 * Vector2 is a mutable type representing a position in x,y coordinates.
 */
public class Vector2 {
    /**
     * Integer representing position on the X axis.
     */
    private int x;

    /**
     * Integer representing position on the Y axis.
     */
    private int y;

    /**
     * Constants to be used for quick initialization of Vector2 objects.
     */
    public static final Vector2 ZERO = new Vector2();
    public static final Vector2 INVALID = new Vector2(Integer.MIN_VALUE, Integer.MIN_VALUE);

    /**
     * Constants to be used when sending item positions as input,
     * MINUS_ONE for item on player's tile, MINUS_TWO for item on opponent's tile.
     */
    public static final Vector2 MINUS_ONE = new Vector2(-1, -1);
    public static final Vector2 MINUS_TWO = new Vector2(-2, -2);

    /**
     * Constants to be used when moving of the map.
     */
    public static final Vector2 UP = new Vector2(0, -1);
    public static final Vector2 DOWN = new Vector2(0, 1);
    public static final Vector2 LEFT = new Vector2(-1, 0);
    public static final Vector2 RIGHT = new Vector2(1, 0);

    /**
     * Create a Vector2 object at position 0,0.
     */
    public Vector2() {
        this.x = this.y = 0;
    }

    /**
     * Create a Vector2 object at a given position.
     * @param x the position on the X axis.
     * @param y the position on the Y axis.
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a Vector2 object using another's attributes.
     * @param other the Vector2 object to be used as initialization.
     */
    public Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Getter method for x attribute.
     * @return x attribute.
     */
    public int getX() {
        return x;
    }

    /**
     * Setter method for x attribute.
     * @param x the new attribute to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter method for y attribute.
     * @return y attribute.
     */
    public int getY() {
        return y;
    }

    /**
     * Setter method for y attribute.
     * @param y the new attribute to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Adds all attributes of a given Vector2 object to the current one.
     * @param other the Vector2 object to add to the current one.
     */
    public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    /**
     * Creates a string representation of a Vector2 object.
     * @return the String representation of the current Vector2 object.
     */
    @Override
    public String toString() {
        return "[x=" + this.x + ", y=" + this.y + "]";
    }

    /**
     * Checks for equality between 2 Vector2 objects.
     * @param obj The other Vector2 object to compare to.
     * @return true if all components are equal.
     *         false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2)obj;
        return this.x == other.x && this.y == other.y;
    }
}
