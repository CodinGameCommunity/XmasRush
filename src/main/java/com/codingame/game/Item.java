package com.codingame.game;

public class Item {
    private String identifier;
    private int playerId;

    public Item(String identifier, int playerId) {
        this.identifier = identifier;
        this.playerId = playerId;
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
}
