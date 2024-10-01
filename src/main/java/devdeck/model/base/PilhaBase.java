package devdeck.model.base;

import devdeck.model.interfaces.Base;
import java.awt.Point;
import devdeck.model.home.PilhaHome;
import devdeck.utils.ConfigCard;
import devdeck.utils.ImageCardUtil;

/**
 * Elemento base da pilha.
 * @author Willy G. M. Barro Raffel
 */
final public class PilhaBase extends Base {
    protected PilhaHome home = null;

    public PilhaBase(int x, int y, PilhaHome home) {
        this.x = x;
        this.y = y;
        this.setIcon(ImageCardUtil.getCarta("border-full.png"));
        this.setBounds(0, 0, ConfigCard.ALTURA_CARTA, ConfigCard.ALTURA_CARTA);
        this.setLocation(this.x, this.y);
        this.home = home;
    }

    /**
     * Retorna a próxima posição disponível para uma carta (em x,y)
     * @return Point
     */
    @Override
    public Point getNextCardPoint() {
        return new Point(this.getX(), this.getY());
    }

    @Override
    public PilhaHome getHome() {
        return this.home;
    }
}