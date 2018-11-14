package com.codingame.game;

import com.codingame.game.Model.PlayerModel;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;

public class Player extends AbstractMultiplayerPlayer {
    private int expectedOutputLines;
    private PlayerModel player;

    @Override
    public int getExpectedOutputLines() {
        return 1;
    }

    public void setExpectedOutputLines(int expectedOutputLines) {
        this.expectedOutputLines = expectedOutputLines;
    }

    public PlayerModel createPlayer() {
        player = new PlayerModel(this.getIndex());
        return player;
    }

    public PlayerModel getPlayer() {
        return player;
    }
}
