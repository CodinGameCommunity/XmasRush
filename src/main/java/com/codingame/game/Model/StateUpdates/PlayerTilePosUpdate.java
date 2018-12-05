package com.codingame.game.Model.StateUpdates;
import com.codingame.game.Utils.Vector2;

public class PlayerTilePosUpdate {
    private Vector2 pos;

    public PlayerTilePosUpdate(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getPos() {
        return pos;
    }

}
