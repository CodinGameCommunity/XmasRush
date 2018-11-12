import java.util.Scanner;

public class PlayerAI {
    static class Item {
        public String name;
        public int playerId;
        Item(String name, int playerId) {
            this.name = name;
            this.playerId = playerId;
        }
        @Override
        public String toString() {
            return name + playerId;
        }
    }

    static class Tile {
        public String pattern;
        public Item item;
        public Vector2 pos;
        Tile(String pattern) {
            this.pattern = pattern;
            this.item = null;
            this.pos = new Vector2(-1, -1);
        }
        public boolean hasItem(Item item) {
            return this.item != null && this.item.name.equals(item.name);
        }
        @Override
        public String toString() {
            String str = pattern;
            if (item != null) {
                str += "-" + item;
            }
            return str;
        }
    }

    static class Card {
        Item item;
        Card(Item item) {
            this.item = item;
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
        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    static class Player {
        public Vector2 pos;
        Player(Vector2 pos) {
            this.pos = pos;
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int boardWidth = in.nextInt();
        int boardHeight = in.nextInt();

        // game loop
        while (true) {
            Tile[][] tiles = new Tile[boardWidth][boardHeight];
            Player myPlayer = null;
            Player oppPlayer = null;
            Tile myTile = null;
            Tile oppTile = null;
            Card myCard = null;
            Card oppCard = null;
            for (int i = 0; i < boardHeight; i++) {
                for (int j = 0; j < boardWidth; j++) {
                    String tile = in.next();
                    tiles[j][i] = new Tile(tile);
                    tiles[j][i].pos = new Vector2(j, i);
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
                    myPlayer = new Player(new Vector2(playerX, playerY));
                    myTile = new Tile(playerTile);
                } else if (i == 1) {
                    oppPlayer = new Player(new Vector2(playerX, playerY));
                    oppTile = new Tile(playerTile);
                }
                System.err.println(numPlayerCards + " " + playerX + "," + playerY + " " + playerTile);
            }
            int numItems = in.nextInt(); // the total number of items available on board and on player tiles (does not include quest cards)
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlayerId = in.nextInt();
                Item item = new Item(itemName, itemPlayerId);
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
                if (questPlayerId == 0) {
                    myCard = new Card(new Item(questItemName, questPlayerId));
                } else if (questPlayerId == 1) {
                    oppCard = new Card(new Item(questItemName, questPlayerId));
                }
                System.err.println(questItemName + questPlayerId);
            }

            System.err.println("Debug Map");
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    System.err.print(tiles[j][i] + " ");
                }
                System.err.println();
            }

            Vector2 myQuestCardPos = null;
            if (myTile.hasItem(myCard.item)) {
                myQuestCardPos = myTile.pos;
            } else if (oppTile.hasItem(myCard.item)) {
                myQuestCardPos = oppTile.pos;
            } else {
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles[i].length; j++) {
                        if (tiles[i][j].item != null
                                && tiles[i][j].item.playerId == 0
                                && tiles[i][j].item.name.equals(myCard.item.name)) {
                            myQuestCardPos = tiles[i][j].pos;
                            System.err.println("Item is at pos " + myQuestCardPos);
                            break;
                        }
                    }
                }
            }
            System.err.println("Dist to item is " + myPlayer.pos.getManhattanDist(myQuestCardPos));
        }
    }
}
