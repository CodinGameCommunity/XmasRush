package com.codingame.game.InputActions;

public abstract class Action {
    private final Type type;

    public Action(Type type) {
        this.type = type;
    }

    //The type of an input action.
    public enum Type {
        PUSH(0),
        MOVE(1),
        PASS(2);

        private int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Type getType() {
        return type;
    }

    public boolean isLegalAction(Action.Type turnType) {
        return turnType.equals(type) ||
                turnType.equals(Type.MOVE) && type.equals(Type.PASS);
    }

    public boolean isPassAction(){
        return type.equals(Type.PASS);
    }
}
