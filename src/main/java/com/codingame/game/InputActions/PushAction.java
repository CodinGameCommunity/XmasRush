package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;

public class PushAction extends Action {
    public int lineId;
    public Constants.Direction direction;

    public PushAction(int lineId, Constants.Direction direction)
    {
        this.lineId = lineId;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return String.format("PUSH %d %s", lineId, direction);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof PushAction)) return false;
        PushAction action = (PushAction)other;
        return this.toString().equals(action.toString());
    }
}
