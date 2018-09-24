package com.codingame.game.View;

import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Utils;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.Sprite;

import static com.codingame.game.Utils.Utils.graphicEntityModule;

public class PlayerView {
    private Sprite sprite;

    public PlayerView(int id) {
        sprite = graphicEntityModule.createSprite()
                .setImage(String.format("agent_%d.png", id))
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.AGENTS.asValue());
    }

    public void setPosInMap(Vector2 pos, double time) {
        Utils.setPosInMap(sprite, pos, time);
    }

    public void setSamePosInMap(double time) {
        sprite.setX(sprite.getX() + 1);

        graphicEntityModule.commitEntityState(time, sprite);
    }
}
