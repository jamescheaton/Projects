

public class ExplodePower extends PowerUp {

    private boolean isActive = false;
    private int timer = 0;
    private int explosionSize = 0;
    private int MAX_EXPLOSION_SIZE = 300;
    private boolean done = false;

    public ExplodePower(int row, int col, int tileWidth, int tileHeight) {
        super(new PVector((float) col * tileWidth + (tileWidth / 2), (float) row * tileHeight + (tileHeight / 2)));
    }

    public void activatePower() {
        isActive = true;
    }

    public void render() {
        if (done) {
            return;
        }
        PVector pos = getPosition().copy();
        float xPos = pos.x;
        float yPos = pos.y;
        if (!isActive) {
            fill(255, 0, 255);
            ellipse(xPos, yPos, 50, 50);
        } else if (explosionSize <= MAX_EXPLOSION_SIZE) {
            if (explosionSize % 2 == 0) {
                fill(255, 0, 0);
            } else {
                fill(255);
            }
            ellipse(xPos, yPos, 50 + explosionSize, 50 + explosionSize);
            explosionSize += 5;
        }
        if (explosionSize > MAX_EXPLOSION_SIZE) {
            isActive = false;
            done = true;
        }

    }

    public boolean getIsActive() {
        return isActive;
    }

    public int getExplosionSize() {
        return this.explosionSize;
    }
}