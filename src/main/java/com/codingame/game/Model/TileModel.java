package com.codingame.game.Model;

import com.codingame.game.Item;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;

public class TileModel {
    public Vector2 pos;
    public Item item;
    public String pattern;
    private boolean hasUp = false;
    private boolean hasDown = false;
    private boolean hasLeft = false;
    private boolean hasRight = false;

    public TileModel(String pattern) {
        this.pattern = pattern;
        this.pos = Vector2.INVALID;
        this.item = null;
        updateDirections();
    }

    public TileModel(String pattern, Vector2 pos) {
        this.pattern = pattern;
        this.pos = pos;
        this.item = null;
        updateDirections();
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        updateDirections();
    }

    public boolean hasUp() {
        return hasUp;
    }

    public boolean hasRight() {
        return hasRight;
    }

    public boolean hasDown() {
        return hasDown;
    }

    public boolean hasLeft() {
        return hasLeft;
    }

    void updateDirections() {
        hasUp = (pattern.charAt(0) == '1');
        hasRight = (pattern.charAt(1) == '1');
        hasDown = (pattern.charAt(2) == '1');
        hasLeft = (pattern.charAt(3) == '1');
    }

    public boolean hasItem() {
        return this.item != null;
    }

    public void putItem(Item item) {
        this.item = item;
    }

    public boolean isCenterTile() {
        int centerX = Constants.MAP_HEIGHT / 2;
        int centerY = Constants.MAP_WIDTH / 2;
        return this.pos.x == centerX && this.pos.y == centerY;
    }

    public boolean isBaseTile() {
        return (this.pos.x == 0 && this.pos.y == 0)
                || (this.pos.x == Constants.MAP_WIDTH - 1 && this.pos.y == Constants.MAP_HEIGHT - 1);
    }

    public boolean isEmpty() {
        return !(hasUp || hasRight || hasDown || hasLeft);
    }

    public void rotate(int numTimes) {
        pattern = pattern.substring(pattern.length() - numTimes)
                + pattern.substring(0, pattern.length() - numTimes);
        updateDirections();
    }

    public String toInputString() {
        if (item != null) {
            return String.format("%s%s%d", pattern, item.getIdentifier(), item.getPlayerId());
        } else {
            return String.format("%s--", pattern);
        }
    }
}
