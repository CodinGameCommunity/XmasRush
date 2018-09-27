package com.codingame.game.Utils;

import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

public class Utils {
    public static GraphicEntityModule graphicEntityModule;

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
    public static int wrap(int val, int min, int max) {
        if (val < min) {
            val = max - (min - val) + 1;
        } else if (val > max) {
            val = min + (val - max) - 1;
        }
        return val;
    }

    public static void setPosInMap(Entity entity, Vector2 pos, double time) {
        int x = Constants.MAP_POS_X + pos.y * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        int y = Constants.MAP_POS_Y + pos.x * (Constants.TILE_SIZE + Constants.TILES_OFFSET);
        entity.setX(x).setY(y);

        graphicEntityModule.commitEntityState(time, entity);
    }

    public static boolean isPosValid(Vector2 pos) {
        return (pos.x >= 0 && pos.x < Constants.MAP_WIDTH && pos.y >= 0 && pos.y < Constants.MAP_HEIGHT);
    }
}
