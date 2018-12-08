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
      Ce puzzle se déroule en <b>ligues</b>.
      <!-- END -->
      <!-- BEGIN level2 -->
      Bienvenue en ligue Bois 1 !
      <!-- END -->
      <!-- BEGIN level3 -->
      Bienvenue en ligue Bronze !
      <!-- END -->
    </p>
    <span class="statement-league-alert-content">
      <!-- BEGIN level1 -->
      Les ligues Bois doivent être considérées comme un tutoriel pour apprendre les différentes règles du jeu. <br>
      En ligue Bronze, toutes les règles sont débloquées et alors débute le challenge, le vrai.
      <!-- END -->
      <!-- BEGIN level2 -->
      En ligue Bois 1, chaque joueur doit terminer <const>6</const> quêtes. <const>1</const> quête au maximum est révélée.
      <!-- END -->
      <!-- BEGIN level3 -->
      En ligue Bronze, chaque joueur doit terminer <const>12</const> quêtes. <const>3</const> quêtes au maximum sont révélées.
      <!-- END -->
    </span>
  </div>
  <!-- END -->

  <!-- GOAL -->
  <div class="statement-section statement-goal">
    <h2>
      <span class="icon icon-goal">&nbsp;</span>
      <span>Objectif</span>
    </h2>
    <div class="statement-goal-content">
      Faites-vous un chemin vers les objets disséminés dans le labyrinthe et soyez le premier à terminer vos quêtes !
    </div>
  </div>

  <!-- RULES -->
  <div class="statement-section statement-rules">
    <h2>
      <span class="icon icon-rules">&nbsp;</span>
      <span>Règles du jeu</span>
    </h2>
    <div class="statement-rules-content">
      <p>
        Une partie est jouée par 2 joueurs sur un plateau 7x7 de 49 tuiles carrées. Les coordonnées (0,0) correspondent à la tuile dans le coin en haut à gauche.
      </p>
      <p>
        Chaque joueur possède une tuile qu'il utilise pour pousser une ligne ou une colonne du plateau, de façon à créer un chemin vers les objets de leurs quêtes.
      </p>
      <p>
        <b>Le plateau de jeu</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          Chaque tuile contient des chemins. Un chemin peut mener à 1 des 4 directions possibles :
          <const>UP</const> (vers le haut), <const>RIGHT</const> (vers la droite), <const>DOWN</const> (vers le bas) et <const>LEFT</const>(vers la gauche).
        </li>
        <li>
          Certaines tuiles contiennent des objets.
        </li>
      </ul>
      <p>
        <b>Les quêtes</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          Chaque quête correspond à un objet sur le plateau.
        </li>
        <li>
          Pour terminer une quête, un joueur doit se déplacer sur la tuile contenant l'objet correspondant. La quête doit être révélée pour qu'un joueur puisse la terminer.
        </li>
          <!-- BEGIN level1 -->
        <li>
          Dans cette ligue, chaque joueur n'a qu' <const>1</const> quête à terminer.
        </li>
          <!-- END -->
          <!-- BEGIN level2 -->
        <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
            Dans cette ligue, chaque joueur a <const>6</const> quêtes à terminer. <const>1</const> quête au maximum est révélée.
          </li>
            <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
          Quand un objet est récupéré par un joueur, l'objet est retiré du jeu et la quête est terminée.
          Après la fin du tour, une nouvelle quête est révélée (si disponible).
        </li>
          <!-- END -->
          <!-- BEGIN level3 -->
        <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
            Dans cette ligue, chaque joueur a <const>12</const> quêtes à terminer. <const>3</const> quête au maximum est révélée. Elles peuvent êtres terminées dans n'importe quel ordre.
          </li>
          <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
          Un joueur peut terminer plusieurs quêtes dans un même tour.
        </li>
          <!-- END -->
          <!-- BEGIN level4 -->
          <li>
            Dans cette ligue, chaque joueur a <const>12</const> quêtes à terminer. <const>3</const> quête au maximum est révélée. Elles peuvent êtres terminées dans n'importe quel ordre.
          </li>
          <li>
          Un joueur peut terminer plusieurs quêtes dans un même tour.
        </li>
          <!-- END -->
        <!-- BEGIN level3 level4 -->
        <li>
          Quand un objet est récupéré par un joueur, l'objet est retiré du jeu et la quête est terminée.
          Après la fin du tour, une nouvelle quête est révélée (si disponible).
        </li>
        <!-- END -->
        <!-- BEGIN level3 -->
        <!-- END -->
      </ul>
      <p>
        <b>Les différents tours de jeu</b>
      </p>
      <p>
        Il y a deux types de tours qui s'alternent l'un après l'autre : un tour de modification (<const>PUSH</const>) et un tour de déplacement (<const>MOVE</const>).
        Le premier tour de jeu est toujours un tour de modification (<const>PUSH</const>).
      </p>
      <p>
        <b>Règles pour modifier le labyrinthe</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          Chaque joueur peut choisir de pousser n'importe quelle ligne ou colonne du plateau. Les lignes sont poussées horizontalement
          (<const>LEFT</const> ou <const>RIGHT</const>), tandis que les colonnes sont poussées verticalement
          (<const>UP</const> ou <const>DOWN</const>).
        </li>
        <li>
          Si les deux joueurs choisissent de pousser la même ligne ou la même colonne, rien ne se passe.
        </li>
        <li>
          Si deux actions de modification s'intersectent (l'une horizontale et l'autre verticale) la ligne est poussée en premier, suivie par la colonne.
          Dans le cas contraire, les deux lignes ou deux colonnes sont poussées simultanément.
        </li>
        <li>
          Si un joueur est poussé hors du plateau de jeu, il se retrouve sur la tuile qui a servi à pousser la ligne ou la colonne.
        </li>
      </ul>
      <p>
        <b>Règles pour se déplacer</b>
      </p>
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          Pour qu'un joueur puisse se déplacer entre deux tuiles, les chemins des deux tuiles doivent se connecter pour en former un plus long.
          Se déplacer sur une tuile adjacente compte pour <const>1</const> pas.
        </li>
        <li>
          Chaque joueur peut se déplacer de <const>20</const> pas au maximum par tour.
        </li>
      </ul>
      <p>
        <b>Actions possibles</b>
      </p>
      Chaque tour de <const>PUSH</const>, le joueur doit :
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          <action>PUSH</action> <var>id</var> <var>direction</var>: pour modifier une ligne <const>id</const>
          (<const>direction</const> <action>LEFT</action> ou <action>RIGHT</action>) ou une colonne <const>id</const>
          (<const>direction</const> <action>UP</action> ou <action>DOWN</action>).
        </li>
      </ul>
      Chaque tour de <const>MOVE</const>, le joueur doit :
      <ul style="padding-top: 0;padding-bottom: 0;">
        <li>
          <action>MOVE</action> <var>direction</var>: pour se déplacer vers la <const>direction</const>
          <action>LEFT</action>, <action>RIGHT</action>, <action>UP</action> ou <action>DOWN</action>.
        </li>
        <li>
          <action>PASS</action>: pour ne rien faire et passer son tour.
        </li>
      </ul>
      <p>
        Une action <action>MOVE</action> doit contenir jusqu'à <const>20</const> <var>directions</var>, chaque direction séparée par un
        space <action>&nbsp;</action>.
      </p>
      <br>
      Note : Vous pouvez activer ou désactiver le décor tile dans les options de la vidéo (<img style="opacity:.8;background:#20252a;" height="18" src="https://www.codingame.com/servlet/fileservlet?id=3463235186409" width="18">).
    </div>
  </div>

  <!-- Victory conditions -->
  <div class="statement-victory-conditions">
    <div class="icon victory"></div>
    <div class="blk">
      <div class="title">Conditions de victoire</div>
      <div class="text">
        <ul style="padding-top: 0;padding-bottom: 0;">
          <li>
            Vous terminez toutes vos quêtes avant votre adversaire.
          </li>
          <li>
            Après <const>150</const> tours, vous terminez plus de quêtes que votre adversaire.
          </li>
        </ul>
      </div>
    </div>
  </div>

  <!-- Lose conditions -->
  <div class="statement-lose-conditions">
    <div class="icon lose"></div>
    <div class="blk">
      <div class="title">Conditions de défaite</div>
      <div class="text">
        <ul style="padding-top: 0;padding-bottom: 0;">
          <li>
            Votre programme ne répond pas dans le temps imparti.
          </li>
          <li>
            Votre programme répond avec une sortie invalide pour le type de tour.
          </li>
          <li>
            Vous terminez moins de quêtes que votre adversaire.
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
      <span>Détails de règles</span>
    </h2>
    <div class="statement-expert-rules-content">
      <p>
        Vous pouvez retrouver le code source du jeu ici&nbsp;: <a href="https://github.com/CodinGameCommunity/XmasRush">https://github.com/CodinGameCommunity/XmasRush</a>.
      </p>
      <p>
        <ul style="padding-top: 0;padding-bottom: 0;">
          <li>
            Les joueurs n'ont pas besoin de terminer leur tour sur un objet pour le récupérer.
            Se déplacer sur une tuile avec un objet pendant une séquence de mouvement est suffisant pour terminer une quête révélée.
          </li>
          <li>
            Une action de déplacement invalide termine le déplacement en cours. Est considérée comme invalide, une commande de déplacement vers une direction sans connection de chemins.
          </li>
          <li>
            Il est possible de terminer une quête pendant un tour de modification. Si la commande déplace le joueur hors du jeu puis sur la tuile utilisée qui contient l'objet d'une quête révélée, alors cette quête est terminée et une nouvelle est révélée à la fin du tour.
          </li>
        </ul>
      </p>
    </div>
  </div>
  <!-- PROTOCOL -->
  <div class="statement-section statement-protocol">
    <h2>
      <span class="icon icon-protocol">&nbsp;</span>
      <span>Protocole du jeu</span>
    </h2>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Entrée pour un tour de jeu</div>
      <div class="text">
          <span class="statement-lineno">Première ligne</span>: un entier <var>turnType</var> pour le type de tour de jeu
          <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            <const>0</const>: un tour de modification (<const>PUSH</const>).
          </li>
          <li>
            <const>1</const>: un tour de déplacement (<const>MOVE</const>).
          </li>
        </ul>
        <span class="statement-lineno">Les <const>7</const> lignes suivantes</span>: <const>7</const> chaînes de caractères
        representant chaque tuile d'une ligne each, en démarrant à partir du haut. Chaque tuile est représentée par 4 chiffres, chaque chiffre représentant une direction :
        en haut, à droite, en bas, à gauche. <const>1</const> signifie que la tuile contient un chemin vers cette direction, <const>0</const> qu'elle n'en contient pas.<br>
        <span class="statement-lineno">Les <const>2</const> lignes suivantes</span>: pour chaque joueur, <var>numPlayerCards</var>,
        <var>playerX</var>, <var>playerY</var>, <var>playerTile</var>:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            Un entier <var>numPlayerCards</var>: le nombre total de quêtes non terminées d'un joueur (cachée ou révélée).
          </li>
          <li>
            Un entier <var>playerX</var>: la position <const>x</const> du joueur sur le plateau (sa colonne).
          </li>
          <li>
            Un entier <var>playerY</var>: la position <const>y</const> du joueur sur le plateau (sa ligne).
          </li>
          <li>
            Une chaîne de caractères <var>playerTile</var>: la tuile du joueur dans le format à 4 chiffres.
          </li>
        </ul>
        <b>Note</b>: L'entrée du joueur est toujours donnée en <b>premier</b>, celle de son adversaire en <b>second</b>.<br>
        <span class="statement-lineno">Ligne suivante</span>: un entier <var>numItems</var> pour le nombre total d'objets disponibles sur le plateau et sur les tuiles des joueurs.<br>
        <span class="statement-lineno">Les <var>numItems</var> lignes suivantes</span>: <var>itemName</var>, <var>itemX</var>,
        <var>itemY</var>, <var>itemPlayerId</var>:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            Une chaîne de caractères <var>itemName</var>: le nom de l'objet.
          </li>
          <li>
            Un entier <var>itemX</var>: la position <const>x</const> de l'objet sur le plateau (sa colonne).
          </li>
          <li>
            Un entier <var>itemY</var>: la position <const>y</const> de l'objet sur le plateau (sa ligne).
          </li>
          <li>
            Un entier <var>itemPlayerId</var>: l'identifiant du joueur à qui l'objet appartient.
          </li>
        </ul>
        <b>Note</b>: Si un objet se trouve sur la tuile d'un joueur, <var>itemX</var> et <var>itemY</var> serons égaux à 
        <const>-1</const> pour ce joueur, et <const>-2</const> pour son adversaire.<br>
        <span class="statement-lineno">Ligne suivante</span>: un entier <var>numQuests</var> pour le nombre total de quêtes révélées pour les deux joueurs.<br>
        <span class="statement-lineno">Les <var>numQuests</var> lignes suivantes</span>: <var>questItemName</var>,
        <var>questPlayerId</var>:
        <ul style="margin-top: 0;padding-bottom: 0;">
          <li>
            Une chaîne de caractères <var>questItemName</var>: le nom de l'objet.
          </li>
          <li>
            Un entier <var>questPlayerId</var>: l'idientifiant du joueur à qui l'objet appartient.
          </li>
        </ul>
        <b>Note</b>: l'identifiant du joueur est toujours <const>0</const> et celui de son adversaire <const>1</const>.
      </div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title" style="padding-bottom: 0;">Sortie pour un tour de modification</div>
      <div class="text">
        <ul style="margin-top: 0;margin-bottom: 0;padding-bottom: 0;">
          <li>
            <action>PUSH</action> <var>id</var> <var>direction</var> où <const>id</const> est compris entre <action>0</action> 
            et <action>6</action>, et où <const>direction</const> vaut <action>UP</action>, <action>DOWN</action>,
            <action>LEFT</action> ou <action>RIGHT</action>.
          </li>
        </ul>
        Exemple: <action>PUSH 3 UP</action> poussera la troisième colonne vers le bas.
      </div>
      <div class="title" style="padding-bottom: 0;">Sortie pour un tour de déplacement</div>
    <div class="text">
        <ul style="margin-top: 0;margin-bottom: 0;padding-bottom: 0;">
          <li>
            <action>MOVE</action> <var>direction</var> où <const>direction</const> vaut <action>UP</action>,
            <action>DOWN</action>, <action>LEFT</action> ou <action>RIGHT</action>.
          </li>
          <li>
            <action>PASS</action> pour passer ton tour.
          </li>
        </ul>
        Une action de déplacement (<action>MOVE</action>) peut inclure jusqu'à <const>20</const> <var>directions</var>, les directions étant séparées par un
        espace <action>&nbsp;</action>.<br>
        Exemple: <action>MOVE LEFT UP RIGHT</action> déplacera le joueur à gauche, puis vers le haut, puis vers la droite.
      </div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Contraintes</div>
      <div class="text">
        <var>largeur du plateau</var> = <const>7</const><br>
        <var>hauteur du plateau</var> = <const>7</const><br>
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
        Temps de réponse pour le premier tour ≤ <const>1</const>s<br>
        Temps de réponse pour un tour de jeu ≤ <const>50</const>ms<br>
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
      Qu'est-ce qui vous attend dans les ligues supérieures ?
    </p>
    <p>
      Voici les règles supplémentaires à débloquer dans les ligues supérieures :
      <ul style="margin-top: 0;padding-bottom: 0;" class="statement-next-rules">
        <!-- BEGIN level1 -->
        <li>En ligue Bois 1, chaque joueur doit terminer <const>6</const> quêtes. <const>1</const> quête au maximum est révélée.</li>
        <!-- END -->
        <!-- BEGIN level1 level2 -->
        <li>En ligue Bronze, chaque joueur doit terminer <const>12</const> quêtes. <const>3</const> quêtes au maximum sont révélées.</li>
        <!-- END -->
      </ul>
    </p>
  </div>
  <!-- END -->

</div>
