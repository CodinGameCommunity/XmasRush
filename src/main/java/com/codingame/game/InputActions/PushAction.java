package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;

import java.util.List;

public class PushAction extends Action {
    private int line;
    private Constants.Direction direction;

    public PushAction(int line, Constants.Direction direction, Action.Type type) {
        super(type);
        assert line >= 0 && line < Constants.MAP_SIZE;
        this.line = line;
        this.direction = direction;
    }

    public int getLine() {
        return line;
    }

    public Constants.Direction getDirection() {
        return direction;
    }

    public boolean isHorizontal() {
        return direction ==  Constants.Direction.RIGHT ||
                direction == Constants.Direction.LEFT;
    }

    //assumes actions is a list of size two
    //and both actions are either horizontal or vertical
    public static boolean pushSameLine(List<PushAction> actions) {
        assert actions.size() == 2;
        assert (actions.get(0).isHorizontal() && actions.get(1).isHorizontal()) ||
                (!actions.get(0).isHorizontal() && !actions.get(1).isHorizontal());
        return actions.get(0).line == actions.get(1).line;
    }

    //used for arrow updates
    public String toString() {
        return line + "" + direction.asValue();
    }
}
