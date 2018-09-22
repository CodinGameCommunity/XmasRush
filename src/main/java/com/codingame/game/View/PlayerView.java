package com.codingame.game.View;

import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
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

    public void setPosInMap(Vector2 pos, double time) {
        int x = Constants.MAP_POS_X + pos.y * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        int y = Constants.MAP_POS_Y + pos.x * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        sprite.setX(x).setY(y);

        graphicEntityModule.commitEntityState(time, sprite);
    }

    public void setSamePosInMap(double time) {
        sprite.setX(sprite.getX() + 1);

        graphicEntityModule.commitEntityState(time, sprite);
    }
}
