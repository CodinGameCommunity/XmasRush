import java.util.Scanner;

public class Player1 {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int boardWidth = in.nextInt();
        int boardHeight = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }

        int turn = 0;
        while (true) {
            for (int i = 0; i < boardHeight; i++) {
                String line = in.nextLine();
                System.err.println(line);
            }
            int numItems = in.nextInt();
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlayerId = in.nextInt();
                System.err.println(itemName + itemPlayerId + " " + itemX + "," + itemY);
            }
            int turnType = in.nextInt();
            for (int i = 0; i < 2; i++) {
                int numPlayerCards = in.nextInt();
                String questItemName = in.next();
                int playerX = in.nextInt();
                int playerY = in.nextInt();
                String playerTile = in.next();
            }
            in.nextLine();

            if (turn == 0) System.out.println("PUSH 1 LEFT");
            if (turn == 1) System.out.println("MOVE 1 RIGHT");
            if (turn == 2) System.out.println("PUSH 1 DOWN");
            if (turn == 3) System.out.println("MOVE 1 LEFT");
            if (turn == 4) System.out.println("PUSH 1 LEFT");
            if (turn == 5) System.out.println("MOVE 1 LEFT");
            if (turn == 6) System.out.println("PUSH 5 UP");
            if (turn == 7) System.out.println("MOVE 1 RIGHT");
            turn++;
        }
    }
}
