
public abstract class RenderedObject {

    private PVector position;

    public RenderedObject(PVector position) {
        this.position = position;
    }

    public abstract void render();

    public PVector getPosition() {
        return this.position;
    }

    public void setPosition(PVector newPos) {
        this.position = newPos;
    }
}