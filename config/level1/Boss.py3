import sys
import math
from collections import namedtuple
from collections import deque

Vector = namedtuple('Vector', ['x', 'y'])
Step = namedtuple('Step', ['direction', 'tile'])
Item = namedtuple('Item', ['name','position'])

boardWidth, boardHeight = 7, 7

# Here are my global variables, these keep their values between turns
column = 0
row = 0
toggle = True
moveType = 0

# I don't know if i'm player one or not yet
player = -1

# A dictionnary linking directions to their vectors
moves = {
    'LEFT': Vector(-1, 0),
    'UP': Vector(0, -1),
    'RIGHT': Vector(1, 0),
    'DOWN': Vector(0, 1)
}
# A dictionnary linking directions to their opposite
oppositeMove = {
    'LEFT': 'RIGHT',
    'UP': 'DOWN',
    'RIGHT': 'LEFT',
    'DOWN': 'UP'
}

# Here is a function allowing me to add two vectors
def addPositions(v1, v2):
    return Vector(v1.x + v2.x, v1.y + v2.y)

# Now we're going to create our tile object
class Tile:

    # This function allows us to pass parameters when creating the object
    def __init__(self, pos, code):
        self.pos = pos # the coordinates of the tile
        # the directions that you can take from this tile
        self.availableMoves = self.parseCode(code)

    # this function allows us to use the '0101' code of the tile and turn it into a list of directions
    def parseCode(self, code):
        moves = []
        if (code[0] == '1'):
            moves.append('UP')
        if (code[1] == '1'):
            moves.append('RIGHT')
        if (code[2] == '1'):
            moves.append('DOWN')
        if (code[3] == '1'):
            moves.append('LEFT')
        return moves
    # Returns the tile in direction direction
    def getTileInDirection(self, board, direction):
        vect = moves.get(direction)
        pos = addPositions(self.pos, vect)
        return board[pos]

    # Returns all the direction you can take from a tile
    def getAccessibleDirections(self, board):
        steps = self.getPossibleSteps(board)
        return list(map(lambda x: x.direction, steps))

    # Returns all the accessible tiles from this tile in one move
    def getPossibleSteps(self, board):
        direc = []
        for move in self.availableMoves:
            vect = moves.get(move)
            dest = addPositions(self.pos, vect)
            
            if dest in board and oppositeMove.get(move) in board[dest].availableMoves:
                direc.append(Step(move, board[dest]))
        
        return direc

    # Returns the path from this tile to the tile you want
    def findPath(self, board, pos):
        currentTile = self
        visited = {currentTile}
        toVisit = deque()
        toVisit.append((currentTile, []))
        while toVisit:
            currentTile, path = toVisit.popleft()
            if currentTile.pos == pos:
                return path
            else:
                for step in currentTile.getPossibleSteps(board):
                    if(step.tile not in visited):
                        toVisit.append((step.tile, path + [step.direction]))
                        visited.add(step.tile)


# game loop
while True:
    # Here are my local variables, these are reset at each turn
    myHeroPos = Vector(0,0)
    quests = []
    items = []

    board = {}  # The board is a dictionnary linking the position of a tile to the tile object

    # Here we get the type of turn : 0 for push and 1 for move
    turnType = int(input())

    # Here we create our representation of the board
    for y in range(boardHeight):
        x = 0
        for tile in input().split():
            temp = Tile([x, y], tile)
            board[Vector(x, y)] = Tile(Vector(x, y), tile)
            x += 1

    # Here we get our position (and ignore all the other infos)
    for i in range(2):
        numPlayerCards, playerX, playerY, playerTile = input().split()
        numPlayerCards = int(numPlayerCards)
        playerX = int(playerX)
        playerY = int(playerY)
        if(i == 0):
            myHeroPos = Vector(playerX, playerY)

    # Here we get the position of our items (and ignore the ones of the other player)
    numItems = int(input())
    for i in range(numItems):
        itemName, itemX, itemY, itemPlayerId = input().split()
        itemPlayerId = int(itemPlayerId)
        if(itemPlayerId == 0):
            items.append(Item(itemName, Vector(int(itemX), int(itemY))))

    # Here we get the list of the items we want (and ignore the ones of the other player)
    numQuests = int(input())
    for i in range(numQuests):
        questItemName, questPlayerId = input().split()
        questPlayerId = int(questPlayerId)
        if (questPlayerId == 0):
            quests.append(questItemName)

    myHeroTile = board[myHeroPos]
    goalPos = Vector(0,0)

    # Now we detect if we are player one or two (just to avoid having boring draws against ourself)
    if (player == -1):
        if(myHeroPos[0] == 0):
            player = 0
        else:
            player = 1
        toggle = player  # to avoid boring draws we start our push phase differently

    # We select the item we want to search
    for item in items:
        if(item.name == quests[0]):
            goalPos = item.position

    # If it's a push turn
    if(turnType == 0):
        # We will alternate between vertical and horizontal push to shuffle the grid
        # and change the line or the column we want to push at each push
        if(toggle):
            print("PUSH " + str(column) + " RIGHT")
            column = (column + 1) % boardHeight
        else:
            print("PUSH " + str(row) + " DOWN")
            row = (row + 1) % boardHeight
        toggle = not(toggle)

    # If it's a move turn
    else:
        # default value: we do nothing
        action = 'PASS'

        # if we can go somewhere select one of the available directions
        if (len(myHeroTile.getAccessibleDirections(board)) != 0):
            action = "MOVE " + myHeroTile.getAccessibleDirections(board)[moveType % len(myHeroTile.getAccessibleDirections(board))]

        # if we can go to our goal item go for it (but we don't want to be too strong so we don't go for more that 2 tiles at once)
        path = myHeroTile.findPath(board,goalPos)
        if path:
            action = 'MOVE ' + " ".join(path[:2])

        # output the move we chose
        print(action)

        # Change the index of the next direction we'll chose if we can go somewhere but not get item
        moveType = (moveType+1) % len(moves)