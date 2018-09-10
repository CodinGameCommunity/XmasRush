package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;

public class Action extends AbstractAction {
    public enum Type {
        PUSH,
        MOVE
    }

    public enum Direction {
        UP(Constants.Direction.UP),
        RIGHT(Constants.Direction.RIGHT),
        DOWN(Constants.Direction.DOWN),
        LEFT(Constants.Direction.LEFT);

        private final Constants.Direction value;
        Direction(Constants.Direction value) {
            this.value = value;
        }
        public Constants.Direction asValue() {
            return value;
        }
    }

    public int id;
    public Type type;
    public Direction direction;
    public int amount;

    public Action()
    {
        this.id = -1;
        this.type = Type.PUSH;
        this.direction = Direction.UP;
        this.amount = -1;
    }

    public static Action newPushAction(int id, Direction direction) {
        Action action = new Action();
        action.id = id;
        action.type = Type.PUSH;
        action.direction = direction;
        return action;
    }

    public static Action newMoveAction(int amount, Direction direction) {
        Action action = new Action();
        action.amount = amount;
        action.type = Type.MOVE;
        action.direction = direction;
        return action;
    }

    @Override
    public String toString() {
        switch (type)
        {
            case PUSH: return String.format("PUSH %d %s", id, direction);
            case MOVE: return String.format("MOVE %d %s", amount, direction);
        }
        return super.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Action)) return false;
        Action action = (Action)other;
        return this.toString().equals(action.toString());
    }
}
