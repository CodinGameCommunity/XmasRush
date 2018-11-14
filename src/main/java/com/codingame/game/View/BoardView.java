package com.codingame.game.View;

import com.codingame.game.Model.TileModel;
import com.codingame.game.Player;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.view.tooltip.TooltipModule;
import com.codingame.gameengine.module.entities.Group;

import java.util.ArrayList;
import java.util.List;

public class BoardView extends AbstractView{
    private TooltipModule tooltipModule;

    private Group group;

    List<TileView> tiles;
    List<PlayerView> players;

    public BoardView(GraphicEntityModule entityModule, TooltipModule tooltipModule){
        super(entityModule);
        this.tooltipModule = tooltipModule;

        tiles = new ArrayList<>();
        players = new ArrayList<>();

        this.group = this.entityModule.createGroup()
                .setX(0)
                .setY(0)
                .setVisible(true)
                .setZIndex(1)
                .setScale(1);
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

    public void updateView() {}
}
