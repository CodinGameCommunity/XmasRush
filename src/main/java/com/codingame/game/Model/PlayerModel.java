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
            Vector2 newCardPos = new Vector2(cardPos.getX(),
                    cardPos.getY() + orientation * i * Constants.CARDS_OFFSET);
            CardModel card = new CardModel(itemList.get(i), newCardPos);
            //check if item belongs to the player
            assert card.getItem().getPlayerId() == id;
            hiddenCards.add(card);
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
        int orientation = (id == 0) ? 1 : -1;
        int offsetY = !hiddenCards.isEmpty() ? hiddenCards.lastElement().getPos().getY() : visibleCards.get(0).getPos().getY();
        for (int i = visibleCards.size() - 2; i >= 0; i--) {
            CardModel card = visibleCards.get(i);
            offsetY += orientation * (Constants.CARDS_OFFSET + Constants.CARD_HEIGHT / 1.5);
            Vector2 newCardPos = new Vector2(card.getPos().getX(), offsetY);
            card.setPos(newCardPos);
            card.updatePosition();
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
