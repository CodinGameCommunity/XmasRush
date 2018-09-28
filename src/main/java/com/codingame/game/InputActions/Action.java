package com.codingame.game.InputActions;

public abstract class Action {
    public enum Type {
        PUSH(0),
        MOVE(1);

        private int value;
        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Type fromInt(int value) {
            switch (value) {
                case 0: return PUSH;
                case 1: return MOVE;
                default: return null;
            }
        }
    }
}
