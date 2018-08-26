package com.codingame.game;

import com.codingame.game.Utils.Vector2;

public class Tile {
    String pattern;
    Vector2 pos;
    Item item;

    public Tile(String pattern) {
        this.pattern = pattern;
        this.pos = Vector2.ZERO;
        this.item = null;
    }

    public Tile(Tile tile) {
        this.pattern = tile.pattern;
        this.pos = new Vector2(tile.pos);
        //this.item = new Item(tile.item);
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

    public boolean isEmpty() {
        return pattern.equals("0000");
    }
}
