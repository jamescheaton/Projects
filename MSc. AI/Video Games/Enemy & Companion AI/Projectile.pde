public class Projectile extends RenderedObject {

    private PVector velocity;
    int width = 2;
    int height = 6;
    private boolean renderable = true;

    public Projectile(PVector position, PVector velocity) {
        super(position);
        this.velocity = velocity;
    }

    public void integrate() {
        PVector position = getPosition();
        PVector newPos = position.add(velocity);
        setPosition(newPos);
    }

    public void render() {
        rectMode(RADIUS);
        fill(0);
        PVector pos = getPosition();
        rect(pos.x, pos.y, width / 2, height / 2);
    }

    public boolean getRenderable() {
        return this.renderable;
    }

    public void destroy() {
        this.renderable = false;
    }
}