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
      The new wood1 rules are...
      <!-- END -->
      <!-- BEGIN level3 -->
      The new bronze rules are...
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

      This game is a two-player game...
      <ul style="padding-bottom: 0;">
        <li>
          This <constant>here</constant> is a constant
        </li>
        <li>
          This <action>here</action> is an output
        </li>
      </ul>

      This next line is invisible in wood2, highlighted in wood1 and normal in bronze+.
      <br>

      <!-- BEGIN level2 -->
      <span style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
        <!-- END -->


        <!-- BEGIN level2 level3 level4 -->
        Some rule.
        <!-- END -->

        <!-- BEGIN level2 -->
      </span>
      <!-- END -->


      <br>
      <strong>More info</strong>
      <ul style="padding-bottom: 0;">
        <li>
          The game...
        </li>
      </ul>
    </div>
  </div>

  <!-- Victory conditions -->
  <div class="statement-victory-conditions">
    <div class="icon victory"></div>
    <div class="blk">
      <div class="title">Victory Conditions</div>
      <div class="text">
        <ul style="padding-bottom: 0;">
          <li>
            Win the game.
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
        <ul style="padding-bottom: 0;">
          <li>
            Lose the game.
          </li>
          <li>
            You do not respond in time or output an unrecognized command.
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

      <!-- BEGIN level1 level2 -->
      Advanced details will be available in the Bronze league, as well as the game's source code for reference. <br>
      <br>
      <!-- END -->
      <!-- BEGIN level3 -->
      <div style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px; display:inline-block;">
        <!-- END -->

        <!-- BEGIN level3 level4 -->
        You can see the game's source code on <a href="https://github.com/CodinGameCommunity/team-6">https://github.com/CodinGameCommunity/team-6</a>.
        <br>
        <br>
        <strong>Complicated stuff</strong>
        <ul style="padding-bottom: 0;">
          <li>
            Complex rule 1
          </li>
          <li>
            Complex rule 2
          </li>
        </ul>

        <!-- END -->
        <!-- BEGIN level3 -->
      </div>
      <!-- END -->

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
        <span class="statement-lineno">Line 1:</span> two integers
        <var>boardWidth</var> and
        <var>boardHeight</var> for the size of the ...
      </div>
    </div>


    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Input for One Game Turn</div>
      <div class="text">
        ...
      </div>
    </div>


    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Output for One Game Turn</div>
      <div class="text">
        <span class="statement-lineno">One line</span>: the action of your agent that can be one of these:<br />


        <ul style="padding-top: 0; padding-bottom: 0;">
          <li>
            <action>PUSH id direction</action> where <b>id</b> can be <action>1</action>, <action>3</action> or <action>5</action>
            and <b>direction</b>
            <action>UP</action>, <action>DOWN</action>, <action>LEFT</action> or <action>RIGHT</action>
          </li>
        </ul>
        Example: <action>PUSH 3 UP</action>

        <br>

        <ul style="padding-top: 0; padding-bottom: 0;">
          <li>
            <action>MOVE direction</action> where <b>direction</b> can be <action>UP</action>, <action>DOWN</action>,
            <action>LEFT</action> or <action>RIGHT</action>
          </li>
          <li>
            <action>PASS</action> to skip movement
          </li>
        </ul>
        <action>MOVE</action> commands support up to 20 directions separated by spaces like <action>MOVE direction
          direction direction</action><br />
        Example: <action>MOVE DOWN</action> or <action>MOVE LEFT UP RIGHT</action> or <action>PASS</action>
        </li>

      </div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Constraints</div>
      <div class="text">
        <const>0</const> ≤ <var>cost</var> ≤ <const>12</const><br>
        <const>0</const> ≤ <b>creatures on one side of the board</b> ≤ <const>6</const><br>
        <const>0</const> ≤ <b>cards in hand</b> ≤ <const>8</const><br>
        <br>
        Response time for the first draft turn ≤ <const>1000</const>ms<br>
        Response time for the first battle turn ≤ <const>1000</const>ms<br>
        Response time per turn ≤ <const>100</const>ms<br>
      </div>
    </div>
  </div>
  <!-- BEGIN level1 level2 level3 -->
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
      <ul style="padding-bottom: 0;" class="statement-next-rules">
        <!-- END -->
        <!-- BEGIN level1 -->
        <li>In Wood 2, creatures can have abilities.</li>
        <!-- END -->
        <!-- BEGIN level1 level2 -->
        <li>In Wood 1, players can draft and play another type of cards: <const>Items</const>.</li>
        <!-- END -->
        <!-- BEGIN level1 level2 level3 -->
        <li>In Bronze, more abilities for creatures!</li>
        <!-- END -->
        <!-- BEGIN level1 level2 level3 -->
      </ul>
    </p>
  </div>
  <!-- END -->

</div>