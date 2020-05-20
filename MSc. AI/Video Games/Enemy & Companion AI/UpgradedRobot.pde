public class UpgradedRobot extends AttackPlayerRobot {

    private int timer = 0;
    private BulletController bc;

    public UpgradedRobot(int r, int c, int tileWidth, int tileHeight, float movementSpeed) {
        super(r, c, tileWidth, tileHeight, movementSpeed);
        bc = new BulletController();
    }

    @Override
    public void render() {
        PVector position = getPosition().copy();
        float xPos = position.x;
        float yPos = position.y;
        fill(255, 255, 0);
        ellipse(xPos, yPos, 40, 40);
        fill(0);
        int newxe = (int) (xPos + 10 * cos(orientation));
        int newye = (int) (yPos + 10 + sin(orientation));
        if (orientation > PI) orientation -= 2*PI ;
        else if (orientation < -PI) orientation += 2*PI ; 
        ellipse(newxe, newye, 10, 10);
    }
}