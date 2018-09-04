package com.codingame.game.View;

import com.codingame.game.InputActions.InvalidAction;
import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;

public class PlayerView {
    public static GraphicEntityModule graphicEntityModule;

    private Sprite sprite;

    public PlayerView(int id) {
        sprite = graphicEntityModule.createSprite()
                .setImage(String.format("agent_%d.png", id))
                .setAnchor(0.5)
                .setZIndex(Constants.MapLayers.AGENTS.asValue());
    }

    public void setPosInMap(int i, int j) {
        int x = Constants.MAP_POS_X + i * (Constants.TILE_SIZE + Constants.TILE_SPACE);
        int y = Constants.MAP_POS_Y + j * (Constants.TILE_SIZE + Constants.TILE_SPACE);
        sprite.setX(x).setY(y);
    }
}
