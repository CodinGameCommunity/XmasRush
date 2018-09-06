package com.codingame.game.Controller;

import com.codingame.game.InputActions.InvalidAction;
import com.codingame.game.InputActions.MoveAction;
import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.PlayerView;

import java.util.List;

public class PlayerController {
    private Player model;
    private PlayerView view;
    private TileController tile;

    public PlayerController(Player model, PlayerView view) {
        this.model = model;
        this.view = view;
    }

    public void setPosInMap(Vector2 pos) {
        model.setAgentPosition(pos);
        view.setPosInMap(pos.y, pos.x);
    }

    public void moveAgentBy(List<MoveAction.Step> steps) throws InvalidAction {
        double time = 0;
        for (MoveAction.Step step : steps) {
            Vector2 offset = step.direction.asValue().mult(step.amount);
            Vector2 newPos = model.getAgentPosition().add(offset);
            if (newPos.x < 0 || newPos.y < 0 || newPos.x >= Constants.MAP_WIDTH || newPos.y >= Constants.MAP_HEIGHT) {
                throw new InvalidAction("Out of map bounds!");
            }
            model.setAgentPosition(newPos);

            time += 1.0 / steps.size();
            view.setPosInMap(newPos.x, newPos.y, time);
        }
    }

    public Vector2 getPos() {
        return model.getAgentPosition();
    }

    public void setTile(TileController tile) {
        this.tile = tile;
    }

    public TileController getTile() {
        return this.tile;
    }

    public Vector2 getTilePosition() {
        int x;
        int y;

        if (this.model.getIndex() == 0) {
            x = Constants.PLAYER_TILE_POS_X;
            y = Constants.PLAYER_TILE_POS_Y;
        } else {
            x = Constants.OPPONENT_TILE_POS_X;
            y = Constants.OPPONENT_TILE_POS_Y;
        }

        return new Vector2(x, y);
    }
}