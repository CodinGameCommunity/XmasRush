package com.codingame.game;

import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.google.inject.Inject;

import java.util.Properties;
import java.util.Random;

public class Referee extends AbstractReferee {
    private static int SCREEN_WIDTH = 1920;
    private static int SCREEN_HEIGHT = 1080;

    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private GameMap map = null;

    @Override
    public void init() {
        Properties params = gameManager.getGameParameters();
        Constants.random = new Random(getSeed(params));

        // TODO: update this when we have gameplay
        gameManager.setMaxTurns(1);

        createBackground();
        createMap();
    }

    private Long getSeed(Properties params) {
        try {
            return Long.parseLong(params.getProperty("seed", "0"));
        } catch(NumberFormatException nfe) {
            return 0L;
        }
    }

    private void createMap() {
        map = new GameMap();

        int tileSpace = 5;
        int mapOffsetX = SCREEN_WIDTH / 2 - (GameMap.MAP_WIDTH * Constants.TILE_SIZE) / 2 + Constants.TILE_SIZE / 2 - tileSpace / 2 * GameMap.MAP_WIDTH;
        int mapOffsetY = SCREEN_HEIGHT / 2 - (GameMap.MAP_HEIGHT * Constants.TILE_SIZE) / 2 + Constants.TILE_SIZE / 2 - tileSpace / 2 * GameMap.MAP_HEIGHT;
        int x = mapOffsetX, y = mapOffsetY;
        for (int i = 0; i < GameMap.MAP_WIDTH; i++) {
            for (int j = 0; j < GameMap.MAP_HEIGHT; j++) {
                int arrowPosX = x, arrowPosY = y, arrowRot = 0, arrowOffset = 85;
                if (j % 2 != 0) {
                    if (i == 0) {
                        arrowPosY -= arrowOffset;
                        arrowRot = 180;
                        createSprite("arrow.png", arrowPosX, arrowPosY, Math.toRadians(arrowRot), Constants.MapLayers.BACKGROUND.asValue());
                    } else if (i == GameMap.MAP_WIDTH - 1) {
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
                    } else if (j == GameMap.MAP_HEIGHT - 1) {
                        arrowPosX += arrowOffset;
                        arrowRot = 270;
                        createSprite("arrow.png", arrowPosX, arrowPosY, Math.toRadians(arrowRot), Constants.MapLayers.BACKGROUND.asValue());
                    }
                }

                String tileValue = map.get(i, j);
                for (int index = 0; index < tileValue.length(); index++) {
                    if (tileValue.charAt(index) == '0') continue;

                    createSprite("tile_background.png", x, y, Math.toRadians(0), Constants.MapLayers.BACKGROUND.asValue());
                    // UP
                    if (index == 0) {
                        createSprite("tile_path.png", x, y, Math.toRadians(0), Constants.MapLayers.TILES.asValue());
                    }
                    // RIGHT
                    if (index == 1) {
                        createSprite("tile_path.png", x, y, Math.toRadians(90), Constants.MapLayers.TILES.asValue());
                    }
                    // DOWN
                    if (index == 2) {
                        createSprite("tile_path.png", x, y, Math.toRadians(180), Constants.MapLayers.TILES.asValue());
                    }
                    // LEFT
                    if (index == 3) {
                        createSprite("tile_path.png", x, y, Math.toRadians(270), Constants.MapLayers.TILES.asValue());
                    }
                }
                x += Constants.TILE_SIZE + tileSpace;
            }
            x = mapOffsetX;
            y += Constants.TILE_SIZE + tileSpace;
        }
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
