import java.util.Scanner;

public class Player1 {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int boardWidth = in.nextInt();
        int boardHeight = in.nextInt();

        int turn = 0;
        // game loop
        while (true) {
            for (int i = 0; i < boardHeight; i++) {
                for (int j = 0; j < boardWidth; j++) {
                    String tile = in.next();
                    System.err.print(tile + " ");
                }
                System.err.println();
            }
            for (int i = 0; i < 2; i++) {
                int numPlayerCards = in.nextInt(); // the number of cards in the stack for each player
                int playerX = in.nextInt();
                int playerY = in.nextInt();
                String playerTile = in.next();
                System.err.println(numPlayerCards + " " + playerX + "," + playerY + " " + playerTile);
            }
            int numItems = in.nextInt(); // the total number of items available on board and on player tiles (does not include quest cards)
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlayerId = in.nextInt();
                System.err.println(itemName + itemPlayerId + " " + itemX + "," + itemY);
            }
            int turnType = in.nextInt();
            int numQuests = in.nextInt(); // the total number of available quest cards for both players
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
                System.err.println(questItemName + questPlayerId);
            }

            if (turn == 0) System.out.println("PUSH 1 RIGHT");
            if (turn == 1) System.out.println("MOVE RIGHT DOWN RIGHT RIGHT DOWN LEFT");
            if (turn == 2) System.out.println("PUSH 2 DOWN");
            if (turn == 3) System.out.println("PASS");
            if (turn == 4) System.out.println("PUSH 2 DOWN");
            if (turn == 5) System.out.println("MOVE LEFT UP RIGHT RIGHT RIGHT DOWN DOWN LEFT LEFT DOWN RIGHT RIGHT RIGHT");
            turn++;
        }
    }
}
