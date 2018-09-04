package com.codingame.game.Controller;

import com.codingame.game.InputActions.InvalidAction;
import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.PlayerView;

public class PlayerController {
    private Player model;
    private PlayerView view;

    public PlayerController(Player model, PlayerView view) {
        this.model = model;
        this.view = view;
    }

    public void setPosInMap(Vector2 pos) {
        model.setAgentPosition(pos);
        view.setPosInMap(pos.y, pos.x);
    }

    public void moveAgentBy(Vector2 dir) throws InvalidAction {
        Vector2 newPos = model.getAgentPosition().add(dir);
        if (newPos.x < 0 || newPos.y < 0 || newPos.x >= Constants.MAP_WIDTH || newPos.y >= Constants.MAP_HEIGHT) {
            throw new InvalidAction("Out of map bounds!");
        }
        model.setAgentPosition(newPos);
        view.setPosInMap(newPos.x, newPos.y);
    }

    public Vector2 getPos() {
        return model.getAgentPosition();
    }

    public void setTile(TileController tile) {
        model.setTile(tile);
    }
}
