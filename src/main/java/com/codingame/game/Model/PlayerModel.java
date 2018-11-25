package com.codingame.game.Model;

import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;

import java.util.*;

public class PlayerModel extends MovingModel {
    public final int id;
    private final Vector2 tilePos;
    private final Stack<CardModel> hiddenCards = new Stack<>();
    private final List<CardModel> visibleCards = new ArrayList<>();

    private TileModel tile;

    public PlayerModel(int id) {
        super(Constants.PLAYER_POSITIONS.get(id));
        this.id = id;
        tilePos = Constants.TILE_MODEL_POSITIONS.get(id);
    }

    //Tile methods
    public void setTile(TileModel tile) {
        removeTile();
        this.tile = tile;
        this.tile.setPos(tilePos);
        tile.setPlayerId(id);
    }

    private void removeTile() {
        if (tile != null)
            tile.setPlayerId(null);
    }

    public TileModel getTile() {
        return this.tile;
    }

    //Card methods
    public void setCards(List<Item> itemList) {
        //check if all items are unique
        assert itemList.size() == new HashSet<>(itemList).size();

        int orientation = (id == 0) ? 1 : -1;
        Vector2 cardPos = new Vector2(Constants.CARD_POSITIONS.get(id));

        for (int i = 0; i < itemList.size(); i++) {
            CardModel card = new CardModel(itemList.get(i), cardPos);
            //check if item belongs to the player
            assert card.getItem().getPlayerId() == id;
            hiddenCards.add(card);
            cardPos.setX(cardPos.getX());
        }
    }

    public List<CardModel> getCards() {
        return Collections.unmodifiableList(hiddenCards);
    }

    private void removeCard(CardModel card) {
        assert visibleCards.contains(card);
        card.remove();
        visibleCards.remove(card);
    }

    private int getNumCards() {
        return hiddenCards.size() + visibleCards.size();
    }

    public int getNumQuestCards() {
        return visibleCards.size();
    }

    public boolean removeItemCard(Item item){
        for (CardModel card : visibleCards) {
            if (item.equals(card.getItem())) {
                removeCard(card);
                adjustCardsPosition();
                return true;
            }
        }
        return false;
    }

    private void flipCard() {
        if (!hiddenCards.isEmpty()) {
            CardModel card = hiddenCards.pop();
            card.flip();
            visibleCards.add(card);
            adjustCardsPosition();
        }
    }

    private void adjustCardsPosition() {
        if (visibleCards.isEmpty()) {
            return;
        }
        int orientation = (id == 0) ? 1 : -1;
        Vector2 cardPos = new Vector2(Constants.CARD_POSITIONS.get(id));
        cardPos.setX(cardPos.getX() + orientation * (Constants.CARD_WIDTH + Constants.CARDS_OFFSET));
        for (int i = 0; i < visibleCards.size(); i++) {
            CardModel card = visibleCards.get(i);
            card.setPos(cardPos);
            card.updatePosition();
            cardPos.setX(cardPos.getX() + orientation * (int)(Constants.CARD_WIDTH / 1.2));
            card.updateZIndex(i);
        }
    }

    //Flip the number of cards required to have the specified number of visible cards (numCards)
    public void flipCards(int numCards) {
        if (visibleCards.size() < numCards) {
            int availableCards = Math.min(numCards - visibleCards.size(), hiddenCards.size());
            for (int i = 0; i < availableCards; i++) flipCard();
        }
    }

    //Player input methods
    public String playerToString() {
        return getNumCards() + " " + getPos().toString() + " " + tile.patternToString();
    }

    public void sendCardsToPlayer(Player player) {
        for (CardModel card : visibleCards) {
            if (player.getIndex() == Constants.PLAYER_INDEX)
                player.sendInputLine(card.cardToString());
            else
                player.sendInputLine(card.opponentCardToString());
        }
    }
}
