package com.codingame.game.Utils;

import java.util.ArrayList;
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

    public static final int MAP_SIZE = 7;
    public static final int MAP_WIDTH = MAP_SIZE;
    public static final int MAP_HEIGHT = MAP_SIZE;

    public static final int MAP_POS_X = SCREEN_WIDTH / 2 - (MAP_HEIGHT * TILE_SIZE) / 2
            + TILE_SIZE / 2 - TILES_OFFSET / 2 * MAP_HEIGHT;
    public static final int MAP_POS_Y = SCREEN_HEIGHT / 2 - (MAP_WIDTH * TILE_SIZE) / 2
            + TILE_SIZE / 2 - TILES_OFFSET / 2 * MAP_WIDTH;

    public static final int PLAYER_INFO_POS_X = (MAP_POS_X - TILE_SIZE / 2) / 2;
    public static final int OPPONENT_INFO_POS_X = SCREEN_WIDTH - PLAYER_INFO_POS_X;

    public static final int PLAYER_TILE_POS_Y = 630;
    public static final int OPPONENT_TILE_POS_Y = SCREEN_HEIGHT - PLAYER_TILE_POS_Y + 10;

    public static final int CARD_WIDTH = 118;
    public static final int CARD_HEIGHT = 160;
    public static final int CARDS_OFFSET = 26;
    public static final int PLAYER_CARDS_POS_X = 140;
    public static final int PLAYER_CARDS_POS_Y = 200;
    public static final int OPPONENT_CARDS_POS_X = SCREEN_WIDTH - PLAYER_CARDS_POS_X;
    public static final int OPPONENT_CARDS_POS_Y = SCREEN_HEIGHT - PLAYER_CARDS_POS_Y - 200;

    public static final List<String> ITEM_NAMES = Arrays.asList(
            "ARROW", "BOOK", "CANE", "CANDY", "DIAMOND", "FISH", "MASK", "KEY", "POTION", "SCROLL", "SHIELD", "SWORD"
    );

    public static final List<List<String>> PATTERNS = new ArrayList<>(Arrays.asList(
            Arrays.asList("1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111",
                    "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111",
                    "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111"),

            Arrays.asList("0110", "0110", "0110", "0110", "0110", "0110", "0110", "0110",
                    "1101", "1101", "1101", "0111", "0111", "0111", "0111", "0111", "0111",
                    "1010", "1010", "1010", "1010", "1010", "1010",
                    "1111")
    ));

    public static final int PLAYER_INDEX = 0;
    public static final int OPPONENT_INDEX = 1;
    public static final int NUM_PLAYERS = 2;

    public static final List<Vector2> PLAYER_POSITIONS = Arrays.asList(
            new Vector2(0, 0),
            new Vector2(MAP_WIDTH - 1, MAP_HEIGHT - 1)
    );

    public static final List<Vector2> CARD_POSITIONS = Arrays.asList(
            new Vector2(PLAYER_CARDS_POS_X, PLAYER_CARDS_POS_Y),
            new Vector2(OPPONENT_CARDS_POS_X, OPPONENT_CARDS_POS_Y)
    );

    public static final List<Vector2> TILE_POSITIONS = Arrays.asList(
            new Vector2(PLAYER_INFO_POS_X, PLAYER_TILE_POS_Y),
            new Vector2(OPPONENT_INFO_POS_X, OPPONENT_TILE_POS_Y)
    );

    public static final List<Vector2> TILE_MODEL_POSITIONS = Arrays.asList(
            Vector2.MINUS_ONE, Vector2.MINUS_TWO);
    //todo
    public static final int MAX_GAME_TURNS = 150;
    public static final int MAX_MOVE_STEPS = 20;

    public static final Pattern PLAYER_INPUT_PUSH_PATTERN = Pattern
            .compile("(?<pushAction>\\bPUSH\\b) (?<id>[ 0-" + (MAP_SIZE - 1) + "]) (?<direction>(\\bUP\\b|\\bRIGHT\\b|\\bDOWN\\b|\\bLEFT\\b))");
    public static final Pattern PLAYER_INPUT_MOVE_PATTERN = Pattern
            .compile(String.format("(?:\\bMOVE\\b)((?: )(?<direction>(\\bUP\\b|\\bRIGHT\\b|\\bDOWN\\b|\\bLEFT\\b))){1,%d}", MAX_MOVE_STEPS));
    public static final Pattern PLAYER_INPUT_MOVE_TOKENS_PATTERN = Pattern
            .compile("(?<direction>(?:\\bUP\\b|\\bRIGHT\\b|\\bDOWN\\b|\\bLEFT\\b))");
    public static final Pattern PLAYER_INPUT_PASS_PATTERN = Pattern
            .compile("PASS");

    public enum Direction {
        UP(Vector2.UP, 0),
        RIGHT(Vector2.RIGHT, 1),
        DOWN(Vector2.DOWN, 2),
        LEFT(Vector2.LEFT, 3);

        private final Vector2 vector;
        private final int value;

        Direction(Vector2 vector, int value) {
            this.vector = vector;
            this.value = value;
        }
        public Vector2 asVector() {
            return vector;
        }

        public int asValue() {
            return value;
        }

        public Direction getOpposite() {
            switch(this) {
                case UP: return DOWN;
                case DOWN: return UP;
                case LEFT: return RIGHT;
                case RIGHT: return LEFT;
            }
            return null;
        }
    }
}
