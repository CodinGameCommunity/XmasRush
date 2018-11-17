package com.codingame.game.InputActions;

import com.codingame.game.Utils.Constants;
import org.junit.Test;

import static org.junit.Assert.*;

public class PassActionTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testConstructorObservers() {
        Action.Type type = Action.Type.PASS;
        PassAction action = new PassAction(type);
        assertEquals(type, action.getType());
        assertTrue(action.isPassAction());
    }

    @Test
    public void testLegalAction() {
        Action.Type type = Action.Type.PASS;
        PassAction action = new PassAction(type);
        Action.Type turn = Action.Type.PUSH;
        assertFalse(action.isLegalAction(turn));
        turn = Action.Type.MOVE;
        assertTrue(action.isLegalAction(turn));
    }

}