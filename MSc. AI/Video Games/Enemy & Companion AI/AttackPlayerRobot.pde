
public class AttackPlayerRobot extends Robot {

    private int row, col;
    private int tileWidth, tileHeight;
    private boolean pathFound = false;
    private ArrayList<AStarNode> searchPath = new ArrayList<AStarNode>();
    PVector attackDirection;
    boolean found = false;
    private float movementSpeed;
    public float orientation;

    private boolean isDead;

    public AttackPlayerRobot(int r, int c, int tileWidth, int tileHeight, float movementSpeed) {
        super(new PVector((float) c * tileWidth + (tileWidth / 2), (float) r * tileHeight + (tileHeight / 2)), movementSpeed);
        this.row = r;
        this.col = c;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.movementSpeed = movementSpeed;
        isDead = false;
        orientation = 0;
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

    public void pursue(RenderedObject target) {
        
    }

    public void setOrientation(float x, float y) {
        orientation = atan2(y, x);
    }

    public void render() {
        PVector position = getPosition();
        float xPos = position.x;
        float yPos = position.y;
        fill(0, 255, 0);
        ellipse(xPos, yPos, 30, 30);
        fill(0);
        int newxe = (int) (xPos + 10 * cos(orientation));
        int newye = (int) (yPos + 10 + sin(orientation));
        if (orientation > PI) orientation -= 2*PI ;
        else if (orientation < -PI) orientation += 2*PI ; 
        ellipse(newxe, newye, 10, 10);
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int newRow) {
        row = newRow;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int newCol) {
        col = newCol;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public void setPathFound() {
        pathFound = true;
    }

    public void setPathNotFound() {
        pathFound = false;
    }

    public boolean getPathFound() {
        return pathFound;
    }

    public void setSearchPath(ArrayList<AStarNode> path) {
        this.searchPath = path;
    }

    public ArrayList<AStarNode> getSearchPath() {
        return this.searchPath;
    }

    public void die() {
        isDead = true;
    }

    public boolean getIsDead() {
        return isDead;
    }


    public void moveUp() {
        if (isFrozen() && getTimer() < 300) {
            incrementTimer();
            return;
        } else if (isFrozen()) {
          unFreeze();
        }
        float movementSpeed = getMovementSpeed();
        PVector velocity = new PVector(0f, -movementSpeed);
        PVector pos = getPosition();
        pos.add(velocity);
        setPosition(pos);
        float newYPos = pos.y;
        if (newYPos < row * tileHeight) {
            int newRow = row - 1;
            setRow(newRow);
        }
    }

    public void moveRight() {
        if (isFrozen() && getTimer() < 300) {
            incrementTimer();
            return;
        }else if (isFrozen()) {
          unFreeze();
        }
        float movementSpeed = getMovementSpeed();
        PVector velocity = new PVector(movementSpeed, 0f);
        PVector pos = getPosition();
        pos.add(velocity);
        setPosition(pos);
        float newXPos = pos.x;
        if (newXPos > (col * tileWidth) + tileWidth) {
            int newCol = col + 1;
            setCol(newCol);
        }
    }

    public void moveLeft() {
        if (isFrozen() && getTimer() < 00) {
            incrementTimer();
            return;
        }else if (isFrozen()) {
          unFreeze();
        }
        float movementSpeed = getMovementSpeed();
        PVector velocity = new PVector(-movementSpeed, 0f);
        PVector pos = getPosition();
        pos.add(velocity);
        setPosition(pos);
        float newXPos = pos.x;
        if (newXPos < col * tileWidth) {
            int newCol = col - 1;
            setCol(newCol);
        }
    }

    public void moveDown() {
        if (isFrozen() && getTimer() < 300) {
            incrementTimer();
            return;
        }else if (isFrozen()) {
          unFreeze();
        }
        float movementSpeed = getMovementSpeed();
        PVector velocity = new PVector(0f, movementSpeed);
        PVector pos = getPosition();
        pos.add(velocity);
        setPosition(pos);
        float newYPos = pos.y;
        if (newYPos > (row * tileHeight) + tileHeight) {
            int newRow = row + 1;
            setRow(newRow);
        }
    }

    public boolean getFound() {
        return this.found;
    }
}
