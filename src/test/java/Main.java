import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Main {
    public static void main(String[] args) {

        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

        gameRunner.addAgent(PlayerAI.class);
        gameRunner.addAgent(EmptyAI.class);

        //required leagueLevel = 0
        System.setProperty("league.level", "0");
        //gameRunner.setSeed(20L);

        gameRunner.start();
    }
}
