package com.codingame.game.View;

import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

import static com.codingame.game.Utils.Utils.graphicEntityModule;

public class CardView {
    /**
     * Acts as a parent for all graphic elements.
     */
    private Group group;

    /**
     * The front sprite of a card.
     */
    private Sprite front;

    /**
     * The back sprite of a card.
     */
    private Sprite back;

    /**
     * The item sprite of a card.
     */
    private Sprite item;

    /**
     * Create a card using a unique id and item id
     * @param itemName The name of the item on the card.
     * @param playerId The id of the player the card belongs to.
     */
    public CardView(String itemName, int playerId) {
        back = graphicEntityModule.createSprite()
                .setImage(String.format("cardBack_%d.png", playerId))
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.TILES.asValue())
                .setVisible(true);
        front = graphicEntityModule.createSprite()
                .setImage("cardFront.png")
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.TILES.asValue())
                .setVisible(false);
        item = graphicEntityModule.createSprite()
                .setImage(String.format("items" + System.getProperty("file.separator") + "item_%s_%d.png", itemName, playerId))
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.TILES.asValue())
                .setVisible(false);
        group = graphicEntityModule.createGroup()
                .setScale(1)
                .setX(0)
                .setY(0);
        group.add(back, front, item);
    }

    /**
     * Sets a player's card position anywhere on the screen.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void setPosAbsolute(int x, int y) {
        group.setX(x).setY(y);
    }

    /**
     * Flips a card's side.
     * Shows the front if the back was visible.
     * Shows the back if the front was visible.
     */
    public void flip() {
        back.setVisible(!back.isVisible());
        front.setVisible(!front.isVisible());
        item.setVisible(!item.isVisible());
    }

    /**
     * Sets a card as invisible.
     */
    public void hide() {
        group.setVisible(false);
    }
}
