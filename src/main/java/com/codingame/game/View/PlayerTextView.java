package com.codingame.game.View;

import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.GraphicEntityModule;

import java.util.Arrays;
import java.util.List;

public class PlayerTextView extends AbstractView{
    //todo
    private final int PLAYER_AVATAR_POS_Y = 880;
    private final int OPPONENT_AVATAR_POS_Y = Constants.SCREEN_HEIGHT - PLAYER_AVATAR_POS_Y + 50;
    private List<Vector2> AVATAR_POS = Arrays.asList(new Vector2(Constants.PLAYER_INFO_POS_X, PLAYER_AVATAR_POS_Y),
            new Vector2(Constants.OPPONENT_INFO_POS_X, OPPONENT_AVATAR_POS_Y));
    private final int AVATAR_SIZE = 128;
    private final int PLAYER_NAME_OFFSET_Y = 40;

    private Player player;
    private Vector2 pos;

    public PlayerTextView(GraphicEntityModule entityModule, Player player){
        super(entityModule);
        this.player = player;
        this.pos = AVATAR_POS.get(player.getIndex());

        createPlayerText();
    }

    private void createPlayerText() {
        entityModule.createSprite()
                .setX(pos.getX())
                .setY(pos.getY())
                .setZIndex(1)
                .setImage(player.getAvatarToken())
                .setBaseWidth(AVATAR_SIZE)
                .setBaseHeight(AVATAR_SIZE)
                .setAnchorX(0.5)
                .setAnchorY(1);

        entityModule.createText(player.getNicknameToken())
                .setX(pos.getX())
                .setY(pos.getY() + PLAYER_NAME_OFFSET_Y)
                .setFillColor(player.getColorToken())
                .setFontSize(40)
                .setFontFamily("Arial Black")
                .setAnchor(0.5);
    }

    public void updateView() {}
}
