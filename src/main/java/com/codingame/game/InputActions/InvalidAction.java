package com.codingame.game.InputActions;

public class InvalidAction extends Exception {
    private boolean isFatal;

    public InvalidAction(String message) {
        super(message);
        this.isFatal = true;
    }

    public InvalidAction(String message, boolean isFatal) {
        super(message);
        this.isFatal = isFatal;
    }

    public boolean isFatal() {
        return isFatal;
    }
}
