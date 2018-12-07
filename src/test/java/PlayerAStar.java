import java.util.*;

public class PlayerAStar {
    static class Item {
        public String name;
        public int playerId;
        public Vector2 pos;
        Item(String name, int playerId, Vector2 pos) {
            this.name = name;
            this.playerId = playerId;
            this.pos = pos;
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
            return pattern.charAt(direction.asIndex()) == '1';
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
        public void sub(Vector2 other) {
            this.x -= other.x;
            this.y -= other.y;
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
        @Override
        public int hashCode() {
            return (37 + x) * 37 + y;
        }
    }

    static class Agent {
        public Vector2 pos;
        Agent(Vector2 pos) {
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
        private final int index;

        Direction(Vector2 vector, int index) {
            this.vector = vector;
            this.index = index;
        }
        public static Direction fromVector2(Vector2 pos) {
            if (pos.equals(Vector2.UP)) return Direction.UP;
            if (pos.equals(Vector2.DOWN)) return Direction.DOWN;
            if (pos.equals(Vector2.LEFT)) return Direction.LEFT;
            if (pos.equals(Vector2.RIGHT)) return Direction.RIGHT;
            return null;
        }
        public Vector2 asVector() {
            return vector;
        }
        public int asIndex() {
            return index;
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
    }

    private static int boardWidth;
    private static int boardHeight;
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        boardWidth = 7;
        boardHeight = 7;

        // game loop
        while (true) {
            Tile[][] tiles = new Tile[boardWidth][boardHeight];
            Agent myAgent = null;
            Agent oppAgent = null;
            Tile myTile = null;
            Tile oppTile = null;
            Vector<Card> myCards = new Vector<>();
            Vector<Card> oppCards = new Vector<>();
            Vector<Item> allItems = new Vector<>();
            int turnType = in.nextInt();
            PrintDebug(Integer.toString(turnType));
            for (int i = 0; i < boardHeight; i++) {
                for (int j = 0; j < boardWidth; j++) {
                    String tile = in.next();
                    tiles[j][i] = new Tile(tile);
                    tiles[j][i].pos = new Vector2(j, i);
                    System.err.print(tile + " ");
                }
                PrintDebug("");
            }
            for (int i = 0; i < 2; i++) {
                int numPlayerCards = in.nextInt(); // the total number of quests for a player (hidden and revealed)
                int playerX = in.nextInt();
                int playerY = in.nextInt();
                String playerTile = in.next();
                if (i == 0) {
                    myAgent = new Agent(new Vector2(playerX, playerY));
                    myTile = new Tile(playerTile);
                } else if (i == 1) {
                    oppAgent = new Agent(new Vector2(playerX, playerY));
                    oppTile = new Tile(playerTile);
                }
                PrintDebug(numPlayerCards + " " + playerX + " " + playerY + " " + playerTile);
            }
            int numItems = in.nextInt(); // the total number of items available on board and on player tiles
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlayerId = in.nextInt();
                Item item = new Item(itemName, itemPlayerId, new Vector2(itemX, itemY));
                allItems.add(item);
                if (itemX == -1 && itemY == -1) {
                    myTile.item = item;
                } else if (itemX == -2 && itemY == -2) {
                    oppTile.item = item;
                } else {
                    tiles[itemX][itemY].item = item;
                }
                PrintDebug(itemName + " " + itemX + " " + itemY + " " + itemPlayerId);
            }
            int numQuests = in.nextInt(); // the total number of revealed quests for both players
            PrintDebug(Integer.toString(numQuests));
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
                Card card = null;
                for (Item item : allItems) {
                    if (item.name.equals(questItemName) && item.playerId == questPlayerId) {
                        card = new Card(new Item(questItemName, questPlayerId, item.pos));
                    }
                }
                if (questPlayerId == 0) {
                    myCards.add(card);
                } else if (questPlayerId == 1) {
                    oppCards.add(card);
                }
                PrintDebug(questItemName + " " + questPlayerId);
            }

            Vector2 myQuestCardPos = myCards.get(myCards.size() - 1).item.pos;
            Vector2 oppQuestCardPos = oppCards.get(oppCards.size() - 1).item.pos;
            PrintDebug("Item is at pos " + myQuestCardPos);
            if (myQuestCardPos.equals(Vector2.MINUS_ONE) || myQuestCardPos.equals(Vector2.MINUS_TWO)) {
                PrintDebug("Item is inaccessible! :(");
                if (turnType == TurnType.PUSH.asValue()) {
                    // push opponent item row
                    if (isValidPush(oppQuestCardPos.y)) {
                        PrintPush(oppQuestCardPos.y, Direction.RIGHT);
                    } else {
                        PrintPush(0, Direction.RIGHT);
                    }
                } else {
                    PrintPass();
                }
                continue;
            }
            PrintDebug("Dist to item is " + myAgent.pos.getManhattanDist(myQuestCardPos));

            int xDiff = myQuestCardPos.x - myAgent.pos.x;
            int yDiff = myQuestCardPos.y - myAgent.pos.y;
            if (turnType == TurnType.PUSH.asValue()) {
                if (xDiff != 0) {
                    if (yDiff == 0) {
                        // push opponent item row
                        if (isValidPush(oppQuestCardPos.y)) {
                            PrintPush(oppQuestCardPos.y, Direction.RIGHT);
                        } else {
                            PrintPush(0, Direction.RIGHT);
                        }
                    } else {
                        if (isValidPush(myQuestCardPos.y)) {
                            PrintPush(myQuestCardPos.y, xDiff > 0 ? Direction.LEFT : Direction.RIGHT);
                        } else {
                            PrintPush(0, Direction.RIGHT);
                        }
                    }
                } else if (yDiff != 0) {
                    if (xDiff == 0) {
                        // push opponent item col
                        if (isValidPush(oppQuestCardPos.x)) {
                            PrintPush(oppQuestCardPos.x, Direction.DOWN);
                        } else {
                            PrintPush(0, Direction.DOWN);
                        }
                    } else {
                        if (isValidPush(myQuestCardPos.x)) {
                            PrintPush(myQuestCardPos.x, yDiff > 0 ? Direction.UP : Direction.DOWN);
                        } else {
                            PrintPush(0, Direction.DOWN);
                        }
                    }
                }
            } else if (turnType == TurnType.MOVE.asValue()) {
                HashMap<Vector2, Vector2> visitable = aStar(tiles, myAgent.pos, myQuestCardPos);
                PrintDebug("aStar size " + visitable.size());
                Iterator it = visitable.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry next = (Map.Entry)it.next();
                    PrintDebug("next " + next.getKey() + " " + next.getValue());
                }
                String move;
                if (visitable.containsKey(myQuestCardPos)) {
                    move = "MOVE " + reconstructPath(visitable, myAgent.pos, myQuestCardPos);
                } else if (!visitable.isEmpty()) {
                    final Vector2 questPos = myQuestCardPos;
                    Vector2 goal = Collections.min(visitable.keySet(), Comparator.comparing(pos -> pos.getManhattanDist(questPos)));
                    if (goal.equals(myAgent.pos)) {
                        move = "PASS";
                    } else {
                        move = "MOVE " + reconstructPath(visitable, myAgent.pos, goal);
                    }
                } else {
                    move = "PASS";
                }
                Print(move);
            }
        }
    }

