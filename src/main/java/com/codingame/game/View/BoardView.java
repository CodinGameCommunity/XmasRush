package com.codingame.game.View;

import com.codingame.game.Model.CardModel;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.view.tooltip.TooltipModule;
import com.codingame.gameengine.module.entities.Group;

import java.util.*;

public class BoardView extends AbstractView{
    private TooltipModule tooltipModule;

    //distance from arrows to tiles
    private final int arrowOffset = 100;
    //distance between arrows
    private final int arrowSpacing = Constants.TILE_SIZE + Constants.TILES_OFFSET;

    private Group group;

    List<TileView> tiles;
    List<PlayerView> players;
    List<CardView> cards;
    Map<String, ArrowView> arrows;

    List<AbstractMap.SimpleEntry<String, Integer>> arrowsToShow = new ArrayList<>();
    List<ArrowView> arrowsToHide = new ArrayList<>();

    public BoardView(GraphicEntityModule entityModule, TooltipModule tooltipModule){
        super(entityModule);
        this.tooltipModule = tooltipModule;

        tiles = new ArrayList<>();
        players = new ArrayList<>();
        cards = new ArrayList<>();
        arrows = new HashMap<>();

        this.group = this.entityModule.createGroup()
                .setX(0)
                .setY(0)
                .setVisible(true)
                .setZIndex(1)
                .setScale(1);

        createArrows();
    }

    public TileView createTileView(TileModel tile) {
        TileView tileView = new TileView(entityModule, tooltipModule, tile);
        group.add(tileView.getEntity().setZIndex(1));
        tiles.add(tileView);
        return tileView;
    }

    public PlayerView createPlayerView(Player player) {
        PlayerView playerView = new PlayerView(entityModule, player, player.getPlayer());
        group.add(playerView.getEntity().setZIndex(2));
        players.add(playerView);
        return playerView;
    }

    public CardView createCardView(CardModel card) {
        CardView cardView = new CardView(entityModule, tooltipModule, card);
        group.add(cardView.getEntity().setZIndex(0));
        cards.add(cardView);
        return cardView;
    }

    public void updateView() {
        for (ArrowView arrow : arrowsToHide)
            arrow.hideArrow();
        arrowsToHide.clear();

        if (arrowsToShow.size() == 2 && isSamePushAction())
            //show two arrows pushing the same line
            showArrow(new AbstractMap.SimpleEntry<>(arrowsToShow.get(0).getKey(), 2));
        else
            arrowsToShow.stream().forEach(arrow -> showArrow(arrow));
        arrowsToShow.clear();
    }

    private boolean isSamePushAction() {
        return arrowsToShow.stream()
                .map(arrow -> arrow.getKey())
                .distinct()
                .count() == 1;
    }

    private void showArrow(AbstractMap.SimpleEntry<String, Integer> arrow) {
        String arrowId = arrow.getKey();
        Integer arrowType = arrow.getValue();
        ArrowView arrowView = arrows.get(arrowId);
        arrowView.showArrow(arrowType);
        arrowsToHide.add(arrowView);
    }

    public void update(AbstractMap.SimpleEntry<String, Integer> action) {
        arrowsToShow.add(action);
    }

    private void createArrows() {
        createUpArrows();
        createRightArrows();
        createDownArrows();
        createLeftArrows();
    }

    private void createUpArrows() {
        Vector2 pos = new Vector2(Constants.MAP_POS_X,
                Constants.MAP_POS_Y + arrowSpacing * (Constants.MAP_HEIGHT - 1) + arrowOffset);
        for (int x = 0; x < Constants.MAP_WIDTH; x++) {
            String id = x + "" + Constants.Direction.UP.asValue();
            ArrowView view = new ArrowView(entityModule, pos, Math.toRadians(0));
            group.add(view.getEntity().setZIndex(0));
            arrows.put(id, view);
            pos.setX(pos.getX() + arrowSpacing);
        }
    }

    private void createRightArrows(){
        Vector2 pos = new Vector2(Constants.MAP_POS_X  - arrowOffset, Constants.MAP_POS_Y);
        for (int y = 0; y < Constants.MAP_HEIGHT; y++){
            String id = y + "" + Constants.Direction.RIGHT.asValue();
            ArrowView view = new ArrowView(entityModule, pos, Math.toRadians(90));
            group.add(view.getEntity().setZIndex(0));
            arrows.put(id, view);
            pos.setY(pos.getY() + arrowSpacing);
        }
    }

    private void createDownArrows() {
        Vector2 pos = new Vector2(Constants.MAP_POS_X, Constants.MAP_POS_Y - arrowOffset);
        for (int x = 0; x < Constants.MAP_WIDTH; x++) {
            String id = x + "" + Constants.Direction.DOWN.asValue();
            ArrowView view = new ArrowView(entityModule, pos, Math.toRadians(180));
            group.add(view.getEntity().setZIndex(0));
            arrows.put(id, view);
            pos.setX(pos.getX() + arrowSpacing);
        }
    }

    private void createLeftArrows(){
        Vector2 pos = new Vector2(Constants.MAP_POS_X + arrowSpacing * (Constants.MAP_WIDTH - 1) + arrowOffset,
                Constants.MAP_POS_Y);
        for (int y = 0; y < Constants.MAP_HEIGHT; y++){
            String id = y + "" + Constants.Direction.LEFT.asValue();
            ArrowView view = new ArrowView(entityModule, pos, Math.toRadians(270));
            group.add(view.getEntity().setZIndex(0));
            arrows.put(id, view);
            pos.setY(pos.getY() + arrowSpacing);
        }
    }
}
