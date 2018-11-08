package com.codingame.game;

import com.codingame.game.Controller.CardController;
import com.codingame.game.Controller.PlayerController;
import com.codingame.game.Controller.TileController;
import com.codingame.game.InputActions.*;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Utils;
import com.codingame.game.Utils.Vector2;
import com.codingame.game.View.PlayerView;
import com.codingame.game.View.TileView;
import com.codingame.gameengine.core.AbstractPlayer;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
import com.codingame.view.endscreen.EndScreenModule;
import com.google.inject.Inject;

import java.util.*;

public class Referee extends AbstractReferee {
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;
    @Inject private EndScreenModule endScreenModule;

    private GameMap map;

    private List<PlayerController> playerControllers = new ArrayList<>();

    private Text turnText;

    private Action.Type turnType;

    private List<Queue<Action>> actionsQueue = new ArrayList<>(Arrays.asList(new ArrayDeque<>(), new ArrayDeque<>()));

    @Override
    public void init() {
        Utils.graphicEntityModule = graphicEntityModule;

        Properties params = gameManager.getGameParameters();
        Constants.random = new Random(getSeed(params));

        gameManager.setMaxTurns(150);

        createBackground();
        createPlayers();
        createMap();
        createPlayerTiles();
        createCards();
        createTexts();

        // make sure the view is initialized at frame 0
        graphicEntityModule.commitWorldState(0);

        sendInitialInputs();
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

    private List<String> getShuffledItemNames() {
        List<String> shuffledItems = new ArrayList<>(Constants.ITEM_NAMES);
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
        List<String> shuffledItemIds = getShuffledItemNames();

        for (Player player : gameManager.getPlayers()) {
            for (int i = 0; i < shuffledItemIds.size(); i++) {
                int playerId = player.getIndex();
                int orientation = playerId == 0 ? 1 : -1;
                Vector2 cardPos = new Vector2(Constants.CARDS_POSITIONS.get(playerId)).add(new Vector2(0, orientation * i * Constants.CARDS_OFFSET));
                playerControllers.get(player.getIndex()).addCard(new Item(shuffledItemIds.get(i), player.getIndex()), cardPos);
            }
        }

        for (PlayerController player : playerControllers) {
            player.flipVisibleCards(Constants.NUM_QUEST_CARDS);
        }
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

    private void createMap() {
        map = new GameMap();
    }

    private void createPlayerTiles() {
        TileController playerTile = new TileController(new TileModel("0110"), new TileView());
        TileController opponentTile = new TileController(new TileModel("1001"), new TileView());
        playerTile.initView();
        opponentTile.initView();

        playerTile.setPosAbsolute(Constants.PLAYER_INDEX, new Vector2(Constants.PLAYER_TILE_POS_X, Constants.PLAYER_TILE_POS_Y));
        opponentTile.setPosAbsolute(Constants.OPPONENT_INDEX, new Vector2(Constants.OPPONENT_TILE_POS_X, Constants.OPPONENT_TILE_POS_Y));

        playerControllers.get(Constants.PLAYER_INDEX).setTile(playerTile);
        playerControllers.get(Constants.OPPONENT_INDEX).setTile(opponentTile);
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

    private void sendInitialInputs() {
        for (Player player : gameManager.getActivePlayers()) {
            player.sendInputLine(String.format("%d %d",
                    Constants.MAP_WIDTH,
                    Constants.MAP_HEIGHT));
        }
    }

    private void sendPlayerInputs() {
        for (Player player : gameManager.getActivePlayers()) {
            // Game map
            for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
                StringBuilder sb = new StringBuilder();
                sb.append(map.getTile(0, y).toInputString());
                for (int x = 1; x < Constants.MAP_WIDTH; x++) {
                    sb.append(" " + map.getTile(x, y).toInputString());
                }
                player.sendInputLine(sb.toString());
            }

            // Player information
            for (int i = 0; i < 2; i++) {
                PlayerController playerController = playerControllers.get(i);
                player.sendInputLine(String.format("%d %d %d %s",
                        playerController.getNumCards(),
                        playerController.getPos().getX(),
                        playerController.getPos().getY(),
                        playerController.getTile().toInputString()));
            }

            // Items
            List<TileController> tilesWithItems = new ArrayList<>();
            TileController playerTile = playerControllers.get(Constants.PLAYER_INDEX).getTile();
            if (playerTile.hasItem()) {
                tilesWithItems.add(playerTile);
            }
            TileController opponentTile = playerControllers.get(Constants.OPPONENT_INDEX).getTile();
            if (opponentTile.hasItem()) {
                tilesWithItems.add(opponentTile);
            }
            for (int y = 0; y < Constants.MAP_HEIGHT; y++) {
                for (int x = 0; x < Constants.MAP_WIDTH; x++) {
                    TileController tile = map.getTile(x, y);
                    if (tile.hasItem()) {
                        tilesWithItems.add(tile);
                    }
                }
            }
            int numItems = tilesWithItems.size();
            player.sendInputLine(Integer.toString(numItems));
            for (TileController tile : tilesWithItems) {
                Item item = tile.getItem();
                player.sendInputLine(String.format("%s %d %d %d",
                        item.getName(),
                        tile.getPos().getX(),
                        tile.getPos().getY(),
                        item.getPlayerId()));
            }

            // Turn type
            player.sendInputLine(Integer.toString(turnType.getValue()));

            // Cards
            int numQuests = playerControllers.get(Constants.PLAYER_INDEX).getNumQuestCards()
                    + playerControllers.get(Constants.OPPONENT_INDEX).getNumQuestCards();
            player.sendInputLine(Integer.toString(numQuests));
            for (int i = 0; i < 2; i++) {
                PlayerController playerController = playerControllers.get(i);
                for (CardController card : playerController.getTopCards()) {
                    player.sendInputLine(String.format("%s %d",
                            card.getItem().getName(),
                            card.getItem().getPlayerId()));
                }
            }

            player.execute();
        }
    }

    private void getPlayerActions() {
        PushAction prevPushAction = null;
        for (Player player : gameManager.getActivePlayers()) {
            try {
                int playerIndex = player.getIndex();
                Queue<Action> playerQueue = actionsQueue.get(playerIndex);
                List<String> outputs = player.getOutputs();
                Action action = player.getAction(outputs.get(0));
                if (action instanceof MoveAction) {
                    MoveAction moveAction = (MoveAction)action;
                    for (MoveAction.Step step : moveAction.getSteps()) {
                        MoveAction stepAction = new MoveAction();
                        stepAction.addStep(step.getDirection());
                        playerQueue.add(stepAction);
                    }
                } else if (action instanceof PushAction) {
                    PushAction pushAction = (PushAction)action;
                    // check if both players tried to push against opposite directions on the same line
                    if (prevPushAction != null && pushAction.getLineId() == prevPushAction.getLineId()
                            && (pushAction.getDirection() == prevPushAction.getDirection().getOpposite()
                            || pushAction.getDirection() == prevPushAction.getDirection())) {
                        gameManager.addToGameSummary("[WARNING] Both players tried to push the same line. Nothing happens!");
                        // remove the previous push action from the first player's queue
                        if (playerIndex > 0) {
                            actionsQueue.get(playerIndex - 1).remove(prevPushAction);
                        }
                    } else {
                        playerQueue.add(action);
                        prevPushAction = pushAction;
                    }
                }
            } catch (AbstractPlayer.TimeoutException e) {
                gameManager.addToGameSummary(String.format("%s: timeout - no input provided", player.getNicknameToken()));
                player.deactivate(String.format("%s: timeout", player.getNicknameToken()));
                forceGameEnd();
            } catch (InvalidAction e) {
                if (e.isFatal()) {
                    gameManager.addToGameSummary(String.format("%s: invalid input - %s", player.getNicknameToken(), e.getMessage()));
                    player.deactivate(String.format("%s: invalid input", player.getNicknameToken()));
                    forceGameEnd();
                } else {
                    gameManager.addToGameSummary(String.format("[WARNING] %s: invalid input - %s", player.getNicknameToken(), e.getMessage()));
                }
            } catch (IndexOutOfBoundsException e) {
                gameManager.addToGameSummary(String.format("%s: timeout - player provided an empty input", player.getNicknameToken()));
                player.deactivate(String.format("%s: timeout", player.getNicknameToken()));
                forceGameEnd();
            }
        }
    }

    private void doPlayerActions() {
        PushAction prevPushAction = null;
        for (Player player : gameManager.getActivePlayers()) {
            try {
                Action action = actionsQueue.get(player.getIndex()).poll();
                if (action == null) {
                    continue;
                }
                PlayerController playerController = playerControllers.get(player.getIndex());
                if (turnType == Action.Type.PUSH && action instanceof PushAction) {
                    PushAction pushAction = (PushAction)action;
                    // check if the previous push action was of the same type as the current one (horizontal or vertical)
                    // similar push actions get processed in the same frame, otherwise they remain in the queue till the next frame
                    if (prevPushAction != null
                            && (prevPushAction.getDirection() != pushAction.getDirection()
                            && prevPushAction.getDirection() != pushAction.getDirection().getOpposite())) {
                        // put the action back in the queue to be processed in the next frame
                        actionsQueue.get(player.getIndex()).add(pushAction);
                        return;
                    }
                    if (pushAction.getDirection() == Constants.Direction.RIGHT
                            || pushAction.getDirection() == Constants.Direction.LEFT) {
                        if (pushAction.getLineId() >= Constants.MAP_HEIGHT) {
                            throw new InvalidAction("out of bounds line index");
                        }
                        doPushAction(new PlayerAction(playerController, pushAction), true);
                    } else {
                        if (pushAction.getLineId() >= Constants.MAP_WIDTH) {
                            throw new InvalidAction("out of bounds line index");
                        }
                        doPushAction(new PlayerAction(playerController, pushAction), false);
                    }
                    prevPushAction = pushAction;
                } else if (turnType == Action.Type.MOVE && action instanceof MoveAction) {
                    MoveAction moveAction = (MoveAction)action;
                    List<MoveAction.Step> steps = moveAction.getSteps();
                    for (MoveAction.Step step : steps) {
                        map.moveAgentBy(playerController, step);
                    }
                } else {
                    throw new InvalidAction(String.format("can't \"%s\" while expecting a %s action", action, turnType));
                }
            } catch (InvalidAction e) {
                if (e.isFatal()) {
                    gameManager.addToGameSummary(String.format("%s: invalid input - %s", player.getNicknameToken(), e.getMessage()));
                    player.deactivate(String.format("%s: invalid input", player.getNicknameToken()));
                    forceGameEnd();
                } else {
                    actionsQueue.get(player.getIndex()).clear();
                    gameManager.addToGameSummary(String.format("[WARNING] %s: invalid input - %s", player.getNicknameToken(), e.getMessage()));
                }
            }
        }
    }

    private void doPushAction(PlayerAction playerAction, boolean isRowPush) {
        PushAction action = (PushAction)playerAction.action;
        TileController poppedTile;
        if (isRowPush) {
            poppedTile = map.pushRow(playerAction.player.getTile(), action.getLineId(), action.getDirection());
        } else {
            poppedTile = map.pushColumn(playerAction.player.getTile(), action.getLineId(), action.getDirection());
        }
        poppedTile.setPosAbsolute(playerAction.player.getId(), playerAction.player.getTilePosition());
        playerAction.player.setTile(poppedTile);

        // if there's a player on the pushed line move them too
        for (Player player : gameManager.getActivePlayers()) {
            int playerLineId = isRowPush ? player.getAgentPosition().getY() : player.getAgentPosition().getX();
            if (playerLineId == action.getLineId()) {
                Vector2 pos = new Vector2(playerControllers.get(player.getIndex()).getPos());
                pos.add(action.getDirection().asValue());
                if (isRowPush) {
                    pos.setX(Utils.wrap(pos.getX(), 0, Constants.MAP_WIDTH - 1));
                } else {
                    pos.setY(Utils.wrap(pos.getY(), 0, Constants.MAP_HEIGHT - 1));
                }
                playerControllers.get(player.getIndex()).setPosInMap(pos);
            }
        }
    }

    private void checkForFinishedItems() {
        gameManager.getActivePlayers().forEach(player -> {
            PlayerController playerController = playerControllers.get(player.getIndex());
            Vector2 pos = player.getAgentPosition();
            TileController tile = map.getTile(pos.getX(), pos.getY());
            if (!tile.hasItem()) return;

            Item item = tile.getItem();
            List<CardController> topCards = playerController.getTopCards();
            for (CardController card : topCards) {
                if (item.getName().equals(card.getItem().getName())
                        && item.getPlayerId() == player.getIndex()) {
                    playerController.removeCard(card);
                    playerController.flipNewCard();
                    tile.removeItem();
                    gameManager.addToGameSummary(String.format("%s: completed a quest card", player.getNicknameToken()));
                    break;
                }
            }
        });
    }

    private void checkForWinner() {
        gameManager.getActivePlayers().forEach(player -> {
            if (!playerControllers.get(player.getIndex()).hasCards()) {
                declareWinner(player);
                forceGameEnd();
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

    public void forceAnimationFrame() {
        for (Player player : gameManager.getActivePlayers()) {
            player.setExpectedOutputLines(0);
            player.execute();
            try {
                player.getOutputs();
            } catch (Exception e) {

            }
        }
    }

    public void forceGameFrame() {
        for (Player player : gameManager.getActivePlayers()) {
            player.setExpectedOutputLines(1);
        }
    }

    @Override
    public void gameTurn(int turn) {
        if (!actionsQueue.get(0).isEmpty() || !actionsQueue.get(1).isEmpty()) {
            forceAnimationFrame();
        } else {
            turnType = turnType == Action.Type.PUSH ? Action.Type.MOVE : Action.Type.PUSH;
            forceGameFrame();
            sendPlayerInputs();
            getPlayerActions();
        }
        updateTexts(turnType);
        doPlayerActions();
        checkForFinishedItems();
        checkForWinner();
    }

    private void forceGameEnd() {
        for (Player player : gameManager.getPlayers()) {
            // the player score will be the number of solved cards
            player.setScore(Constants.ITEM_NAMES.size() - playerControllers.get(player.getIndex()).getNumCards());
            if (player.isActive()) {
                player.deactivate();
            }
        }
        gameManager.endGame();
    }

    @Override
    public void onEnd() {
        endScreenModule.setScores(gameManager.getPlayers().stream().mapToInt(player -> player.getScore()).toArray());
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
