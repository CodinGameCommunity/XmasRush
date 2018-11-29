package com.codingame.game.Model;

import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;

import java.util.*;

public class PlayerModel extends MovingModel {
    public final int id;
    public final int orientation;
    private final Vector2 tilePos;
    private final Stack<CardModel> hiddenCards = new Stack<>();
    private final List<CardModel> visibleCards = new ArrayList<>();

    private final int maxNumCards = 3; //max number of cards to show
    private int numVisibleCards;
    private int numTotalCards;
    private final Vector2 deckPosition;
    private final Vector2 cardPosition;

    private TileModel tile;

    public PlayerModel(int id) {
        super(Constants.PLAYER_POSITIONS.get(id));
        this.id = id;
        orientation = (id == 0) ? 1 : -1;
        deckPosition = new Vector2(Constants.DECK_POSITIONS.get(id));
        cardPosition = new Vector2(Constants.CARD_POSITIONS.get(id));
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

        numTotalCards = itemList.size();
        Vector2 cardPos = new Vector2(deckPosition);
        for (Item item : itemList) {
            CardModel card = new CardModel(item, cardPos);
            //check if item belongs to the player
            assert card.getItem().getPlayerId() == id;
            hiddenCards.add(card);
        }
    }

    public List<CardModel> getCards() {
        return Collections.unmodifiableList(hiddenCards);
    }

    public void setNumVisibleCards(int numVisibleCards) {
        this.numVisibleCards = (numVisibleCards <= maxNumCards) ? numVisibleCards : maxNumCards;
    }

    private void adjustCardsPosition(boolean isFirstFlip) {
        if (visibleCards.isEmpty()) {
            return;
        }
        int orientation = (id == 0) ? 1 : -1;
        Vector2 cardPos = new Vector2(cardPosition);
        int layer = 0;
        for (int i = 0; i < visibleCards.size(); i++) {
            CardModel card = visibleCards.get(i);
            card.move(cardPos);
            card.updatePosition(isFirstFlip ? 0 : 1);
            layer++;
            card.setCardLayer(layer);
            cardPos.setX(cardPos.getX() + orientation * (Constants.CARD_SIZE + Constants.CARDS_OFFSET_X));
        }
    }

    private int getNumCards() {
        return hiddenCards.size() + visibleCards.size();
    }

    public int getNumDeckCards() {
        return hiddenCards.size();
    }

    public int getNumQuestCards() {
        return visibleCards.size();
    }

    public boolean removeCard(Item item){
        for (CardModel card : visibleCards) {
            if (item.equals(card.getItem())) {
                card.remove();
                visibleCards.remove(card);
                adjustCardsPosition(false);
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
        }
    }

    //Flip the number of cards required to have the specified number of visible cards
    public void flipCards() {
        boolean isFirstFlip = (hiddenCards.size() == numTotalCards);
        if (visibleCards.size() < numVisibleCards) {
            int availableCards = Math.min(numVisibleCards - visibleCards.size(), hiddenCards.size());
            if (availableCards > 0) {
                for (int i = 0; i < availableCards; i++) {
                    flipCard();
                }
                adjustCardsPosition(isFirstFlip);
            }
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
