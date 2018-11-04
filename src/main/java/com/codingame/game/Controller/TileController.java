package com.codingame.game.Controller;

import com.codingame.game.Item;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Utils;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.TileView;

public class TileController {
    /**
     * The tile's model (logic).
     */
    private TileModel model;

    /**
     * The tile's view (graphical interface).
     */
    private TileView view;

    /**
     * Creates a TileController with the given parameters.
     * @param model A tile model to be updated by the controller.
     * @param view A tile view to be updated by the controller.
     */
    public TileController(TileModel model, TileView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Sets the tile's pattern, updates the model and initializes the view.
     * @param pattern A string to be passed to the model.
     *                The pattern represents path directions (up, right, down, left)
     *                as 4 binary digits (1 for path, 0 for no path).
     */
    public void setPattern(String pattern) {
        model.setPattern(pattern);

        initView();
    }

    /**
     * Rotates a tile clockwise a number of times; each rotation corresponds to a 90 degrees angle.
     * @param numTimes An integer representing the number of times to apply a 90 degrees rotation.
     */
    public void rotate(int numTimes) {
        // shift characters to the right - 1 shift corresponds to a 90 deg rotation
        numTimes %= model.getPattern().length();
        if (numTimes == 0) {
            return;
        }
        model.rotate(numTimes);
        view.rotate(numTimes);
    }

    /**
     * Sets a tile position on the map.
     * @param pos A Vector2 object representing the position on the map. Must be within map bounds.
     * @throws RuntimeException if the position is out of map bounds.
     */
    public void setPosInMap(Vector2 pos) {
        if (!Utils.isPosValid(pos)) {
            throw new RuntimeException("Tile position out of map bounds!");
        }
        model.setPos(pos);
        view.setPosInMap(pos);
    }

    /**
     * Sets a player's tile position anywhere on the screen.
     * The model's position will be (-1,-1) for the player's tile and (-2,-2) for the opponent's tile.
     * @param playerId The player's identifier (0 for the player, 1 for the opponent).
     * @param pos A Vector2 object representing the position on the screen.
     */
    public void setPosAbsolute(int playerId, Vector2 pos) {
        if (playerId == Constants.PLAYER_INDEX) {
            model.setPos(Vector2.MINUS_ONE);
        } else if (playerId == Constants.OPPONENT_INDEX){
            model.setPos(Vector2.MINUS_TWO);
        }
        view.setPosAbsolute(pos);
    }

    /**
     * Initializes a tile's UI based on the information present in the model.
     */
    public void initView() {
        view.init(this.model);
    }

    /**
     * Adds an item model to the tile. Updates the tile's view.
     * @param item The item model.
     */
    public void addItem(Item item) {
        model.setItem(item);
        view.addItem(item);
    }

    /**
     * Checks if a tile model is empty (doesn't have directions set up).
     * @return true if the tile doesn't have any directions set up.
     *         false otherwise.
     */
    public boolean isEmpty() {
        return model.isEmpty();
    }

    /**
     * Checks if a tile model has an item.
     * @return true if the tile has an item.
     *         false otherwise.
     */
    public boolean hasItem() {
        return model.hasItem();
    }

    /**
     * Returns the item on a tile.
     * @return the tile's item.
     */
    public Item getItem() {
        return model.getItem();
    }

    /**
     * Removes the item from a tile. Sets the item model to null and hides the item from view.
     */
    public void removeItem() {
        model.setItem(null);
        view.removeItem();
    }

    /**
     * Returns the position of a tile.
     * @return the tile's position.
     */
    public Vector2 getPos() {
        return model.getPos();
    }

    /**
     * Checks if a tile has a given direction.
     * @param dir Represents a direction (up, right, down, left).
     * @return true if the tile has the direction.
     *         false otherwise.
     */
    public boolean hasDir(Constants.Direction dir) {
        if (dir == Constants.Direction.UP) return model.hasUp();
        if (dir == Constants.Direction.DOWN) return model.hasDown();
        if (dir == Constants.Direction.LEFT) return model.hasLeft();
        if (dir == Constants.Direction.RIGHT) return model.hasRight();
        return false;
    }

    /**
     * Checks if a tile has an opposite direction from the given one.
     * @param dir Represents a direction (up, right, down, left).
     * @return true if the tile has the opposite direction.
     *         false otherwise.
     */
    public boolean hasOppDir(Constants.Direction dir) {
        if (dir == Constants.Direction.UP) return model.hasDown();
        if (dir == Constants.Direction.DOWN) return model.hasUp();
        if (dir == Constants.Direction.LEFT) return model.hasRight();
        if (dir == Constants.Direction.RIGHT) return model.hasLeft();
        return false;
    }

    /**
     * Returns the tile's pattern as a string.
     * @return the tile's pattern (4 binary digits)
     */
    @Override
    public String toString() {
        return this.model.getPattern();
    }

    /**
     * The tile's representation to be given to the user as input.
     * @return the tile model's input representation.
     */
    public String toInputString() {
        return model.toInputString();
    }
}
