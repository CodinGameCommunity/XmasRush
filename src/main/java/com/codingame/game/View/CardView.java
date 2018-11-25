package com.codingame.game.View;

import com.codingame.game.Model.CardModel;
import com.codingame.game.Model.Item;
import com.codingame.game.Model.StateUpdates.CardPositionUpdate;
import com.codingame.game.Model.StateUpdates.CardZIndexUpdate;
import com.codingame.game.Model.StateUpdates.FlipCardUpdate;
import com.codingame.game.Model.StateUpdates.RemoveCardUpdate;
import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

import java.util.Observable;

public class CardView extends AbstractView {
    private Group group;
    private Sprite front;
    private Sprite back;
    private Sprite item;

    private Item cardItem;
    private CardModel model;

    public CardView(GraphicEntityModule entityModule, CardModel card) {
        super(entityModule);
        this.model = card;
        this.cardItem = model.getItem();
        card.addObserver(this);
        createCardView();
    }

    public void createCardView() {
        back = entityModule.createSprite()
                .setImage(String.format("cardBack_%d.png", cardItem.getPlayerId()))
                .setBaseWidth(Constants.CARD_WIDTH)
                .setBaseHeight(Constants.CARD_HEIGHT)
                .setAnchor(0.5)
                .setZIndex(0)
                .setVisible(true);
        front = entityModule.createSprite()
                .setImage("cardFront.png")
                .setBaseWidth(Constants.CARD_WIDTH)
                .setBaseHeight(Constants.CARD_HEIGHT)
                .setAnchor(0.5)
                .setZIndex(1)
                .setVisible(false);
        item = entityModule.createSprite()
                .setImage(String.format("items" + System.getProperty("file.separator") + "item_%s_%d.png", cardItem.getName(), cardItem.getPlayerId()))
                .setAnchor(0.5)
                .setZIndex(2)
                .setVisible(false);
        group = entityModule.createGroup()
                .setScale(1)
                .setX(0)
                .setY(0)
                .setZIndex(2);
        group.add(back, front, item);
        group.setX(model.getPos().getX()).setY(model.getPos().getY());
    }

    public void updateView() {}

    private void flip() {
        front.setVisible(!front.isVisible());
        back.setVisible(!back.isVisible());
        item.setVisible(!item.isVisible());
        entityModule.commitEntityState(0, front, item);
    }

    private void updatePosition() {
        group.setX(model.getPos().getX()).setY(model.getPos().getY());
        entityModule.commitEntityState(0.5, group);
    }

    private void updateZIndex(int zIndex) {
        group.setZIndex(zIndex);
    }

    private void removeCardView() {
        group.setAlpha(0);
        group.setZIndex(group.getZIndex() - 1);
        entityModule.commitEntityState(0.5, group);
        doDispose();
    }

    public void update(Observable observable, Object update) {
        super.update(model, update);
        if (update instanceof FlipCardUpdate) {
            flip();
        } else if (update instanceof CardPositionUpdate) {
            updatePosition();
        } else if (update instanceof RemoveCardUpdate) {
            removeCardView();
        } else if (update instanceof CardZIndexUpdate) {
            updateZIndex(((CardZIndexUpdate)update).getZIndex());
        }
    }
}
