import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Main {
    public static void main(String[] args) {

        final String AVATAR_TZUPY = "https://static.codingame.com/servlet/fileservlet?id=3633797551022&format=profile_avatar";
        final String AVATAR_RONICA = "https://static.codingame.com/servlet/fileservlet?id=23023322189851&format=profile_avatar";

        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

        gameRunner.addAgent(Player1.class, "Tzupy", AVATAR_TZUPY);
        gameRunner.addAgent(Player2.class, "rnkey", AVATAR_RONICA);

        System.setProperty("league.level", "3");
        gameRunner.setSeed(20L);

        gameRunner.start();

    }
}
