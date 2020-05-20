
public class AttackHumanRobot extends Robot {

    final float ORIENTATION_INCREMENT = PI/32 ;

    private float movementSpeed;
    private final int radius = 30;
    private HumanState state = HumanState.WANDER;
    public float orientation;
    private PVector velocity;
    private Human target;
    private boolean isDead = false;

    public AttackHumanRobot(int r, int c, int tileWidth, int tileHeight, float movementSpeed) {
        super(new PVector((float) c * tileWidth + (tileWidth / 2), (float) r * tileHeight + (tileHeight / 2)), movementSpeed);
        this.movementSpeed = movementSpeed;
        velocity = new PVector(0, 0);
        orientation = 0;
    }

    public void pursue(Human target) {

    }

    public void pursue(RenderedObject target) {

    }

    public void die() {
        isDead = true;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void reverse() {
        this.orientation += PI;
    }

    public void render() {
        integrate();
        PVector position = getPosition();
        float xPos = position.x;
        float yPos = position.y;
        fill(0, 0, 255);
        ellipse(xPos, yPos, 30, 30);
        fill(0);
        int newxe = (int) (xPos + 10 * cos(orientation));
        int newye = (int) (yPos + 10 + sin(orientation));
        //if (orientation > PI) orientation -= 2*PI ;
        //else if (orientation < -PI) orientation += 2*PI ; 
        ellipse(newxe, newye, 10, 10);
    }

    public void integrate() {
        if (isFrozen() && getTimer() < 300) {
            incrementTimer();
            return;
        }else if (isFrozen()) {
          unFreeze();
        }
        switch(state) {
            case WANDER:
                wanderIntegrate();
                break;
            case SEEK:
                seekIntegrate();
        }
    }

    public void wanderIntegrate() {
        velocity.x = cos(orientation);
        velocity.y = sin(orientation);
        velocity.mult(movementSpeed);

        PVector pos = getPosition();
        pos.add(velocity);
        float targetOrientation = atan2(velocity.y, velocity.x) ;

        orientation += random(0, PI/64) - random(0, PI/64) ;
        
        if (abs(targetOrientation - orientation) <= ORIENTATION_INCREMENT) {
            orientation = targetOrientation ;
            return ;
        }

        // if it's less than me, then how much if up to PI less, decrease otherwise increase
        if (targetOrientation < orientation) {
            if (orientation - targetOrientation < PI) orientation -= ORIENTATION_INCREMENT ;
            else orientation += ORIENTATION_INCREMENT ;
        }
        else {
            if (targetOrientation - orientation < PI) orientation += ORIENTATION_INCREMENT ;
            else orientation -= ORIENTATION_INCREMENT ; 
        }
    
        // Keep in bounds
        if (orientation > PI) orientation -= 2*PI ;
        else if (orientation < -PI) orientation += 2*PI ;

    }

    public void seekIntegrate() {
        PVector curPos = getPosition().copy();
        PVector humanPos = target.getPosition().copy();
        PVector diff = humanPos.sub(curPos);
        diff.normalize();
        diff.mult(movementSpeed);
        getPosition().add(diff);
        orientation = atan2(diff.y, diff.x);
    }

    public void setTarget(Human target) {
        this.target = target;
    }

    public void removeTarget() {
        this.target = null;
    }

    public boolean isTargetSet() {
        if (this.target == null) {
            return false;
        } else {
            return true;
        }
    }

    public void switchState(HumanState newState) {
        this.state = newState;
    }
}
