package com.codingame.game;

import com.codingame.game.Utils.Vector2;

public class Item {
    private String name;
    private int playerId;

    public Item(String name, int playerId) {
        this.name = name;
        this.playerId = playerId;
    }

    public Item(String name, int playerId, Vector2 pos) {
        this.name = name;
        this.playerId = playerId;
    }

    public String getName() {
        return this.name;
    }

    public int getPlayerId() {
        return this.playerId;
    }
}
