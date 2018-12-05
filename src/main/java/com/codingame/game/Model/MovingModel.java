package com.codingame.game.Model;

import com.codingame.game.Utils.Constants.Direction;
import com.codingame.game.Utils.Vector2;

public abstract class MovingModel extends AbstractModel{

    public MovingModel(Vector2 pos) {
        super(pos);
    }

    //move to a pos
    public void move(Vector2 pos) {
        super.setPos(pos);
    }

    //move in the specified direction
    public void move(Direction direction) {
        Vector2 pos = getPos();
        move(pos.add(direction.asVector()));
    }
}
