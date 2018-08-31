package com.codingame.game;

import com.codingame.game.Controller.TileController;
import com.codingame.game.InputActions.AbstractAction;
import com.codingame.game.InputActions.InvalidAction;
import com.codingame.game.InputActions.PushAction;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.TileView;
import com.codingame.gameengine.core.AbstractPlayer;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.google.inject.Inject;

import java.util.*;

public class Referee extends AbstractReferee {
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private GameMap map;

    private List<Item> playerCards = new ArrayList<>();
    private List<Item> opponentCards = new ArrayList<>();

    private Vector2 playerPosition;
    private Vector2 opponentPosition;

    private TileController playerTile;
    private TileController opponentTile;

    @Override
    public void init() {
        TileView.graphicEntityModule = graphicEntityModule;

        Properties params = gameManager.getGameParameters();
        Constants.random = new Random(getSeed(params));

        // TODO: update this when we have gameplay
        gameManager.setMaxTurns(1);

        createBackground();
        createPlayers();
        createMap();
        createPlayerTiles();
        createCards();
    }

    private Long getSeed(Properties params) {
        try {
            return Long.parseLong(params.getProperty("seed", "0"));
        } catch(NumberFormatException nfe) {
            return 0L;
        }
    }

    private void drawPlayers() {
        int playerOffsetX = Constants.MAP_POS_X + (Constants.TILE_SIZE + Constants.TILE_SPACE) * playerPosition.x;
        int playerOffsetY = Constants.MAP_POS_Y + (Constants.TILE_SIZE + Constants.TILE_SPACE) * playerPosition.y;
        createSprite("agent_1.png", playerOffsetX, playerOffsetY, 0, Constants.MapLayers.AGENTS.asValue());
        int opponentOffsetX = Constants.MAP_POS_X + (Constants.TILE_SIZE + Constants.TILE_SPACE) * opponentPosition.x;
        int opponentOffsetY = Constants.MAP_POS_Y + (Constants.TILE_SIZE + Constants.TILE_SPACE) * opponentPosition.y;
        createSprite("agent_2.png", opponentOffsetX, opponentOffsetY, 0, Constants.MapLayers.AGENTS.asValue());
    }

    private void createPlayers() {
        playerPosition = new Vector2(0, 0);
        opponentPosition = new Vector2(Constants.MAP_WIDTH - 1, Constants.MAP_HEIGHT - 1);

        drawPlayers();
    }

    private void drawCards() {
        int cardWidth = 128;
        int cardHeight = 256;
        int playerOffsetX = 100 + cardWidth / 2;
        int playerOffsetY = 25 + cardHeight / 2;
        int opponentOffsetX = Constants.SCREEN_WIDTH - playerOffsetX;
        int opponentOffsetY = Constants.SCREEN_HEIGHT - playerOffsetY;

        for (ListIterator<Item> beginIterator = playerCards.listIterator(); beginIterator.hasNext();) {
            Item item = beginIterator.next();
            int index = beginIterator.nextIndex();

            if (index == playerCards.size()) {
                createSprite("cardFront.png", playerOffsetX, playerOffsetY + index*15, 0, Constants.MapLayers.TILES.asValue());
                String itemsPath = "items" + System.getProperty("file.separator") + "item_%s_%d.png";
                String spritePath = String.format(itemsPath, item.getLowercaseIdentifier(), item.getPlayerId());
                createSprite(spritePath, playerOffsetX, playerOffsetY + index*15, 0, Constants.MapLayers.ITEMS.asValue());
            } else {
                createSprite("cardBack_1.png", playerOffsetX, playerOffsetY + index*15, 0, Constants.MapLayers.TILES.asValue());
            }
        }

        for (ListIterator<Item> beginIterator = opponentCards.listIterator(); beginIterator.hasNext();) {
            Item item = beginIterator.next();
            int index = beginIterator.nextIndex();
            createSprite("cardBack_2.png", opponentOffsetX, opponentOffsetY - index*15, 0, Constants.MapLayers.TILES.asValue());
        }
    }

    private void createCards() {
        Collections.shuffle(Constants.ITEM_IDENTIFIERS);
        for (ListIterator<String> beginIterator = Constants.ITEM_IDENTIFIERS.listIterator(); beginIterator.hasNext();) {
            String beginIdentifier = beginIterator.next();
            playerCards.add(new Item(beginIdentifier, 1));
            opponentCards.add(new Item(beginIdentifier, 2));
        }

        drawCards();
    }

    private void drawArrows() {
        int x = Constants.MAP_POS_X, y = Constants.MAP_POS_Y;
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
                x += Constants.TILE_SIZE + Constants.TILE_SPACE;
            }
            x = Constants.MAP_POS_X;
            y += Constants.TILE_SIZE + Constants.TILE_SPACE;
        }
    }

    private void createMap() {
        map = new GameMap();
        drawArrows();
    }

    private void createPlayerTiles() {
        TileModel playerTileModel = new TileModel("0110", Vector2.INVALID);
        TileModel opponentTileModel = new TileModel("1001", Vector2.INVALID);
        playerTile = new TileController(playerTileModel, new TileView());
        opponentTile = new TileController(opponentTileModel, new TileView());
        playerTile.init();
        opponentTile.init();

        playerTile.setPosAbsolute(new Vector2(Constants.PLAYER_TILE_POS_X, Constants.PLAYER_TILE_POS_Y));
        opponentTile.setPosAbsolute(new Vector2(Constants.OPPONENT_TILE_POS_X, Constants.OPPONENT_TILE_POS_Y));
    }

    private void createBackground() {
        graphicEntityModule.createRectangle()
                .setX(0)
                .setY(0)
                .setWidth(Constants.SCREEN_WIDTH)
                .setHeight(Constants.SCREEN_HEIGHT)
                .setFillColor(0x669999)
                .setZIndex(Constants.MapLayers.BACKGROUND.asValue());

        graphicEntityModule.createSprite()
                .setImage("logoCG.png")
                .setX(200)
                .setY(Constants.SCREEN_HEIGHT - 80)
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

    private void doPushAction(PushAction action, Player player) {
        if (player.getIndex() == 0) {
            playerTile = map.pushLine(playerTile, action.id, action.direction.asValue());
            playerTile.setPosAbsolute(new Vector2(Constants.PLAYER_TILE_POS_X, Constants.PLAYER_TILE_POS_Y));
        } else if (player.getIndex() == 1) {
            opponentTile = map.pushLine(opponentTile, action.id, action.direction.asValue());
            opponentTile.setPosAbsolute(new Vector2(Constants.OPPONENT_TILE_POS_X, Constants.OPPONENT_TILE_POS_Y));
        }
    }

    private void doPlayerActions() {
        for (Player player : gameManager.getActivePlayers()) {
            try {
                AbstractAction action = player.getAction();
                if (action instanceof PushAction) {
                    PushAction pushAction = (PushAction) action;
                    doPushAction(pushAction, player);
                }
            } catch (NumberFormatException | AbstractPlayer.TimeoutException | InvalidAction e) {
                player.deactivate("Eliminated!");
            }
        }
    }

    @Override
    public void gameTurn(int turn) {
        sendPlayerInputs();
        doPlayerActions();
    }
}
