package com.codingame.game;
import com.codingame.game.Controller.TileController;
import com.codingame.game.InputActions.AbstractAction;
import com.codingame.game.InputActions.InvalidAction;
import com.codingame.game.InputActions.MoveAction;
import com.codingame.game.InputActions.PushAction;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;

import java.util.regex.Matcher;

public class Player extends AbstractMultiplayerPlayer {
    private TileController tile;
    private Vector2 pos;

    public AbstractAction getAction() throws TimeoutException, InvalidAction {
        try {
            String playerAction = this.getOutputs().get(0);
            Matcher matchPush = Constants.PLAYER_INPUT_PUSH_PATTERN.matcher(playerAction);
            Matcher matchMove = Constants.PLAYER_INPUT_MOVE_PATTERN.matcher(playerAction);
            if (matchPush.matches()) {
                return new PushAction(Integer.parseInt(matchPush.group("id")),
                        Constants.Direction.valueOf(matchPush.group("direction")));
            } else if (matchMove.matches()) {
                return new MoveAction(Integer.parseInt(matchMove.group("amount")),
                        Constants.Direction.valueOf(matchMove.group("direction")));
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

    public void setAgentPosition(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getAgentPosition() {
        return this.pos;
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
