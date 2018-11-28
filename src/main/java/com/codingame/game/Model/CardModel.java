package com.codingame.game.Model;

import com.codingame.game.Model.StateUpdates.CardPositionUpdate;
import com.codingame.game.Model.StateUpdates.FlipCardUpdate;
import com.codingame.game.Model.StateUpdates.RemoveCardUpdate;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;

public class CardModel extends MovingModel {
    private final Item item;
    public int cardLayer = -1;

    private void checkRep() {
        assert getPos().getX() >= 0 && getPos().getX() < Constants.SCREEN_WIDTH;
        assert getPos().getY() >= 0 && getPos().getY() < Constants.SCREEN_HEIGHT;
    }

    public CardModel(Item item, Vector2 pos) {
        super(pos);
        this.item = item;
        checkRep();
    }

    public Item getItem() {
        return item;
    }

    public void flip() {
        updateState(new FlipCardUpdate());
    }

    public void updatePosition() {
        updateState(new CardPositionUpdate());
    }

    public void setCardLayer(int layer) {
        cardLayer = layer;
    }

    public void remove() {
        updateState(new RemoveCardUpdate());
    }

    public String cardToString() {
        return item.itemToString();
    }

    public String opponentCardToString() {
        return item.opponentItemToString();
    }
}
