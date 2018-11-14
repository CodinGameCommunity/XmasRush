package com.codingame.game.View;

import java.util.Observable;
import java.util.Observer;

import com.codingame.gameengine.module.entities.GraphicEntityModule;

public abstract class AbstractView implements Observer {
    protected GraphicEntityModule entityModule;
    private boolean disposable = false;

    public AbstractView(GraphicEntityModule entityManager) {
        this.entityModule = entityManager;
    }

    public abstract void updateView();

    public boolean isDisposable() {
        return disposable;
    }

    public void doDispose() {
        disposable = true;
    }

    public void update(Observable observable, Object arg) {}
}
