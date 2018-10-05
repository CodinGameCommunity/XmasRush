import java.util.Scanner;

public class Player2 {
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
            int numItems = in.nextInt(); // the total number of items available on board and on player tiles (does not include quest cards)
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlayerId = in.nextInt();
                System.err.println(itemName + itemPlayerId + " " + itemX + "," + itemY);
            }
            int turnType = in.nextInt();
            for (int i = 0; i < 2; i++) {
                int numPlayerCards = in.nextInt(); // the number of cards in the stack for each player
                int playerX = in.nextInt();
                int playerY = in.nextInt();
                String playerTile = in.next();
                System.err.println(numPlayerCards + " " + playerX + "," + playerY + " " + playerTile);
            }
            int numQuests = in.nextInt(); // the total number of available quest cards for both players
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
                System.err.println(questItemName + questPlayerId);
            }

            if (turn == 0) System.out.println("PUSH 1 UP");
            if (turn == 1) System.out.println("MOVE LEFT");
            if (turn == 2) System.out.println("PUSH 5 UP");
            if (turn == 3) System.out.println("MOVE RIGHT");
            if (turn == 4) System.out.println("PUSH 5 UP");
            if (turn == 5) System.out.println("MOVE DOWN");
            if (turn == 6) System.out.println("PUSH 5 UP");
            turn++;
        }
    }
}
