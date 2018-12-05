package com.codingame.game.View;

import com.codingame.game.Model.PlayerModel;
import com.codingame.game.Player;
import com.codingame.game.Referee;
import com.codingame.game.InputActions.Action;
import com.codingame.gameengine.module.entities.Entity;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;

public class PlayerView extends MovingView{
    private Sprite sprite;

    private Player player;
    private PlayerModel model;

    public PlayerView(GraphicEntityModule entityModule, Player player, PlayerModel model) {
        super(entityModule);
        this.player = player;
        this.model = model;
        model.addObserver(this);

        createPlayerView();
    }

    private void createPlayerView() {
        sprite = entityModule.createSprite()
                .setImage(String.format("elf_%d.png", player.getIndex()))
                .setAnchor(0.5);
    }

    public void updateView(){
        if (Referee.getTurnType() == Action.Type.PUSH) {
            entityModule.commitEntityState(0.3, sprite);
            setMapPos(sprite, model.getPos());
            entityModule.commitEntityState(0.6, sprite);
        } else
            setMapPos(sprite, model.getPos());
    }

    public Entity getEntity() {
        return sprite;
    }
}
