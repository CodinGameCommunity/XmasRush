package com.codingame.game.View;

import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;

public class ArrowView extends AbstractView {
    private final Vector2 pos;
    private final double rotation;

    private Sprite sprite;

    public ArrowView(GraphicEntityModule entityModule, Vector2 pos, double rotation) {
        super(entityModule);
        this.pos = pos;
        this.rotation = rotation;

        createArrowView();
    }

    private void createArrowView() {
        sprite = entityModule.createSprite()
                .setImage(String.format("arrow_%d.png", 0))
                .setX(pos.getX())
                .setY(pos.getY())
                .setBaseWidth(Constants.TILE_SIZE)
                .setBaseHeight(Constants.TILE_SIZE / 2)
                .setAnchor(0.5)
                .setZIndex(0)
                .setRotation(rotation)
                .setVisible(false);
    }

    public void updateView() {}

    public void showArrow(int type) {
        sprite.setImage(String.format("arrow_%d.png", type))
                .setVisible(true);
        entityModule.commitEntityState(0, sprite);
    }

    public void hideArrow() {
        sprite.setVisible(false);
        entityModule.commitEntityState(0, sprite);
    }

    public Entity getEntity() {
        return sprite;
    }
}
