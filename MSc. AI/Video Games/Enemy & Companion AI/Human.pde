

public class Human extends RenderedObject {

    private float movementSpeed;
    private String colour;
    int width = 22;
    int height = 22;
    HumanState state = HumanState.WANDER;
    private float orientation;
    private PVector velocity;
    private Player curPlayer;
    private boolean dead = false;

    public Human(int r, int c, int tileWidth, int tileHeight, float movementSpeed, String colour, Player curPlayer) {
        super(new PVector((float) c * tileWidth + (tileWidth / 2), (float) r * tileHeight + (tileHeight / 2)));
        this.movementSpeed = movementSpeed;
        this.colour = colour;
        velocity = new PVector(0, 0);
        orientation = 0;
        this.curPlayer = curPlayer;
    }

    // Attempt to reach player
    public void seek() {

    }

    // Flee from robot
    public void flee(Robot enemy) {

    }

    public void reverse() {
        this.orientation += PI;
    }

    public void render() {
        checkSaved();
        if (colour.equals("RED")) {
            fill(255, 0, 0);
        } else if (colour.equals("GREEN")) {
            fill(0, 255, 0);
        } else if (colour.equals("BLUE")) {
            fill(0, 0, 255);
        }
        rectMode(RADIUS);
        PVector pos = getPosition();
        rect(pos.x, pos.y, width / 2, height / 2);
        fill(0);
        int newxe = (int) (pos.x + 5 * cos(orientation));
        int newye = (int) (pos.y + 5 + sin(orientation));

        ellipse(newxe, newye, 10, 10);
        
        
    }

    public void switchState(HumanState newState) {
        this.state = newState;
    }

    public void integrate() {
        switch(state) {
            case WANDER:
                wanderIntegrate();
                break;
            case FLEE:

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

        orientation += random(0, PI/64) - random(0, PI/64) ;
        
        // Keep in bounds
        //if (orientation > PI) orientation -= 2*PI ;
        //else if (orientation < -PI) orientation += 2*PI ;

    }

    public void seekIntegrate(){
        PVector curPos = getPosition().copy();
        PVector playerPos = curPlayer.getPosition().copy();
        PVector diff = playerPos.sub(curPos);
        diff.normalize();
        diff.mult(movementSpeed);
        getPosition().add(diff);
        orientation = atan2(diff.y, diff.x);
    }

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public String getColour() {
        return this.colour;
    }

    public void checkSaved() {
        PVector playerPos = curPlayer.getPosition().copy();
        PVector curPos = getPosition().copy();
        float dist = PVector.dist(playerPos, curPos);
        if (dist < 30) {
            if (colour.equals("RED")) {
                currentScore +=50;
            } else if (colour.equals("GREEN")) {
                currentScore += 100;
            } else if (colour.equals("BLUE")) {
                currentScore += 150;
            }
            die();
        }
    }
}