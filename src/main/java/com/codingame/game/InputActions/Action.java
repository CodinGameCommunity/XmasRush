package com.codingame.game.InputActions;

public abstract class Action {
    public enum Type {
        PUSH,
        MOVE;
        public static Type fromInt(int value) {
            switch(value) {
                case 0: return PUSH;
                case 1: return MOVE;
                default: throw new RuntimeException("Action type must be 0(MOVE) or 1(PUSH)");
            }
        }
    }
}
