public final class PlayerController {

    private Player player;
    private Tile[][] tiles;
    private int tileWidth, tileHeight;

    public PlayerController(Player player, Tile[][] map) {
        this.player = player;
        this.tiles = map;
    }

    public void movePlayer(int direction) {
        if (checkWallCollision(direction)) {
            if (direction == 0) {
                player.moveUp();
            } else if (direction == 1) {
                player.moveRight();
            } else if (direction == 2) {
                player.moveDown();
            } else if (direction == 3) {
                player.moveLeft();
            }
        }
    }

    public void fire() {
        player.fire();
    }

    private boolean checkWallCollision(int direction) {
        PVector curPos = player.getPosition();
        float xPos = curPos.x;
        float yPos = curPos.y;
        int curRow = player.getRow();
        int curCol = player.getCol();
        int tileWidth = player.getTileWidth();
        int tileHeight = player.getTileHeight();
        float movementDist = player.getMovementDistance();

        Tile next;
        if (direction == 0) {
            int upEdge = curRow * tileHeight;
            float newY = yPos - movementDist;
            if (newY < upEdge) {
                try {
                    next = tiles[curRow - 1][curCol];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return false;
                }
                if (next.getIsMap()) {
                    player.setRow(curRow - 1);
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else if (direction == 1) {
            int rightEdge = (curCol * tileWidth) + tileWidth;
            float newX = xPos + movementDist;
            if (newX > rightEdge) {
                try {
                    next = tiles[curRow][curCol + 1];
                } catch(ArrayIndexOutOfBoundsException e) {
                    return false;
                }
                if (next.getIsMap()) {
                    player.setCol(curCol + 1);
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else if (direction == 2) {
            int bottomEdge = curRow * tileHeight + tileHeight;
            float newY = yPos + movementDist;
            if (newY > bottomEdge) {
                try {
                    next = tiles[curRow + 1][curCol];
                } catch (ArrayIndexOutOfBoundsException e) {
                    return false;
                }
                if (next.getIsMap()) {
                    player.setRow(curRow + 1);
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else if (direction == 3) {
            int leftEdge = curCol * tileWidth;
            float newX = xPos - movementDist;
            if (newX < leftEdge) {
                try {
                    next = tiles[curRow][curCol - 1];
                } catch(ArrayIndexOutOfBoundsException e) {
                    return false;
                }
                if (next.getIsMap()) {
                    player.setCol(curCol - 1);
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
        return false;
        
    }

    public void renderPlayer() {
        player.render();
    }

    public void checkBullets() {
        BulletController bc = player.getBulletController();
        bc.checkWallHit(tiles);
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }
}
