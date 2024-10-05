package devdeck.model.base;

import devdeck.model.home.ListaHome;
import devdeck.utils.ConfigCarta;
import devdeck.utils.RecursoImagens;

import java.awt.*;

/**
 * A classe {@code ListaBase} representa um elemento base da lista, que
 * normalmente terá uma carta sobre ele. Este elemento é utilizado para
 * gerenciar a posição onde cartas podem ser empilhadas na lista de cartas.
 */
public class ListaBase extends Base {

    /**
     * A lista de cartas associada a este elemento base.
     */
    public ListaHome home = null;

    /**
     * Construtor que cria uma nova instância de {@code ListaBase}
     * na posição especificada (x, y).
     *
     * @param x A coordenada x da posição.
     * @param y A coordenada y da posição.
     * @param home A lista de cartas associada a este elemento base.
     */
    public ListaBase(int x, int y, ListaHome home) {
        this.x = x;
        this.y = y;
        this.setIcon(RecursoImagens.getCarta("borda-metade.png"));
        this.setBounds(0, 0, ConfigCarta.LARGURA, ConfigCarta.ALTURA);
        this.setLocation(this.x, this.y);
        this.home = home;
    }

    /**
     * Retorna a próxima posição disponível para uma carta em coordenadas (x, y).
     *
     * @return Um objeto {@code Point} representando a próxima posição
     *         disponível para uma carta.
     */
    @Override
    public Point getNextCardPoint() {
        int nextY = this.getBaseY();
        if (this.home.contarNos() > 0) {
            nextY += (ConfigCarta.DESLOCAMENTO_Y * this.home.contarNos() + 1);
        }
        return new Point(this.getBaseX(), nextY);
    }

    /**
     * Retorna a lista de cartas associada a este elemento base.
     *
     * @return A lista de cartas associada.
     */
    @Override
    public ListaHome getHome() {
        return this.home;
    }
}
