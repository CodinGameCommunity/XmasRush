import com.codingame.gameengine.runner.MultiplayerGameRunner;
import com.codingame.gameengine.runner.dto.GameResult;

public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

            gameRunner.addAgent(PlayerAI.class);
            gameRunner.addAgent(EmptyAI.class);

            //required leagueLevel = 0
            System.setProperty("league.level", "0");
            gameRunner.setSeed(20L);
            GameResult result = gameRunner.simulate();
            System.out.println(String.format("Match %d: %d - %d", i, result.scores.get(0), result.scores.get(1)));
        }
        /*MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

        gameRunner.addAgent(PlayerAI.class);
        gameRunner.addAgent(EmptyAI.class);

        System.setProperty("league.level", "0");
        gameRunner.setSeed(20L);
        gameRunner.start();*/
    }
}
