
public abstract class Robot extends RenderedObject {

    private float movementSpeed;
    private boolean isFrozen = false;
    private int frozenTimer = 0;

    public Robot(PVector position, float movementSpeed) {
        super(position);
        this.movementSpeed = movementSpeed;
    }

    public abstract void pursue(RenderedObject target);

    public void patrol() {

    }

    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    public void setMovementSpeed(float newSpeed) {
        this.movementSpeed = newSpeed;
    }

    public void freeze() {
        isFrozen = true;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void unFreeze() {
        this.isFrozen = false;
    }

    public int getTimer() {
        return this.frozenTimer;
    }

    public void incrementTimer() {
        frozenTimer++;
        if (frozenTimer > 300) {
            frozenTimer = 0;
            unFreeze();
        }
    }
}