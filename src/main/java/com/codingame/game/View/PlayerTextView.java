package com.codingame.game.View;

import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;

import java.util.Arrays;
import java.util.List;

public class PlayerTextView extends AbstractView{
    //todo
    private final int PLAYER_TEXT_POS_X = 105;
    private final int PLAYER_TEXT_POS_Y = 800;
    private final int OPPONENT_TEXT_POS_X = Constants.SCREEN_WIDTH - PLAYER_TEXT_POS_X - 115;
    private final int OPPONENT_TEXT_POS_Y = Constants.SCREEN_HEIGHT - PLAYER_TEXT_POS_Y - 160;
    private List<Vector2> PLAYER_TEXT_POS = Arrays.asList(new Vector2(PLAYER_TEXT_POS_X, PLAYER_TEXT_POS_Y),
            new Vector2(OPPONENT_TEXT_POS_X, OPPONENT_TEXT_POS_Y));

    private Player player;
    private Vector2 pos;

    public PlayerTextView(GraphicEntityModule entityModule, Player player){
        super(entityModule);
        this.player = player;
        this.pos = PLAYER_TEXT_POS.get(player.getIndex());

        createPlayerText();
    }

    private void createPlayerText() {
        entityModule.createSprite()
                .setX(pos.getX())
                .setY(pos.getY())
                .setZIndex(1)
                .setImage(player.getAvatarToken());

        entityModule.createText(player.getNicknameToken())
                .setX(pos.getX() - 20)
                .setY(pos.getY() + 120)
                .setFillColor(player.getColorToken())
                .setScale(1.5)
                .setFontFamily("Arial Black");
    }

    public void updateView() {}
}
