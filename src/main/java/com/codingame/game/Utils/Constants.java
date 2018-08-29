package com.codingame.game.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Constants {
    public static final int TILE_SIZE = 128;

    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;

    public static final int MAP_HEIGHT = 7;
    public static final int MAP_WIDTH = 7;

    public static Random random;

    public static final int TILE_SPACE = 5;
    public static final int MAP_POS_X = SCREEN_WIDTH / 2 - (Constants.MAP_WIDTH * Constants.TILE_SIZE) / 2
            + Constants.TILE_SIZE / 2 - TILE_SPACE / 2 * Constants.MAP_WIDTH;
    public static final int MAP_POS_Y = SCREEN_HEIGHT / 2 - (Constants.MAP_HEIGHT * Constants.TILE_SIZE) / 2
            + Constants.TILE_SIZE / 2 - TILE_SPACE / 2 * Constants.MAP_HEIGHT;

    public static final int PLAYER_TILE_POS_X = 164;
    public static final int PLAYER_TILE_POS_Y = Constants.SCREEN_HEIGHT / 2;
    public static final int OPPONENT_TILE_POS_X = Constants.SCREEN_WIDTH - PLAYER_TILE_POS_X;
    public static final int OPPONENT_TILE_POS_Y = Constants.SCREEN_HEIGHT - PLAYER_TILE_POS_Y;

    public enum MapLayers {
        BACKGROUND(0),
        TILES(1),
        ITEMS(2),
        AGENTS(3);

        private final int value;
        MapLayers(int value) {
            this.value = value;
        }
        public int asValue() {
            return value;
        }
    }

    public static final List<String> ITEM_IDENTIFIERS = new ArrayList<>(Arrays.asList(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
    ));

    public enum PushDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }
}
