package com.codingame.game.Controller;

import com.codingame.game.Item;
import com.codingame.game.View.CardView;

public class CardController {
    private Item model;
    private CardView view;

    public CardController(Item model, CardView view) {
        this.model = model;
        this.view = view;
    }

    public Item getItem() {
        return model;
    }

    public void flip() {
        view.flip();
    }
}
