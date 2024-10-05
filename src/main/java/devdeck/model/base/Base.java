package devdeck.model.base;

import devdeck.model.home.Home;

import javax.swing.*;
import java.awt.*;

/**
 * A classe {@code Base} é uma classe abstrata que estende
 * {@code JLabel}. Ela representa uma base onde cartas podem
 * ser empilhadas ou organizadas. Essa classe fornece métodos
 * para gerenciar a posição das bases e integrar-se com a
 * interface gráfica do jogo.
 */
public abstract class Base extends JLabel {

    /**
     * A coordenada x da base.
     */
    protected int x = 0;

    /**
     * A coordenada y da base.
     */
    protected int y = 0;

    /**
     * Retorna a instância de {@code Home} associada a esta base.
     *
     * @return A {@code Home} correspondente a esta base.
     */
    abstract public Home getHome();

    /**
     * Define a coordenada x da base.
     *
     * @param x A nova coordenada x.
     */
    public void setBaseX(int x) {
        this.x = x;
    }

    /**
     * Retorna a coordenada x da base.
     *
     * @return A coordenada x atual da base.
     */
    public int getBaseX() {
        return this.x;
    }

    /**
     * Define a coordenada y da base.
     *
     * @param y A nova coordenada y.
     */
    public void setBaseY(int y) {
        this.y = y;
    }

    /**
     * Retorna a coordenada y da base.
     *
     * @return A coordenada y atual da base.
     */
    public int getBaseY() {
        return this.y;
    }

    /**
     * Retorna a próxima posição disponível para uma carta
     * (em coordenadas x e y).
     *
     * @return Um objeto {@code Point} representando a próxima
     *         posição disponível para uma carta.
     */
    public abstract Point getNextCardPoint();
}
