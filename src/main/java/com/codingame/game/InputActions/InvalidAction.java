package com.codingame.game.InputActions;

/**
 * Invalid action input exception
 */
public class InvalidAction extends Exception {
    /**
     * The fatal value says if a player should be disqualified then the exception happens.
     */
    private boolean isFatal;

    /**
     * Creates an invalid action exception that is fatal by default.
     * @param message The exception message.
     */
    public InvalidAction(String message) {
        super(message);
        this.isFatal = true;
    }

    /**
     * Creates an invalid action exception with fatal information.
     * @param message The exception message.
     * @param isFatal Says if the exception is fatal and the player should be disqualified.
     */
    public InvalidAction(String message, boolean isFatal) {
        super(message);
        this.isFatal = isFatal;
    }

    /**
     * @return true if the exception is fatal and the player should be disqualified.
     *         false otherwise.
     */
    public boolean isFatal() {
        return isFatal;
    }
}
