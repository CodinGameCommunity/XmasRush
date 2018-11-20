package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MoveActionTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testConstructorMutatorsObservers() {
        Action.Type type = Action.Type.MOVE;
        Constants.Direction step1 = Constants.Direction.UP;
        Constants.Direction step2 = Constants.Direction.LEFT;
        MoveAction action = new MoveAction(type);
        assertEquals(type, action.getType());
        assertEquals(null, action.getStep());
        action.addStep(step1);
        action.addStep(step2);
        assertEquals(step1, action.getStep());
        assertEquals(step2, action.getStep());
        assertEquals(null, action.getStep());
    }

    @Test
    public void testIsEmptySetEmpty() {
        Action.Type type = Action.Type.MOVE;
        Constants.Direction step1 = Constants.Direction.UP;
        MoveAction action = new MoveAction(type);
        assertTrue(action.isEmpty());
        action.addStep(step1);
        assertFalse(action.isEmpty());
        action.setEmpty();
        assertTrue(action.isEmpty());
    }

    @Test
    public void testActionMethods() {
        Action.Type turn = Action.Type.MOVE;
        MoveAction action = new MoveAction(turn);
        assertFalse(action.isPassAction());
        assertTrue(action.isLegalAction(turn));
        Constants.Direction step1 = Constants.Direction.UP;
        Constants.Direction step2 = Constants.Direction.LEFT;
        action.addStep(step1);
        action.addStep(step2);
        assertTrue(action.getStep() == step1);
        assertTrue(action.getStep() == step2);
        turn = Action.Type.PUSH;
        assertFalse(action.isLegalAction(turn));
        assertEquals(1, action.getType().getValue());
    }
}