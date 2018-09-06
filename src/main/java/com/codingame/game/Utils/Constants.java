package com.codingame.game.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Constants {
    public static Random random;

    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;

    public static final int TILE_SIZE = 128;
    public static final int TILES_OFFSET = 5;

    public static final int MAP_HEIGHT = 7;
    public static final int MAP_WIDTH = 7;
    public static final int MAP_POS_X = SCREEN_WIDTH / 2 - (Constants.MAP_WIDTH * Constants.TILE_SIZE) / 2
            + Constants.TILE_SIZE / 2 - TILES_OFFSET / 2 * Constants.MAP_WIDTH;
    public static final int MAP_POS_Y = SCREEN_HEIGHT / 2 - (Constants.MAP_HEIGHT * Constants.TILE_SIZE) / 2
            + Constants.TILE_SIZE / 2 - TILES_OFFSET / 2 * Constants.MAP_HEIGHT;

    public static final int PLAYER_TILE_POS_X = 164;
    public static final int PLAYER_TILE_POS_Y = Constants.SCREEN_HEIGHT / 2;
    public static final int OPPONENT_TILE_POS_X = Constants.SCREEN_WIDTH - PLAYER_TILE_POS_X;
    public static final int OPPONENT_TILE_POS_Y = Constants.SCREEN_HEIGHT - PLAYER_TILE_POS_Y;

    public static final int CARD_WIDTH = 128;
    public static final int CARD_HEIGHT = 256;
    public static final int CARDS_OFFSET = 15;
    public static final int PLAYER_CARDS_POS_X = 100 + Constants.CARD_WIDTH / 2;
    public static final int PLAYER_CARDS_POS_Y = 25 + Constants.CARD_HEIGHT / 2;
    public static final int OPPONENT_CARDS_POS_X = Constants.SCREEN_WIDTH - PLAYER_CARDS_POS_X;
    public static final int OPPONENT_CARDS_POS_Y = Constants.SCREEN_HEIGHT - PLAYER_CARDS_POS_Y;


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

    public static final List<String> ITEM_IDENTIFIERS = Arrays.asList(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
    );

    public static final List<Vector2> PLAYER_POSITIONS = Arrays.asList(
            Vector2.ZERO,
            new Vector2(Constants.MAP_WIDTH - 1, Constants.MAP_HEIGHT - 1)
    );

    public static final List<Vector2> CARDS_POSITIONS = Arrays.asList(
            new Vector2(PLAYER_CARDS_POS_X, PLAYER_CARDS_POS_Y),
            new Vector2(OPPONENT_CARDS_POS_X, OPPONENT_CARDS_POS_Y)
    );

    public static final Pattern PLAYER_INPUT_PUSH_PATTERN = Pattern
            .compile("(?<pushAction>\\bpush\\b) (?<id>[1,3,5]) (?<direction>(\\bup\\b|\\bright\\b|\\bdown\\b|\\bleft\\b))",
                    Pattern.CASE_INSENSITIVE);
    public static final Pattern PLAYER_INPUT_MOVE_PATTERN = Pattern
            .compile("(?:\\bmove\\b) ((?<amount>[1-6]) (?<direction>(\\bup\\b|\\bright\\b|\\bdown\\b|\\bleft\\b))(?:;?))+",
                    Pattern.CASE_INSENSITIVE);
    public static final Pattern PLAYER_INPUT_MOVE_TOKENS_PATTERN = Pattern
            .compile("(?<amount>[1-6]) (?<direction>(?:\\bup\\b|\\bright\\b|\\bdown\\b|\\bleft\\b))",
                    Pattern.CASE_INSENSITIVE);

    public enum Direction {
        UP(Vector2.UP),
        RIGHT(Vector2.RIGHT),
        DOWN(Vector2.DOWN),
        LEFT(Vector2.LEFT);

        private final Vector2 value;
        Direction(Vector2 value) {
            this.value = value;
        }
        public Vector2 asValue() {
            return value;
        }
    }
}
