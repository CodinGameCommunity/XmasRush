import com.codingame.gameengine.runner.MultiplayerGameRunner;
import com.codingame.gameengine.runner.dto.GameResult;

public class Main {
    public static void main(String[] args) {

        String league = "1";
        boolean useSeed = true;

        //runBatch(league, 100);
        runOnce(league, useSeed, -4277586949332252956L);
    }

    private static void runBatch(String league, int numRounds) {
        int score0 = 0;
        int score1 = 0;
        int score2 = 0;
        int score3 = 0;
        for (int i = 0; i < numRounds; i++) {
            MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

            gameRunner.addAgent(PlayerAStar.class);
            //gameRunner.addAgent("python.exe config/level1/Boss.py3");
            gameRunner.addAgent(EmptyAI.class);

            System.setProperty("league.level", league);

            GameResult result = gameRunner.simulate();
            System.out.println(String.format("Match %d (%d turns): %d-%d, %s", i, result.views.size(), result.scores.get(0),
                    result.scores.get(1), result.uinput.get(0).replace("\n", "")));
            switch (result.scores.get(0)) {
                case 0: score0++; break;
                case 1: score1++; break;
                case 2: score2++; break;
                case 3: score3++; break;
            }
            if (result.scores.get(0) < 0) {
                throw new RuntimeException("something fishy with score 0");
            }
            if (result.scores.get(1) < 0) {
                throw new RuntimeException("something fishy with score 1");
            }
        }
        System.out.println(String.format("0-0: %d, 1-0: %d, 2-0: %d, 3-0: %d", score0, score1, score2, score3));
    }

    private static void runOnce(String league, boolean useSeed, long seed) {
        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

        gameRunner.addAgent(PlayerAStar.class);
        //gameRunner.addAgent("python.exe config/level1/Boss.py3");
        gameRunner.addAgent(EmptyAI.class);

        System.setProperty("league.level", league);
        if (useSeed) {
            gameRunner.setSeed(seed);
        }

        gameRunner.start();
    }
}
