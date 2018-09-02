package com.codingame.game;

import com.codingame.game.Controller.TileController;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.TileView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameMap {
    private TileController[][] tileControllers = new TileController[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];

    // pattern format is UP, RIGHT, DOWN, LEFT represented as binary digits
    private static final String[][] tilePatterns = {
            { "0110", "0000", "0111", "0000", "0111", "0000", "0011" },
            { "0000", "0000", "0000", "0000", "0000", "0000", "0000" },
            { "1110", "0000", "1110", "0000", "0111", "0000", "1011" },
            { "0000", "0000", "0000", "0000", "0000", "0000", "0000" },
            { "1110", "0000", "1101", "0000", "1011", "0000", "1011" },
            { "0000", "0000", "0000", "0000", "0000", "0000", "0000" },
            { "1100", "0000", "1101", "0000", "1101", "0000", "1001" }
    };

    private List<String> availableTilePatterns = new ArrayList<>(Arrays.asList(
            "0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110",
            "1101", "1101", "1101",
            "1010", "1010", "1010", "1010", "1010", "1010"
    ));

    public GameMap() {
        // initialize tiles ids and positions
        for (int i = 0; i < Constants.MAP_WIDTH; i++) {
            for (int j = 0; j < Constants.MAP_HEIGHT; j++) {
                TileModel tileModel = new TileModel(get(i, j), new Vector2(i, j));
                TileController tileController = new TileController(tileModel, new TileView());
                tileController.init();
                tileController.setPosInMap(tileModel.pos);
                tileControllers[i][j] = tileController;
            }
        }

        // set up empty tiles
        for (int i = 0; i < Constants.MAP_WIDTH; i++) {
            for (int j = 0; j < Constants.MAP_HEIGHT; j++) {
                // set up tiles above the secondary diagonal
                if (i + j > Constants.MAP_WIDTH - 1) {
                    continue;
                }

                // only set up empty tiles
                TileController tileController = tileControllers[i][j];
                if (!tileController.isEmpty()) continue;

                String availableTilePattern = takeRandomAvailableTilePattern();
                tileController.setPattern(availableTilePattern);
                int rotTimes = Constants.random.nextInt(3);
                tileController.rotate(rotTimes);

                // do not duplicate the center tile
                if (tileController.isCenterTile()) continue;

                // mirror a new tile via the secondary diagonal
                tileController = getOppositeTile(i, j);
                tileController.setPattern(availableTilePattern);
                tileController.rotate(rotTimes + 2); // rotate 180 deg to be symmetric
            }
        }

        // set up items
        for (String identifier : Constants.ITEM_IDENTIFIERS) {
            Item item = new Item(identifier, 1);
            TileController tileController = getRandomMapTile();
            while (tileController.isCenterTile() || tileController.isBaseTile() || tileController.hasItem()) {
                tileController = getRandomMapTile();
            }
            int row = tileController.getPos().x;
            int col = tileController.getPos().y;
            tileControllers[row][col].addItem(item);

            // add the mirrored item
            item = new Item(identifier, 2);
            tileController = getOppositeTile(row, col);
            tileControllers[tileController.getPos().x][tileController.getPos().y].addItem(item);
        }
    }

    public String get(int i, int j) {
        return tilePatterns[i][j];
    }

    private String takeRandomAvailableTilePattern() {
        int index = Constants.random.nextInt(availableTilePatterns.size());
        String pattern = availableTilePatterns.get(index);
        availableTilePatterns.remove(index);
        return pattern;
    }

    private TileController getRandomMapTile() {
        int index = Constants.random.nextInt(Constants.MAP_WIDTH * Constants.MAP_HEIGHT);
        int row = index / Constants.MAP_WIDTH;
        int col = index % Constants.MAP_WIDTH;
        return tileControllers[row][col];
    }

    private TileController getOppositeTile(int row, int col) {
        return tileControllers[Constants.MAP_WIDTH - row - 1][Constants.MAP_HEIGHT - col - 1];
    }

    public TileController pushLine(TileController tile, int index, Constants.PushDirection dir) {
        if (index % 2 == 0) {
            // only odd rows are pushable
            throw new RuntimeException();
        }
        int lastRowIndex = Constants.MAP_WIDTH - 1;
        int lastColIndex = Constants.MAP_HEIGHT - 1;
        if (dir == Constants.PushDirection.LEFT) {
            TileController poppedTile = tileControllers[index][0];
            for (int i = 0; i < lastRowIndex; i++) {
                tileControllers[index][i] = tileControllers[index][i + 1];
                tileControllers[index][i].setPosInMap(new Vector2(index, i));
            }
            tileControllers[index][lastRowIndex] = tile;
            tileControllers[index][lastRowIndex].setPosInMap(new Vector2(index, lastRowIndex));
            return poppedTile;
        } else if (dir == Constants.PushDirection.RIGHT) {
            TileController poppedTile = tileControllers[index][lastRowIndex];
            for (int i = lastRowIndex; i > 0; i--) {
                tileControllers[index][i] = tileControllers[index][i - 1];
                tileControllers[index][i].setPosInMap(new Vector2(index, i));
            }
            tileControllers[index][0] = tile;
            tileControllers[index][0].setPosInMap(new Vector2(index, 0));
            return poppedTile;
        } else if (dir == Constants.PushDirection.UP) {
            TileController poppedTile = tileControllers[0][index];
            for (int i = 0; i < lastColIndex; i++) {
                tileControllers[i][index] = tileControllers[i + 1][index];
                tileControllers[i][index].setPosInMap(new Vector2(i, index));
            }
            tileControllers[lastColIndex][index] = tile;
            tileControllers[lastColIndex][index].setPosInMap(new Vector2(lastColIndex, index));
            return poppedTile;
        } else if (dir == Constants.PushDirection.DOWN) {
            TileController poppedTile = tileControllers[lastColIndex][index];
            for (int i = lastColIndex; i > 0; i--) {
                tileControllers[i][index] = tileControllers[i - 1][index];
                tileControllers[i][index].setPosInMap(new Vector2(i, index));
            }
            tileControllers[0][index] = tile;
            tileControllers[0][index].setPosInMap(new Vector2(0, index));
            return poppedTile;
        }
        return null;
    }
}
