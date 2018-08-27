package com.codingame.game;

import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.google.inject.Inject;

import java.util.*;

public class Referee extends AbstractReferee {
    private static int SCREEN_WIDTH = 1920;
    private static int SCREEN_HEIGHT = 1080;

    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private GameMap map = null;

    List<String> itemIdentifiers = new ArrayList<>(Arrays.asList(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
    ));

    private List<Item> playerCards = new ArrayList<>();
    private List<Item> opponentCards = new ArrayList<>();

    @Override
    public void init() {
        Properties params = gameManager.getGameParameters();
        Constants.random = new Random(getSeed(params));

        // TODO: update this when we have gameplay
        gameManager.setMaxTurns(1);

        createBackground();
        createMap();
        createCards();
    }

    private Long getSeed(Properties params) {
        try {
            return Long.parseLong(params.getProperty("seed", "0"));
        } catch(NumberFormatException nfe) {
            return 0L;
        }
    }


    private void drawCards() {
        int cardWidth = 128;
        int cardHeight = 256;
        int playerOffsetX = 100 + cardWidth / 2;
        int playerOffsetY = 25 + cardHeight / 2;
        int opponentOffsetX = SCREEN_WIDTH - (100 + cardWidth / 2);
        int opponentOffsetY = SCREEN_HEIGHT - (150 + cardHeight / 2);

        for (ListIterator<Item> beginIterator = playerCards.listIterator(); beginIterator.hasNext();) {
            Item item = beginIterator.next();
            int index = beginIterator.nextIndex();
            createSprite("cardBack_1.png", playerOffsetX, playerOffsetY + index*15, 0, Constants.MapLayers.TILES.asValue());
        }

        for (ListIterator<Item> beginIterator = opponentCards.listIterator(); beginIterator.hasNext();) {
            Item item = beginIterator.next();
            int index = beginIterator.nextIndex();
            createSprite("cardBack_2.png", opponentOffsetX, opponentOffsetY - index*15, 0, Constants.MapLayers.TILES.asValue());
        }
    }

    private void createCards() {
        Collections.shuffle(itemIdentifiers);
        for (ListIterator<String> beginIterator = itemIdentifiers.listIterator(); beginIterator.hasNext();) {
            String beginIdentifier = beginIterator.next();
            playerCards.add(new Item(beginIdentifier, 1));
            opponentCards.add(new Item(beginIdentifier, 2));
        }

        drawCards();
    }

    private void drawMap() {
        int tileSpace = 5;
        int mapOffsetX = SCREEN_WIDTH / 2 - (Constants.MAP_WIDTH * Constants.TILE_SIZE) / 2 + Constants.TILE_SIZE / 2 - tileSpace / 2 * Constants.MAP_WIDTH;
        int mapOffsetY = SCREEN_HEIGHT / 2 - (Constants.MAP_HEIGHT * Constants.TILE_SIZE) / 2 + Constants.TILE_SIZE / 2 - tileSpace / 2 * Constants.MAP_HEIGHT;
        int x = mapOffsetX, y = mapOffsetY;
        for (int i = 0; i < Constants.MAP_WIDTH; i++) {
            for (int j = 0; j < Constants.MAP_HEIGHT; j++) {
                int arrowPosX = x, arrowPosY = y, arrowRot = 0, arrowOffset = 85;
                if (j % 2 != 0) {
                    if (i == 0) {
                        arrowPosY -= arrowOffset;
                        arrowRot = 180;
                        createSprite("arrow.png", arrowPosX, arrowPosY, Math.toRadians(arrowRot), Constants.MapLayers.BACKGROUND.asValue());
                    } else if (i == Constants.MAP_WIDTH - 1) {
                        arrowPosY += arrowOffset;
                        arrowRot = 0;
                        createSprite("arrow.png", arrowPosX, arrowPosY, Math.toRadians(arrowRot), Constants.MapLayers.BACKGROUND.asValue());
                    }
                }
                else if (i % 2 != 0) {
                    if (j == 0) {
                        arrowPosX -= arrowOffset;
                        arrowRot = 90;
                        createSprite("arrow.png", arrowPosX, arrowPosY, Math.toRadians(arrowRot), Constants.MapLayers.BACKGROUND.asValue());
                    } else if (j == Constants.MAP_HEIGHT - 1) {
                        arrowPosX += arrowOffset;
                        arrowRot = 270;
                        createSprite("arrow.png", arrowPosX, arrowPosY, Math.toRadians(arrowRot), Constants.MapLayers.BACKGROUND.asValue());
                    }
                }

                createSprite("tile_background.png", x, y, Math.toRadians(0), Constants.MapLayers.BACKGROUND.asValue());

                Tile tile = map.get(i, j);
                if (tile.hasUp()) {
                    createSprite("tile_path.png", x, y, Math.toRadians(0), Constants.MapLayers.TILES.asValue());
                }
                if (tile.hasRight()) {
                    createSprite("tile_path.png", x, y, Math.toRadians(90), Constants.MapLayers.TILES.asValue());
                }
                if (tile.hasDown()) {
                    createSprite("tile_path.png", x, y, Math.toRadians(180), Constants.MapLayers.TILES.asValue());
                }
                if (tile.hasLeft()) {
                    createSprite("tile_path.png", x, y, Math.toRadians(270), Constants.MapLayers.TILES.asValue());
                }

                if (tile.hasItem()) {
                    String itemsPath = "items" + System.getProperty("file.separator") + "item_%s_%d.png";
                    String spritePath = String.format(itemsPath, tile.item.getLowercaseIdentifier(), tile.item.getPlayerId());
                    createSprite(spritePath, x, y, 0, Constants.MapLayers.ITEMS.asValue());
                }

                x += Constants.TILE_SIZE + tileSpace;
            }
            x = mapOffsetX;
            y += Constants.TILE_SIZE + tileSpace;
        }
    }

    private void createMap() {
        map = new GameMap();
        drawMap();
    }

    private void createBackground() {
        graphicEntityModule.createRectangle()
                .setX(0)
                .setY(0)
                .setWidth(SCREEN_WIDTH)
                .setHeight(SCREEN_HEIGHT)
                .setFillColor(0x669999)
                .setZIndex(Constants.MapLayers.BACKGROUND.asValue());

        graphicEntityModule.createSprite()
                .setImage("logoCG.png")
                .setX(SCREEN_WIDTH - 230)
                .setY(930)
                .setAnchor(0.5);
    }

    private Sprite createSprite(String path, int x, int y, double rotation, int zIndex) {
        return graphicEntityModule.createSprite()
                .setImage(path)
                .setX(x)
                .setY(y)
                .setRotation(rotation)
                .setZIndex(zIndex)
                .setAnchor(0.5);
    }

    private void sendPlayerInputs() {
        for (Player p : gameManager.getActivePlayers()) {
            // TODO: send actual inputs
            p.sendInputLine("0");
            p.execute();
        }
    }

    @Override
    public void gameTurn(int turn) {
        sendPlayerInputs();
    }
}
