

public final class WaveController {

    int[][] map =
    {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    };


    private int waveNumber;
    private boolean nextRound = true;
    private Game curGame;
    private Tile[][] tiles;
    private int screenWidth, screenHeight;
    private Player player;
    private AttackPlayerRobot robot;
    private ArrayList<AttackPlayerRobot> robotList = new ArrayList<AttackPlayerRobot>();
    private ArrayList<Human> humans = new ArrayList<Human>();
    private ArrayList<AttackHumanRobot> humanBots = new ArrayList<AttackHumanRobot>();
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private PowerUpController puController;
    private ConvertHumanRobot convertRobot;

    public WaveController(Game game, int screenWidth, int screenHeight, int waveNumber) {
        this.curGame = game;
        tiles = new Tile[map.length][map[0].length];
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.waveNumber = waveNumber;
     }

    public void drawMap() {

    }

    public void newMap() {
        int tileWidth = screenWidth / map[0].length;
        int tileHeight = screenHeight / map.length;
        for (int i = 0; i < map.length ; i++) {
            for (int j = 0; j < map[i].length ; j++) {
                int centerX = (tileWidth * j) + tileWidth / 2;
                int centerY = (tileHeight * i) + tileHeight / 2;
                tiles[i][j] = new Tile(tileWidth, tileHeight, centerX, centerY);
            }
        }
        generateMap();
    }

