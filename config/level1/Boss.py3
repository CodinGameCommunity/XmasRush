import sys
import math

# Help the Christmas elves fetch presents in a magical labyrinth!

board_width, board_height = [7,7]
column = 0
row = 0
toggle = True
moveType = 0
moves = {
    'LEFT': [-1,0],
    'UP':[0,-1],
    'RIGHT':[1,0],
    'DOWN':[0,1]
}
oppositeMove = {
    'LEFT': 'RIGHT',
    'UP':'DOWN',
    'RIGHT':'LEFT',
    'DOWN':'UP'
}
def addPositions(pos1,pos2):
    return [pos1[0]+pos2[0],pos1[1]+pos2[1]]
class Tile:
    def __init__(self, pos, code):
        self.pos = pos
        self.availableMoves = self.transcribeCode(code)
    def transcribeCode(self,code):
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
    def getPathConnectedTiles(self,board):
        paths = {self:'STOP'}
        tovisit = self.getAccessibleTiles(board)
        while(len(tovisit)!=0):
            nexttiles = tovisit[0][1].getAccessibleTiles(board)
            if (len(nexttiles)!=0):
                for tile in nexttiles:
                    try :
                        paths[tile[1]]
                    except KeyError:
                        tovisit.append(tile)
            paths[tovisit[0][1]]=tovisit[0][0]
            tovisit.remove(tovisit[0])
        return paths    
    def getTileInDirection(self,board,direction):
        vect = moves.get(direction)
        pos = addPositions(self.pos,vect)
        return board[pos[0],pos[1]]
    def getAccessibleDirections(self,board):
        direc = []
        for move in self.availableMoves:
            vect = moves.get(move)
            dest = addPositions(self.pos,vect)
            try :
                if (oppositeMove.get(move) in board[dest[0],dest[1]].availableMoves):
                    direc.append(move)
            except KeyError:
                pass
        return direc
    def getAccessibleTiles(self,board):
        direc = []
        for move in self.availableMoves:
            vect = moves.get(move)
            dest = addPositions(self.pos,vect)
            try :
                if (oppositeMove.get(move) in board[dest[0],dest[1]].availableMoves):
                    direc.append([move,board[dest[0],dest[1]]])
            except KeyError:
                pass
        return direc
# game loop
def getPathTo(board,path,pos):
    directions = []
    currtile = None
    try:
        currtile = board[pos]
        directions.append(path[currtile])
    except KeyError:
        return False
    while True:
        currtile = currtile.getTileInDirection(board,oppositeMove[directions[len(directions)-1]])
        if (path.get(currtile) != 'STOP'):
            directions.append(path.get(currtile))
        else: 
            return list(reversed(directions))

while True:
    playerinfos = []
    quests = []
    items = []
    board = {}
    for y in range(board_height):
        x = 0
        for tile in input().split():
            temp =  Tile([x,y],tile)
            board[x,y] = Tile([x,y],tile)
            x += 1
    for i in range(2):
        # num_player_cards: the number of cards in the stack for each player
        num_player_cards, player_x, player_y, player_tile = input().split()
        num_player_cards = int(num_player_cards)
        player_x = int(player_x)
        player_y = int(player_y)
        playerinfos.append([num_player_cards, player_x, player_y, player_tile])
    num_items = int(input())  # the total number of items available on board and on player tiles (does not include quest cards)
    for i in range(num_items):
        item_name, item_x, item_y, item_player_id = input().split()
        item_x = int(item_x)
        item_y = int(item_y)
        item_player_id = int(item_player_id)
        if(item_player_id == 0):
            items.append([item_name, item_x, item_y])
    turn_type = int(input())
    num_quests = int(input())  # the total number of available quest cards for both players
    for i in range(num_quests):
        quest_item_name, quest_player_id = input().split()
        quest_player_id = int(quest_player_id)
        if (quest_player_id == 0):
            quests.append(quest_item_name)

    # Write an action using print
    ## To debug: print("Debug messages...", file=sys.stderr)
    
    myInfos = playerinfos[0]
    myHeroPos = [myInfos[1],myInfos[2]]
    myHeroTile = board[myHeroPos[0],myHeroPos[1]]
    goalPos = []
    for i in items:
        if(i[0] == quests[0]):
            goalPos = [i[1],i[2]]
    if(turn_type == 0):
        if(toggle):
            print("PUSH "+str(column) +" RIGHT")
            column = (column +1)%board_height
        else:
            print("PUSH "+str(row) +" DOWN")
            row = (row +1)%board_height
        toggle = not(toggle)
    # PUSH <id> <direction> | MOVE <direction> | PASS
    else :
        action = 'PASS'
        if (len(myHeroTile.getAccessibleDirections(board)) != 0):
            action = "MOVE " + myHeroTile.getAccessibleDirections(board)[moveType%len(myHeroTile.getAccessibleDirections(board))]
        for direct1,val1 in moves.items():
            if not(myHeroPos[0] + val1[0] == goalPos[0] and myHeroPos[1] + val1[1] == goalPos[1]):
                for direct2,val2 in moves.items():
                    if (myHeroPos[0] + val1[0] + val2[0] == goalPos[0] and myHeroPos[1] + val1[1] + val2[1] == goalPos[1]):
                        action = "MOVE " + direct1 + " " + direct2
        for direct in myHeroTile.getAccessibleDirections(board):
            if (myHeroTile.getTileInDirection(board,direct).pos == goalPos):
                action = "MOVE " + direct
        paths = myHeroTile.getPathConnectedTiles(board)
        path = getPathTo(board,paths,(goalPos[0],goalPos[1]))
        if path != False :
            action = 'MOVE ' + " ".join(path[:20])
        print(action)
        moveType = (moveType+1)%len(moves)