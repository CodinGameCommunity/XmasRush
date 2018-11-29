package com.codingame.game.View;

import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

public abstract class MovingView extends AbstractView {

    public MovingView(GraphicEntityModule entityModule) {
        super(entityModule);
    }

    public void setMapPos(Entity entity, Vector2 pos) {
        Vector2 newPos = Vector2.fromMapSpaceToViewSpace(pos);
        entity.setX(newPos.getX()).setY(newPos.getY());
    }

    public abstract Entity getEntity();
}
