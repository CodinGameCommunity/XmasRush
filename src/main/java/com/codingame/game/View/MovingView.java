package com.codingame.game.View;

import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

public abstract class MovingView extends AbstractView {

    public MovingView(GraphicEntityModule entityModule) {
        super(entityModule);
    }

    public void setMapPos(Entity entity, Vector2 pos) {
        int x = Constants.MAP_POS_X + pos.getX() * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        int y = Constants.MAP_POS_Y + pos.getY() * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        entity.setX(x).setY(y);
    }

    public abstract Entity getEntity();
}