    private void generateMap(){
        int placeRoomChance = 0;
        int changeDirectionChance = 0;
        int numRowTiles = map[0].length;
        int numColTiles = map.length;
        int startX = (int) random(0, numRowTiles - 1);
        int startY = (int) random(0, numColTiles - 1);
        int startDirection = (int) random(0, 4);
        int tileWidth = screenWidth / map[0].length;
        int tileHeight = screenHeight / map.length;

        InvinciblePower ip = null;
        FreezePower fp = null;
        ExplodePower ep = null;

        int num_attackBots = (int) random(waveNumber, waveNumber + 3);
        ArrayList<Integer> attackBotPositions = new ArrayList<Integer>();
        for (int i = 0; i < num_attackBots; i++) {
            Integer pos = (int) random(20, 400);
            attackBotPositions.add(pos);
        }
        int num_humanBots = (int) random(waveNumber, waveNumber + 3);
        ArrayList<Integer> humanBotPositions = new ArrayList<Integer>();
        for (int i = 0; i < num_humanBots; i++) {
            Integer pos = (int) random(20, 400);
            humanBotPositions.add(pos);
        }

        int num_obstacles = (int) random (waveNumber + 5, waveNumber + 10);
        ArrayList<Integer> obstaclePositions = new ArrayList<Integer>();
        for (int i = 0; i < num_obstacles; i++) {
            Integer pos = (int) random(20, 400);
            obstaclePositions.add(pos);
        }
       
        for (int i = 0; i < 400; i++) {
            try {
            Tile current = tiles[startY][startX];
            if (startY == 0 || startX == 0 || startY == map.length || startX == map[0].length) {
                continue;
            }
            current.setMap();
            if (i == 100) {
                player = new Player(startY, startX, tileWidth, tileHeight, 7f);
            } else if (player == null && i == 110) {
              player = new Player(startY, startX, tileWidth, tileHeight, 7f);
            }
            for (Integer currentPos : obstaclePositions) {
                if (i == currentPos) {
                    obstacles.add(new Obstacle(startY, startX, tileWidth, tileHeight));
                }
            }
            for (Integer currentPos : attackBotPositions) {
                if (i == currentPos) {
                    robotList.add(new AttackPlayerRobot(startY, startX, tileWidth, tileHeight, 5f));
                }
            }
            for (Integer currentPos : humanBotPositions) {
                if (i == currentPos) {
                    humanBots.add(new AttackHumanRobot(startY, startX, tileWidth, tileHeight, 5f));
                }
            }
            /*
            if (i == 100) {
                robotList.add(new AttackPlayerRobot(startY, startX, tileWidth, tileHeight, 5f));
            }
            if (i == 150) {
                robotList.add(new AttackPlayerRobot(startY, startX, tileWidth, tileHeight, 5f));
            }
            if (i == 200) {
                robotList.add(new AttackPlayerRobot(startY, startX, tileWidth, tileHeight, 5f));
            }
            */
            if (i == 150) {
                humans.add(new Human(startY, startX, tileWidth, tileHeight, 5f, "RED", player));
            }
            if (i == 152) {
                humans.add(new Human(startY, startX, tileWidth, tileHeight, 5f, "GREEN", player));
            }
            if (i == 154) {
                humans.add(new Human(startY, startX, tileWidth, tileHeight, 5f, "BLUE", player));
            }
            /*
            if (i == 200) {
                humanBots.add(new AttackHumanRobot(startY, startX, tileWidth, tileHeight, 5f));
            }
            if (i == 202) {
                humanBots.add(new AttackHumanRobot(startY, startX, tileWidth, tileHeight, 5f));
            }
            if (i == 204) {
                humanBots.add(new AttackHumanRobot(startY, startX, tileWidth, tileHeight, 5f));
            }
            */
            if (i == 70) {
                ip = new InvinciblePower(startY, startX, tileWidth, tileHeight);
            }

            if (i == 95) {
                ep = new ExplodePower(startY, startX, tileWidth, tileHeight);
            }

            if (i == 120) {
                fp = new FreezePower(startY, startX, tileWidth, tileHeight);
            }

            if (i == 200) {
                convertRobot = new ConvertHumanRobot(startY, startX, tileWidth, tileHeight, 5f);
            }

            int placeRoomTest = (int) random(0, 100);
            if (placeRoomChance > placeRoomTest) {
                placeRoom(startX, startY);
                placeRoomChance = 0;
            } else {
                placeRoomChance += 2;
            }
            
            if (startDirection == 3) { // case up
                startY--;
            } else if (startDirection == 2) { // case right
                startX++;
            } else if(startDirection == 1) { // case down
                startY++;
            } else if (startDirection == 0) {
                startX--;
            }

            if (startX < 1) {
                startX++; //= numRowTiles - 1;
            } else if (startX >= numRowTiles - 2) {
                startX--;// = 0;
            }
            if (startY < 1) {
                startY++;// = numColTiles - 1;
            } else if (startY >= numColTiles - 2) {
                startY--;// = 0;
            }

            

            int changeDirectionTest = (int) random(0, 100);
            if (changeDirectionChance > changeDirectionTest) {
                int newDirection = (int) random(0, 4);
                startDirection = newDirection;
                changeDirectionChance = 0;
            } else {
                changeDirectionChance += 5;
            }

            
        } catch (ArrayIndexOutOfBoundsException e) {
             if (startDirection == 3) { // case up
                startY--;
            } else if (startDirection == 2) { // case right
                startX++;
            } else if(startDirection == 1) { // case down
                startY++;
            } else if (startDirection == 0) {
                startX--;
            }

            if (startX < 1) {
                startX++; //= numRowTiles - 1;
            } else if (startX >= numRowTiles - 2) {
                startX--;// = 0;
            }
            if (startY < 1) {
                startY++;// = numColTiles - 1;
            } else if (startY >= numColTiles - 2) {
                startY--;// = 0;
            }
            e.printStackTrace();
        }
    }
    puController = new PowerUpController(ep, ip, fp);
    }

    private void placeRoom(int startX, int startY) {
        int roomWidth = (int) random(1, 3);
        int roomHeight = (int) random(1, 3);
        for (int i = 0 - roomHeight; i < roomHeight; i++) {
            for (int j = 0 - roomWidth; j < roomWidth; j++) {
                if ((startY + i >= map[0].length - 2) || (startX + j >= map.length - 2)) {
                    continue;
                } else if ((startY + i < 2) || (startX + j < 2)){
                    continue;
                }
                Tile curTile = tiles[startY + i][startX + j];
                curTile.setMap();
            }
        }
    }

    public void renderMap() {
        
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                Tile curTile = tiles[i][j];
                curTile.render();
            }
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public ArrayList<AttackPlayerRobot> getRobots() {
        return this.robotList;
    }

    public ArrayList<Human> getHumans() {
        return this.humans;
    }

    public ArrayList<AttackHumanRobot> getHumanBots() {
        return this.humanBots;
    }

    public ArrayList<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public PowerUpController getPowerUpController() {
        return this.puController;
    }

    public ConvertHumanRobot getConvertHumanRobot() {
        return this.convertRobot;
    }

}
