package com.codingame.game.Utils;

import java.util.Random;

public class Constants {
    public static final int TILE_SIZE = 128;

    public static final int MAP_HEIGHT = 7;
    public static final int MAP_WIDTH = 7;

    public static Random random;

    public enum MapLayers {
        BACKGROUND(0),
        TILES(1),
        ITEMS(2);

        private final int value;
        MapLayers(int value) {
            this.value = value;
        }
        public int asValue() {
            return value;
        }
    }

}
