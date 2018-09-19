package com.codingame.game.View;

import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

public class CardView {
    public static GraphicEntityModule graphicEntityModule;

    private Group group;

    private Sprite front;
    private Sprite back;
    private Sprite item;

    public CardView(int id, String itemId) {
        back = graphicEntityModule.createSprite()
                .setImage(String.format("cardBack_%d.png", id))
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.TILES.asValue())
                .setVisible(true);
        front = graphicEntityModule.createSprite()
                .setImage("cardFront.png")
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.TILES.asValue())
                .setVisible(false);
        item = graphicEntityModule.createSprite()
                .setImage(String.format("items" + System.getProperty("file.separator") + "item_%s_%d.png", itemId, id))
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.TILES.asValue())
                .setVisible(false);
        group = graphicEntityModule.createGroup()
                .setScale(1)
                .setX(0)
                .setY(0);
        group.add(back, front, item);
    }

    public void setPosAbsolute(int x, int y) {
        group.setX(x).setY(y);
    }

    public void flip() {
        back.setVisible(!back.isVisible());
        front.setVisible(!front.isVisible());
        item.setVisible(!item.isVisible());
    }

    public void hide() {
        group.setVisible(false);
    }
}
