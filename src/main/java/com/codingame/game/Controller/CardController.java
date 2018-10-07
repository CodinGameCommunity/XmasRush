package com.codingame.game.Controller;

import com.codingame.game.Item;
import com.codingame.game.View.CardView;

public class CardController {
    /**
     * The item model.
     */
    private Item model;

    /**
     * The card's view (UI).
     */
    private CardView view;

    /**
     * Creates a card from a model and a view.
     * @param model The card's model.
     * @param view The card's view.
     */
    public CardController(Item model, CardView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * @return the card's item model.
     */
    public Item getItem() {
        return model;
    }

    /**
     * Flips a card's side.
     * Shows the front if the back was visible.
     * Shows the back if the front was visible.
     */
    public void flip() {
        view.flip();
    }

    /**
     * Removes a card from the screen (makes it invisible).
     */
    public void remove() {
        model = null;
        view.hide();
    }
}
