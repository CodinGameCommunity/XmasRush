import sys
import math
import random

random.seed('Help the Christmas elves fetch presents in a magical labyrinth!')

board_width, board_height = [7,7]


def approach(target_x, player_x, target_y, player_y):
    if target_x < player_x:
        return 'LEFT'
    elif target_x > player_x:
        return 'RIGHT'
    elif target_y < player_y:
        return 'UP'
    else:
        return 'DOWN'


spinner = {
    (-1, -1): 'RIGHT',
    (0, -1): 'RIGHT',
    (1, -1): 'DOWN',
    (1, 0): 'DOWN',
    (1, 1): 'LEFT',
    (0, 1): 'LEFT',
    (-1, 1): 'UP',
    (-1, 0): 'UP'
}
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
# game loop
while True:
    target_x = 0
    target_y = 0
    player_x = 0
    player_y = 0
    
    turn_type = int(input())
    board = {}
    for y in range(board_height):
        x=0
        for tile in input().split():
            temp =  Tile([x,y],tile)
            board[x,y] = Tile([x,y],tile)
            x += 1
    for i in range(2):
        # num_player_cards: the number of cards in the stack for each player
        num_player_cards, x, y, player_tile = input().split()
        num_player_cards = int(num_player_cards)
        x = int(x)
        y = int(y)
        if i == 0:
            player_x = x
            player_y = y
    print(x, y, file=sys.stderr)
    # the total number of items available on board and on player tiles (does not include quest cards)
    items = {}
    num_items = int(input())
    for i in range(num_items):
        item_name, item_x, item_y, item_player_id = input().split()
        # print(item_name, file=sys.stderr)
        item_x = int(item_x)
        item_y = int(item_y)
        item_player_id = int(item_player_id)
        items[item_name + str(item_player_id)] = (item_x, item_y)

    # the total number of available quest cards for both players
    num_quests = int(input())
    for i in range(num_quests):
        quest_item_name, quest_player_id = input().split()
        quest_player_id = int(quest_player_id)
        if quest_player_id == 0:
            target_x = items[quest_item_name + str(quest_player_id)][0]
            target_y = items[quest_item_name + str(quest_player_id)][1]

    if turn_type == 0:
        if target_x < 0:
            direction = random.choice(('RIGHT', 'UP', 'LEFT', 'DOWN'))
            to_push = random.randint(0, 6)
            print(f'PUSH {to_push} {direction}')
        else:
            diff = (target_x - player_x, target_y - player_y)
            if diff in spinner:
                direction = spinner[diff]
                to_push = target_y if (direction in (
                    'RIGHT', 'LEFT')) else target_x
                print(f'PUSH {to_push} {direction}')
            else:
                if abs(diff[0]) != 1 and player_y != target_y:
                    direction = 'LEFT' if player_x < target_x else 'RIGHT'
                    to_push = target_y
                    print(f'PUSH {to_push} {direction}')
                elif abs(diff[1]) != 1 and player_x != target_x:
                    direction = 'UP' if player_y < target_y else 'DOWN'
                    to_push = target_x
                    print(f'PUSH {to_push} {direction}')
                else:
                    direction = random.choice(('RIGHT', 'UP', 'LEFT', 'DOWN'))
                    to_push = random.randint(0, 6)
                    print(f'PUSH {to_push} {direction}')
    else:
        tcheby = max(abs(target_x - player_x), abs(target_y - player_y))
        manhat = abs(target_x - player_x) + abs(target_y - player_y)
        currTile = board[player_x,player_y]
        nextMove = approach(target_x, player_x, target_y, player_y)
        if (tcheby != 1 or manhat == 1) and nextMove in currTile.getAccessibleDirections(board):
            print("MOVE " + approach(target_x, player_x, target_y, player_y))
        else:
            print("PASS")
