package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PushActionTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test(expected=AssertionError.class)
    public void testIncorrectLine() throws AssertionError {
        int lineId = Constants.MAP_SIZE + 1;
        Constants.Direction direction = Constants.Direction.UP;
        Action.Type type = Action.Type.PUSH;
        PushAction action = new PushAction(lineId, direction, type);
    }

    @Test(expected=AssertionError.class)
    public void testPushSameLineLargerActionsSize() throws AssertionError {
        int lineId = 1;
        Constants.Direction direction1 = Constants.Direction.UP;
        Constants.Direction direction2 = Constants.Direction.DOWN;
        Action.Type type = Action.Type.PUSH;

        PushAction action1 = new PushAction(lineId, direction1, type);
        PushAction action2 = new PushAction(lineId, direction2, type);
        PushAction action3 = new PushAction(lineId, direction1, type);

        PushAction.pushSameLine(Arrays.asList(action1, action2, action3));
    }

    @Test(expected=AssertionError.class)
    public void testPushSameLineSmallerActionsSize() throws AssertionError {
        int lineId = 1;
        Constants.Direction direction1 = Constants.Direction.UP;
        Action.Type type = Action.Type.PUSH;

        PushAction action1 = new PushAction(lineId, direction1, type);

        PushAction.pushSameLine(Arrays.asList(action1));
    }

    @Test(expected=AssertionError.class)
    public void testPushSameLineMixedPlanes() throws AssertionError {
        int lineId = 1;
        Constants.Direction direction1 = Constants.Direction.UP;
        Constants.Direction direction2 = Constants.Direction.LEFT;
        Action.Type type = Action.Type.PUSH;

        PushAction action1 = new PushAction(lineId, direction1, type);
        PushAction action2 = new PushAction(lineId, direction2, type);

        PushAction.pushSameLine(Arrays.asList(action1, action2));
    }

    @Test
    public void testConstructorObservers() {
        int lineId = 5;
        Constants.Direction direction = Constants.Direction.UP;
        Action.Type type = Action.Type.PUSH;
        PushAction action = new PushAction(lineId, direction, type);
        assertEquals(type, action.getType());
        assertEquals(lineId, action.getLine());
        assertEquals(direction, action.getDirection());
        assertFalse(action.isHorizontal());
    }

    @Test
    public void testActionMethods() {
        int lineId = 5;
        Constants.Direction direction = Constants.Direction.UP;
        Action.Type type = Action.Type.PUSH;
        Action.Type turn = Action.Type.PUSH;
        PushAction action = new PushAction(lineId, direction, type);
        assertFalse(action.isPassAction());
        assertTrue(action.isLegalAction(turn));
        turn = Action.Type.MOVE;
        assertFalse(action.isLegalAction(turn));
        assertEquals(0, action.getType().getValue());
    }

    @Test
    public void testPushSameLine() {
        int lineId1 = 5;
        int lineId2 = 1;
        Constants.Direction direction1 = Constants.Direction.UP;
        Constants.Direction direction2 = Constants.Direction.DOWN;
        Action.Type type = Action.Type.PUSH;

        PushAction action1 = new PushAction(lineId1, direction1, type);
        PushAction action2 = new PushAction(lineId1, direction1, type);
        assertTrue(PushAction.pushSameLine(Arrays.asList(action1, action2)));

        PushAction action3 = new PushAction(lineId1, direction2, type);
        assertTrue(PushAction.pushSameLine(Arrays.asList(action1, action3)));

        PushAction action4 = new PushAction(lineId2, direction1, type);
        assertFalse(PushAction.pushSameLine(Arrays.asList(action1, action4)));
    }

}