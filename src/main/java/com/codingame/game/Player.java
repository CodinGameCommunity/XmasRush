package com.codingame.game;
import com.codingame.game.InputActions.AbstractAction;
import com.codingame.game.InputActions.InvalidAction;
import com.codingame.game.InputActions.PushAction;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends AbstractMultiplayerPlayer {
    private static final Pattern PLAYER_INPUT_PUSH_PATTERN = Pattern
            .compile("(?<pushAction>\\bpush\\b) (?<id>[1,3,5]) (?<direction>(\\bup\\b|\\bright\\b|\\bdown\\b|\\bleft\\b))",
                    Pattern.CASE_INSENSITIVE);

    // TODO: look at reference for pattern matcher: https://github.com/CodinGame/coders-of-the-caribbean/blob/0478489c0a3a18163b87a773d53902847bd98a81/Referee.java
    public AbstractAction getAction() throws TimeoutException, InvalidAction {
        try {
            String playerAction = this.getOutputs().get(0);
            Matcher matchMove = PLAYER_INPUT_PUSH_PATTERN.matcher(playerAction);
            if (matchMove.matches()) {
                return new PushAction(Integer.parseInt(matchMove.group("id")),
                        PushAction.PushDirection.valueOf(matchMove.group("direction")));
            } else {
                throw new InvalidAction("Invalid output.");
            }
        } catch (TimeoutException | InvalidAction e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidAction("Invalid output.");
        }
    }

    @Override
    public int getExpectedOutputLines() {
        // Returns the number of expected lines of outputs for a player
        return 1;
    }
}
