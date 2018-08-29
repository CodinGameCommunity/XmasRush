package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;

public class PushAction extends AbstractAction {
    public enum PushDirection {
        UP(Constants.PushDirection.UP),
        RIGHT(Constants.PushDirection.RIGHT),
        DOWN(Constants.PushDirection.DOWN),
        LEFT(Constants.PushDirection.LEFT);

        private final Constants.PushDirection value;
        PushDirection(Constants.PushDirection value) {
            this.value = value;
        }
        public Constants.PushDirection asValue() {
            return value;
        }
    }

    public int id;
    public PushDirection direction;

    public PushAction(int id, PushDirection direction) {
        this.id = id;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return id + " " + this.direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PushAction) {
            PushAction other = (PushAction) obj;
            return this.id == other.id && this.direction == other.direction;
        } else {
            return false;
        }
    }
}
