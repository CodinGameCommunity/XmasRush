package com.codingame.game;

import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GameMap {
    public static final int MAP_WIDTH = 7;
    public static final int MAP_HEIGHT = 7;

    // format is UP, RIGHT, DOWN, LEFT represented as binary digits
    List<Tile> tileMap = new LinkedList<>(Arrays.asList(
            new Tile("0110"), new Tile("0000"), new Tile("0111"), new Tile("0000"),
            new Tile("0111"), new Tile("0000"), new Tile("0011"), new Tile("0000"),
            new Tile("0000"), new Tile("0000"), new Tile("0000"), new Tile("0000"),
            new Tile("0000"), new Tile("0000"), new Tile("1110"), new Tile("0000"),
            new Tile("1110"), new Tile("0000"), new Tile("0111"), new Tile("0000"),
            new Tile("1011"), new Tile("0000"), new Tile("0000"), new Tile("0000"),
            new Tile("0000"), new Tile("0000"), new Tile("0000"), new Tile("0000"),
            new Tile("1110"), new Tile("0000"), new Tile("1101"), new Tile("0000"),
            new Tile("1011"), new Tile("0000"), new Tile("1011"), new Tile("0000"),
            new Tile("0000"), new Tile("0000"), new Tile("0000"), new Tile("0000"),
            new Tile("0000"), new Tile("0000"), new Tile("1100"), new Tile("0000"),
            new Tile("1101"), new Tile("0000"), new Tile("1101"), new Tile("0000"),
            new Tile("1001")
    ));

    List<Tile> availableTiles = new LinkedList<>(Arrays.asList(
            new Tile("0110"), new Tile("0110"), new Tile("0110"), new Tile("0110"),
            new Tile("0110"), new Tile("0110"), new Tile("0110"), new Tile("0110"),
            new Tile("1101"), new Tile("1101"), new Tile("1101"), new Tile("1010"),
            new Tile("1010"), new Tile("1010"), new Tile("1010"), new Tile("1010"),
            new Tile("1010")
    ));

    public GameMap() {
        // initialize positions
        for (int i = 0; i < MAP_WIDTH; i++) {
            for (int j = 0; j < MAP_HEIGHT; j++) {
                Tile tile = get(i, j);
                tile.pos = new Vector2(i, j);
            }
        }

        for (ListIterator<Tile> beginIterator = tileMap.listIterator(); beginIterator.hasNext();) {
            int index = beginIterator.nextIndex();
            Tile beginTile = beginIterator.next();
            if (index % MAP_WIDTH >= MAP_WIDTH - index / MAP_HEIGHT) continue;
            if (beginTile.isEmpty()) {
                Tile newTile = getRandomTile();
                newTile.pos = new Vector2(beginTile.pos);
                beginIterator.set(new Tile(newTile));

                // do not duplicate the center tile
                if (index == MAP_WIDTH * MAP_HEIGHT / 2) continue;

                // mirror a new tile via the secondary diagonal
                ListIterator<Tile> endIterator = tileMap.listIterator(tileMap.size() - index - 1);
                Tile endTile = endIterator.next();
                rotateTile(newTile, 2);
                newTile.pos = new Vector2(endTile.pos);
                endIterator.set(newTile);
            }
        }
    }

    public Tile get(int i, int j) {
        return tileMap.get(i * MAP_WIDTH + j);
    }

    private Tile getRandomTile() {
        int index = Constants.random.nextInt(availableTiles.size());
        Tile tile = availableTiles.get(index);
        rotateTile(tile, Constants.random.nextInt(3));
        availableTiles.remove(index);
        return tile;
    }

    private void rotateTile(Tile tile, int numTimes) {
        // shift characters to the right - 1 shift corresponds to a 90 deg rotation
        numTimes %= tile.pattern.length();
        if (numTimes == 0) {
            return;
        }
        tile.pattern = tile.pattern.substring(tile.pattern.length() - numTimes)
                + tile.pattern.substring(0, tile.pattern.length() - numTimes);
    }
}
