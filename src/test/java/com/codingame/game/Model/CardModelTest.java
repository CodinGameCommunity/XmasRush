package com.codingame.game.Model;

import com.codingame.game.Utils.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;


public class CardModelTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test(expected=AssertionError.class)
    public void testIncorrectItem() throws AssertionError {
        CardModel card = new CardModel(new Item("APPLE", 3),
                new Vector2(2, 1));
    }

    @Test
    public void testConstructorObservers() {
        Item item = new Item("BOOK", 1);
        Vector2 pos1 = new Vector2(2, 1);
        Vector2 pos2 = new Vector2(1, 3);
        CardModel card = new CardModel(item, pos1);
        assertTrue(card.getItem().equals(item));
        assertTrue(card.getPos().equals(pos1));
        card.setPos(pos2);
        assertTrue(card.getPos().equals(pos2));
    }

    @Test
    public void testConstructorExposure() {
        Item item = new Item("BOOK", 1);
        Vector2 pos = new Vector2(2, 1);
        CardModel card = new CardModel(item, pos);
        item = new Item("ARROW", 0);
        pos = new Vector2(1, 3);
        assertFalse(card.getItem().equals(item));
        assertFalse(card.getPos().equals(pos));
    }

    @Test
    public void testGetterExposure() {
        Item item1 = new Item("BOOK", 1);
        Vector2 pos1 = new Vector2(2, 1);
        CardModel card = new CardModel(item1, pos1);
        Item item2 = card.getItem();
        Vector2 pos2 = card.getPos();
        item2 = new Item("ARROW", 0);
        pos2 = new Vector2(1, 3);
        assertFalse(card.getItem().equals(item2));
        assertFalse(card.getPos().equals(pos2));
        assertTrue(card.getItem().equals(item1));
        assertTrue(card.getPos().equals(pos1));
    }

    @Test
    public void testCardToString() {
        Item item = new Item("BOOK", 1);
        Vector2 pos = new Vector2(2, 1);
        CardModel card = new CardModel(item, pos);
        assertEquals("BOOK 1", card.cardToString());
    }

    @Test
    public void testOpponentCardToString() {
        Item item = new Item("BOOK", 1);
        Vector2 pos = new Vector2(2, 1);
        CardModel card = new CardModel(item, pos);
        assertEquals("BOOK 0", card.opponentCardToString());
    }

    @Test
    public void testFlip() {
        Item item = new Item("BOOK", 1);
        Vector2 pos = new Vector2(2, 1);
        CardModel card = new CardModel(item, pos);
        assertFalse(item.getHighlight());
        card.flip();
        assertTrue(item.getHighlight());
        card.flip();
        assertTrue(item.getHighlight());
    }
}