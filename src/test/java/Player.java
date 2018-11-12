import java.util.Scanner;

public class Player {
    static class Item {
        public String name;
        public int x;
        public int y;
        public int playerId;
        Item(String name, int x, int y, int playerId) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.playerId = playerId;
        }
    }

    static class Tile {
        public String pattern;
        public Item item;
        Tile(String pattern) {
            this.pattern = pattern;
            this.item = null;
        }
        @Override
        public String toString() {
            String str = pattern;
            if (item != null) {
                str += "-" + item.name + item.playerId;
            }
            return str;
        }
    }

    static class Vector2 {
        int x;
        int y;
        Vector2(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getManhattanDist(Vector2 other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y);
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int boardWidth = in.nextInt();
        int boardHeight = in.nextInt();

        // game loop
        while (true) {
            Tile[][] tiles = new Tile[boardWidth][boardHeight];
            Tile myTile = null;
            Tile oppTile = null;
            for (int i = 0; i < boardHeight; i++) {
                for (int j = 0; j < boardWidth; j++) {
                    String tile = in.next();
                    tiles[j][i] = new Tile(tile);
                    System.err.print(tile + " ");
                }
                System.err.println();
            }
            for (int i = 0; i < 2; i++) {
                int numPlayerCards = in.nextInt(); // the number of cards in the stack for each player
                int playerX = in.nextInt();
                int playerY = in.nextInt();
                String playerTile = in.next();
                if (i == 0) {
                    myTile = new Tile(playerTile);
                }
                System.err.println(numPlayerCards + " " + playerX + "," + playerY + " " + playerTile);
            }
            int numItems = in.nextInt(); // the total number of items available on board and on player tiles (does not include quest cards)
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlayerId = in.nextInt();
                Item item = new Item(itemName, itemX, itemY, itemPlayerId);
                if (itemX == -1 && itemY == -1) {
                    myTile.item = item;
                } else if (itemX == -2 && itemY == -2) {
                    oppTile.item = item;
                } else {
                    tiles[itemX][itemY].item = item;
                }
                System.err.println(itemName + itemPlayerId + " " + itemX + "," + itemY);
            }
            int turnType = in.nextInt();
            int numQuests = in.nextInt(); // the total number of available quest cards for both players
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
                System.err.println(questItemName + questPlayerId);
            }

            System.err.println("Debug Map");
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    System.err.print(tiles[j][i] + " ");
                }
                System.err.println();
            }
        }
    }
}
