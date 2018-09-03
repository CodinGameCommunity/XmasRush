package com.codingame.game;
import com.codingame.game.Controller.TileController;
import com.codingame.game.InputActions.AbstractAction;
import com.codingame.game.InputActions.InvalidAction;
import com.codingame.game.InputActions.Action;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends AbstractMultiplayerPlayer {
    private TileController tile;
    private Vector2 agentPosition;

    private static final Pattern PLAYER_INPUT_PUSH_PATTERN = Pattern
            .compile("(?<pushAction>\\bpush\\b) (?<id>[1,3,5]) (?<direction>(\\bup\\b|\\bright\\b|\\bdown\\b|\\bleft\\b))",
                    Pattern.CASE_INSENSITIVE);
    private static final Pattern PLAYER_INPUT_MOVE_PATTERN = Pattern
            .compile("(?<moveAction>\\bmove\\b) ((?<amount>[1-6]) (?<direction>(\\bup\\b|\\bright\\b|\\bdown\\b|\\bleft\\b))(;)?)+",
                    Pattern.CASE_INSENSITIVE);

    // TODO: look at reference for pattern matcher: https://github.com/CodinGame/coders-of-the-caribbean/blob/0478489c0a3a18163b87a773d53902847bd98a81/Referee.java
    public AbstractAction getAction() throws TimeoutException, InvalidAction {
        try {
            String playerAction = this.getOutputs().get(0);
            Matcher matchPush = PLAYER_INPUT_PUSH_PATTERN.matcher(playerAction);
            Matcher matchMove = PLAYER_INPUT_MOVE_PATTERN.matcher(playerAction);
            if (matchPush.matches()) {
                return Action.newPushAction(Integer.parseInt(matchPush.group("id")),
                        Action.Direction.valueOf(matchPush.group("direction")));
            } else if (matchMove.matches()) {
                return Action.newMoveAction(Integer.parseInt(matchMove.group("amount")),
                        Action.Direction.valueOf(matchMove.group("direction")));
            } else {
                throw new InvalidAction("Invalid output.");
            }
        } catch (TimeoutException | InvalidAction e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidAction("Invalid output.");
        }
    }

    public void setTile(TileController tile) {
        this.tile = tile;
    }
    public TileController getTile() {
        return this.tile;
    }

    public void setAgentPosition(Vector2 position) {
        this.agentPosition = position;
    }

    public Vector2 getAgentPosition() {
        return this.agentPosition;
    }

    public Vector2 getTilePosition() {
        int x;
        int y;

        if (this.getIndex() == 0) {
            x = Constants.PLAYER_TILE_POS_X;
            y = Constants.PLAYER_TILE_POS_Y;
        } else {
            x = Constants.OPPONENT_TILE_POS_X;
            y = Constants.OPPONENT_TILE_POS_Y;
        }

        return new Vector2(x, y);
    }

    @Override
    public int getExpectedOutputLines() {
        // Returns the number of expected lines of outputs for a player
        return 1;
    }
}
