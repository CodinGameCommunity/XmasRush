package com.codingame.game.Model;

import com.codingame.game.Utils.Constants;

/**
 * Item is an immutable type representing the icon that can appear on a tile or card.
 * Each item is uniquely identified by its name and id of the player it can be acquired by.
 */
public class Item {
    private final String name;
    private final int playerId;

    private void checkRep() {
       assert name != null;
       assert Constants.ITEM_NAMES.contains(name);
       assert playerId == Constants.PLAYER_INDEX || playerId == Constants.OPPONENT_INDEX;
    }

    public Item(String name, int playerId) {
        this.name = name;
        this.playerId = playerId;
        checkRep();
    }

    public String getName() {
        return this.name;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public int getOpponentId() {
        return (playerId == Constants.PLAYER_INDEX) ? Constants.OPPONENT_INDEX : Constants.PLAYER_INDEX;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Item)) {
            return false;
        }
        Item other = (Item)obj;
        return this.name.equals(other.name) &&
                this.playerId == other.playerId;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + playerId;
        result = 37 * result + name.hashCode();
        return result;
    }

    public String itemToString() {
        return name + " " + playerId;
    }

    public String toTooltip() {
        return "item: " + itemToString();
    }

    public String opponentItemToString() {
        return name + " " + getOpponentId();
    }
}
