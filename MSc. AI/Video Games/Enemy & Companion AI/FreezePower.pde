

public class FreezePower extends PowerUp {

    private boolean isActive = false;
    private int timer = 0;

    public FreezePower(int row, int col, int tileWidth, int tileHeight) {
        super(new PVector((float) col * tileWidth + (tileWidth / 2), (float) row * tileHeight + (tileHeight / 2)));
    }

    public void activatePower() {
        isActive = true;
    }

    public void render() {
        if (!isActive) {
            PVector pos = getPosition().copy();
            float xPos = pos.x;
            float yPos = pos.y;
            fill(0, 0, 255);
            ellipse(xPos, yPos, 50, 50);
        }

    }

    public boolean getIsActive() {
        return isActive;
    }
}