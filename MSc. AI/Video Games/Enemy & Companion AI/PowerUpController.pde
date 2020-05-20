public final class PowerUpController {

    private ExplodePower ep;
    private InvinciblePower ip;
    private FreezePower fp;

    public PowerUpController(ExplodePower ep, InvinciblePower ip, FreezePower fp) {
        this.ep = ep;
        this.ip = ip;
        this.fp = fp;
    }

    public void checkActivated(Player curPlayer) {
        PVector playerPos = curPlayer.getPosition().copy();
        if (!ep.getIsActive()) {
            PVector ePos = ep.getPosition().copy();
            float dist = PVector.dist(playerPos, ePos);
            if (dist < 60) {
                ep.activatePower();
            }
        }
        if (!ip.getIsActive()) {
            PVector iPos = ip.getPosition().copy();
            float dist = PVector.dist(playerPos, iPos);
            if (dist < 60) {
                ip.activatePower();
            }
        }
        if (!fp.getIsActive()) {
            PVector fPos = fp.getPosition().copy();
            float dist = PVector.dist(playerPos, fPos);
            if (dist < 60) {
                fp.activatePower();
            }
        }
    }

    public void renderAll() {
        ep.render();
        ip.render();
        fp.render();
    }

    public boolean isInvincible() {
        return ip.getIsActive();
    }

    public boolean isFrozen() {
        return fp.getIsActive();
    }

    public FreezePower getFreezePower() {
        return this.fp;
    }

    public boolean isExploding() {
        return ep.getIsActive();
    }


    public void checkExplosionHit(ArrayList<AttackPlayerRobot> attackBots, ArrayList<AttackHumanRobot> humanBots) {
        PVector pos = ep.getPosition().copy();
        int explosionSize = ep.getExplosionSize();
        for (AttackHumanRobot current : humanBots) {
            PVector curPos = current.getPosition().copy();
            float dist = PVector.dist(curPos, pos);
            if (dist < 80 + explosionSize) {
                current.die();
            }
        }
        for (AttackPlayerRobot current : attackBots) {
            PVector curPos = current.getPosition().copy();
            float dist = PVector.dist(curPos, pos);
            if (dist < 80 + explosionSize) {
                current.die();
            }
        }
    }
}