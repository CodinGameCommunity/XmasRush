import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Main {
    public static void main(String[] args) {

        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

        gameRunner.addAgent(Player1.class);
        gameRunner.addAgent(Player2.class);

        System.setProperty("league.level", "3");
        gameRunner.setSeed(20L);

        gameRunner.start();

    }
}
