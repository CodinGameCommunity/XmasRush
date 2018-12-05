import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Main {
    public static void main(String[] args) {

        final String AVATAR_TWINKLE = "https://static.codingame.com/servlet/fileservlet?id=23672527342224&format=profile_avatar";
        final String AVATAR_PIXIE = "https://static.codingame.com/servlet/fileservlet?id=23672566492606&format=profile_avatar";

        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

        gameRunner.addAgent(Player1.class, "Twinkle", AVATAR_TWINKLE);
        gameRunner.addAgent(Player2.class, "Pixie", AVATAR_PIXIE);

        System.setProperty("league.level", "3");
        gameRunner.setSeed(20L);

        gameRunner.start();

    }
}
