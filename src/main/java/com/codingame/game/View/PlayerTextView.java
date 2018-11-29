package com.codingame.game.View;

import com.codingame.game.Player;
import com.codingame.game.Utils.Constants;
import com.codingame.game.Utils.Vector2;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;

import java.util.Arrays;
import java.util.List;

public class PlayerTextView extends AbstractView {
    private final int AVATAR_SIZE = Constants.TILE_SIZE;

    private final int PLAYER_INFO_POS_Y = Constants.SCREEN_HEIGHT - 230;
    private final int OPPONENT_INFO_POS_Y = Constants.SCREEN_HEIGHT - PLAYER_INFO_POS_Y;

    private List<Vector2> INFO_POS = Arrays.asList(new Vector2(Constants.PLAYER_DECK_POS_X, PLAYER_INFO_POS_Y),
            new Vector2(Constants.OPPONENT_DECK_POS_X, OPPONENT_INFO_POS_Y));

    private final int NAME_VERTICAL_OFFSET = 110;
    private List<Integer> BACKGROUND_HORIZONTAL_OFFSET = Arrays.asList(-15, -5);
    private int BACKGROUND_VERTICAL_OFFSET = 120;

    private Group group;
    private Text name;
    private Sprite avatar;
    private Sprite background;

    private Player player;
    private Vector2 pos;
    private int id;
    private int orientation;


    public PlayerTextView(GraphicEntityModule entityModule, Player player){
        super(entityModule);
        this.player = player;
        id = player.getIndex();
        pos = INFO_POS.get(id);
        orientation = this.player.getPlayer().orientation;

        createPlayerText();
    }

    private void createPlayerText() {
        avatar = entityModule.createSprite()
                .setX(0)
                .setY(0)
                .setZIndex(1)
                .setImage(player.getAvatarToken())
                .setBaseWidth(AVATAR_SIZE)
                .setBaseHeight(AVATAR_SIZE)
                .setAnchor(0.5);
        //reconsider the length param when changing font family or font size
        name = entityModule.createText(player.getNicknameToken())
                .setX(0)
                .setY(NAME_VERTICAL_OFFSET * orientation)
                .setZIndex(1)
                .setFillColor(player.getColorToken())
                .setFontSize(30)
                .setFontFamily("Arial Black")
                .setAnchor(0.5);
        background = entityModule.createSprite()
                .setImage(String.format("background_name_%d.png", id))
                .setX(BACKGROUND_HORIZONTAL_OFFSET.get(id))
                .setY(BACKGROUND_VERTICAL_OFFSET * orientation)
                .setZIndex(0)
                .setAnchor(0.5);
        group = entityModule.createGroup()
                .setX(pos.getX())
                .setY(pos.getY())
                .setScale(1);
        group.add(avatar, name, background);
    }

    public void updateView() {}
}
