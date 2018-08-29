package com.codingame.game;

import com.codingame.game.Controller.TileController;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.TileView;

import java.util.*;

public class GameMap {
    private List<TileController> tileControllers = new ArrayList<>();

    // format is UP, RIGHT, DOWN, LEFT represented as binary digits
    private List<TileModel> tileModels = new ArrayList<>(Arrays.asList(
            new TileModel("0110"), new TileModel("0000"), new TileModel("0111"), new TileModel("0000"),
            new TileModel("0111"), new TileModel("0000"), new TileModel("0011"), new TileModel("0000"),
            new TileModel("0000"), new TileModel("0000"), new TileModel("0000"), new TileModel("0000"),
            new TileModel("0000"), new TileModel("0000"), new TileModel("1110"), new TileModel("0000"),
            new TileModel("1110"), new TileModel("0000"), new TileModel("0111"), new TileModel("0000"),
            new TileModel("1011"), new TileModel("0000"), new TileModel("0000"), new TileModel("0000"),
            new TileModel("0000"), new TileModel("0000"), new TileModel("0000"), new TileModel("0000"),
            new TileModel("1110"), new TileModel("0000"), new TileModel("1101"), new TileModel("0000"),
            new TileModel("1011"), new TileModel("0000"), new TileModel("1011"), new TileModel("0000"),
            new TileModel("0000"), new TileModel("0000"), new TileModel("0000"), new TileModel("0000"),
            new TileModel("0000"), new TileModel("0000"), new TileModel("1100"), new TileModel("0000"),
            new TileModel("1101"), new TileModel("0000"), new TileModel("1101"), new TileModel("0000"),
            new TileModel("1001")
    ));

    private List<TileModel> availableTileModels = new ArrayList<>(Arrays.asList(
            new TileModel("0110"), new TileModel("0110"), new TileModel("0110"), new TileModel("0110"),
            new TileModel("0110"), new TileModel("0110"), new TileModel("0110"), new TileModel("0110"),
            new TileModel("1101"), new TileModel("1101"), new TileModel("1101"), new TileModel("1010"),
            new TileModel("1010"), new TileModel("1010"), new TileModel("1010"), new TileModel("1010"),
            new TileModel("1010")
    ));

    public GameMap() {
        // initialize tiles ids and positions
        int tileId = 0;
        for (int i = 0; i < Constants.MAP_WIDTH; i++) {
            for (int j = 0; j < Constants.MAP_HEIGHT; j++) {
                TileModel tileModel = get(i, j);
                tileModel.id = tileId++;
                tileModel.pos = new Vector2(i, j);
                TileController tileController = new TileController(tileModel, new TileView());
                tileController.init();
                tileController.setPosInMap(tileModel.pos);
                tileControllers.add(tileController);
            }
        }

        // set up empty tiles
        for (int i = 0; i < tileModels.size(); i++) {
            // set up tiles above the secondary diagonal
            if (i % Constants.MAP_WIDTH >= Constants.MAP_WIDTH - i / Constants.MAP_HEIGHT) continue;

            // only set up empty tiles
            TileModel tileModel = tileModels.get(i);
            if (!tileModel.isEmpty()) continue;

            TileModel availableTileModel = takeRandomAvailableTile();
            TileController tileController = tileControllers.get(tileModel.id);
            tileController.setPattern(availableTileModel.pattern);
            int rotTimes = Constants.random.nextInt(3);
            tileController.rotate(rotTimes);

            // do not duplicate the center tile
            if (tileModel.isCenterTile()) continue;

            // mirror a new tile via the secondary diagonal
            TileModel mirroredTileModel = tileModels.get(tileModels.size() - i - 1);
            tileController = tileControllers.get(mirroredTileModel.id);
            tileController.setPattern(availableTileModel.pattern);
            tileController.rotate(rotTimes + 2); // rotate 180 deg to be symmetric
        }

        // set up items
        for (String identifier : Constants.ITEM_IDENTIFIERS) {
            Item item = new Item(identifier, 1);
            TileModel tileModel = getRandomMapTile();
            while (tileModel.isCenterTile() || tileModel.isBaseTile() || tileModel.hasItem()) {
                tileModel = getRandomMapTile();
            }
            tileControllers.get(tileModel.id).addItem(item);

            item = new Item(identifier, 2);
            tileModel = getOppositeTile(tileModel.id);
            tileControllers.get(tileModel.id).addItem(item);
        }
    }

    public TileModel get(int i, int j) {
        return tileModels.get(i * Constants.MAP_WIDTH + j);
    }

    private TileModel takeRandomAvailableTile() {
        int index = Constants.random.nextInt(availableTileModels.size());
        TileModel tile = availableTileModels.get(index);
        availableTileModels.remove(index);
        return tile;
    }

    private TileModel getRandomMapTile() {
        int index = Constants.random.nextInt(tileModels.size());
        return tileModels.get(index);
    }

    private TileModel getOppositeTile(int index) {
        return tileModels.get(tileModels.size() - index - 1);
    }

    public TileModel pushRow(TileModel tile, int row) {
        // TODO
        /*TileModel poppedTile = get(row, 0);
        for (int i = 0; i < Constants.MAP_WIDTH - 1; i++) {
            TileController tileController = tileControllers.get(row * Constants.MAP_WIDTH + i);
            TileModel nextTileModel = tileModels.get(row * Constants.MAP_WIDTH + i + 1);
            tileModels.set(row * Constants.MAP_WIDTH + i, nextTileModel);
            tileController.setPosInMap(nextTileModel.pos);
        }
        tileModels.set(row * Constants.MAP_WIDTH, tile);
        tileControllers.get((row + 1) * Constants.MAP_WIDTH - 1).setPosInMap(new Vector2(row, 0));
        return poppedTile;*/
        return null;
    }
}
