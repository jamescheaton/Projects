

public class Obstacle extends RenderedObject {

    private boolean isDestroyed = false;
    private int width = 22;
    private int height = 22;

    public Obstacle(int row, int col, int tileWidth, int tileHeight) {
        super(new PVector((float) col * tileWidth + (tileWidth / 2), (float) row * tileHeight + (tileHeight / 2)));
    }

    public void explode() {
        this.isDestroyed = true;
    }

    public boolean getIsDestroyed() {
        return this.isDestroyed;
    }

    public void render() {
        fill(255, 0, 0);
        rectMode(RADIUS);
        PVector pos = getPosition();
        rect(pos.x, pos.y, width / 2, height / 2);
    }


}