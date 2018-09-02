package com.codingame.game.Model;

import com.codingame.game.Item;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;

public class TileModel {
    public String pattern;
    public Vector2 pos;
    public Item item;

    public TileModel(String pattern) {
        this.pattern = pattern;
        this.pos = Vector2.INVALID;
        this.item = null;
    }

    public TileModel(String pattern, Vector2 pos) {
        this.pattern = pattern;
        this.pos = pos;
        this.item = null;
    }

    public boolean hasUp() {
        return pattern.charAt(0) == '1';
    }

    public boolean hasRight() {
        return pattern.charAt(1) == '1';
    }

    public boolean hasDown() {
        return pattern.charAt(2) == '1';
    }

    public boolean hasLeft() {
        return pattern.charAt(3) == '1';
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
        return pattern.equals("0000");
    }
}
