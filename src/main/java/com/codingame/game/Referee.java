package com.codingame.game;

import com.codingame.game.Controller.CardController;
import com.codingame.game.Controller.PlayerController;
import com.codingame.game.Controller.TileController;
import com.codingame.game.InputActions.*;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Utils;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.CardView;
import com.codingame.game.View.PlayerView;
import com.codingame.game.View.TileView;
import com.codingame.gameengine.core.AbstractPlayer;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
import com.google.inject.Inject;

import java.util.*;

public class Referee extends AbstractReferee {
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private GameMap map;

    private List<PlayerController> playerControllers = new ArrayList<>();

    private Text turnText;

    @Override
    public void init() {
        TileView.graphicEntityModule = graphicEntityModule;
        PlayerView.graphicEntityModule = graphicEntityModule;
        CardView.graphicEntityModule = graphicEntityModule;

        Properties params = gameManager.getGameParameters();
        Constants.random = new Random(getSeed(params));

        gameManager.setMaxTurns(150);

        createBackground();
        createPlayers();
        createMap();
        createPlayerTiles();
        createCards();
        createTexts();
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
                playerControllers.get(player.getIndex()).addCard(new Item(shuffledItemIds.get(i), player.getIndex()), cardPos);
            }
        }

        playerControllers.get(0).flipTopCard();
    }

    private void createTexts() {
        turnText = graphicEntityModule.createText(String.format("Turn: %s", Action.Type.PUSH))
                .setX(Constants.TURN_TEXT_POS_X)
                .setY(Constants.TURN_TEXT_POS_Y)
                .setFontSize(50)
                .setFillColor(0x000000)
                .setAnchorX(1)
                .setAnchorY(1);
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
                } else if (i % 2 != 0) {
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
        playerTile.initView();
        opponentTile.initView();

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
            // Game Map
            String[] mapRows = map.toInputStrings();
            for (int i = 0; i < Constants.MAP_HEIGHT; i++) {
                player.sendInputLine(mapRows[i]);
            }

            // Player deck
            PlayerController playerController = playerControllers.get(player.getIndex());
            player.sendInputLine(Integer.toString(playerController.getNumCards()));
            player.sendInputLine(playerController.getTopCard().getItem().getIdentifier() + player.getIndex());

            // Opponents deck
            int opponentIndex = (player.getIndex() == 0) ? 1 : 0;
            PlayerController opponentController = playerControllers.get(opponentIndex);
            player.sendInputLine(Integer.toString(opponentController.getNumCards()));

            // Player agent position
            player.sendInputLine(Integer.toString(playerController.getPos().x));
            player.sendInputLine(Integer.toString(playerController.getPos().y));

            // Opponent agent position
            player.sendInputLine(Integer.toString(opponentController.getPos().x));
            player.sendInputLine(Integer.toString(opponentController.getPos().y));

            // Player extra tile
            player.sendInputLine(playerController.getTile().toInputString());

            // Opponent extra tile
            player.sendInputLine(opponentController.getTile().toInputString());
            player.execute();
        }
    }

    private void doPlayerActions(Action.Type turnType) {
        List<PlayerAction> playerPushRowActions = new ArrayList<>();
        List<PlayerAction> playerPushColumnActions = new ArrayList<>();
        PushAction prevPushAction = null;
        for (Player player : gameManager.getActivePlayers()) {
            try {
                PlayerController playerController = playerControllers.get(player.getIndex());
                Action action = player.getAction();
                if (turnType == Action.Type.PUSH && action instanceof PushAction) {
                    PushAction pushAction = (PushAction)action;
                    // check if both players tried to push against opposite directions on the same line
                    if (prevPushAction != null && pushAction.lineId == prevPushAction.lineId && pushAction.direction == prevPushAction.direction.getOpposite()) {
                        gameManager.addToGameSummary("WARNING: Both players tried to push in opposite directions. Nothing happens!");
                        // clear the previous pending commands
                        playerPushRowActions.clear();
                        playerPushColumnActions.clear();
                    } else if (pushAction.direction == Constants.Direction.RIGHT || pushAction.direction == Constants.Direction.LEFT) {
                        playerPushRowActions.add(new PlayerAction(playerController, pushAction));
                    } else {
                        playerPushColumnActions.add(new PlayerAction(playerController, pushAction));
                    }
                    prevPushAction = pushAction;
                } else if (turnType == Action.Type.MOVE && (action instanceof MoveAction || action instanceof PassAction)) {
                    if (action instanceof MoveAction) {
                        MoveAction moveAction = (MoveAction) action;
                        List<MoveAction.Step> steps = moveAction.steps;
                        map.moveAgentBy(playerController, steps);
                    } else if (action instanceof PassAction) {
                        // do nothing
                    }
                } else {
                    throw new InvalidAction(String.format("can't \"%s\" while expecting a %s action", action, turnType));
                }
            } catch (AbstractPlayer.TimeoutException e) {
                player.deactivate(String.format("%s: timeout", player.getNicknameToken()));
                gameManager.addToGameSummary(String.format("%s: timeout - no input provided", player.getNicknameToken()));
            } catch (InvalidAction e) {
                player.deactivate(String.format("%s: invalid input", player.getNicknameToken()));
                gameManager.addToGameSummary(String.format("%s: invalid input - %s", player.getNicknameToken(), e.getMessage()));
            } catch (IndexOutOfBoundsException e) {
                player.deactivate(String.format("%s: timeout", player.getNicknameToken()));
                gameManager.addToGameSummary(String.format("%s: timeout - player provided an empty input", player.getNicknameToken()));
            }
        }
		
		List<Integer> pushedRows = new ArrayList<>();
        playerPushRowActions.forEach(playerAction -> {
            PushAction action = (PushAction)playerAction.action;
            pushedRows.add(action.lineId);
            TileController poppedTile = map.pushRow(playerAction.player.getTile(), action.lineId, action.direction);
            poppedTile.setPosAbsolute(playerAction.player.getTilePosition(), 0.5);
            playerAction.player.setTile(poppedTile);

            // if there's a player on the pushed row move them too
            for (Player player : gameManager.getActivePlayers()) {
                if (player.getAgentPosition().x == action.lineId) {
                    Vector2 pos = new Vector2(playerControllers.get(player.getIndex()).getPos());
                    pos.add(action.direction.asValue());
                    pos.y = Utils.wrap(pos.y, 0, Constants.MAP_HEIGHT - 1);
                    playerControllers.get(player.getIndex()).setPosInMap(pos, 0.5);
                }
            }
        });

        final List<Integer> rowsToSkip = pushedRows;
        playerPushColumnActions.forEach(playerAction -> {
            PushAction action = (PushAction)playerAction.action;
            TileController poppedTile = map.pushColumn(playerAction.player.getTile(), action.lineId, action.direction, rowsToSkip);
            poppedTile.setPosAbsolute(playerAction.player.getTilePosition(), 1);
            playerAction.player.setTile(poppedTile);

            // if there's a player on the pushed column move them too
            for (Player player : gameManager.getActivePlayers()) {
                if (player.getAgentPosition().y == action.lineId) {
                    Vector2 pos = new Vector2(playerControllers.get(player.getIndex()).getPos());
                    pos.add(action.direction.asValue());
                    pos.x = Utils.wrap(pos.x, 0, Constants.MAP_WIDTH - 1);
                    if (!rowsToSkip.isEmpty()) {
                        playerControllers.get(player.getIndex()).setSamePosInMap(0.5);
                    }
                    playerControllers.get(player.getIndex()).setPosInMap(pos, 1);
                }
            }
        });
    }

    private void checkForFinishedItems() {
        gameManager.getActivePlayers().forEach(player -> {
            PlayerController playerController = playerControllers.get(player.getIndex());
            Vector2 pos = player.getAgentPosition();
            TileController tile = map.getTile(pos.x, pos.y);
            if (!tile.hasItem()) return;

            CardController topCard = playerController.getTopCard();
            if (tile.getItem().getIdentifier().equals(topCard.getItem().getIdentifier())
                    && tile.getItem().getPlayerId() == player.getIndex()) {
                playerController.removeTopCard();
                playerController.flipTopCard();
                tile.removeItem();
                gameManager.addToGameSummary(String.format("%s: completed a quest card", player.getNicknameToken()));
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
        gameManager.addToGameSummary(player.getNicknameToken() + " is a winner!");
    }

    private void updateTexts(Action.Type turnType) {
        turnText.setText(String.format("Turn: %s", turnType));
        graphicEntityModule.commitEntityState(0, turnText);
    }

    @Override
    public void gameTurn(int turn) {
        Action.Type turnType = Action.Type.values()[turn % 2];
        updateTexts(turnType);
        sendPlayerInputs();
        doPlayerActions(turnType);
        checkForFinishedItems();
        checkForWinner();
    }

    @Override
    public void onEnd() {
        gameManager.getActivePlayers().forEach(player -> {
            // Player score will be the number of solved cards
            player.setScore(Constants.ITEM_IDENTIFIERS.size() - playerControllers.get(player.getIndex()).getNumCards());
            System.out.println(player.getNicknameToken() + " score is " + player.getScore());
        });
    }

    static class PlayerAction {
        PlayerController player;
        Action action;

        public PlayerAction(PlayerController player, Action action) {
            this.player = player;
            this.action = action;
        }
    }
}
