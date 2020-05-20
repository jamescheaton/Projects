public final class BulletController {

    private ArrayList<Projectile> bullets;

    public BulletController() {
        bullets = new ArrayList<Projectile>();
    }

    public void add(Projectile bullet) {
        bullets.add(bullet);
    }

    public void renderAll() {
        checkBulletHit();
        for (Projectile bullet : bullets) {
            bullet.render();
            bullet.integrate();
        }
    }

    public void checkBulletHit() {
        Iterator<Projectile> iter = bullets.iterator();
        while (iter.hasNext()) {
            Projectile current = iter.next();
            if (!current.getRenderable()) {
                iter.remove();
            }
        }
    }
    
    public void checkWallHit(Tile[][] map) {
        for (Projectile bullet : bullets) {
            PVector pos = bullet.getPosition();
            int tileWidth = displayWidth / map[0].length;
            int tileHeight = displayHeight / map.length;
            int xPos = (int) (pos.x / tileWidth);
            int yPos = (int) (pos.y / tileHeight);
            Tile curTile = map[yPos][xPos];
            if (!curTile.getIsMap()) {
                bullet.destroy();
            }
        }
    }

    public void checkRobotHit(ArrayList<AttackPlayerRobot> targets) {
        for (Projectile bullet : bullets) {

        
        PVector pos = bullet.getPosition();
        PVector curPos = pos.copy();
        for (AttackPlayerRobot target : targets) {
            PVector botPos = target.getPosition();
            PVector targetPos = botPos.copy();
            //PVector difference = botPos.diff(targetPos);
            float diff = PVector.dist(botPos, curPos);
            if (diff < 30) {
                bullet.destroy();
                target.die();
                currentScore += 100;
            }
        }
        }
    }

    public void checkHumanRobotHit(ArrayList<AttackHumanRobot> targets) {
        for (Projectile bullet : bullets) {

        
            PVector pos = bullet.getPosition();
            PVector curPos = pos.copy();
            for (AttackHumanRobot target : targets) {
                PVector botPos = target.getPosition();
                PVector targetPos = botPos.copy();
                //PVector difference = botPos.diff(targetPos);
                float diff = PVector.dist(botPos, curPos);
                if (diff < 30) {
                    bullet.destroy();
                    target.die();
                    currentScore += 50;
                }
            }
        }
    }

    public void checkObstacleHit(ArrayList<Obstacle> obstacles) {
        for (Projectile bullet : bullets) {
            PVector pos = bullet.getPosition().copy();
            for (Obstacle obstacle : obstacles) {
                PVector obstaclePos = obstacle.getPosition().copy();
                float diff = PVector.dist(pos, obstaclePos);
                if (diff < 10) {
                    bullet.destroy();
                    obstacle.explode();
                }
            }
        }
    }
}