package com.codingame.game.View;

import com.codingame.game.Model.PlayerModel;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;

public class CardDeckView extends AbstractView {
    private Sprite back;
    private Group group;
    private Text numCards;

    private PlayerModel player;
    private final Vector2 pos;

    public CardDeckView(GraphicEntityModule entityModule, PlayerModel player) {
        super(entityModule);
        this.player = player;
        pos = new Vector2(Constants.DECK_POSITIONS.get(player.id));
        player.addObserver(this);

        createCardDeckView();
    }

    private void createCardDeckView() {
        back = entityModule.createSprite()
                .setImage(String.format("cardBack_%d.png", player.id))
                .setBaseWidth(Constants.CARD_SIZE)
                .setBaseHeight(Constants.CARD_SIZE)
                .setAnchor(0.5)
                .setZIndex(0);
        numCards = entityModule.createText("")
                .setFillColor(0x000000)
                .setFontSize(60)
                .setAnchor(0.5)
                .setZIndex(1);
        group = entityModule.createGroup()
                .setScale(1)
                .setX(0)
                .setY(0)
                .setZIndex(0);
        group.add(back, numCards);
        group.setX(pos.getX()).setY(pos.getY());
    }

    public void updateView() {
        numCards.setText(String.valueOf(player.getNumDeckCards()));
        entityModule.commitEntityState(0, numCards);
    }
}
