package com.codingame.game;

public class Item {
    private String identifier;
    private int playerId;

    public Item(String identifier, int playerId) {
        this.identifier = identifier;
        this.playerId = playerId;
    }

    public Item(Item item) {
        this.identifier = item.identifier;
        this.playerId = item.playerId;
    }

    public String getLowercaseIdentifier() {
        return this.identifier.toLowerCase();
    }

    public int getPlayerId() {
        return this.playerId;
    }
}
