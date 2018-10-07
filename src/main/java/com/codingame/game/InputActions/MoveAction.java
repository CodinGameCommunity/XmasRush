package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MoveAction extends Action {
    public class Step {
        private Constants.Direction direction;

        public Step(Constants.Direction direction) {
            this.direction = direction;
        }

        public Constants.Direction getDirection() {
            return direction;
        }

        @Override
        public String toString() {
            return String.format("MOVE %s", direction);
        }
    }

    private List<Step> steps;

    public MoveAction() {
        this.steps = new ArrayList<>();
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void addAction(Constants.Direction direction) {
        steps.add(new Step(direction));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MOVE ");

        for (int i = 0; i < steps.size(); i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(steps.get(i).direction.name());
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
