package com.codingame.game.Utils;

import java.util.Random;

public class Constants {
    public static final int TILE_SIZE = 128;

    public static Random random;

    public enum MapLayers {
        BACKGROUND(0),
        TILES(1);

        private final int value;
        MapLayers(int value) {
            this.value = value;
        }
        public int asValue() {
            return value;
        }
    }

}
