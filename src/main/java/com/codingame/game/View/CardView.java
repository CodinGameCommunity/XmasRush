package com.codingame.game.View;

import com.codingame.game.Model.CardModel;
import com.codingame.game.Model.Item;
import com.codingame.game.Model.StateUpdates.CardPositionUpdate;
import com.codingame.game.Model.StateUpdates.FlipCardUpdate;
import com.codingame.game.Model.StateUpdates.RemoveCardUpdate;
import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

import java.util.Observable;

public class CardView extends MovingView {
    private Group group;
    private Sprite front;
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

    private void createCardView() {
        front = entityModule.createSprite()
                .setImage(String.format("cardFront_%d.png", cardItem.getPlayerId()))
                .setBaseWidth(Constants.CARD_SIZE)
                .setBaseHeight(Constants.CARD_SIZE)
                .setAnchor(0.5)
                .setZIndex(0);
        item = entityModule.createSprite()
                .setImage(String.format("item_%s_%d", cardItem.getName(), cardItem.getPlayerId()))
                .setAnchor(0.5)
                .setZIndex(1);
        group = entityModule.createGroup()
                .setScale(1)
                .setX(0)
                .setY(0)
                .setVisible(false);
        group.add(front, item);
    }

    public void updateView() {
        group.setZIndex(model.cardLayer);
        entityModule.commitEntityState(0, group);
        group.setX(model.getPos().getX()).setY(model.getPos().getY());
        entityModule.commitEntityState(0.5, group);
    }

    private void flip() {
        group.setVisible(true);
        entityModule.commitEntityState(0, group);
    }

    private void updatePosition(double time) {
        group.setX(model.getPos().getX()).setY(model.getPos().getY());
        entityModule.commitEntityState(time, group);
    }

    private void removeCardView() {
        group.setAlpha(0);
        group.setZIndex(group.getZIndex() - 1);
        entityModule.commitEntityState(1, group);
        doDispose();
    }

    public void update(Observable observable, Object update) {
        super.update(model, update);
        if (update instanceof FlipCardUpdate) {
            flip();
        } else if (update instanceof CardPositionUpdate) {
            updatePosition(((CardPositionUpdate)update).getTime());
        } else if (update instanceof RemoveCardUpdate){
            removeCardView();
        }
    }

    public Entity getEntity() {
        return group;
    }
}
