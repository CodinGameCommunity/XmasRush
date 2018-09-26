import java.util.Scanner;

class Player {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int boardWidth = scanner.nextInt();
        System.err.println(boardWidth);
        int boardHeight = scanner.nextInt();
        System.err.println(boardHeight);
        int numItems = scanner.nextInt();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        int turn = 0;
        while (true) {
            for (int i = 0; i < boardHeight; i++) {
                String line = scanner.nextLine();
                System.err.println(line);
            }
            for (int i = 0; i < numItems; i++) {
                String itemId = scanner.next();
                int itemPosX = scanner.nextInt();
                int itemPosY = scanner.nextInt();
                int itemPlayerId = scanner.nextInt();
                System.err.println(itemId + " " + itemPosX + " " + itemPosY + " " + itemPlayerId);
            }
            int turnType = scanner.nextInt();
            int numPlayerCards = scanner.nextInt();
            int numOpponentCards = scanner.nextInt();
            String questItemId = scanner.next();
            int playerX = scanner.nextInt();
            int playerY = scanner.nextInt();
            int opponentX = scanner.nextInt();
            int opponentY = scanner.nextInt();
            String playerTile = scanner.next();
            String opponentTile = scanner.next();
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // testing player input
            System.err.println(turnType + " " + numPlayerCards + " " + " " + numOpponentCards + " " + questItemId);
            System.err.println(playerX + " " + playerY + " " + opponentX + " " + opponentY);
            System.err.println(playerTile + " " + opponentTile);

            if (turn == 0) System.out.println("PUSH 1 LEFT");
            if (turn == 1) System.out.println("MOVE 1 UP");
            if (turn == 2) System.out.println("PUSH 5 LEFT");
            if (turn == 3) System.out.println("MOVE 1 DOWN");
            if (turn == 4) System.out.println("PUSH 5 LEFT");
            if (turn == 5) System.out.println("MOVE 1 RIGHT");
            if (turn == 6) System.out.println("PUSH 5 LEFT");
            turn++;
        }
    }
}
