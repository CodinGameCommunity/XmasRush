package com.codingame.game.View;

import com.codingame.game.Referee;
import com.codingame.game.Utils.Constants;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Text;

public class TurnTextView extends AbstractView {
    private final int TURN_TEXT_POS_X = Constants.SCREEN_WIDTH - 490;
    private final int NUM_TURNS_TEXT_POS_X = Constants.SCREEN_WIDTH - 180;
    private final int TYPE_TEXT_POS_X = Constants.SCREEN_WIDTH - 160;
    private final int TURN_TEXT_POS_Y = 70;

    //import it once
    private final int maxTurns = Constants.MAX_GAME_TURNS;

    private Text turnText;
    private Text typeText;
    private Text numTurnsText;


    public TurnTextView(GraphicEntityModule entityModule){
        super(entityModule);

        createTexts();
    }

    private void createTexts() {
        turnText = entityModule.createText("")
                .setX(TURN_TEXT_POS_X)
                .setY(TURN_TEXT_POS_Y)
                .setZIndex(2)
                .setFontSize(50)
                .setFillColor(0xF0EFEF)
                .setFontFamily("Arial")
                //aligned to the left
                .setAnchorX(0)
                .setAnchorY(1);
        numTurnsText = entityModule.createText("")
                .setX(NUM_TURNS_TEXT_POS_X)
                .setY(TURN_TEXT_POS_Y)
                .setZIndex(2)
                .setFontSize(50)
                .setFillColor(0xF0EFEF)
                .setFontFamily("Arial")
                //aligned to the right
                .setAnchorX(1)
                .setAnchorY(1);
        typeText = entityModule.createText("")
                .setX(TYPE_TEXT_POS_X)
                .setY(TURN_TEXT_POS_Y)
                .setZIndex(2)
                .setFontSize(50)
                .setFillColor(0xF0EFEF)
                .setFontFamily("Arial")
                //aligned to the left
                .setAnchorX(0)
                .setAnchorY(1);
    }

    public void updateView() {
        if (Referee.turnType != null){
            turnText.setText("Turn:");
            typeText.setText(Referee.turnType.toString());
            numTurnsText.setText(String.format("%d/%d", maxTurns - Referee.gameTurnsLeft, maxTurns));
        }
        entityModule.commitEntityState(0, turnText, numTurnsText, typeText);
    }
}
