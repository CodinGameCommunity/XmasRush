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
import java.util.stream.Collectors;

public class Referee extends AbstractReferee {
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private GameMap map;

    private Player player;
    private Player opponent;

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
        int playerOffsetX = Constants.MAP_POS_X + (Constants.TILE_SIZE + Constants.TILE_SPACE) * this.player.getAgentPosition().x;
        int playerOffsetY = Constants.MAP_POS_Y + (Constants.TILE_SIZE + Constants.TILE_SPACE) * this.player.getAgentPosition().y;
        createSprite("agent_1.png", playerOffsetX, playerOffsetY, 0, Constants.MapLayers.AGENTS.asValue());
        int opponentOffsetX = Constants.MAP_POS_X + (Constants.TILE_SIZE + Constants.TILE_SPACE) * this.opponent.getAgentPosition().x;
        int opponentOffsetY = Constants.MAP_POS_Y + (Constants.TILE_SIZE + Constants.TILE_SPACE) * this.opponent.getAgentPosition().y;
        createSprite("agent_2.png", opponentOffsetX, opponentOffsetY, 0, Constants.MapLayers.AGENTS.asValue());
    }

    private void createPlayers() {
        this.player = gameManager.getPlayer(0);
        this.opponent = gameManager.getPlayer(1);

        this.player.setAgentPosition(new Vector2(0, 0));
        this.opponent .setAgentPosition(new Vector2(Constants.MAP_WIDTH - 1, Constants.MAP_HEIGHT - 1));

        drawPlayers();
    }

    private void drawCards() {
        int cardWidth = 128;
        int cardHeight = 256;
        int playerOffsetX = 100 + cardWidth / 2;
        int playerOffsetY = 25 + cardHeight / 2;
        int opponentOffsetX = Constants.SCREEN_WIDTH - playerOffsetX;
        int opponentOffsetY = Constants.SCREEN_HEIGHT - playerOffsetY;

        List<Item> playerCards = player.getCards();
        List<Item> opponentCards = opponent.getCards();

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
        List<String> shuffledItemIdentifiers = getShuffledItemIdentifiers();

        for (ListIterator<String> beginIterator = shuffledItemIdentifiers.listIterator(); beginIterator.hasNext();) {
            String beginIdentifier = beginIterator.next();
            player.addItemCard(new Item(beginIdentifier, 1));
            opponent.addItemCard(new Item(beginIdentifier, 2));
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
        TileController playerTile = new TileController(new TileModel("0110"), new TileView());
        TileController opponentTile = new TileController(new TileModel("1001"), new TileView());
        playerTile.init();
        opponentTile.init();

        playerTile.setPosAbsolute(new Vector2(Constants.PLAYER_TILE_POS_X, Constants.PLAYER_TILE_POS_Y));
        opponentTile.setPosAbsolute(new Vector2(Constants.OPPONENT_TILE_POS_X, Constants.OPPONENT_TILE_POS_Y));

        player.setTile(playerTile);
        opponent.setTile(opponentTile);
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

    private void doPlayerActions() {
        List<PlayerAction> playerPushRowActions = new ArrayList<>();
        List<PlayerAction> playerPushColumnActions = new ArrayList<>();
        for (Player player: gameManager.getActivePlayers()) {
            try {
                AbstractAction action = player.getAction();
                if (action instanceof PushAction) {
                    PushAction pushAction = (PushAction) action;
                    if (pushAction.direction.asValue() == Constants.PushDirection.RIGHT || pushAction.direction.asValue() == Constants.PushDirection.LEFT) {
                        playerPushRowActions.add(new PlayerAction(player, pushAction));
                    } else {
                        playerPushColumnActions.add(new PlayerAction(player, pushAction));
                    }
                }
            } catch (NumberFormatException | AbstractPlayer.TimeoutException | InvalidAction e) {
                player.deactivate("Eliminated!");
            }
        }
        List<Integer> pushedRows = new ArrayList<>();
        playerPushRowActions.forEach(playerAction -> {
            PushAction pushAction = (PushAction) playerAction.action;
            pushedRows.add(pushAction.id);
            TileController tile = map.pushRow(playerAction.player.getTile(), pushAction.id, pushAction.direction.asValue());
            tile.setPosAbsolute(playerAction.player.getTilePosition());
        });

        final List<Integer> rowsToSkip = pushedRows;
        playerPushColumnActions.forEach(playerAction -> {
            PushAction pushAction = (PushAction) playerAction.action;
            TileController tile = map.pushColumn(playerAction.player.getTile(), pushAction.id, pushAction.direction.asValue(), rowsToSkip);
            tile.setPosAbsolute(playerAction.player.getTilePosition());
        });
    }

    private void checkForFinishedItems() {
        gameManager.getActivePlayers().forEach(player -> {
            Vector2 pos = player.getAgentPosition();
            TileController tile = map.getTile(pos.x, pos.y);
            Item topCard = player.getTopCard();
            if (tile.hasItem() && tile.getItem().getLowercaseIdentifier().equals(topCard.getLowercaseIdentifier())) {
                player.removeCard(topCard);
            }
        });
    }

    @Override
    public void gameTurn(int turn) {
        sendPlayerInputs();
        doPlayerActions();
        checkForFinishedItems();
    }

    static class PlayerAction {
        Player player;
        AbstractAction action;

        public PlayerAction(Player player, AbstractAction action) {
            this.player = player;
            this.action = action;
        }
    }
}
