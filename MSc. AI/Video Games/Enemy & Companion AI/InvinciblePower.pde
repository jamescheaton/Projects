

public class InvinciblePower extends PowerUp {

    private boolean isActive = false;
    private int timer = 0;
    private boolean done = false;

    public InvinciblePower(int row, int col, int tileWidth, int tileHeight) {
        super(new PVector((float) col * tileWidth + (tileWidth / 2), (float) row * tileHeight + (tileHeight / 2)));
    }

    public void activatePower() {
        isActive = true;
    }

    public void render() {
        if (done) {
            return;
        }
        if (!isActive) {
            PVector pos = getPosition().copy();
            float xPos = pos.x;
            float yPos = pos.y;
            fill(0, 255, 255);
            ellipse(xPos, yPos, 50, 50);
        } else {
            if (timer < 300) {
                timer++;
            } else {
                timer = 0;
                isActive = false;
                done = true;
            }
        }

    }

    public boolean getIsActive() {
        return isActive;
    }
}