    private static HashMap<Vector2, Vector2> aStar(Tile[][] tiles, Vector2 start, Vector2 end) {
        Queue<Vector2> frontier = new PriorityQueue<>(1, Vector2::getManhattanDist);
        frontier.add(start);
        HashMap<Vector2, Vector2> came_from = new HashMap<>();
        came_from.put(start, null);
        HashMap<Vector2, Integer> cost_so_far = new HashMap<>();
        cost_so_far.put(start, 0);

        while (!frontier.isEmpty()) {
            Vector2 current = frontier.poll();
            if (current.equals(end)) {
                return came_from;
            }
            List<Vector2> neighbors = getNeighbors(tiles, current);
            for (Vector2 next : neighbors) {
                int new_cost = cost_so_far.get(current) + current.getManhattanDist(next);
                if (new_cost <= 20 && (!cost_so_far.containsKey(next) || new_cost < cost_so_far.get(next))) {
                    cost_so_far.put(next, new_cost);
                    frontier.add(next);
                    came_from.put(next, current);
                }
            }
        }
        return came_from;
    }

    private static String reconstructPath(HashMap<Vector2, Vector2> came_from, Vector2 start, Vector2 end) {
        List<Vector2> path = new ArrayList<>();
        Vector2 current = end;
        while (!current.equals(start)) {
            path.add(current);
            current = came_from.get(current);
        }

        Collections.reverse(path);
        StringBuilder sb = new StringBuilder();
        Vector2 currPos = new Vector2(start);
        for (Vector2 pos : path) {
            Vector2 nextPos = new Vector2(pos);
            nextPos.sub(currPos);
            Direction dir = Direction.fromVector2(nextPos);
            sb.append(dir + " ");
            currPos = pos;
        }
        return sb.toString().trim();
    }

    private static List<Vector2> getNeighbors(Tile[][] tiles, Vector2 pos) {
        List<Vector2> neighbors = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            if (isValidMove(tiles, pos, dir)) {
                Vector2 nextPos = new Vector2(pos);
                nextPos.add(dir.asVector());
                neighbors.add(nextPos);
            }
        }
        return neighbors;
    }

    private static boolean isValidPush(int id) {
        return id >= 0 && id < boardWidth;
    }

    private static boolean isValidMove(Tile[][] tiles, Vector2 pos, Direction direction) {
        Vector2 newPos = new Vector2(pos);
        //PrintDebug("Trying to move " + direction);
        if (tiles[newPos.x][newPos.y].hasDirection(direction)) {
            newPos.add(direction.asVector());
            if (isValidPos(newPos) && tiles[newPos.x][newPos.y].hasDirection(direction.getOpposite())) {
                return true;
            }
        }
        //PrintDebug("No can do!");
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
        if (!isValidPush(id)) {
            throw new RuntimeException("trying to push at id " + id);
        }
        Print(String.format("PUSH %d %s", id, dir));
    }

    private static void PrintMove(Direction dir) {
        Print(String.format("MOVE %s", dir));
    }

    private static void PrintPass() {
        Print(String.format("PASS"));
    }
}
