package devdeck.model.base;

import devdeck.model.home.PilhaHome;
import devdeck.utils.ConfigCarta;
import devdeck.utils.RecursoImagens;

import java.awt.*;

/**
 * A classe {@code PilhaBase} representa a base de uma pilha de cartas
 * em um jogo de cartas. Esta classe herda da classe {@code Base} e
 * fornece uma estrutura para a pilha, permitindo gerenciar as cartas
 * contidas nela.
 */
final public class PilhaBase extends Base {

    /**
     * A pilha de cartas associada a esta base.
     */
    private final PilhaHome home;

    /**
     * Construtor que cria uma nova instância de {@code PilhaBase}
     * em uma posição específica (x, y) e associa uma pilha de cartas.
     *
     * @param x A coordenada x da posição da pilha.
     * @param y A coordenada y da posição da pilha.
     * @param home A pilha de cartas associada a esta base.
     */
    public PilhaBase(int x, int y, PilhaHome home) {
        this.x = x;
        this.y = y;
        this.setIcon(RecursoImagens.getCarta("borda-completa.png"));
        this.setBounds(0, 0, ConfigCarta.LARGURA, ConfigCarta.ALTURA);
        this.setLocation(this.x, this.y);
        this.home = home;
    }

    /**
     * Retorna a próxima posição disponível para uma carta na pilha
     * (em coordenadas x, y).
     *
     * @return Um objeto {@code Point} representando a posição (x, y)
     *         onde a próxima carta pode ser colocada.
     */
    @Override
    public Point getNextCardPoint() {
        return new Point(this.getX(), this.getY());
    }

    /**
     * Retorna a pilha de cartas associada a esta base.
     *
     * @return A pilha de cartas associada a esta base.
     */
    @Override
    public PilhaHome getHome() {
        return this.home;
    }
}
