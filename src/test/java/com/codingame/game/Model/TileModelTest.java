package com.codingame.game.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;

public class TileModelTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test(expected=AssertionError.class)
    public void testTileIncorrectPatternDigit() throws AssertionError {
        TileModel tile = new TileModel("1112", new Vector2(1, 2));
    }

    @Test(expected=AssertionError.class)
    public void testTileIncorrectPatternLength() throws AssertionError {
        TileModel tile = new TileModel("11100", new Vector2(1, 2));
    }

    @Test(expected=AssertionError.class)
    public void testTileSetIncorrectItem() throws AssertionError {
        TileModel tile = new TileModel("1111", new Vector2(1, 2));
        tile.setItem(new Item("APPLE", 0));
    }

    @Test(expected=AssertionError.class)
    public void testTileSetItem() throws AssertionError {
        TileModel tile = new TileModel("1111", new Vector2(1, 2));
        tile.setItem(new Item("BOOK", 0));
        tile.setItem(new Item("ARROW", 0));
    }

    @Test(expected=AssertionError.class)
    public void testTileRemoveItem() throws AssertionError {
        TileModel tile = new TileModel("1111", new Vector2(1, 2));
        tile.removeItem();
    }

    @Test(expected=AssertionError.class)
    public void testOpponentTileNoItem() throws AssertionError {
        TileModel tile = new TileModel("1111", new Vector2(1, 2));
        tile.opponentTileToString();
    }

    @Test
    public void testSetPosExposure() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1111", pos);
        assertTrue(tile.getPos().equals(pos));
        pos.setX(4);
        pos.setY(3);
        assertFalse(tile.getPos().equals(pos));
    }

    @Test
    public void testGetPosExposure() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1111", pos);
        assertTrue(tile.getPos().equals(pos));
        Vector2 newPos = tile.getPos();
        newPos.setX(4);
        newPos.setY(3);
        assertTrue(tile.getPos().equals(pos));
    }

    @Test
    public void testConstructorPosExposure() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1111", pos);
        assertTrue(tile.getPos().equals(pos));
        pos.setX(4);
        pos.setY(3);
        assertTrue(tile.getPos().equals(new Vector2(1, 2)));
    }

    @Test
    public void testDirectionCheck() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1111", pos);
        assertTrue(tile.hasDirection(Constants.Direction.UP));
        assertTrue(tile.hasDirection(Constants.Direction.RIGHT));
        assertTrue(tile.hasDirection(Constants.Direction.DOWN));
        assertTrue(tile.hasDirection(Constants.Direction.LEFT));
        tile = new TileModel("0100", pos);
        assertFalse(tile.hasDirection(Constants.Direction.UP));
        assertTrue(tile.hasDirection(Constants.Direction.RIGHT));
        assertFalse(tile.hasDirection(Constants.Direction.DOWN));
        assertFalse(tile.hasDirection(Constants.Direction.LEFT));
        tile = new TileModel("0010", pos);
        assertFalse(tile.hasDirection(Constants.Direction.UP));
        assertFalse(tile.hasDirection(Constants.Direction.RIGHT));
        assertTrue(tile.hasDirection(Constants.Direction.DOWN));
        assertFalse(tile.hasDirection(Constants.Direction.LEFT));
        tile = new TileModel("0001", pos);
        assertFalse(tile.hasDirection(Constants.Direction.UP));
        assertFalse(tile.hasDirection(Constants.Direction.RIGHT));
        assertFalse(tile.hasDirection(Constants.Direction.DOWN));
        assertTrue(tile.hasDirection(Constants.Direction.LEFT));
    }

    @Test
    public void testRotatePattern() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1000", pos);
        assertTrue(tile.hasDirection(Constants.Direction.UP));
        tile.rotate(0);
        assertTrue(tile.hasDirection(Constants.Direction.UP));
        tile.rotate(1);
        assertTrue(tile.hasDirection(Constants.Direction.RIGHT));
        tile.rotate(2);
        assertTrue(tile.hasDirection(Constants.Direction.LEFT));
        tile.rotate(3);
        assertTrue(tile.hasDirection(Constants.Direction.DOWN));
    }

    @Test
    public void testGetSetItem() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1000", pos);
        assertFalse(tile.hasItem());
        assertNull(tile.getItem());
        Item item = new Item("BOOK", 0);
        tile.setItem(item);
        assertTrue(tile.hasItem());
        assertTrue(tile.getItem().equals(item));
        item = new Item("ARROW", 1);
        assertFalse(tile.getItem().equals(item));
        Item item2 = tile.getItem();
        item2 = new Item("ARROW", 1);
        assertFalse(tile.getItem().equals(item2));
    }

    @Test
    public void testRemoveItem() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1111", pos);
        Item item = new Item("BOOK", 0);
        tile.setItem(item);
        tile.removeItem();
        assertNull(tile.getItem());
    }

    @Test
    public void testPatternToString() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1010", pos);
        assertEquals("1010", tile.patternToString());
    }

    @Test
    public void testTileToString() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1010", pos);
        Item item = new Item("BOOK", 0);
        tile.setItem(item);
        assertEquals("BOOK 1 2 0", tile.tileToString());
        tile.removeItem();
        assertEquals("", tile.tileToString());
    }

    @Test
    public void testMoveTileToPos() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1010", pos);
        Vector2 newPos = new Vector2(3, 4);
        tile.move(newPos);
        assertEquals(newPos, tile.getPos());
        newPos = new Vector2(0, 0);
        tile.move(newPos);
        assertEquals(newPos, tile.getPos());
        newPos = Vector2.MINUS_ONE;
        tile.move(newPos);
        assertEquals(newPos, tile.getPos());
    }

    @Test
    public void testMoveTileInDirection() {
        Vector2 pos = new Vector2(1, 2);
        TileModel tile = new TileModel("1010", pos);
        tile.move(Constants.Direction.UP);
        assertEquals(new Vector2(1, 1), tile.getPos());
        tile.move(Constants.Direction.RIGHT);
        assertEquals(new Vector2(2, 1), tile.getPos());
        tile.move(Constants.Direction.DOWN);
        assertEquals(new Vector2(2, 2), tile.getPos());
        tile.move(Constants.Direction.LEFT);
        assertEquals(new Vector2(1, 2), tile.getPos());
    }

    @Test
    public void testIsThreeWayPlus() {
        TileModel tile1 = new TileModel("0000",new Vector2(1, 2));
        TileModel tile2 = new TileModel("1010",new Vector2(1, 2));
        TileModel tile3 = new TileModel("1110",new Vector2(1, 2));
        TileModel tile4 = new TileModel("1111",new Vector2(1, 2));
        assertFalse(tile1.isThreeWayPlus());
        assertFalse(tile2.isThreeWayPlus());
        assertTrue(tile3.isThreeWayPlus());
        assertTrue(tile4.isThreeWayPlus());
    }

    @Test
    public void testOpponentTile() {
        Vector2 pos1 = new Vector2(1, 2);
        TileModel tile = new TileModel("1010", pos1);
        Item item = new Item("BOOK", 0);
        tile.setItem(item);
        assertEquals("BOOK 1 2 1", tile.opponentTileToString());
        tile.setPos(Constants.TILE_MODEL_POSITIONS.get(0));
        tile.setPlayerId(0);
        assertEquals("BOOK -2 -2 1", tile.opponentTileToString());
        tile.setPos(Constants.TILE_MODEL_POSITIONS.get(1));
        tile.setPlayerId(1);
        assertEquals("BOOK -1 -1 1", tile.opponentTileToString());
    }

    @Test
    public void testPlayerId() {
        Vector2 pos = new Vector2(1, 2);
        Integer id = 1;
        TileModel tile = new TileModel("1010", pos);
        assertNull(tile.getPlayerId());
        tile.setPlayerId(id);
        assertEquals(id, tile.getPlayerId());
    }

}
