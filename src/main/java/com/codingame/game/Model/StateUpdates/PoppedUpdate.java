package com.codingame.game.Model.StateUpdates;

import com.codingame.game.Utils.Constants.Direction;

public class PoppedUpdate {
    private Direction direction;

    public PoppedUpdate(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
