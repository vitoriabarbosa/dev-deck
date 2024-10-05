package devdeck.model.base;

import devdeck.model.home.ListaHome;
import devdeck.utils.ConfigCarta;
import devdeck.utils.RecursoImagens;

import java.awt.*;

/**
 * Elemento base da lista (primeiro buraco da lista, normalmente terá uma carta sobre ele)
 */
public class ListaBase extends Base {
    public ListaHome home = null;
    
    public ListaBase(int x, int y, ListaHome home) {
        this.x = x;
        this.y = y;
        this.setIcon(RecursoImagens.getCarta("borda-metade.png"));
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
        int nextY = this.getBaseY();
        if (this.home.contarNos() > 0) {
            nextY += (ConfigCarta.DESLOCAMENTO_Y * this.home.contarNos() + 1);
        }
        return new Point(this.getBaseX(), nextY);
    }
    
    @Override
    public ListaHome getHome() {
        return this.home;
    }
}