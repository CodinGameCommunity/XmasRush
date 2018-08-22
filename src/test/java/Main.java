import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Main {
    public static void main(String[] args) {

        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

        // Adds as many player as you need to test your game
        gameRunner.addAgent(Player1.class);
        gameRunner.addAgent(Player2.class);

        // gameRunner.addAgent("python3 /home/user/player.py");

        gameRunner.start();
    }
}
