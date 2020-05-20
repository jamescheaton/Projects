public class HumanController {

    private Tile[][] tiles;
    private ArrayList<Human> humans;
    private int tileWidth, tileHeight;
    private Player curPlayer;


    public HumanController(Tile[][] map, ArrayList<Human> humans, Player curPlayer) {
        this.tiles = map;
        this.humans = humans;
        this.tileWidth = displayWidth / tiles[0].length;
        this.tileHeight = displayHeight / tiles.length;
        this.curPlayer = curPlayer;
    }

    public void renderAll() {
        Iterator<Human> iter = humans.iterator();
        while (iter.hasNext()) {
            Human next = iter.next();
            if (next.isDead()) {
                iter.remove();
            }
        }
        for (Human human : humans) {
            checkPlayerSeen();
            human.render();
            human.integrate();
            checkOutOfBounds(human);
            
        }
    }

    public void checkOutOfBounds(Human current) {
        PVector pos = current.getPosition().copy();
        int xPos = (int) pos.x;
        int yPos = (int) pos.y;

        
        int col = (int) pos.x / tileWidth;
        int row = (int) pos.y / tileHeight;

        Tile curTile = tiles[row][col];
        if (!curTile.getIsMap()) {
            current.reverse();
            current.switchState(HumanState.WANDER);
        }
        
    }

    public void checkPlayerSeen() {
        
        PVector playerPos = curPlayer.getPosition().copy();
        for (Human current : humans) {
            PVector curPos = current.getPosition().copy();
            
            int playerRow = (int) playerPos.y / tileHeight;
            int playerCol = (int) playerPos.x / tileWidth;
            int startRow = (int) curPos.y / tileHeight;
            int startCol = (int) curPos.x / tileWidth;

            PVector hStart = new PVector(startCol, startRow);
            PVector pStart = new PVector(playerCol, playerRow);
            PVector diff = pStart.sub(hStart);

            PVector n = diff.normalize();
            while (startRow != playerRow && startCol != playerCol) {
                Tile curTile = tiles[startRow][startCol];
                if (!curTile.getIsMap()) {
                    current.switchState(HumanState.WANDER);                   
                    break;
                }
                hStart.add(n);
                startRow = (int) hStart.y;
                startCol = (int) hStart.x;
                current.switchState(HumanState.SEEK);
            }
 

        
        }
    }

    public ArrayList<Human> getHumans() {
        return this.humans;
    }
}