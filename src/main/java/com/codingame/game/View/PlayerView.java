package com.codingame.game.View;

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
        int x = Constants.MAP_POS_X + i * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        int y = Constants.MAP_POS_Y + j * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        sprite.setX(x).setY(y);
    }

    public void setPosInMap(int i, int j, double time) {
        setPosInMap(i, j);
        graphicEntityModule.commitEntityState(time, sprite);
    }
}
