import java.util.Scanner;

public class Player1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int turn = 0;
        while (true) {
            String input = scanner.nextLine();
            switch(turn) {
                case 0: System.out.println("PUSH 1 LEFT"); break;
                case 1: System.out.println("MOVE 1 RIGHT"); break;
                case 2: System.out.println("PUSH 1 DOWN"); break;
                case 3: System.out.println("MOVE 1 LEFT"); break;
                case 4: System.out.println("PUSH 1 LEFT"); break;
                case 5: System.out.println("MOVE 1 LEFT"); break;
                case 6: System.out.println("PUSH 5 UP"); break;
                case 7: System.out.println("MOVE 1 RIGHT"); break;
            }
            turn++;
        }
    }
}
