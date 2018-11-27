package com.codingame.game.View;

import com.codingame.game.Model.PlayerModel;
import com.codingame.game.Player;
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
                .setScale(2)
                .setAnchor(0.5);
    }

    public void updateView(){
        setMapPos(sprite, model.getPos());
    }

    public Entity getEntity() {
        return sprite;
    }
}
