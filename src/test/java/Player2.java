import java.util.Scanner;

public class Player2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int turn = 0;
        while (true) {
            String input = scanner.nextLine();
            switch(turn) {
                case 0: System.out.println("PUSH 3 DOWN"); break;
                case 1: System.out.println("MOVE 1 UP"); break;
                case 2: System.out.println("PUSH 5 LEFT"); break;
                case 3: System.out.println("MOVE 1 DOWN"); break;
                case 4: System.out.println("PUSH 5 LEFT"); break;
                case 5: System.out.println("MOVE 1 RIGHT"); break;
                case 6: System.out.println("PUSH 5 LEFT"); break;
            }
            turn++;
        }
    }
}
