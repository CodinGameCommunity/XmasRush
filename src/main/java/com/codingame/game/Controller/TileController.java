package com.codingame.game.Controller;

import com.codingame.game.Item;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Utils;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.TileView;

public class TileController {
    private TileModel model;
    private TileView view;

    public TileController(TileModel model, TileView view) {
        this.model = model;
        this.view = view;
    }

    public void setPattern(String pattern) {
        model.setPattern(pattern);

        initView();
    }

    public void rotate(int numTimes) {
        // shift characters to the right - 1 shift corresponds to a 90 deg rotation
        numTimes %= model.pattern.length();
        if (numTimes == 0) {
            return;
        }
        model.rotate(numTimes);
        view.rotate(numTimes);
    }

    public void setPosInMap(Vector2 pos) {
        setPosInMap(pos, 0);
    }

    public void setSamePosInMap(double time) {
        view.setSamePosInMap(time);
    }

    public void setPosInMap(Vector2 pos, double time) {
        if (!Utils.isPosValid(pos)) {
            throw new RuntimeException("Tile position out of map bounds!");
        }
        model.pos = pos;
        view.setPosInMap(pos, time);
    }

    public void setPosAbsolute(int playerId, Vector2 pos) {
        setPosAbsolute(playerId, pos, 0);
    }

    public void setPosAbsolute(int playerId, Vector2 pos, double time) {
        if (playerId == Constants.PLAYER_INDEX) {
            model.pos = Vector2.MINUS_ONE;
        } else if (playerId == Constants.OPPONENT_INDEX){
            model.pos = Vector2.MINUS_TWO;
        }
        view.setPosAbsolute(pos, time);
    }

    public void setSamePosAbsolute(double time) {
        view.setSamePosAbsolute(time);
    }

    public void initView() {
        view.init(this.model);
    }

    public void addItem(Item item) {
        model.putItem(item);
        view.addItem(item);
    }

    public boolean isEmpty() {
        return model.isEmpty();
    }

    public boolean hasItem() {
        return model.hasItem();
    }

    public Item getItem() {
        return model.item;
    }

    public void removeItem() {
        model.item = null;
        view.removeItem();
    }

    public Vector2 getPos() {
        return model.pos;
    }

    public boolean hasDir(Constants.Direction dir) {
        if (dir == Constants.Direction.UP) return model.hasUp();
        if (dir == Constants.Direction.DOWN) return model.hasDown();
        if (dir == Constants.Direction.LEFT) return model.hasLeft();
        if (dir == Constants.Direction.RIGHT) return model.hasRight();
        return false;
    }

    public boolean hasOppDir(Constants.Direction dir) {
        if (dir == Constants.Direction.UP) return model.hasDown();
        if (dir == Constants.Direction.DOWN) return model.hasUp();
        if (dir == Constants.Direction.LEFT) return model.hasRight();
        if (dir == Constants.Direction.RIGHT) return model.hasLeft();
        return false;
    }

    @Override
    public String toString() {
        return this.model.pattern;
    }

    public String toInputString() {
        return model.toInputString();
    }
}
