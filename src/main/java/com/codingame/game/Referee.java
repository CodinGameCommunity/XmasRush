package com.codingame.game;

import com.codingame.game.Controller.PlayerController;
import com.codingame.game.Controller.TileController;
import com.codingame.game.InputActions.AbstractAction;
import com.codingame.game.InputActions.InvalidAction;
import com.codingame.game.InputActions.MoveAction;
import com.codingame.game.InputActions.PushAction;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.CardView;
import com.codingame.game.View.PlayerView;
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

    private List<PlayerController> playerControllers = new ArrayList<>();

    @Override
    public void init() {
        TileView.graphicEntityModule = graphicEntityModule;
        PlayerView.graphicEntityModule = graphicEntityModule;
        CardView.graphicEntityModule = graphicEntityModule;

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

    private void createPlayers() {
        for (Player player : gameManager.getActivePlayers()) {
            PlayerController playerController = new PlayerController(player, new PlayerView(player.getIndex()));
            playerController.setPosInMap(Constants.PLAYER_POSITIONS.get(player.getIndex()));
            playerControllers.add(playerController);
        }
    }

    private List<String> getShuffledItemIdentifiers() {
        List<String> shuffledItems = new ArrayList<>(Constants.ITEM_IDENTIFIERS);
        Random rnd = Constants.random;

        //Fisher-Yates shuffle
        for (int i = shuffledItems.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(shuffledItems.size());
            String a = shuffledItems.get(index);
            shuffledItems.set(index, shuffledItems.get(i));
            shuffledItems.set(i, a);
        }

        return shuffledItems;
    }

    private void createCards() {
        List<String> shuffledItemIds = getShuffledItemIdentifiers();

        for (Player player : gameManager.getPlayers()) {
            for (int i = 0; i < shuffledItemIds.size(); i++) {
                int playerId = player.getIndex();
                int orientation = playerId == 0 ? 1 : -1;
                Vector2 cardPos = new Vector2(Constants.CARDS_POSITIONS.get(playerId)).add(new Vector2(0, orientation * i * Constants.CARDS_OFFSET));
                playerControllers.get(player.getIndex()).addItemCard(new Item(shuffledItemIds.get(i), player.getIndex()), cardPos);
            }
        }

        playerControllers.get(0).flipTopCard();
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
                x += Constants.TILE_SIZE + Constants.TILES_OFFSET;
            }
            x = Constants.MAP_POS_X;
            y += Constants.TILE_SIZE + Constants.TILES_OFFSET;
        }
    }

    private void createMap() {
        map = new GameMap();
        drawArrows();
    }

    private void createPlayerTiles() {
        TileController playerTile = new TileController(new TileModel("0110"), new TileView());
        TileController opponentTile = new TileController(new TileModel("1001"), new TileView());
        playerTile.init();
        opponentTile.init();

        playerTile.setPosAbsolute(new Vector2(Constants.PLAYER_TILE_POS_X, Constants.PLAYER_TILE_POS_Y));
        opponentTile.setPosAbsolute(new Vector2(Constants.OPPONENT_TILE_POS_X, Constants.OPPONENT_TILE_POS_Y));

        playerControllers.get(0).setTile(playerTile);
        playerControllers.get(1).setTile(opponentTile);
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
        for (Player player : gameManager.getActivePlayers()) {
            // TODO: send actual inputs
            player.sendInputLine("0");
            player.execute();
        }
    }

    private void doPlayerActions() {
        List<PlayerAction> playerPushRowActions = new ArrayList<>();
        List<PlayerAction> playerPushColumnActions = new ArrayList<>();
        for (Player player : gameManager.getActivePlayers()) {
            try {
                PlayerController playerController = playerControllers.get(player.getIndex());
                AbstractAction action = player.getAction();
                if (action instanceof PushAction) {
                    PushAction pushAction = (PushAction)action;
                    if (pushAction.direction == Constants.Direction.RIGHT || pushAction.direction == Constants.Direction.LEFT) {
                        playerPushRowActions.add(new PlayerAction(playerController, pushAction));
                    } else {
                        playerPushColumnActions.add(new PlayerAction(playerController, pushAction));
                    }
                } else if (action instanceof MoveAction) {
                    MoveAction moveAction = (MoveAction)action;
                    List<MoveAction.Step> steps = moveAction.steps;
                    playerController.moveAgentBy(steps);
                }
            } catch (NumberFormatException | AbstractPlayer.TimeoutException | InvalidAction e) {
                player.deactivate("Eliminated!");
            }
        }
        List<Integer> pushedRows = new ArrayList<>();
        playerPushRowActions.forEach(playerAction -> {
            PushAction action = (PushAction) playerAction.action;
            pushedRows.add(action.lineId);
            TileController tile = map.pushRow(playerAction.player.getTile(), action.lineId, action.direction);
            tile.setPosAbsolute(playerAction.player.getTilePosition());
        });

        final List<Integer> rowsToSkip = pushedRows;
        playerPushColumnActions.forEach(playerAction -> {
            PushAction action = (PushAction) playerAction.action;
            TileController tile = map.pushColumn(playerAction.player.getTile(), action.lineId, action.direction, rowsToSkip);
            tile.setPosAbsolute(playerAction.player.getTilePosition());
        });
    }

    private void checkForFinishedItems() {
        gameManager.getActivePlayers().forEach(player -> {
            PlayerController playerController = playerControllers.get(player.getIndex());
            Vector2 pos = player.getAgentPosition();
            TileController tile = map.getTile(pos.x, pos.y);
            Item topCard = playerController.getTopCardItem();
            if (tile.hasItem() && tile.getItem().getLowercaseIdentifier().equals(topCard.getLowercaseIdentifier()) && tile.getItem().getPlayerId() == player.getIndex()) {
                playerController.removeCard(topCard);
                tile.removeItem();
            }
        });
    }

    private void checkForWinner() {
        gameManager.getActivePlayers().forEach(player -> {
            if (!playerControllers.get(player.getIndex()).hasCards()) {
                declareWinner(player);
                gameManager.endGame();
            }
        });
    }

    private void declareWinner(Player player) {
        System.out.println(player.getNicknameToken() + " is a winner!");
    }

    @Override
    public void gameTurn(int turn) {
        sendPlayerInputs();
        doPlayerActions();
        checkForFinishedItems();
        checkForWinner();
    }

    @Override
    public void onEnd() {
        gameManager.getActivePlayers().forEach(player -> {
            // Player score will be the number of solved cards
            player.setScore(Constants.ITEM_IDENTIFIERS.size() - player.getCards().size());
            System.out.println(player.getNicknameToken() + " score is " + player.getScore());
        });
    }

    static class PlayerAction {
        PlayerController player;
        AbstractAction action;

        public PlayerAction(PlayerController player, AbstractAction action) {
            this.player = player;
            this.action = action;
        }
    }
}
