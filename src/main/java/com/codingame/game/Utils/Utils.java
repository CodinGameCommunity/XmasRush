package com.codingame.game.Utils;

import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

public class Utils {
    /**
     * Static reference to the Referee's GraphicEntityModule.
     */
    public static GraphicEntityModule graphicEntityModule;

    /**
     * Wraps's a value between a minimum and maximum value.
     * If the value surpasses the maximum value, the value takes the minimum value plus that difference.
     * @param val The value to be wrapped.
     * @param min The minimum value. Must be lower than maximum.
     * @param max The maximum value. Must be higher than minimum.
     * @return the wrapped value between min and max.
     */
    public static int wrap(int val, int min, int max) {
        if (val < min) {
            val = max - (min - val) + 1;
        } else if (val > max) {
            val = min + (val - max) - 1;
        }
        return val;
    }

    /**
     * Sets an entity's position on the map at a given frame time.
     * @param entity The object to be placed on the map.
     * @param pos A Vector2 object representing the position on the map. Must be within map bounds.
     * @param time The engine's frame time used to set the tile's position. Must be between 0 and 1.
     */
    public static void setPosInMap(Entity entity, Vector2 pos, double time) {
        int x = Constants.MAP_POS_X + pos.getX() * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        int y = Constants.MAP_POS_Y + pos.getY() * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        entity.setX(x).setY(y);

        graphicEntityModule.commitEntityState(time, entity);
    }

    /**
     * Checks if a position is within map bounds.
     * @param pos The position to be checked.
     * @return true if the position is within map bounds.
     *         false otherwise.
     */
    public static boolean isPosValid(Vector2 pos) {
        return (pos.getX() >= 0 && pos.getX() < Constants.MAP_WIDTH && pos.getY() >= 0 && pos.getY() < Constants.MAP_HEIGHT);
    }

    /**
     * Checks if a position represents a tile on which the player can spawn (can be upper left corner or lower bottom corner).
     * @param pos The position to be checked.
     * @return true if the position represents a player spawn tile.
     *         false otherwise.
     */
    public static boolean isPlayerBase(Vector2 pos) {
        return (pos.getX() == 0 && pos.getY() == 0) || (pos.getX() == Constants.MAP_WIDTH - 1 && pos.getY() == Constants.MAP_HEIGHT - 1);
    }

    /**
     * Checks if a position is in the center of the map (only works for maps with odd sizes).
     * @param pos The position to be checked.
     * @return true if the position is in the center of the map.
     *         false otherwise.
     */
    public static boolean isCenterTile(Vector2 pos) {
        int centerX = Constants.MAP_WIDTH / 2;
        int centerY = Constants.MAP_HEIGHT / 2;
        return pos.getX() == centerX && pos.getY() == centerY;
    }
}
