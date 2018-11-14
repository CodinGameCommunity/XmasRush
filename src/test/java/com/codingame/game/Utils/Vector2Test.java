package com.codingame.game.Utils;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


/**
 * Test suite for Vector2 specs.
 */
public class Vector2Test {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testNegativeConstants() {
        Vector2 vector1 = Vector2.MINUS_ONE;
        assertEquals(-1, vector1.getX());
        assertEquals(-1, vector1.getY());
        Vector2 vector2 = Vector2.MINUS_TWO;
        assertEquals(-2, vector2.getX());
        assertEquals(-2, vector2.getY());
    }

    @Test
    public void testVectorConstructorMutator() {
        Vector2 vector = new Vector2(3, 5);
        assertEquals(3, vector.getX());
        assertEquals(5, vector.getY());
        vector.setX(4);
        vector.setY(2);
        assertEquals(4, vector.getX());
        assertEquals(2, vector.getY());
    }

    @Test
    public void testVectorFromAnotherVector() {
        Vector2 vector1 = new Vector2(3, 5);
        Vector2 vector2 = new Vector2(vector1);
        vector1.setX(4);
        vector1.setY(2);
        assertEquals(3, vector2.getX());
        assertEquals(5, vector2.getY());
    }

    @Test
    public void testVectorWrapping() {
        Vector2 vector1 = new Vector2(3, 5);
        Vector2 vector2 = new Vector2(2, -3);
        Vector2 vector3 = new Vector2(-4, 2);
        Vector2 vector4 = new Vector2(1, 4);
        vector1.wrap(vector2);
        assertEquals(5, vector1.getX());
        assertEquals(2, vector1.getY());
        vector1.wrap(vector3);
        assertEquals(1, vector1.getX());
        assertEquals(4, vector1.getY());
        vector1.wrap(vector4);
        assertEquals(2, vector1.getX());
        assertEquals(1, vector1.getY());
        vector1.wrap(vector3);
        assertEquals(5, vector1.getX());
        assertEquals(3, vector1.getY());
    }

    @Test
    public void testVectorAddition() {
        Vector2 vector1 = new Vector2(3, 5);
        Vector2 vector2 = new Vector2(-4, 2);
        vector1.add(vector2);
        assertEquals(-1, vector1.getX());
        assertEquals(7, vector1.getY());

    }

    @Test
    public void testVectorMovement() {
        Vector2 vector1 = new Vector2(2, 4);
        vector1.add(Vector2.UP);
        assertEquals(2, vector1.getX());
        assertEquals(3, vector1.getY());
        vector1.add(Vector2.RIGHT);
        assertEquals(3, vector1.getX());
        assertEquals(3, vector1.getY());
        vector1.add(Vector2.DOWN);
        assertEquals(3, vector1.getX());
        assertEquals(4, vector1.getY());
        vector1.add(Vector2.LEFT);
        assertEquals(2, vector1.getX());
        assertEquals(4, vector1.getY());
    }

    @Test
    public void testVectorEquality() {
        Set<Vector2> set = new HashSet<>();
        Vector2 vector1 = new Vector2(3, 5);
        Vector2 vector2 = new Vector2(2, 4);
        set.add(vector1);
        set.add(vector2);
        assertTrue(set.contains(vector1));
        assertTrue(set.contains(vector2));
        assertEquals(2, set.size());
        vector1.setX(2);
        vector1.setY(4);
        assertTrue(set.contains(vector1));
        assertTrue(set.contains(vector2));
        assertEquals(2, set.size());
    }

    @Test
    public void testVectorToString() {
        Vector2 vector = new Vector2(3, 5);
        assertEquals("3 5", vector.toString());
    }

}