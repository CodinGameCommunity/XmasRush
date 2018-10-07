package com.codingame.game;

import com.codingame.game.Controller.PlayerController;
import com.codingame.game.Controller.TileController;
import com.codingame.game.InputActions.InvalidAction;
import com.codingame.game.InputActions.MoveAction;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Utils;
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
        // initialize tile positions
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            for (int x = 0; x < Constants.MAP_WIDTH; x++) {
                TileModel tileModel = new TileModel(tilePatterns[y][x], new Vector2(x, y));
                TileController tileController = new TileController(tileModel, new TileView());
                tileController.initView();
                tileController.setPosInMap(tileModel.getPos());
                tileControllers[x][y] = tileController;
            }
        }

        // set up empty tiles
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            for (int x = 0; x < Constants.MAP_WIDTH; x++) {
                // set up tiles above the secondary diagonal
                if (x + y > Constants.MAP_WIDTH - 1) {
                    continue;
                }

                // only set up empty tiles
                TileController tileController = tileControllers[x][y];
                if (!tileController.isEmpty())
                    continue;

                String availableTilePattern = takeRandomAvailableTilePattern();
                tileController.setPattern(availableTilePattern);
                int rotTimes = Constants.random.nextInt(3);
                tileController.rotate(rotTimes);

                // do not duplicate the center tile
                if (Utils.isCenterTile(tileController.getPos())) continue;

                // mirror a new tile via the secondary diagonal
                tileController = getOppositeTile(x, y);
                tileController.setPattern(availableTilePattern);
                tileController.rotate(rotTimes + 2); // rotate 180 deg to be symmetric
            }
        }

        // set up items
        for (String name : Constants.ITEM_NAMES) {
            TileController tileController = getRandomMapTile();
            while (Utils.isCenterTile(tileController.getPos()) || Utils.isPlayerBase(tileController.getPos()) || tileController.hasItem()) {
                tileController = getRandomMapTile();
            }

            addItem(tileController, name, 0);

            // add the mirrored item
            Vector2 tilePos = tileController.getPos();
            tileController = getOppositeTile(tilePos.getX(), tilePos.getY());
            addItem(tileController, name, 1);
        }
    }

    private void addItem(TileController tileController, String name, int playerId) {
        Vector2 tilePos = tileController.getPos();
        Item item = new Item(name, playerId);
        tileControllers[tilePos.getX()][tilePos.getY()].addItem(item);
    }

    public TileController getTile(int x, int y) {
        return tileControllers[x][y];
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
        int col = index % Constants.MAP_HEIGHT;
        return tileControllers[row][col];
    }

    private TileController getOppositeTile(int row, int col) {
        return tileControllers[Constants.MAP_WIDTH - row - 1][Constants.MAP_HEIGHT - col - 1];
    }

    public TileController pushColumn(TileController pushedTile, int index, Constants.Direction dir, List<Integer> rowsToSkip) {
        if (index % 2 == 0) {
            throw new RuntimeException("Only odd columns are pushable");
        }
        boolean waitForRows = !rowsToSkip.isEmpty();
        int lastRowIndex = Constants.MAP_HEIGHT - 1;
        if (waitForRows) {
            for (int i = 0; i <= lastRowIndex; i++) {
                if (rowsToSkip.contains(i)) {
                    continue;
                }
                tileControllers[index][i].setPosInMap(new Vector2(index, i), 0);
                tileControllers[index][i].setSamePosInMap(0.5);
            }
        }
        if (dir == Constants.Direction.UP) {
            TileController poppedTile = tileControllers[index][0];
            for (int i = 0; i < lastRowIndex; i++) {
                tileControllers[index][i] = tileControllers[index][i + 1];
                tileControllers[index][i].setPosInMap(new Vector2(index, i), 1);
            }

            if (waitForRows) {
                pushedTile.setSamePosAbsolute(0.5);
            }
            tileControllers[index][lastRowIndex] = pushedTile;
            tileControllers[index][lastRowIndex].setPosInMap(new Vector2(index, lastRowIndex), 1);
            return poppedTile;
        } else if (dir == Constants.Direction.DOWN) {
            TileController poppedTile = tileControllers[index][lastRowIndex];
            if (waitForRows) {
                poppedTile.setSamePosInMap(0.5);
            }
            for (int i = lastRowIndex; i > 0; i--) {
                tileControllers[index][i] = tileControllers[index][i - 1];
                tileControllers[index][i].setPosInMap(new Vector2(index, i), 1);
            }
            if (waitForRows) {
                pushedTile.setSamePosAbsolute(0.5);
            }
            tileControllers[index][0] = pushedTile;
            tileControllers[index][0].setPosInMap(new Vector2(index, 0), 1);
            return poppedTile;
        }
        return null;
    }
	
	public TileController pushRow(TileController pushedTile, int index, Constants.Direction dir) {
        if (index % 2 == 0) {
            throw new RuntimeException("Only odd rows are pushable!");
        }
        int lastColIndex = Constants.MAP_WIDTH - 1;
        for (int i = 0; i <= lastColIndex; i++) {
            tileControllers[i][index].setPosInMap(new Vector2(i, index), 0);
        }
        if (dir == Constants.Direction.LEFT) {
            TileController poppedTile = tileControllers[0][index];
            for (int i = 0; i < lastColIndex; i++) {
                tileControllers[i][index] = tileControllers[i + 1][index];
                tileControllers[i][index].setPosInMap(new Vector2(i, index), 0.5);
            }
            tileControllers[lastColIndex][index] = pushedTile;
            tileControllers[lastColIndex][index].setPosInMap(new Vector2(lastColIndex, index), 0.5);
            return poppedTile;
        } else if (dir == Constants.Direction.RIGHT) {
            TileController poppedTile = tileControllers[lastColIndex][index];
            for (int i = lastColIndex; i > 0; i--) {
                tileControllers[i][index] = tileControllers[i - 1][index];
                tileControllers[i][index].setPosInMap(new Vector2(i, index), 0.5);
            }
            tileControllers[0][index] = pushedTile;
            tileControllers[0][index].setPosInMap(new Vector2(0, index), 0.5);
            return poppedTile;
        }
        return null;
    }

    private boolean isInBounds(Vector2 pos) {
        return (pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < Constants.MAP_WIDTH && pos.getY() < Constants.MAP_HEIGHT);
    }

    private boolean canMove(PlayerController playerController, MoveAction.Step step) {
        Vector2 pos = new Vector2(playerController.getPos());
        // check if the current tile has a path to the next tile
        if (!getTile(pos.getX(), pos.getY()).hasDir(step.getDirection())) {
            return false;
        }
        // move to the next tile
        pos.add(step.getDirection().asValue());
        if (!isInBounds(pos)
                || !getTile(pos.getX(), pos.getY()).hasOppDir(step.getDirection())) { // check if the current tile we moved to has a path to the previous tile
            return false;
        }
        return true;
    }

    public void moveAgentBy(PlayerController playerController, List<MoveAction.Step> steps) throws InvalidAction {
        double time = 0;
        for (MoveAction.Step step : steps) {
            if (!canMove(playerController, step)) {
                throw new InvalidAction(step.toString(), false);
            }
            time += 1.0 / steps.size();
            Vector2 offset = new Vector2(step.getDirection().asValue());
            Vector2 pos = new Vector2(playerController.getPos());
            pos.add(offset);
            playerController.setPosInMap(pos, time);
        }
    }
}
