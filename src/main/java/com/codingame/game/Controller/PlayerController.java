package com.codingame.game.Controller;

import com.codingame.game.Item;
import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Utils;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.CardView;
import com.codingame.game.View.PlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PlayerController {
    private Player model;
    private PlayerView view;
    private TileController tile;
    private Stack<CardController> hiddenCards = new Stack<>();
    private List<CardController> visibleCards = new ArrayList<>();

    public PlayerController(Player model, PlayerView view) {
        this.model = model;
        this.view = view;
    }

    public void setPosInMap(Vector2 pos) {
        setPosInMap(pos, 0);
    }

    public void setPosInMap(Vector2 pos, double time) {
        if (!Utils.isPosValid(pos)) {
            throw new RuntimeException("Player position out of map bounds!");
        }
        model.setAgentPosition(pos);
        view.setPosInMap(pos, time);
    }

    public void setSamePosInMap(double time) {
        view.setSamePosInMap(time);
    }

    public Vector2 getPos() {
        return model.getAgentPosition();
    }

    public void setTile(TileController tile) {
        this.tile = tile;
    }

    public TileController getTile() {
        return this.tile;
    }

    public Vector2 getTilePosition() {
        int x;
        int y;

        if (this.model.getIndex() == 0) {
            x = Constants.PLAYER_TILE_POS_X;
            y = Constants.PLAYER_TILE_POS_Y;
        } else {
            x = Constants.OPPONENT_TILE_POS_X;
            y = Constants.OPPONENT_TILE_POS_Y;
        }

        return new Vector2(x, y);
    }

    public void addCard(Item item, Vector2 pos) {
        CardView view = new CardView(item.getName(), this.model.getIndex());
        view.setPosAbsolute(pos.getX(), pos.getY());
        CardController card = new CardController(item, view);
        this.hiddenCards.add(card);
    }

    public void removeCard(CardController card) {
        if (card != null) {
            card.remove();
            this.visibleCards.remove(card);
        }
    }

    public List<CardController> getTopCards() {
        return visibleCards;
    }

    public void flipNewCard() {
        if (!hiddenCards.empty()) {
            CardController card = this.hiddenCards.pop();
            card.flip();
            this.visibleCards.add(card);
        }
    }

    public void flipVisibleCards(int numCards) {
        if (hiddenCards.size() >= numCards) {
            for (int i = 0; i < numCards; i++) {
                CardController card = this.hiddenCards.pop();
                card.flip();
                this.visibleCards.add(card);
            }
        }
    }

    public boolean hasCards() {
        return !this.visibleCards.isEmpty();
    }

    public int getNumCards() {
        return this.hiddenCards.size() + this.visibleCards.size();
    }

    public int getNumQuestCards() {
        return this.visibleCards.size();
    }

    public int getId() {
        return model.getIndex();
    }
}
