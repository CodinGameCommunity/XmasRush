package com.codingame.game.Model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class ItemTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test(expected=AssertionError.class)
    public void testItemIncorrectName() throws AssertionError {
        Item item = new Item("APPLE", 0);
    }

    @Test(expected=AssertionError.class)
    public void testItemIncorrectId() throws AssertionError {
        Item item = new Item("BOOK", 3);
    }

    @Test
    public void testItemObservers() {
        Item item = new Item("BOOK", 0);
        assertEquals("BOOK", item.getName());
        assertEquals(0, item.getPlayerId());
    }

    @Test
    public void testTwoItemsDifferentName() {
        Set<Item> items = new HashSet<>();
        Item item1 = new Item("BOOK", 1);
        Item item2 = new Item("POTION", 1);
        items.add(item1);
        items.add(item2);
        assertTrue(items.size() == 2);
    }

    @Test
    public void testTwoItemsDifferentIds() {
        Set<Item> items = new HashSet<>();
        Item item1 = new Item("BOOK", 0);
        Item item2 = new Item("BOOK", 1);
        items.add(item1);
        items.add(item2);
        assertTrue(items.size() == 2);
    }

    @Test
    public void testTwoSameItems() {
        Set<Item> items = new HashSet<>();
        Item item1 = new Item("BOOK", 0);
        Item item2 = new Item("BOOK", 0);
        items.add(item1);
        items.add(item2);
        assertTrue(items.size() == 1);
    }

    @Test
    public void testRefExposure() {
        Item item1 = new Item("BOOK", 0);
        Item item2 = item1;
        item1 = new Item("DIAMOND", 1);
        assertTrue(item2.getName().equals("BOOK"));
        assertTrue(item2.getPlayerId() == 0);
    }

    @Test
    public void testItemToString() {
        Item item = new Item("BOOK", 1);
        assertEquals("BOOK 1", item.itemToString());
    }

    @Test
    public void testGetOpponentsId() {
        Item item = new Item("BOOK", 1);
        assertEquals(0, item.getOpponentId());
    }

    @Test
    public void testOpponentVectorToString() {
        Item item = new Item("BOOK", 1);
        assertEquals("BOOK 0", item.opponentItemToString());
    }

    @Test
    public void testSetGetHighlight() {
        Item item = new Item("BOOK", 1);
        assertFalse(item.getHighlight());
        item.setHighlight();
        assertTrue(item.getHighlight());
        item.setHighlight();
        assertTrue(item.getHighlight());
    }
}