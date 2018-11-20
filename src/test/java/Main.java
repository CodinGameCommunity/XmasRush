import com.codingame.gameengine.runner.MultiplayerGameRunner;
import com.codingame.gameengine.runner.dto.GameResult;

public class Main {
    public static void main(String[] args) {

        int score0 = 0;
        int score1 = 0;
        int score2 = 0;
        int score3 = 0;
        for (int i = 0; i < 100; i++) {
            MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

            gameRunner.addAgent(PlayerAI.class);
            gameRunner.addAgent(EmptyAI.class);

            //required leagueLevel = 0
            System.setProperty("league.level", "0");
            gameRunner.setSeed(20L);
            GameResult result = gameRunner.simulate();
            System.out.println(String.format("Match %d (%d): %d - %d", i, result.views.size(), result.scores.get(0), result.scores.get(1)));
            switch (result.scores.get(0)) {
                case 0: score0++; break;
                case 1: score1++; break;
                case 2: score2++; break;
                case 3: score3++; break;
            }
        }
        System.out.println(String.format("0-0: %d, 1-0: %d, 2-0: %d, 3-0: %d", score0, score1, score2, score3));
        /*MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

        gameRunner.addAgent(PlayerAI.class);
        gameRunner.addAgent(EmptyAI.class);

        System.setProperty("league.level", "0");
        gameRunner.setSeed(20L);
        gameRunner.start();*/
    }
}
