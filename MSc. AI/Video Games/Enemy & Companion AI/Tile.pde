
public class Tile {

    private int width;
    private int height;
    private int halfWidth, halfHeight;
    private int centerX, centerY;

    private boolean isMap = false;

    public Tile(int width, int height, int centerX, int centerY) {
        this.width = width;
        this.height = height;
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void render() {
        rectMode(RADIUS);
        if (isMap) {
            fill(255);
        } else {
            fill(0);
        }
        rect(centerX, centerY, halfWidth, halfHeight);
    }

    public void setMap() {
        isMap = true;
    }

    public boolean getIsMap() {
        return isMap;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}