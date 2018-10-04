package com.codingame.game;

/**
 * Item is an immutable type representing the icon that can appear on a tile or card.
 * Each item is uniquely identified by its name and id of the player it can be acquired by.
 */
public class Item {
    /**
     * All uppercase String object.
     * May only be assigned values from Constants.ITEM_NAMES.
     */
    private String name;

    /**
     * The id of the player that can acquire the item (0 for player or 1 for opponent).
     */
    private int playerId;

    /**
     * Create an Item.
     * @param name Item name. Must be a valid Item name.
     * @param playerId The id of the player that can acquire the item (0 for player or 1 for opponent).
     */
    public Item(String name, int playerId) {
        this.name = name;
        this.playerId = playerId;
    }

    /**
     * @return the item name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the id of the player that can acquire the item (0 for player or 1 for opponent).
     */
    public int getPlayerId() {
        return this.playerId;
    }
}
