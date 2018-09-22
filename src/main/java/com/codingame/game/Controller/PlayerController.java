package com.codingame.game.Controller;

import com.codingame.game.Item;
import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.CardView;
import com.codingame.game.View.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {
    private Player model;
    private PlayerView view;
    private TileController tile;
    private List<CardController> cards = new ArrayList<>();

    public PlayerController(Player model, PlayerView view) {
        this.model = model;
        this.view = view;
    }

    private boolean isPosValid(Vector2 pos) {
        return (pos.x >= 0 && pos.x < Constants.MAP_WIDTH && pos.y >= 0 && pos.y < Constants.MAP_HEIGHT);
    }

    public void setPosInMap(Vector2 pos) {
        setPosInMap(pos, 0);
    }

    public void setPosInMap(Vector2 pos, double time) {
        if (!isPosValid(pos)) {
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
        CardView view = new CardView(this.model.getIndex(), item.getLowerCaseIdentifier());
        view.setPosAbsolute(pos.x, pos.y);
        CardController card = new CardController(item, view);
        this.cards.add(card);
    }

    public void removeTopCard() {
        CardController card = getTopCard();
        card.remove();
        this.cards.remove(card);
    }

    public CardController getTopCard() {
        return this.cards.get(this.cards.size() - 1);
    }

    public void flipTopCard() {
        getTopCard().flip();
    }

    public boolean hasCards() {
        return !this.cards.isEmpty();
    }

    public int getNumCards() {
        return this.cards.size();
    }
}
