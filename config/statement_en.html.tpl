<!-- LEAGUES level1 level2 level3 -->

<div id="statement_back" class="statement_back" style="display: none"></div>
<div class="statement-body">
    <!-- GOAL -->
    <div class="statement-section statement-goal">
        <h2>
            <span class="icon icon-goal">&nbsp;</span> <span>The Goal</span>
        </h2>
        <div class="statement-goal-content">
            Make your way to the items on the board and be the first to complete the quest cards to win!
        </div>
    </div>
    <!-- PROTOCOL -->
    <div class="statement-section statement-protocol">
        <!-- Protocol block -->
        <div class="blk">
            <div class="title">Output for one game turn</div>
            <span class="statement-lineno">One line</span>: the action of your agent that can be one of these:<br/>
            <div class="title">Output for a PUSH turn:</div>
            <div class="text">
                <ul style="padding-top: 0; padding-bottom: 0;">
                    <li><action>PUSH id direction</action> where <b>id</b> can be <action>1</action>, <action>3</action> or <action>5</action> and <b>direction</b> <action>UP</action>, <action>DOWN</action>, <action>LEFT</action> or <action>RIGHT</action></li>
                </ul>
                Example: <action>PUSH 3 UP</action>
            </div>
            <div class="title">Output for a MOVE turn:</div>
            <div class="text">
                <ul style="padding-top: 0; padding-bottom: 0;">
                    <li><action>MOVE direction</action> where <b>direction</b> can be <action>UP</action>, <action>DOWN</action>, <action>LEFT</action> or <action>RIGHT</action></li>
                    <li><action>PASS</action> to skip movement</li>
                </ul>
                <action>MOVE</action> commands support up to 20 directions separated by spaces like <action>MOVE direction direction direction</action><br/>
                Example: <action>MOVE DOWN</action> or <action>MOVE LEFT UP RIGHT</action> or <action>PASS</action></li>
            </div>
        </div>
    </div>
</div>
