import sys
import math

board_width, board_height = [7, 7]

# Here are my global variables, these keep their values between turns
column = 0
row = 0
toggle = True
moveType = 0

# I don't know if i'm player one or not yet
player = -1

# A dictionnary linking directions to their vectors
moves = {
    'LEFT': [-1, 0],
    'UP': [0, -1],
    'RIGHT': [1, 0],
    'DOWN': [0, 1]
}
# A dictionnary linking directions to their opposite
oppositeMove = {
    'LEFT': 'RIGHT',
    'UP': 'DOWN',
    'RIGHT': 'LEFT',
    'DOWN': 'UP'
}

# Here is a function allowing me to add two vectors
def addPositions(pos1, pos2):
    return [pos1[0]+pos2[0], pos1[1]+pos2[1]]

# Now we're going to create our tile object
class Tile:

    # This function allows us to pass parameters when creating the object
    def __init__(self, pos, code):
        self.pos = pos # the coordinates of the tile
        # the directions that you can take from this tile
        self.availableMoves = self.transcribeCode(code)

    # this function allows us to use the '0101' code of the tile and turn it into a list of directions
    def transcribeCode(self, code):
        tilemoves = []
        if (code[0] == '1'):
            tilemoves.append('UP')
        if (code[1] == '1'):
            tilemoves.append('RIGHT')
        if (code[2] == '1'):
            tilemoves.append('DOWN')
        if (code[3] == '1'):
            tilemoves.append('LEFT')
        return tilemoves

    # This function allows us to get every path to every reachable tile from the current tile
    def getPathConnectedTiles(self, board):
        paths = {self: 'STOP'}
        tovisit = self.getAccessibleTiles(board)
        while(len(tovisit) != 0):
            nexttiles = tovisit[0][1].getAccessibleTiles(board)
            if (len(nexttiles) != 0):
                for tile in nexttiles:
                    try:
                        paths[tile[1]]
                    except KeyError:
                        tovisit.append(tile)
            paths[tovisit[0][1]] = tovisit[0][0]
            tovisit.remove(tovisit[0])
        return paths

    # Returns the tile in direction direction
    def getTileInDirection(self, board, direction):
        vect = moves.get(direction)
        pos = addPositions(self.pos, vect)
        return board[pos[0], pos[1]]

    # Returns all the direction you can take from a tile
    def getAccessibleDirections(self, board):
        direc = []
        for move in self.availableMoves:
            vect = moves.get(move)
            dest = addPositions(self.pos, vect)
            try:
                if (oppositeMove.get(move) in board[dest[0], dest[1]].availableMoves):
                    direc.append(move)
            except KeyError:
                pass
        return direc

    # Returns all the accessible tiles from this tile in one move
    def getAccessibleTiles(self, board):
        direc = []
        for move in self.availableMoves:
            vect = moves.get(move)
            dest = addPositions(self.pos, vect)
            try:
                if (oppositeMove.get(move) in board[dest[0], dest[1]].availableMoves):
                    direc.append([move, board[dest[0], dest[1]]])
            except KeyError:
                pass
        return direc

# Takes the list of all the paths and gets you the one leading to the tile you want
def getPathTo(board, path, pos):
    directions = []
    currtile = None
    try:
        currtile = board[pos]
        directions.append(path[currtile])
    except KeyError:
        return []
    while True:
        currtile = currtile.getTileInDirection(
            board, oppositeMove[directions[len(directions)-1]])
        if (path.get(currtile) != 'STOP'):
            directions.append(path.get(currtile))
        else:
            return list(reversed(directions))


# game loop
while True:
    # Here are my local variables, these are reset at each turn
    playerinfos = []
    quests = []
    items = []

    board = {}  # The board is a dictionnary linking the position of a tile to the tile object

    # Here we get the type of turn : 0 for push and 1 for move
    turn_type = int(input())

    # Here we create our representation of the board
    for y in range(board_height):
        x = 0
        for tile in input().split():
            temp = Tile([x, y], tile)
            board[x, y] = Tile([x, y], tile)
            x += 1

    # Here we get the player infos
    for i in range(2):
        num_player_cards, player_x, player_y, player_tile = input().split()
        num_player_cards = int(num_player_cards)
        player_x = int(player_x)
        player_y = int(player_y)
        playerinfos.append([num_player_cards, player_x, player_y, player_tile])

    # Here we get the position of our items (and ignore the ones of the other player)
    num_items = int(input())
    for i in range(num_items):
        item_name, item_x, item_y, item_player_id = input().split()
        item_x = int(item_x)
        item_y = int(item_y)
        item_player_id = int(item_player_id)
        if(item_player_id == 0):
            items.append([item_name, item_x, item_y])

    # Here we get le list of the items we want (and ignore the ones of the other player)
    num_quests = int(input())
    for i in range(num_quests):
        quest_item_name, quest_player_id = input().split()
        quest_player_id = int(quest_player_id)
        if (quest_player_id == 0):
            quests.append(quest_item_name)

    # To debug: print("Debug messages...", file=sys.stderr)

    myInfos = playerinfos[0]
    myHeroPos = [myInfos[1], myInfos[2]]
    myHeroTile = board[myHeroPos[0], myHeroPos[1]]
    goalPos = []

    # Now we detect if we are player one or two (just to avoid having boring draws against ourself)
    if (player == -1):
        if(myHeroPos[0] == 0):
            player = 0
        else:
            player = 1
        toggle = player  # to avoid boring draws we start our push phase differently

    # We select the item we want to search
    for i in items:
        if(i[0] == quests[0]):
            goalPos = [i[1], i[2]]

    # If it's a push turn
    if(turn_type == 0):
        # We will alternate between vertical and horizontal push to shuffle the grid
        # and change the line or the column we want to push at each push
        if(toggle):
            print("PUSH "+str(column) + " RIGHT")
            column = (column + 1) % board_height
        else:
            print("PUSH "+str(row) + " DOWN")
            row = (row + 1) % board_height
        toggle = not(toggle)

    # If it's a move turn
    else:
        # default value: we do nothing
        action = 'PASS'

        # if we can go somewhere select one of the available directions
        if (len(myHeroTile.getAccessibleDirections(board)) != 0):
            action = "MOVE " + myHeroTile.getAccessibleDirections(
                board)[moveType % len(myHeroTile.getAccessibleDirections(board))]

        # if we can go to our goal item go for it (but we don't want to be too strong so we don't go for more that 2 tiles at once)
        paths = myHeroTile.getPathConnectedTiles(board)
        path = getPathTo(board, paths, (goalPos[0], goalPos[1]))
        if path != []:
            action = 'MOVE ' + " ".join(path[:2])

        # output the move we chose
        print(action)

        # Change the index of the next direction we'll chose if we can go somewhere but not get item
        moveType = (moveType+1) % len(moves)