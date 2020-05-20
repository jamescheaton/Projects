WaveController wc;
Player curPlayer;
PlayerController pc;
RobotController rc;
HumanController hc;
ObstacleController oc;
PowerUpController puc;
boolean pathFound = false;
ArrayList<AttackPlayerRobot> robots;
ArrayList<Human> humans;
ArrayList<AttackHumanRobot> humanBots;
ArrayList<Obstacle> obstacles;
ConvertHumanRobot convertBot;
Game curGame;
int wave = 0;
public int currentScore;
boolean gameOver = false;
int newLifeScore = 0;
int prevscore = 0;
int frameScore = 0;

void setup(){
    fullScreen();
    curGame = new Game(displayWidth, displayHeight);
    newLevel();
}

void newLevel() {
    wave++;
    wc = new WaveController(curGame, displayWidth, displayHeight, wave);
    wc.newMap();
    //wc.generateMap();
    Tile[][] tiles = wc.getTiles();
    curPlayer = wc.getPlayer();
    pc = new PlayerController(curPlayer, wc.getTiles());
    robots = wc.getRobots();
    humans = wc.getHumans();
    humanBots = wc.getHumanBots();
    obstacles = wc.getObstacles();
    convertBot = wc.getConvertHumanRobot();
    rc = new RobotController(tiles, robots, humanBots, humans, convertBot);
    hc = new HumanController(tiles, humans, curPlayer);
    oc = new ObstacleController(curPlayer, obstacles, tiles);
    puc = wc.getPowerUpController();
    
    
    //wc.renderMap();
}

void draw() {
    if (!gameOver) {
    rc.checkPlayerHit(curPlayer);
    if (curPlayer.getLives() < 1) {
        gameOver = true;
    }
    wc.renderMap();
    puc = wc.getPowerUpController();
    FreezePower fp;
    try {
        fp = puc.getFreezePower();
        if (fp.getIsActive()) {
        rc.freezeAll();
    }
    } catch (NullPointerException e) {
        e.printStackTrace();
    }
    if (puc.isExploding()) {
        puc.checkExplosionHit(robots, humanBots);
    }
    if (puc.isInvincible()) {
        curPlayer.setInvincible(true);
    } else {
        curPlayer.setInvincible(false);
    }

    stroke(0, 0, 255);
    line(mouseX - 30, mouseY, mouseX + 30, mouseY);
    line(mouseX, mouseY - 30, mouseX, mouseY + 30);
    stroke(0);
    
    
    rc.renderAll(curPlayer);
    hc.renderAll();

    pc.renderPlayer();
    pc.checkBullets();
    puc.checkActivated(curPlayer);
    puc.renderAll();
    //robot.render();
    //rc.renderRobot();
    oc.renderAll();
    if (rc.allEliminated()) {
        newLevel();
    }

    fill(255, 0, 0);
    textSize(50);
    text("Score: " + currentScore, 0, 50);
    text("Wave: " + wave, 0, 100);
    text("Lives: " + curPlayer.getLives(), 0, 150);
    frameScore = currentScore - prevscore;
    prevscore = currentScore;
    newLifeScore += frameScore;
    if (newLifeScore >= 1000) {
        curPlayer.giveLife();
        newLifeScore = 0;
    }
    
    } else {
        background(0);
    textSize(100);
    fill(255);
    text("GAME OVER", (displayWidth / 2) - 600, displayHeight / 2);
    text("YOUR SCORE: " + currentScore, (displayWidth / 2) - 600, (displayHeight) / 2 + 100);
    text("Press Any Key to Start Again", (displayWidth / 2) - 600, (displayHeight) / 2 + 200);
    }
    
}

void keyPressed() {
    if (!gameOver) {
    Player curPlayer = wc.getPlayer();
    if (key == CODED) {
        if (keyCode == UP) {
            pc.movePlayer(0);
        } else if (keyCode == RIGHT) {
            pc.movePlayer(1);
        } else if (keyCode == DOWN) {
            pc.movePlayer(2);
        } else if (keyCode == LEFT) {
            pc.movePlayer(3);
        }
    }
    } else {
        wave = 0;
        currentScore = 0;
        gameOver = false;
        newLifeScore = 0;
        frameScore =0;
        newLevel();
    } 
}

void mousePressed() {
    if (mouseButton == LEFT) {
        pc.fire();
    }
}
