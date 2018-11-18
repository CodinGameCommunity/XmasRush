import java.util.Random;
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
        public boolean hasDirection(Direction direction) {
            return pattern.charAt(direction.asValue()) == '1';
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
        private static final Vector2 UP = new Vector2(0, -1);
        private static final Vector2 DOWN = new Vector2(0, 1);
        private static final Vector2 LEFT = new Vector2(-1, 0);
        private static final Vector2 RIGHT = new Vector2(1, 0);
        private static final Vector2 MINUS_ONE = new Vector2(-1, -1);
        private static final Vector2 MINUS_TWO = new Vector2(-2, -2);
        Vector2(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Vector2(Vector2 other) {
            this.x = other.x;
            this.y = other.y;
        }
        public void add(Vector2 other) {
            this.x += other.x;
            this.y += other.y;
        }
        public int getManhattanDist(Vector2 other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y);
        }
        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Vector2))
                return false;
            Vector2 other = (Vector2) obj;
            return this.x == other.x && this.y == other.y;
        }
    }

    static class Player {
        public Vector2 pos;
        Player(Vector2 pos) {
            this.pos = pos;
        }
    }

    enum TurnType {
        PUSH(0),
        MOVE(1);
        private int value;
        TurnType(int value) {
            this.value = value;
        }
        int asValue() {
            return value;
        }
    }

    public enum Direction {
        UP(Vector2.UP, 0),
        RIGHT(Vector2.RIGHT, 1),
        DOWN(Vector2.DOWN, 2),
        LEFT(Vector2.LEFT, 3);

        private final Vector2 vector;
        private final int value;

        Direction(Vector2 vector, int value) {
            this.vector = vector;
            this.value = value;
        }
        public Vector2 asVector() {
            return vector;
        }

        public int asValue() {
            return value;
        }

        public Direction getOpposite() {
            switch(this) {
                case UP: return DOWN;
                case DOWN: return UP;
                case LEFT: return RIGHT;
                case RIGHT: return LEFT;
            }
            return null;
        }

        public static Direction fromInt(int value) {
            switch(value) {
                case 0: return UP;
                case 1: return RIGHT;
                case 2: return DOWN;
                case 3: return LEFT;
            }
            return null;
        }
    }

    private static int boardWidth;
    private static int boardHeight;
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        boardWidth = in.nextInt();
        boardHeight = in.nextInt();
        Random random = new Random();

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
                    //PrintDebug(tile + " ");
                }
                //PrintDebug("");
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
                //PrintDebug(numPlayerCards + " " + playerX + "," + playerY + " " + playerTile);
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
                //PrintDebug(itemName + itemPlayerId + " " + itemX + "," + itemY);
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
                //PrintDebug(questItemName + questPlayerId);
            }

            PrintDebug("Debug Map");
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    System.err.print(tiles[j][i] + " ");
                }
                PrintDebug("");
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
                            break;
                        }
                    }
                }
            }
            PrintDebug("Item is at pos " + myQuestCardPos);
            if (myQuestCardPos.equals(Vector2.MINUS_ONE) || myQuestCardPos.equals(Vector2.MINUS_TWO)) {
                PrintDebug("Item is inaccessible! :(");
                if (turnType == TurnType.PUSH.asValue()) {
                    // push random row
                    PrintPush(random.nextInt(boardWidth), Direction.fromInt(random.nextInt(Direction.values().length)));
                } else {
                    PrintPass();
                }
                continue;
            }
            PrintDebug("Dist to item is " + myPlayer.pos.getManhattanDist(myQuestCardPos));

            int xDiff = myQuestCardPos.x - myPlayer.pos.x;
            int yDiff = myQuestCardPos.y - myPlayer.pos.y;
            PrintDebug("x diff " + xDiff);
            PrintDebug("y diff " + yDiff);
            if (turnType == TurnType.PUSH.asValue()) {
                if (xDiff != 0) {
                    if (yDiff == 0) {
                        // push random row
                        Direction[] directions  = {Direction.LEFT, Direction.RIGHT};
                        PrintPush(random.nextInt(boardWidth), Direction.fromInt(random.nextInt(directions.length)));
                    } else {
                        PrintPush(myQuestCardPos.y, xDiff > 0 ? Direction.LEFT : Direction.RIGHT);
                    }
                } else if (yDiff != 0) {
                    if (xDiff == 0) {
                        // push random col
                        Direction[] directions  = {Direction.UP, Direction.DOWN};
                        PrintPush(random.nextInt(boardHeight), Direction.fromInt(random.nextInt(directions.length)));
                    } else {
                        PrintPush(myQuestCardPos.x, yDiff > 0 ? Direction.UP : Direction.DOWN);
                    }
                }
            } else if (turnType == TurnType.MOVE.asValue()) {
                Direction xDir = xDiff > 0 ? Direction.RIGHT : Direction.LEFT;
                Direction yDir = yDiff > 0 ? Direction.DOWN : Direction.UP;
                if (xDiff != 0 && isValidMove(tiles, myPlayer.pos, xDir)) {
                    PrintMove(xDir);
                } else if (yDiff != 0 && isValidMove(tiles, myPlayer.pos, yDir)) {
                    PrintMove(yDir);
                } else {
                    PrintPass();
                }
            }
        }
    }

    private static boolean isValidMove(Tile[][] tiles, Vector2 pos, Direction direction) {
        Vector2 newPos = new Vector2(pos);
        PrintDebug("Trying to move " + direction);
        if (tiles[newPos.x][newPos.y].hasDirection(direction)) {
            newPos.add(direction.asVector());
            if (isValidPos(newPos) && tiles[newPos.x][newPos.y].hasDirection(direction.getOpposite())) {
                return true;
            }
        }
        PrintDebug("No can do!");
        return false;
    }

    private static boolean isValidPos(Vector2 pos) {
        return (pos.x >= 0 && pos.y >= 0 &&
                pos.x < boardWidth && pos.y < boardHeight);
    }

    private static void Print(String str) {
        System.out.println(str);
    }

    private static void PrintDebug(String str) {
        System.err.println(str);
    }

    private static void PrintPush(int id, Direction dir) {
        Print(String.format("PUSH %d %s", id, dir));
    }

    private static void PrintMove(Direction dir) {
        Print(String.format("MOVE %s", dir));
    }

    private static void PrintPass() {
        Print(String.format("PASS"));
    }
}
