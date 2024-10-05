package devdeck.model.base;

import devdeck.model.home.PilhaHome;
import devdeck.utils.ConfigCarta;
import devdeck.utils.RecursoImagens;

import java.awt.*;

/**
 * Elemento base da pilha.
 */
final public class PilhaBase extends Base {
    protected PilhaHome home = null;
    
    public PilhaBase(int x, int y, PilhaHome home) {
        this.x = x;
        this.y = y;
        this.setIcon(RecursoImagens.getCarta("borda-completa.png"));
        this.setBounds(0, 0, ConfigCarta.LARGURA, ConfigCarta.ALTURA);
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