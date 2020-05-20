
public final class Player extends RenderedObject {

    private int num_lives = 3;
    private float movementSpeed;
    private int row, col;
    private int tileWidth, tileHeight;
    private BulletController bc;
    private boolean isInvincible = false;

    public Player(int row, int col, int tileWidth, int tileHeight, float movementSpeed) {
        super(new PVector((float) col * tileWidth + (tileWidth / 2), (float) row * tileHeight + (tileHeight / 2)));
        this.row = row;
        this.col = col;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.movementSpeed = movementSpeed;
        bc = new BulletController();
    }

    public void move(PVector direction) {
        PVector initialPos = getPosition();
        PVector newPos = initialPos.add(direction);
        setPosition(newPos);
    }

    public void fire() {
        PVector p = getPosition();
        PVector pos = p.copy();
        PVector target = new PVector(mouseX, mouseY);
        PVector v = target.sub(pos);
        PVector n = v.copy();
        n.normalize();
        n = n.mult(15);
        Projectile bullet = new Projectile(pos, n);
        bc.add(bullet);
    }

    public boolean saveHuman() {
        return false;
    }

    public void render() {
        PVector position = getPosition();
        float xPos = position.x;
        float yPos = position.y;
        fill(255, 0, 0);
        ellipse(xPos, yPos, 30, 30);
        bc.renderAll();
    }

    public void moveUp() {
        PVector velocity = new PVector(0f, -movementSpeed);
        PVector pos = getPosition();
        pos.add(velocity);
        setPosition(pos);
    }

    public void moveRight() {
        PVector velocity = new PVector(movementSpeed, 0f);
        PVector pos = getPosition();
        pos.add(velocity);
        setPosition(pos);
    }

    public void moveLeft() {
        PVector velocity = new PVector(-movementSpeed, 0f);
        PVector pos = getPosition();
        pos.add(velocity);
        setPosition(pos);
    }

    public void moveDown() {
        PVector velocity = new PVector(0f, movementSpeed);
        PVector pos = getPosition();
        pos.add(velocity);
        setPosition(pos);
    }

    public void shoot() {

    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int newRow) {
        this.row = newRow;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int newCol) {
        this.col = newCol;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public float getMovementDistance() {
        return this.movementSpeed;
    }

    public BulletController getBulletController() {
        return this.bc;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    public void die() {
        if (isInvincible) {
            return;
        }
        num_lives--;
    }

    public int getLives() {
        return this.num_lives;
    }

    public void giveLife() {
        num_lives++;
    }
}