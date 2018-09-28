package com.codingame.game;

import com.codingame.game.Utils.Vector2;

public class Item {
    private String name;
    private int playerId;
    private Vector2 pos;

    public Item(String name, int playerId) {
        this.name = name;
        this.playerId = playerId;
        this.pos = Vector2.INVALID;
    }

    public Item(String name, int playerId, Vector2 pos) {
        this.name = name;
        this.playerId = playerId;
        this.pos = pos;
    }

    public String getName() {
        return this.name;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getPos() {
        return pos;
    }
}
