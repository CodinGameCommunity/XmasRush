package com.codingame.game.View;

import com.codingame.game.Model.CardModel;
import com.codingame.game.Model.TileModel;
import com.codingame.game.Player;
import com.codingame.game.Referee;
import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.view.tooltip.TooltipModule;

import java.util.ArrayList;
import java.util.List;

public class ViewController {
    private List<AbstractView> views = new ArrayList<>();
    private GraphicEntityModule entityModule;
    private TooltipModule tooltipModule;

    private BoardView board;

    public ViewController(GraphicEntityModule entityModule, TooltipModule tooltipModule) {
        this.entityModule = entityModule;
        this.tooltipModule = tooltipModule;

        this.board = new BoardView(entityModule, tooltipModule);
        Referee.setObserver(board);

        initView();
    }

    private void initView() {
        createBackground();
    }

    public void update() {
        board.updateView();

        for(AbstractView view : views) {
            view.updateView();
        }
        disposeViews();
    }

    private void disposeViews() {
        views.removeIf(view -> view.isDisposable());
    }

    private void createBackground() {
        entityModule.createRectangle()
                .setX(0)
                .setY(0)
                .setWidth(Constants.SCREEN_WIDTH)
                .setHeight(Constants.SCREEN_HEIGHT)
                .setFillColor(0x669999)
                .setZIndex(0);

        entityModule.createSprite()
                .setImage("logoCG.png")
                .setX(200)
                .setY(Constants.SCREEN_HEIGHT - 80)
                .setAnchor(0.5);
    }

    public void createCardView(CardModel card) {
        CardView cardView = new CardView(entityModule, card);
        views.add(cardView);
    }

    public void createTileView(TileModel tile) {
        views.add(board.createTileView(tile));
    }

    public void createPlayerView(Player player) {
        //create view with player name and avatar
        views.add(new PlayerTextView(entityModule, player) );
        views.add(board.createPlayerView(player));
    }

    public void createTextView() {
        TurnTextView textView = new TurnTextView(entityModule);
        views.add(textView);
    }
}
