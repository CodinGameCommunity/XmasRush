package com.codingame.game.Model;

import java.util.Observable;

import com.codingame.game.Utils.Vector2;

public abstract class AbstractModel extends Observable {
    private Vector2 pos;

    public AbstractModel(Vector2 pos) {
         this.pos = new Vector2(pos);
    }

    public void setPos(Vector2 pos) {
        this.pos = new Vector2(pos);
    }

    public Vector2 getPos() {
        return new Vector2(pos);
    }

    public void updateState(Object update) {
        this.setChanged();
        this.notifyObservers(update);
    }
}
