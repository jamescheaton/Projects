
public class ConvertHumanRobot extends AttackHumanRobot {

    private boolean upgraded = false;
    private ArrayList<AStarNode> searchPath;
    private boolean found;
    private int tileWidth, tileHeight;
    private int row, col;

    public ConvertHumanRobot(int r, int c, int tileWidth, int tileHeight, float movementSpeed) {
        super(r, c, tileWidth, tileHeight, movementSpeed);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.row = r;
        this.col = c;
    }

    public void convert() {
        upgraded = true;
        searchPath = new ArrayList<AStarNode>();
    }

    public void pursue(Player target) {
        PVector pos = target.getPosition();
        PVector playerPos = pos.copy();
        int xPos = (int) playerPos.x;
        int yPos = (int) playerPos.y;
        int playerRow = yPos / tileHeight;
        int playerCol = xPos / tileWidth;
        if (playerRow == row && playerCol == col) {
            PVector botPos = getPosition();
            PVector curPos = botPos.copy();
            PVector n = playerPos.sub(curPos);
            n.normalize();
            //n.mult(movementSpe);
            botPos.add(n);
            setPosition(botPos);
            found = true;
        } else {
            found = false;
        }
    }
    @Override
    public void render() {
        integrate();
        PVector position = getPosition();
        float xPos = position.x;
        float yPos = position.y;
        fill(0, 255, 255);
        ellipse(xPos, yPos, 30, 30);
        fill(0);
        int newxe = (int) (xPos + 10 * cos(orientation));
        int newye = (int) (yPos + 10 + sin(orientation));
        //if (orientation > PI) orientation -= 2*PI ;
        //else if (orientation < -PI) orientation += 2*PI ; 
        ellipse(newxe, newye, 10, 10);
    }



    
}