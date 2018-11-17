package com.codingame.game.View;

import com.codingame.game.Referee;
import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Text;


public class TurnTextView extends AbstractView {
    public static final int TURN_TEXT_POS_X = Constants.SCREEN_WIDTH - 50;
    public static final int TURN_TEXT_POS_Y = 100;

    private Text turnText;

    public TurnTextView(GraphicEntityModule entityModule){
        super(entityModule);

        createTurnText();
    }

    private void createTurnText() {
        turnText = entityModule.createText(String.format("Turn: %s", Referee.turnType))
                .setX(TURN_TEXT_POS_X)
                .setY(TURN_TEXT_POS_Y)
                .setFontSize(50)
                .setFillColor(0x000000)
                .setAnchorX(1)
                .setAnchorY(1);
        turnText.setVisible(false);
    }

    public void updateView() {
        turnText.setText(String.format("Turn: %s", Referee.turnType));
        if (Referee.turnType != null) turnText.setVisible(true);
        entityModule.commitEntityState(0, turnText);
    }
}
