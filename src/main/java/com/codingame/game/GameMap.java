package com.codingame.game;

import com.codingame.game.Utils.Constants;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GameMap {
    public static final int MAP_WIDTH = 7;
    public static final int MAP_HEIGHT = 7;

    // format is UP, RIGHT, DOWN, LEFT represented as binary digits
    List<String> map = new LinkedList<>(Arrays.asList(
            "0110", "0000", "0111", "0000", "0111", "0000", "0011",
            "0000", "0000", "0000", "0000", "0000", "0000", "0000",
            "1110", "0000", "1110", "0000", "0111", "0000", "1011",
            "0000", "0000", "0000", "0000", "0000", "0000", "0000",
            "1110", "0000", "1101", "0000", "1011", "0000", "1011",
            "0000", "0000", "0000", "0000", "0000", "0000", "0000",
            "1100", "0000", "1101", "0000", "1101", "0000", "1001"
    ));
    List<String> availableTiles = new LinkedList<>(Arrays.asList(
            "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110",
            "1101", "1101", "1101", "1101", "1101", "1101",
            "1010", "1010", "1010", "1010", "1010", "1010", "1010", "1010", "1010", "1010", "1010", "1010"
    ));

    public GameMap() {
        for (ListIterator<String> beginIterator = map.listIterator(); beginIterator.hasNext();) {
            int index = beginIterator.nextIndex();
            String beginTile = beginIterator.next();
            if (index % MAP_WIDTH >= MAP_WIDTH - index / MAP_HEIGHT) continue;
            if (beginTile.equals("0000")) {
                String newTile = getRandomTile();
                beginIterator.set(newTile);
                ListIterator<String> endIterator = map.listIterator(map.size() - index - 1);
                String endTile = endIterator.next();
                endIterator.set(rotateTile(newTile, 2));
            }
        }
    }

    public String get(int i, int j) {
        return map.get(i * MAP_WIDTH + j);
    }

    private String getRandomTile() {
        int index = Constants.random.nextInt(availableTiles.size());
        String tile = availableTiles.get(index);
        tile = rotateTile(tile, Constants.random.nextInt(3));
        availableTiles.remove(index);
        return tile;
    }

    private String rotateTile(String tile, int numTimes) {
        // shift characters to the right - 1 shift corresponds to a 90 deg rotation
        numTimes %= tile.length();
        if (numTimes == 0) {
            return tile;
        }
        return tile.substring(tile.length() - numTimes) + tile.substring(0, tile.length() - numTimes);
    }
}
