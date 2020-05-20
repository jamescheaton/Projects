public final class ObstacleController {

    private ArrayList<Obstacle> obstacles;
    private Player curPlayer;
    Tile[][] map;

    public ObstacleController(Player player, ArrayList<Obstacle> obstacles, Tile[][] map) {
        this.curPlayer = player;
        this.obstacles = obstacles;
        this.map = map;
    }

    public void renderAll() {
        checkHitBy();
        checkPlayerHit();
        Iterator<Obstacle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Obstacle next = iter.next();
            if (next.getIsDestroyed()) {
                iter.remove();
            }
        }
        for (Obstacle current : obstacles) {
            current.render();
        }
    }

    public void checkPlayerHit() {
        PVector playerPos = curPlayer.getPosition().copy();
        for (Obstacle current : obstacles) {
            PVector obstaclePos = current.getPosition().copy();
            float diff = PVector.dist(obstaclePos, playerPos);
            if (diff < 40) {
                curPlayer.die();
                current.explode();
            }
        }
    }

    public void checkHitBy() {
        BulletController bc = curPlayer.getBulletController();
        bc.checkObstacleHit(obstacles);
    }

    

    
}