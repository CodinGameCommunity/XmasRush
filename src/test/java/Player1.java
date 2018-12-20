import java.util.Scanner;

public class Player1 {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int boardWidth = 7;
        int boardHeight = 7;

        int turn = 0;
        // game loop
        while (true) {

            int turnType = in.nextInt();
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
            int numQuests = in.nextInt(); // the total number of available quest cards for both players
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
                System.err.println(questItemName + questPlayerId);
            }

            //league 3
            if (turn == 0) System.out.println("PUSH 0 DOWN");
            if (turn == 1) System.out.println("MOVE RIGHT UP RIGHT RIGHT DOWN");
            if (turn == 2) System.out.println("PUSH 1 RIGHT");
            if (turn == 3) System.out.println("MOVE UP RIGHT RIGHT LEFT LEFT DOWN");
            if (turn == 4) System.out.println("PUSH 6 UP");
            if (turn == 5) System.out.println("MOVE DOWN DOWN DOWN DOWN LEFT DOWN RIGHT RIGHT LEFT LEFT UP RIGHT UP UP UP UP UP RIGHT RIGHT");
            if (turn == 6) System.out.println("PUSH 4 RIGHT");
            if (turn == 7) System.out.println("PASS");
            if (turn == 8) System.out.println("PUSH 6 DOWN");
            if (turn == 9) System.out.println("MOVE UP");
            if (turn == 10) System.out.println("PUSH 5 UP");
            if (turn == 11) System.out.println("PASS");
            if (turn == 12) System.out.println("PUSH 4 LEFT");
            if (turn == 13) System.out.println("PASS");
            if (turn == 14) System.out.println("PUSH 0 LEFT");
            if (turn == 15) System.out.println("MOVE RIGHT");
            if (turn == 16) System.out.println("PUSH 0 RIGHT");
            if (turn == 17) System.out.println("MOVE RIGHT");
            if (turn == 18) System.out.println("PUSH 0 RIGHT");
            if (turn == 19) System.out.println("MOVE DOWN RIGHT UP RIGHT DOWN DOWN");
            if (turn == 20) System.out.println("PUSH 2 RIGHT");
            if (turn == 21) System.out.println("MOVE DOWN DOWN UP UP");
            if (turn == 22) System.out.println("PUSH 2 LEFT");
            if (turn == 23) System.out.println("MOVE UP UP LEFT DOWN");
            turn++;
        }
    }
}
