package com.codingame.game.Model;

import com.codingame.game.Item;
import com.codingame.game.Utils.Vector2;

public class TileModel {
    /**
     * The tile's position.
     */
    private Vector2 pos;

    /**
     * The tile's item. Can be null.
     */
    private Item item;

    /**
     * Represents path directions (up, right, down, left) as 4 binary digits (1 for path, 0 for no path).
     */
    private String pattern;

    /**
     * Provide information about tile path directions.
     */
    private boolean hasUp = false;
    private boolean hasDown = false;
    private boolean hasLeft = false;
    private boolean hasRight = false;

    /**
     * Create a tile based on a pattern.
     * @param pattern A 4 binary digits string (1 for path, 0 for no path).
     */
    public TileModel(String pattern) {
        this.pattern = pattern;
        this.pos = Vector2.INVALID;
        this.item = null;
        updateDirections();
    }

    /**
     * Create a tile based on a pattern and at a certain position.
     * @param pattern A 4 binary digits string (1 for path, 0 for no path).
     * @param pos The position to put the tile at.
     */
    public TileModel(String pattern, Vector2 pos) {
        this.pattern = pattern;
        this.pos = pos;
        this.item = null;
        updateDirections();
    }

    /**
     * @return the tile position.
     */
    public Vector2 getPos() {
        return pos;
    }

    /**
     * @param pos The tile's new position.
     */
    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    /**
     * @return the tile's item. Can be null.
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item The tile's new item.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return the tile's pattern as a 4 binary digits string (1 for path, 0 for no path).
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern The tile's new pattern.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
        updateDirections();
    }

    /**
     * @return true if the tile has an up path.
     *         false otherwise.
     */
    public boolean hasUp() {
        return hasUp;
    }

    /**
     * @return true if the tile has a right path.
     *         false otherwise.
     */
    public boolean hasRight() {
        return hasRight;
    }

    /**
     * @return true if the tile has a down path.
     *         false otherwise.
     */
    public boolean hasDown() {
        return hasDown;
    }

    /**
     * @return true if the tile has a left path.
     *         false otherwise.
     */
    public boolean hasLeft() {
        return hasLeft;
    }

    /**
     * Updates the directions booleans.
     */
    void updateDirections() {
        hasUp = (pattern.charAt(0) == '1');
        hasRight = (pattern.charAt(1) == '1');
        hasDown = (pattern.charAt(2) == '1');
        hasLeft = (pattern.charAt(3) == '1');
    }

    /**
     * Checks if a tile has an item on it.
     * @return true if a tile has a non null item.
     *         false otherwise.
     */
    public boolean hasItem() {
        return this.item != null;
    }

    /**
     * Check if a tile has no paths.
     * @return true if a tile has no paths (directions).
     *         false otherwise.
     */
    public boolean isEmpty() {
        return !(hasUp || hasRight || hasDown || hasLeft);
    }

    /**
     * Rotates a tile clockwise a number of times; each rotation corresponds to a 90 degrees angle.
     * @param numTimes An integer representing the number of times to apply a 90 degrees rotation.
     */
    public void rotate(int numTimes) {
        pattern = pattern.substring(pattern.length() - numTimes)
                + pattern.substring(0, pattern.length() - numTimes);
        updateDirections();
    }

    /**
     * The tile's representation to be given to the user as input.
     * @return the tile's pattern.
     */
    public String toInputString() {
        return pattern;
    }
}
