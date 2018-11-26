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
        entityModule.createSprite()
                .setImage("background.jpg")
                .setBaseWidth(Constants.SCREEN_WIDTH)
                .setBaseHeight(Constants.SCREEN_HEIGHT)
                .setX(0)
                .setY(0)
                .setScale(1)
                .setAnchor(0)
                .setZIndex(-1);
    }

    public void createCardView(CardModel card) {
        views.add(board.createCardView(card));
    }

    public void createCardDeckView(Player player) {
        CardDeckView deckView = new CardDeckView(entityModule, player.getPlayer());
        views.add(deckView);
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
