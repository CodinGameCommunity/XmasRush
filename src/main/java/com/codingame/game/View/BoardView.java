package com.codingame.game.View;


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
    private final int arrowOffset = 85;
    //distance between arrows
    private final int arrowSpacing = Constants.TILE_SIZE + Constants.TILES_OFFSET;

    private Group group;

    List<TileView> tiles;
    List<PlayerView> players;
    Map<String, ArrowView> arrows;

    Set<AbstractMap.SimpleEntry<String, Integer>> arrowsToShow = new HashSet<>();
    List<ArrowView> arrowsToHide = new ArrayList<>();

    public BoardView(GraphicEntityModule entityModule, TooltipModule tooltipModule){
        super(entityModule);
        this.tooltipModule = tooltipModule;

        tiles = new ArrayList<>();
        players = new ArrayList<>();
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
        group.add(tileView.getEntity().setZIndex(0));
        tiles.add(tileView);
        return tileView;
    }

    public PlayerView createPlayerView(Player player) {
        PlayerView playerView = new PlayerView(entityModule, player, player.getPlayer());
        group.add(playerView.getEntity().setZIndex(1));
        players.add(playerView);
        return playerView;
    }

    public void updateView() {
        for (ArrowView arrow : arrowsToHide)
            arrow.hideArrows();
        arrowsToHide.clear();

        for (AbstractMap.SimpleEntry<String, Integer> arrow : arrowsToShow){
            String arrowId = arrow.getKey();
            int arrowType = arrow.getValue();
            ArrowView arrowView = arrows.get(arrowId);
            arrowView.showArrow(arrowType);
            arrowsToHide.add(arrowView);
        }
        arrowsToShow.clear();
    }

    public void update(AbstractMap.SimpleEntry<String, Integer> action) {
        arrowsToShow.add(action);
    }

    public void createArrows() {
        createUpArrows();
        createRightArrows();
        createDownArrows();
        createLeftArrows();
    }

    public void createUpArrows() {
        Vector2 pos = new Vector2(Constants.MAP_POS_X - Constants.TILE_SIZE / 4,
                Constants.MAP_POS_Y + arrowSpacing * (Constants.MAP_HEIGHT - 1) + arrowOffset);
        for (int x = 0; x < Constants.MAP_WIDTH; x++) {
            String id = x + "" + Constants.Direction.UP.asValue();
            ArrowView view = new ArrowView(entityModule, pos, Math.toRadians(0), id);
            group.add(view.getEntity().setZIndex(0));
            arrows.put(id, view);
            pos.setX(pos.getX() + arrowSpacing);
        }
    }

    public void createRightArrows(){
        Vector2 pos = new Vector2(Constants.MAP_POS_X  - arrowOffset,
                Constants.MAP_POS_Y - Constants.TILE_SIZE / 4);
        for (int y = 0; y < Constants.MAP_HEIGHT; y++){
            String id = y + "" + Constants.Direction.RIGHT.asValue();
            ArrowView view = new ArrowView(entityModule, pos, Math.toRadians(90),id);
            group.add(view.getEntity().setZIndex(0));
            arrows.put(id, view);
            pos.setY(pos.getY() + arrowSpacing);
        }
    }

    public void createDownArrows() {
        Vector2 pos = new Vector2(Constants.MAP_POS_X + Constants.TILE_SIZE / 4,
                Constants.MAP_POS_Y - arrowOffset);
        for (int x = 0; x < Constants.MAP_WIDTH; x++) {
            String id = x + "" + Constants.Direction.DOWN.asValue();
            ArrowView view = new ArrowView(entityModule, pos, Math.toRadians(180), id);
            group.add(view.getEntity().setZIndex(0));
            arrows.put(id, view);
            pos.setX(pos.getX() + arrowSpacing);
        }
    }

    public void createLeftArrows(){
        Vector2 pos = new Vector2(Constants.MAP_POS_X + arrowSpacing * (Constants.MAP_WIDTH - 1) + arrowOffset,
                Constants.MAP_POS_Y + Constants.TILE_SIZE / 4);
        for (int y = 0; y < Constants.MAP_HEIGHT; y++){
            String id = y + "" + Constants.Direction.LEFT.asValue();
            ArrowView view = new ArrowView(entityModule, pos, Math.toRadians(270), id);
            group.add(view.getEntity().setZIndex(0));
            arrows.put(id, view);
            pos.setY(pos.getY() + arrowSpacing);
        }
    }
}
