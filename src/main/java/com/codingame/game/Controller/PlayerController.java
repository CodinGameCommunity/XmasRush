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

    public void setPosInMap(Vector2 pos) {
        model.setAgentPosition(pos);
        view.setPosInMap(pos, 1.0);
    }

    public void setPosInMap(Vector2 pos, double time) {
        model.setAgentPosition(pos);
        view.setPosInMap(pos, time);
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

    public void addItemCard(Item item, Vector2 pos) {
        CardView view = new CardView(this.model.getIndex(), item.getLowercaseIdentifier());
        view.setPosAbsolute(pos.x, pos.y);
        CardController card = new CardController(item, view);
        this.cards.add(card);
    }

    public Item getTopCardItem() {
        return this.cards.get(this.cards.size() - 1).getItem();
    }

    public void flipTopCard() {
        this.cards.get(this.cards.size() - 1).flip();
    }

    public void removeCard(Item item) {
        this.cards.remove(item);
    }

    public boolean hasCards() {
        return !this.cards.isEmpty();
    }

    public int getNumCards() {
        return this.cards.size();
    }
}
