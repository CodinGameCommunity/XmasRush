package com.codingame.game;

import com.codingame.game.Utils.Vector2;

public class Item {
    private String identifier;
    private int playerId;
    private Vector2 pos;

    public Item(String identifier, int playerId) {
        this.identifier = identifier;
        this.playerId = playerId;
        this.pos = Vector2.INVALID;
    }

    public Item(String identifier, int playerId, Vector2 pos) {
        this.identifier = identifier;
        this.playerId = playerId;
        this.pos = pos;
    }

    public String getLowerCaseIdentifier() {
        return this.identifier.toLowerCase();
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public Vector2 getPos() {
        return pos;
    }
}
