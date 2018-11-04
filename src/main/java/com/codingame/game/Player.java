package com.codingame.game;
import com.codingame.game.InputActions.*;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;

import java.util.regex.Matcher;

public class Player extends AbstractMultiplayerPlayer {
    private int expectedOutputLines;
    private Vector2 pos;
	
    @Override
    public int getExpectedOutputLines() {
        return expectedOutputLines;
    }

    public void setExpectedOutputLines(int expectedOutputLines) {
        this.expectedOutputLines = expectedOutputLines;
    }

    public Action getAction(String playerAction) throws InvalidAction {
        Matcher matchPush = Constants.PLAYER_INPUT_PUSH_PATTERN.matcher(playerAction);
        Matcher matchMove = Constants.PLAYER_INPUT_MOVE_PATTERN.matcher(playerAction);
        Matcher matchPass = Constants.PLAYER_INPUT_PASS_PATTERN.matcher(playerAction);
        if (matchPush.matches()) {
            return new PushAction(Integer.parseInt(matchPush.group("id")),
                    Constants.Direction.valueOf(matchPush.group("direction")));
        } else if (matchMove.matches()) {
            Matcher tokensMatcher = Constants.PLAYER_INPUT_MOVE_TOKENS_PATTERN.matcher(playerAction);
            MoveAction moveAction = new MoveAction();
            while (tokensMatcher.find()) {
                moveAction.addAction(Constants.Direction.valueOf(tokensMatcher.group("direction")));
            }
            return moveAction;
        } else if (matchPass.matches()) {
            return new PassAction();
        } else {
            throw new InvalidAction(playerAction);
        }
    }

    public void setAgentPosition(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getAgentPosition() {
        return this.pos;
    }
}
