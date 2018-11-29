package com.codingame.game.Model.StateUpdates;

import com.codingame.game.Utils.Constants.Direction;

public class PushedUpdate {
    private Direction direction;

    public PushedUpdate(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
