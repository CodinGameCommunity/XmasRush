package com.codingame.game.View;


import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;


public class ArrowView extends AbstractView {
    private final Vector2 pos;
    private final double rotation;
    private final String id;

    private Sprite sprite;

    public ArrowView(GraphicEntityModule entityModule, Vector2 pos, double rotation, String id) {
        super(entityModule);
        this.pos = pos;
        this.rotation = rotation;
        this.id = id;
        createArrowView();
    }

    private void createArrowView() {
        sprite = entityModule.createSprite()
                .setImage(String.format("arrow_%d.png", 2))
                .setX(pos.getX())
                .setY(pos.getY())
                .setRotation(rotation)
                .setVisible(false)
                .setZIndex(0);
    }

    public void updateView() {}

    public void showArrow(int type) {
        sprite.setImage(String.format("arrow_%d.png", type))
                .setVisible(true);
        entityModule.commitEntityState(0, sprite);
    }

    public void hideArrows() {
        sprite.setVisible(false);
        entityModule.commitEntityState(0, sprite);
    }

    public Entity getEntity() {
        return sprite;
    }
}
