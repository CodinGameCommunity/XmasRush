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
    </p>
    <span class="statement-league-alert-content">
      <!-- BEGIN level1 -->
      Wood leagues should be considered as a tutorial which lets players discover the different rules of the game. <br>
      In Bronze league, all rules will be unlocked and the real challenge will begin.
      <!-- END -->
      <!-- BEGIN level2 -->
      In Wood 1, players must complete <const>6</const> quests. At most <const>1</const> quest is revealed.
      <!-- END -->
      <!-- BEGIN level3 -->
      In Bronze, players must complete <const>12</const> quests. At most <const>3</const> quests are revealed.
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
      Make your way to the items on the board and be the first to complete your quests!
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
        The game is played by 2 players on a 7x7 board with 49 square tiles. The (0,0) coordinate corresponds to the top
        left corner.
      </p>
      <p>
        Each player has 1 tile which they can use to push a row or a column on the board, trying to make a path toward
        their quest items.
      </p>
      <p>
        <b>The board</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          The board contains square tiles with paths on them. A path can lead to one of the four directions
          (<const>UP</const>, <const>RIGHT</const>, <const>DOWN</const> and <const>LEFT</const>).
        </li>
        <li>
          Some tiles have items on them.
        </li>
      </ul>
      <p>
        <b>Quest</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          Each quest corresponds to an item on the board.
        </li>
        <li>
          To complete a quest, a player must move to the tile containing the corresponding item. The quest must be
          revealed to be able to complete it.
        </li>
          <!-- BEGIN level1 -->
        <li>
          For this league, each player has <const>1</const> quest to complete.
        </li>
          <!-- END -->
          <!-- BEGIN level2 -->
        <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
            For this league, each player has <const>6</const> quests to complete. At most <const>1</const> quest is
            revealed.
          </li>
            <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
          When an item is collected, it is removed from the tile and the quest is marked as completed and removed
          from the player's quest deck. After the turn ends, a new quest is revealed (if available).
        </li>
          <!-- END -->
          <!-- BEGIN level3 -->
        <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
            For this league, each player has <const>12</const> quests to complete. At most <const>3</const> quests are
            revealed. They can be collected in any order.
          </li>
          <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
          A player can complete multiple quests in one turn.
        </li>
          <!-- END -->
          <!-- BEGIN level4 -->
          <li>
            For this league, each player has <const>12</const> quests to complete. At most <const>3</const> quests are
            revealed. They can be collected in any order.
          </li>
          <li>
          A player can complete multiple quests in one turn.
        </li>
          <!-- END -->
        <!-- BEGIN level3 level4 -->
        <li>
          When an item is collected, it is removed from the tile and the quest is marked as completed and removed
          from the player's quest deck. After the turn ends, a new quest is revealed (if available).
        </li>
        <!-- END -->
        <!-- BEGIN level3 -->
        <!-- END -->
      </ul>
      <p>
        <b>The game turns</b>
      </p>
      <p>
        Each game turn alternates between a <const>PUSH</const> turn and a <const>MOVE</const> turn.
        The first turn is always a <const>PUSH</const> turn.
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
          If both players push the same row or column, no matter the direction, nothing happens.
        </li>
        <li>
          If push commands intersect (one is horizontal and the other one vertical), the row is pushed first,
          followed by the column. Otherwise, they get pushed simultaneously.
        </li>
        <li>
          If a player is on a tile which gets pushed out of the map, the player is wrapped on the other end of the line.
        </li>
      </ul>
      <p>
        <b>Rules for moving</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          To allow a player to move between two adjacent tiles, the tiles' respective paths must connect to form a longer
          path. Moving to an adjacent tile counts as <const>1</const> step.
        </li>
        <li>
          Each player can move at most <const>20</const> steps during this turn via connected paths.
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
          <action>PASS</action>: to do nothing.
        </li>
      </ul>
      <p>
        A <action>MOVE</action> can contain up to <const>20</const> <var>directions</var>, each direction separated by a
        space <action>&nbsp;</action>.
      </p>
      <br>
      Note: You may toggle tile scenery on/off in the settings panel (<img style="opacity:.8;background:#20252a;" height="18" src="https://www.codingame.com/servlet/fileservlet?id=3463235186409" width="18">).
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
            You complete all your quests before your opponent.
          </li>
          <li>
            After <const>150</const> turns, you complete more quests than your opponent.
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
            You complete fewer quests than your opponent.
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
      <p>
        You can see the game's source code here: <a href="https://github.com/CodinGameCommunity/XmasRush">https://github.com/CodinGameCommunity/XmasRush</a>.
      </p>
      <p>
        <ul style="padding-top: 0;padding-bottom: 0;">
          <li>
            Players don't need to finish their turn on an item to collect it. Moving over it during a longer movement
            sequence is sufficient to complete revealed quests.
          </li>
          <li>
            An invalid move ends the current movement. Moving to a direction without a connected path in that direction
            is considered as invalid.
          </li>
          <li>
            It is possible to complete a quest during a push turn. If a push command warps a player onto a quest item,
            the quest, if revealed, is completed and another one is revealed at the end of the turn.
          </li>
        </ul>
      </p>
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
      <div class="title">Input for one game turn</div>
      <div class="text">
        <span class="statement-lineno">First line</span>: Integer <var>turnType</var>: the game turn type:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            <const>0</const>: a <const>PUSH</const> turn.
          </li>
          <li>
            <const>1</const>: a <const>MOVE</const> turn.
          </li>
        </ul>
        <span class="statement-lineno">Next <const>7</const> lines</span>: <const>7</const> space-separated strings
        representing each tile on a row, starting from the top. Each tile is represented by a 4 digit group, each digit
        corresponding to a directional path: up, right, down, left. <const>1</const> means the tile has a path for the
        respective direction, <const>0</const> means the tile doesn't.<br>
        <span class="statement-lineno">Next <const>2</const> lines</span>: for each player, <var>numPlayerCards</var>,
        <var>playerX</var>, <var>playerY</var>, <var>playerTile</var>:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            Integer <var>numPlayerCards</var>: the total number of quests for a player (hidden and revealed).
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
        <b>Note</b>: The player's input always comes <b>first</b>, the opponent's input comes <b>second</b>.
        <span class="statement-lineno">Next line</span>: Integer <var>numItems</var>: the total number of items
        available on board and on player tiles.<br>
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
        <span class="statement-lineno">Next line</span>: Integer <var>numQuests</var>: the total number of revealed quests
       for both players.<br>
        <span class="statement-lineno">Next <var>numQuests</var> lines</span>: <var>questItemName</var>,
        <var>questPlayerId</var>:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            <var>questItemName</var>: the item's name.
          </li>
          <li>
            <var>questPlayerId</var>: the id of the player the quest belongs to.
          </li>
        </ul>
        <b>Note</b>: The player's id is always <const>0</const> and the opponent's <const>1</const>.
      </div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title" style="padding-bottom: 0;">Output for one PUSH game turn</div>
      <div class="text">
        <ul style="margin-top: 0;margin-bottom: 0;padding-bottom: 0;">
          <li>
            <action>PUSH</action> <var>id</var> <var>direction</var> where <const>id</const> is between <action>0</action> 
            and <action>6</action>, and <const>direction</const> can be <action>UP</action>, <action>DOWN</action>,
            <action>LEFT</action> or <action>RIGHT</action>.
          </li>
        </ul>
        Example: <action>PUSH 3 UP</action> will push the third column upwards.
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
        Example: <action>MOVE LEFT UP RIGHT</action> will make the player move left, then up, then right.
      </div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Constraints</div>
      <div class="text">
        <var>board width</var> = <const>7</const><br>
        <var>board height</var> = <const>7</const><br>
        <!-- BEGIN level1 -->
        <var>numPlayerCards</var> = <const>1</const><br>
        <const>0</const> ≤ <var>numItems</var> ≤ <const>2</const><br>
        <const>0</const> ≤ <var>numQuests</var> ≤ <const>2</const><br>
        <!-- END -->
        <!-- BEGIN level2 -->
        <div style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
          <const>0</const> ≤ <var>numPlayerCards</var> ≤ <const>6</const><br>
          <const>0</const> ≤ <var>numItems</var> ≤ <const>12</const><br>
          <const>0</const> ≤ <var>numQuests</var> ≤ <const>2</const><br>
        </div>
        <!-- END -->
        <!-- BEGIN level3 -->
        <div style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
          <const>0</const> ≤ <var>numPlayerCards</var> ≤ <const>12</const><br>
          <const>0</const> ≤ <var>numItems</var> ≤ <const>24</const><br>
          <const>0</const> ≤ <var>numQuests</var> ≤ <const>6</const><br>
        </div>
        <!-- END -->
        <!-- BEGIN level4 -->
        <const>0</const> ≤ <var>numPlayerCards</var> ≤ <const>12</const><br>
        <const>0</const> ≤ <var>numItems</var> ≤ <const>24</const><br>
        <const>0</const> ≤ <var>numQuests</var> ≤ <const>6</const><br>
        <!-- END -->

        <br>
        Response time for the first turn ≤ <const>1</const>s<br>
        Response time per turn ≤ <const>50</const>ms<br>
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
        <li>In Wood 1, players must complete <const>6</const> quests. At most <const>1</const> quest is revealed.</li>
        <!-- END -->
        <!-- BEGIN level1 level2 -->
        <li>In Bronze, players must complete <const>12</const> quests. At most <const>3</const> quests are revealed.</li>
        <!-- END -->
      </ul>
    </p>
  </div>
  <!-- END -->

</div>
