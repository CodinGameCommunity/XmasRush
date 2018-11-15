package com.codingame.game.Model;

import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard {
    private final Vector2 centerTilePos = new Vector2(Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 2);
    private final String[] centerTilePatterns = new String[]{"1111", "1010", "0101"};
    private final Vector2 playerBasePos = Constants.PLAYER_POSITIONS.get(Constants.PLAYER_INDEX);
    private final String playerBasePattern = "0110";

    private TileModel[][] tiles = new TileModel[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
    private List<TileModel> tilesWithItems = new ArrayList<>();

    private List<String> availablePatterns;

    public GameBoard(List<String> availablePatterns) {
        this.availablePatterns = availablePatterns;
        String centerTilePattern = centerTilePatterns[Constants.random.nextInt(centerTilePatterns.length)];
        setTile(centerTilePos, new TileModel(centerTilePattern, centerTilePos));
        setTile(playerBasePos, new TileModel(playerBasePattern, playerBasePos));

        fillEmptyTiles();
    }

    //GameBoard generation methods
    private void fillEmptyTiles() {
        // set up tiles above the secondary diagonal
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            for (int x = 0; x < Constants.MAP_WIDTH - y; x++) {
                Vector2 pos = new Vector2(x, y);
                if (getTile(pos) == null) {
                    String pattern = getRandomAvailablePattern();
                    TileModel tile = new TileModel(pattern, pos);
                    int rotTimes = Constants.random.nextInt(3);
                    tile.rotate(rotTimes);
                    setTile(x, y, tile);
                }
                Vector2 oppositePos = getOppositeTilePos(pos);
                TileModel oppositeTile = new TileModel(getTile(pos).getPattern(), oppositePos);
                oppositeTile.rotate(2); // rotate 180 deg to be symmetric
                setTile(oppositePos, oppositeTile);
            }
        }
    }

    private String getRandomAvailablePattern() {
        int index = Constants.random.nextInt(availablePatterns.size());
        String pattern = availablePatterns.get(index);
        availablePatterns.remove(index);
        return pattern;
    }

    private Vector2 getOppositeTilePos(Vector2 pos) {
        return new Vector2(Constants.MAP_WIDTH - pos.getX() - 1,
                Constants.MAP_HEIGHT - pos.getY() - 1);
    }

    //Item methods
    private TileModel getRandomEmptyTile(boolean threeWayTiles) {
        int index = Constants.random.nextInt(Constants.MAP_WIDTH * Constants.MAP_HEIGHT);
        int x = index / Constants.MAP_WIDTH;
        int y = index % Constants.MAP_HEIGHT;
        Vector2 pos = new Vector2(x, y);
        while (isCenterOrPlayerBase(pos) || getTile(pos).hasItem() ||
                //only checks if the tile is 3+ when required
                (!getTile(pos).isThreeWayPlus() && threeWayTiles))
            return getRandomEmptyTile(threeWayTiles);
        return getTile(pos);
    }

    public void placeItems(List<List<Item>> itemList, boolean threeWayTiles) {
        assert itemList.size() == 2;
        assert itemList.get(0).size() == itemList.get(1).size();

        int numItems = itemList.get(0).size();

        for (int i = 0; i < numItems; i++) {
            TileModel playerTile = getRandomEmptyTile(threeWayTiles);
            TileModel opponentTile = getTile(getOppositeTilePos(playerTile.getPos()));
            playerTile.setItem(itemList.get(Constants.PLAYER_INDEX).get(i));
            opponentTile.setItem(itemList.get(Constants.OPPONENT_INDEX).get(i));
            //deals with the fact that cards are stored in a stack
            tilesWithItems.addAll(0, Arrays.asList(playerTile, opponentTile));
        }
    }

    public void removeItem(TileModel tile) {
        assert tile.hasItem();
        tile.removeItem();
        tilesWithItems.remove(tile);
    }

    //Tile methods
    private void setTile(int x, int y, TileModel tile) {
        tiles[x][y] = tile;
    }

    private void setTile(Vector2 pos, TileModel tile) {
        setTile(pos.getX(), pos.getY(), tile);
    }

    public TileModel getTile(int x, int y) {
        return isValidPos(new Vector2(x, y)) ? tiles[x][y] : null;
    }

    public TileModel getTile(Vector2 pos) {
        return getTile(pos.getX(), pos.getY());
    }

    //Pushing methods
    public TileModel pushLine(TileModel pushedTile, int lineId, Constants.Direction dir) {
        if (dir == Constants.Direction.UP) return pushUp(pushedTile, lineId);
        else if (dir == Constants.Direction.RIGHT) return pushRight(pushedTile, lineId);
        else if (dir == Constants.Direction.DOWN) return pushDown(pushedTile, lineId);
        else return pushLeft(pushedTile, lineId);
    }

    private TileModel pushUp(TileModel pushedTile, int col) {
        int maxRow = Constants.MAP_HEIGHT - 1;
        TileModel poppedTile = getTile(col, 0);

        for (int i = 0; i < maxRow; i++) {
            setTile(col, i, getTile(col,i + 1));
            getTile(col, i).move(Constants.Direction.UP);
        }
        setTile(col, maxRow, pushedTile);
        getTile(col, maxRow).move(new Vector2(col, maxRow));

        return poppedTile;
    }

    private TileModel pushRight(TileModel pushedTile, int row) {
        int maxCol = Constants.MAP_WIDTH - 1;
        TileModel poppedTile = getTile(maxCol, row);

        for (int i = maxCol; i > 0; i--) {
            setTile(i, row, getTile(i - 1, row));
            getTile(i, row).move(Constants.Direction.RIGHT);
        }
        setTile(0, row, pushedTile);
        getTile(0, row).move(new Vector2(0, row));
        return poppedTile;
    }

    private TileModel pushDown(TileModel pushedTile, int col) {
        int maxRow = Constants.MAP_HEIGHT - 1;
        TileModel poppedTile = getTile(col, maxRow);

        for (int i = maxRow; i > 0; i--) {
            setTile(col, i, getTile(col,i - 1));
            getTile(col, i).move(Constants.Direction.DOWN);
        }
        setTile(col, 0, pushedTile);
        getTile(col, 0).move(new Vector2(col, 0));
        return poppedTile;
    }

    private TileModel pushLeft(TileModel pushedTile, int row) {
        int maxCol = Constants.MAP_WIDTH - 1;
        TileModel poppedTile = getTile(0, row);
        for (int i = 0; i < maxCol; i++) {
            setTile(i, row, getTile(i + 1, row));
            getTile(i, row).move(Constants.Direction.LEFT);
        }
        setTile(maxCol, row, pushedTile);
        getTile(maxCol, row).move(new Vector2(maxCol, row));
        return poppedTile;
    }

    //Checkers
    public boolean isValidMove(Vector2 pos, Constants.Direction direction) {
        Vector2 newPos = new Vector2(pos);
        if (getTile(newPos).hasDirection(direction)) {
            newPos.add(direction.asVector());
            if (isValidPos(newPos) && getTile(newPos).hasDirection(direction.getOpposite()))
                return true;
        }
        return false;
    }

    private boolean isValidPos(Vector2 pos) {
        return (pos.getX() >= 0 && pos.getY() >= 0 &&
                pos.getX() < Constants.MAP_WIDTH && pos.getY() < Constants.MAP_HEIGHT);
    }

    private boolean isCenterOrPlayerBase(Vector2 pos) {
        return pos.equals(centerTilePos) ||
                Constants.PLAYER_POSITIONS.contains(pos);
    }

    //Player input methods
    public void sendMapToPlayer(Player player) {
        for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTile(0, y).patternToString());
            for (int x = 1; x < Constants.MAP_WIDTH; x++) {
                sb.append(" " + getTile(x, y).patternToString());
            }
            player.sendInputLine(sb.toString());
        }
    }

    public void sendItemsToPlayer(Player player) {
        int numItems = tilesWithItems.size();
        player.sendInputLine(Integer.toString(numItems));
        for (TileModel tile : tilesWithItems) {
            if (player.getIndex() == Constants.PLAYER_INDEX)
                player.sendInputLine(tile.tileToString());
            else
                player.sendInputLine(tile.opponentTileToString());
        }
    }
}
