<!-- LEAGUES level1 level2 level3 level4 -->
<div class="statement-body">
  <!-- BEGIN level1 level2 level3 -->
  <div style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 20px;
        margin-right: 15px;
        margin-left: 15px;
        margin-bottom: 10px;
        text-align: left;">
    <div style="text-align: center; margin-bottom: 6px">
      <img src="//cdn.codingame.com/smash-the-code/statement/league_wood_04.png" />
    </div>
    <p style="text-align: center; font-weight: 700; margin-bottom: 6px;">
      <!-- BEGIN level1 -->
      This is a <b>league based</b> challenge.
      <!-- END -->
      <!-- BEGIN level2 -->
      Welcome to the Wood1 league!
      <!-- END -->
      <!-- BEGIN level3 -->
      Welcome to the Bronze league!
      <!-- END -->
      <!-- BEGIN level2 level3 -->
      Summary of the new rules:
      <!-- END -->
    </p>
    <span class="statement-league-alert-content">
      <!-- BEGIN level1 -->
      Wood leagues should be considered as a tutorial which lets players discover the different rules of the game. <br>
      In Bronze league, all rules will be unlocked and the real challenge will begin.
      <!-- END -->
      <!-- BEGIN level2 -->
      In Wood 1, players have 6 quest cards with 1 visible one.
      <!-- END -->
      <!-- BEGIN level3 -->
      In Bronze, players have 12 quest cards with 3 visible ones.
      <!-- END -->
    </span>
  </div>
  <!-- END -->

  <!-- GOAL -->
  <div class="statement-section statement-goal">
    <h2>
      <span class="icon icon-goal">&nbsp;</span>
      <span>The Goal</span>
    </h2>
    <div class="statement-goal-content">
      Make your way to the items on the board and be the first to complete the quest cards to win!
    </div>
  </div>

  <!-- RULES -->
  <div class="statement-section statement-rules">
    <h2>
      <span class="icon icon-rules">&nbsp;</span>
      <span>Rules</span>
    </h2>
    <div class="statement-rules-content">
      <p>
        The game is played by 2 players on a 7x7 board with 49 square tiles. The (0,0) coordinate is the top left
        corner.
      </p>
      <p>
        Each player has 1 tile which they can use to push rows or columns on the board, trying to make a path toward
        their quest items.
      </p>
      <p>
        <b>The board</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          The board contains square tiles with paths on them. Paths can lead to one or more directions (left, right,
          up or down). If a player is on a tile and wants to reach an adjacent tile, then the tile the player is on
          and the adjacent one need to have 2 opposite directions that connects them. For example, if the current tile
          has an <b>up</b> path, then the tile directly above it needs to have a <b>down</b> path to allow a player to
          move between them.
        </li>
        <li>
          Some tiles have items on them corresponding to the items on each player's quest cards.
        </li>
      </ul>
      <p>
        <b>The game turns</b>
      </p>
      <p>
        Each game turn alternates between a <const>PUSH</const> turn and a <const>MOVE</const> turn.
        The first turn is always <const>PUSH</const> turn.
      </p>
      <p>
        <b>Rules for pushing</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          Each player can choose to push any row or column on the board. Rows can only be pushed horizontally
          (<const>LEFT</const> or <const>RIGHT</const>), while columns can only be pushed vertically
          (<const>UP</const> or <const>DOWN</const>).
        </li>
        <li>
          If both players push the same row or column, no matter the direction, nothing will happen.
        </li>
        <li>
          If push commands intersect (one is horizontal and the other one vertical), rows will get pushed first,
          followed by columns. Otherwise they get pushed simultaneously.
        </li>
      </ul>
      <p>
        <b>Rules for moving</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          Each player can move at most <const>20</const> steps during this turn via connected paths. They can't move if
          a path is not connected with another one.
        </li>
        <li>
          If a player lands on a tile with an item that is also visible in their cards deck, they complete that card and
          they can continue moving.
        </li>
        <li>
          If a player is on a tile which gets pushed out of the map, the player will wrap on the other end of the line.
        </li>
      </ul>
      <p>
        <b>Actions</b>
      </p>
      Every <const>PUSH</const> turn the player must:
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          <action>PUSH</action> <var>id</var> <var>direction</var>: to push a row <const>id</const>
          (<const>direction</const> <action>LEFT</action> or <action>RIGHT</action>) or a column <const>id</const>
          (<const>direction</const> <action>UP</action> or <action>DOWN</action>).
        </li>
      </ul>
      Every <const>MOVE</const> turn the player must either:
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          <action>MOVE</action> <var>direction</var>: to move one step towards <const>direction</const>
          <action>LEFT</action>, <action>RIGHT</action>, <action>UP</action> or <action>DOWN</action>.
        </li>
        <li>
          <action>PASS</action>: to skip moving this turn.
        </li>
      </ul>
      <p>
        A <action>MOVE</action> can contain up to <const>20</const> <var>directions</var>, each direction separated by a
        space <action>&nbsp;</action>.
      </p>
      <p>
        <b>Quest cards</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <!-- BEGIN level1 -->
        <li>
          For this league, each player will have to collect one item on the board corresponding to their quest card (the
          item must match exactly the one on the quest card).
        </li>
        <!-- END -->
        <!-- BEGIN level2 -->
        <li>
          For this league, each player will have to collect 6 items on the board corresponding to their quest cards (the
          item must match exactly the one on their visible quest card). Players have only 1 visible quest card that can
          be collected at a time.
        </li>
        <!-- END -->
        <!-- BEGIN level3 level4 -->
        <li>
          For this league, each player will have to collect 12 items on the board corresponding to their quest cards
          (the items must match exactly the ones on their visible quest cards). Players have 3 visible quest cards that
          can be collected in any order.
        </li>
        <!-- END -->
        <!-- BEGIN level2 level3 level4 -->
        <li>
          When an item is collected, it is removed from the tile and the quest card is marked as completed and removed
          from the player's quest deck. After the turn ends, a new quest card will be flipped (if available).
        </li>
        <!-- END -->
        <!-- BEGIN level3 level4 -->
        <li>
          Players will be able to collect multiple items in the same turn if there are paths available to them (within
          <const>20</const> move steps).
        </li>
        <!-- END -->
        <!-- BEGIN level1 -->
        <li>
          Hidden quest cards will have a <const>0</const> on them in the viewer indicating there are none available.
        </li>
        <!-- END -->
        <!-- BEGIN level2 level3 level4 -->
        <li>
          Hidden quest cards will have a number on them in the viewer indicating how many are left in the deck.
        </li>
        <!-- END -->
      </ul>
    </div>
  </div>

  <!-- Victory conditions -->
  <div class="statement-victory-conditions">
    <div class="icon victory"></div>
    <div class="blk">
      <div class="title">Victory Conditions</div>
      <div class="text">
        <ul style="padding-top: 0;padding-bottom: 0;">
          <li>
            You complete all your quest cards.
          </li>
          <li>
            After <const>150</const> turns, you complete more quest cards than your opponent.
          </li>
        </ul>
      </div>
    </div>
  </div>

  <!-- Lose conditions -->
  <div class="statement-lose-conditions">
    <div class="icon lose"></div>
    <div class="blk">
      <div class="title">Loss Conditions</div>
      <div class="text">
        <ul style="padding-top: 0;padding-bottom: 0;">
          <li>
            Your program times out.
          </li>
          <li>
            Your program provides invalid output for the active turn type.
          </li>
          <li>
            You completed fewer quest cards than your opponent.
          </li>
        </ul>
      </div>
    </div>
  </div>
  <br>

  <!-- EXPERT RULES -->
  <div class="statement-section statement-expertrules">
    <h2>
      <span class="icon icon-expertrules">&nbsp;</span>
      <span>Advanced Details</span>
    </h2>
    <div class="statement-expert-rules-content">
      <div style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px; display:inline-block;">
        You can see the game's source code <a href="https://github.com/CodinGameCommunity/team-6">here</a>.
      </div>
    </div>
  </div>
  <!-- PROTOCOL -->
  <div class="statement-section statement-protocol">
    <h2>
      <span class="icon icon-protocol">&nbsp;</span>
      <span>Game Input</span>
    </h2>
    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Initialization Input</div>
      <div class="text">
        <span class="statement-lineno">Line 1</span>: two integers <var>boardWidth</var> and <var>boardHeight</var> for
        the size of the board.
      </div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Input for one game turn</div>
      <div class="text">
        <span class="statement-lineno">Next <var>boardHeight</var> lines</span>: <var>boardWidth</var> strings
        representing each tile on a row, starting from the top. Each tile is represented by a 4 digit group, each digit
        corresponding to a directional path: up, right, down, left. <const>1</const> means the tile has a path for the
        respective direction, <const>0</const> means the tile doesn't.<br>
        <span class="statement-lineno">Next <const>2</const> lines</span>: for each player, <var>numPlayerCards</var>,
        <var>playerX</var>, <var>playerY</var>, <var>playerTile</var>:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            Integer <var>numPlayerCards</var>: the total number of quest cards for a player (hidden and visible).
          </li>
          <li>
            Integer <var>playerX</var>: the player's <const>x</const> position on the board (the column).
          </li>
          <li>
            Integer <var>playerY</var>: the player's <const>y</const> position on the board (the row).
          </li>
          <li>
            String <var>playerTile</var>: the player's tile in 4 digit format.
          </li>
        </ul>
        <span class="statement-lineno">Next line</span>: Integer <var>numItems</var>: the total number of items
        available on board and on player tiles (does not include quest cards).<br>
        <span class="statement-lineno">Next <var>numItems</var> lines</span>: <var>itemName</var>, <var>itemX</var>,
        <var>itemY</var>, <var>itemPlayerId</var>:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            Integer <var>itemName</var>: the item's name.
          </li>
          <li>
            Integer <var>itemX</var>: the item's <const>x</const> position on the board (the column).
          </li>
          <li>
            Integer <var>itemY</var>: the item's <const>y</const> position on the board (the row).
          </li>
          <li>
            String <var>itemPlayerId</var>: the id of the player the item belongs to.
          </li>
        </ul>
        <b>Note</b>: If an item is on a player's tile, <var>itemX</var> and <var>itemY</var> will both be
        <const>-1</const> for the player and <const>-2</const> for the opponent.
        <span class="statement-lineno">Next line</span>: Integer <var>turnType</var>: the game turn type:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            <const>0</const>: a <const>PUSH</const> turn.
          </li>
          <li>
            <const>1</const>: a <const>MOVE</const> turn.
          </li>
        </ul>
        <span class="statement-lineno">Next line</span>: Integer <var>numQuests</var>: the total number of visible quest
        cards for both players.<br>
        <span class="statement-lineno">Next <var>numQuests</var> lines</span>: <var>questItemName</var>,
        <var>questPlayerId</var>:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            <const>questItemName</const>: the item's name.
          </li>
          <li>
            <const>questPlayerId</const>: the id of the player the card belongs to.
          </li>
        </ul>
        <b>Note</b>: The player's input always comes <b>first</b>, the opponent's input comes <b>second</b>. Therefore,
        the player's input will always have id <b>0</b> and the opponent's <b>1</b>.
      </div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title" style="padding-bottom: 0;">Output for one PUSH game turn</div>
      <div class="text">
        <ul style="margin-top: 0;margin-bottom: 0;padding-bottom: 0;">
          <li>
            <action>PUSH</action> <var>id</var> <var>direction</var> where <const>id</const> can be <action>0</action>,
            <action>1</action>, <action>2</action>, <action>3</action>, <action>4</action>, <action>5</action> or
            <action>6</action> and <const>direction</const> <action>UP</action>, <action>DOWN</action>,
            <action>LEFT</action> or <action>RIGHT</action>.
          </li>
        </ul>
        Example: <action>PUSH 3 UP</action>.
      </div>
      <div class="title" style="padding-bottom: 0;">Output for one MOVE game turn</div>
	  <div class="text">
        <ul style="margin-top: 0;margin-bottom: 0;padding-bottom: 0;">
          <li>
            <action>MOVE</action> <var>direction</var> where <const>direction</const> can be <action>UP</action>,
            <action>DOWN</action>, <action>LEFT</action> or <action>RIGHT</action>.
          </li>
          <li>
            <action>PASS</action> to skip moving this turn.
          </li>
        </ul>
        A <action>MOVE</action> can contain up to <const>20</const> <var>directions</var>, each direction separated by a
        space <action>&nbsp;</action>.<br>
        Example: <action>MOVE DOWN</action> or <action>MOVE LEFT UP RIGHT</action> or <action>PASS</action>.
      </div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Constraints</div>
      <div class="text">
        <var>boardWidth</var> = <const>7</const><br>
        <var>boardHeight</var> = <const>7</const><br>
        <!-- BEGIN level1 -->
        <var>numPlayerCards</var> = <const>1</const><br>
        <const>0</const> ≤ <var>numItems</var> ≤ <const>2</const><br>
        <const>0</const> ≤ <var>numQuests</var> ≤ <const>2</const><br>
        <!-- END -->
        <!-- BEGIN level2 -->
        <const>0</const> ≤ <var>numPlayerCards</var> ≤ <const>6</const><br>
        <const>0</const> ≤ <var>numItems</var> ≤ <const>12</const><br>
        <const>0</const> ≤ <var>numQuests</var> ≤ <const>2</const><br>
        <!-- END -->
        <!-- BEGIN level3 level4 -->
        <const>0</const> ≤ <var>numPlayerCards</var> ≤ <const>12</const><br>
        <const>0</const> ≤ <var>numItems</var> ≤ <const>24</const><br>
        <const>0</const> ≤ <var>numQuests</var> ≤ <const>6</const><br>
        <!-- END -->
        <br>
        Response time per turn ≤ <const>100</const>ms<br>
      </div>
    </div>
  </div>
  <!-- BEGIN level1 level2 -->
  <div style="color: #7cc576;
      background-color: rgba(124, 197, 118,.1);
      padding: 20px;
      margin-top: 10px;
      text-align: left;">
    <div style="text-align: center; margin-bottom: 6px">
      <img src="//cdn.codingame.com/smash-the-code/statement/league_wood_04.png" />
    </div>
    <p style="text-align: center; font-weight: 700; margin-bottom: 6px;">
      What is in store in the higher leagues?
    </p>
    <p>
      The extra rules available in higher leagues are:
      <ul style="margin-top: 0;padding-bottom: 0;" class="statement-next-rules">
        <!-- BEGIN level1 -->
        <li>In Wood 1, players will have 6 quest cards with 1 visible one.</li>
        <!-- END -->
        <!-- BEGIN level1 level2 -->
        <li>In Bronze, players will have 12 quest cards with 3 visible ones.</li>
        <!-- END -->
      </ul>
    </p>
  </div>
  <!-- END -->

</div>