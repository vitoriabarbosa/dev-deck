package devdeck.model.base;

import devdeck.model.interfaces.Base;
import devdeck.model.home.ListaHome;
import java.awt.Point;

import devdeck.utils.ConfigCard;
import devdeck.utils.ImageCardUtil;
import devdeck.view.GameGUI;

public class ListaBase extends Base {
    public ListaHome home = null;

    public ListaBase(int x, int y, ListaHome home) {
        this.x = x;
        this.y = y;
        this.setIcon(ImageCardUtil.getCarta("border-half.png"));
        this.setBounds(0, 0, ConfigCard.ALTURA_CARTA, ConfigCard.ALTURA_CARTA);
        this.setLocation(this.x, this.y);
        this.home = home;
    }

    @Override
    public Point getNextCardPoint() {
        int nextY = this.getBaseY();
        if(this.home.contarNos() > 0)
            nextY += (GameGUI.cardYOffset * this.home.contarNos()+1);

        return new Point(this.getBaseX(), nextY);
    }

    @Override
    public ListaHome getHome() {
        return this.home;
    }
}