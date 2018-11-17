package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;

import java.util.*;

public class MoveAction extends Action {
    private Deque<Constants.Direction> steps;

    public MoveAction(Action.Type type) {
        super(type);
        this.steps = new ArrayDeque<>(Constants.MAX_MOVE_STEPS);
    }

    public void addStep(Constants.Direction direction) {
        steps.add(direction);
    }

    public Constants.Direction getStep() {
        return steps.pollFirst();
    }

    public void setEmpty() {
        steps.clear();
    }

    public boolean isEmpty() {
        return steps.isEmpty();
    }
}
