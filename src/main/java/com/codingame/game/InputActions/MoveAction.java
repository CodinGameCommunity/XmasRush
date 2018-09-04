package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MoveAction extends AbstractAction {
    public class Step {
        public int amount;
        public Constants.Direction direction;
        public Step(int amount, Constants.Direction direction) {
            this.amount = amount;
            this.direction = direction;
        }
    }
    public List<Step> steps = new ArrayList<>();

    public MoveAction(int amount, Constants.Direction direction)
    {
        steps.add(new Step(amount, direction));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MOVE ");
        for (Step step : steps) {
            sb.append(String.format("%d %s", step.amount, step.direction.name()));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof MoveAction)) return false;
        MoveAction action = (MoveAction)other;
        return this.toString().equals(action.toString());
    }
}
