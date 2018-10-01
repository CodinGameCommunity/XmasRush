import java.util.Scanner;

public class Player2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int turn = 0;
        
        while (true) {
            for (int i = 0; i < 7; i++) {
                String line = scanner.nextLine();
                System.err.println(line);
            }
            int numPlayerCards = scanner.nextInt();
            String questItemId = scanner.next();
            int numOpponentCards = scanner.nextInt();
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
            System.err.println(numPlayerCards + " " + questItemId + " " + numOpponentCards);
            System.err.println(playerX + " " + playerY + " " + opponentX + " " + opponentY);
            System.err.println(playerTile + " " + opponentTile);

            if (turn == 0) System.out.println("PUSH 1 LEFT");
            if (turn == 1) System.out.println("MOVE UP");
            if (turn == 2) System.out.println("PUSH 5 LEFT");
            if (turn == 3) System.out.println("MOVE DOWN");
            if (turn == 4) System.out.println("PUSH 5 LEFT");
            if (turn == 5) System.out.println("MOVE RIGHT");
            if (turn == 6) System.out.println("PUSH 5 LEFT");
            turn++;
        }
    }
}
