
public abstract class PowerUp extends RenderedObject {

    public PowerUp(PVector position) {
        super(position);
    }

    public abstract void activatePower();
}