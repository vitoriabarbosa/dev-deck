package devdeck.model;

import devdeck.model.home.Home;
import devdeck.utils.ConfigCarta;
import devdeck.utils.RecursoImagens;

import javax.swing.*;
import java.awt.*;

/**
 * A classe {@code NoCarta} representa uma carta individual com um número e um naipe.
 * Ela pode ser exibida de forma aberta (frente) ou fechada (verso), e pode estar associada a um "home" como uma lista ou pilha.
 */
final public class NoCarta extends JLabel {

    /**
     * O número da carta (de 1 a 7).
     */
    private final int numero;

    /**
     * O ícone da carta quando está aberta (frente).
     */
    private final ImageIcon openedIcon;

    /**
     * O naipe da carta.
     */
    private Naipe naipe;

    /**
     * A próxima carta conectada a esta (como uma pilha de cartas).
     */
    private NoCarta prox = null;

    /**
     * Indica se a carta está aberta (frente visível).
     */
    private boolean aberta = false;

    /**
     * Indica se a carta pode ser arrastada (draggable).
     */
    private boolean draggable = false;

    /**
     * A instância de "home" que contém esta carta. Pode ser uma ListaHome, PilhaHome ou Baralho.
     */
    private Home home = null;

    /**
     * Construtor que cria uma nova carta com um número e um naipe.
     *
     * @param numero O número da carta.
     * @param naipe O naipe da carta.
     */
    public NoCarta(int numero, Naipe naipe) {
        this.numero = numero;
        this.naipe = naipe;
        this.setBounds(0, 0, ConfigCarta.LARGURA, ConfigCarta.ALTURA);
        this.openedIcon = RecursoImagens.getCarta(this.getNumRep().toLowerCase() + "-" + this.getNaipeRep() + ".png");
        this.setOpen(false);
    }

    /**
     * Conta o número de cartas ligadas a esta carta (cartas "próximas").
     *
     * @return O número de cartas conectadas a esta.
     */
    public int getCountProx() {
        int totalProx = 0;
        NoCarta aux = this;
        while (aux.getProx() != null) {
            totalProx++;
            aux = aux.getProx();
        }
        return totalProx;
    }

    /**
     * Obtém a próxima carta conectada.
     *
     * @return A próxima carta ou {@code null} se não houver.
     */
    public NoCarta getProx() {
        return this.prox;
    }

    /**
     * Define a próxima carta conectada.
     *
     * @param prox A próxima carta.
     */
    public void setProx(NoCarta prox) {
        this.prox = prox;
    }

    /**
     * Verifica se a carta está aberta.
     *
     * @return {@code true} se a carta estiver aberta; caso contrário, {@code false}.
     */
    public boolean isOpen() {
        return this.aberta;
    }

    /**
     * Verifica se a carta é arrastável (draggable).
     *
     * @return {@code true} se a carta for arrastável; caso contrário, {@code false}.
     */
    public boolean isDraggable() {
        return this.draggable;
    }

    /**
     * Define se a carta deve ser exibida aberta (frente) ou fechada (verso).
     *
     * @param open Se a carta deve estar aberta.
     */
    public void setOpen(boolean open) {
        this.setOpen(open, true);
    }

    /**
     * Define se a carta pode ser arrastada.
     *
     * @param draggable Se a carta é arrastável.
     */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    /**
     * Define se a carta está aberta e se pode ser arrastada.
     *
     * @param open Se a carta deve estar aberta.
     * @param draggable Se a carta deve ser arrastável.
     */
    public void setOpen(boolean open, boolean draggable) {
        if (open) {
            this.setIcon(this.openedIcon);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.setIcon(Baralho.cartaFechada);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        this.aberta = open;
        this.setDraggable(draggable);
    }

    /**
     * Obtém o número da carta.
     *
     * @return O número da carta.
     */
    public int getNumero() {
        return this.numero;
    }

    /**
     * Retorna a representação textual do número da carta.
     * Para números especiais como 1, 7, retorna "A" e "K", respectivamente.
     *
     * @return A representação do número da carta.
     */
    public String getNumRep() {
        String ret;
        if (this.numero == 1) {
            ret = "A";
        } else if (this.numero == 7) {
            ret = "K";
        } else {
            ret = String.valueOf(this.numero);
        }
        return ret;
    }

    /**
     * Retorna a representação textual do naipe da carta.
     *
     * @return A representação do naipe da carta.
     */
    public String getNaipeRep() {
        return switch (this.naipe.getNaipe()) {
            case JAVA -> "java";
            case C -> "c";
            case PYTHON -> "python";
            default -> "c++";
        };
    }

    /**
     * Obtém o naipe da carta.
     *
     * @return O naipe da carta.
     */
    public Naipe getNaipe() {
        return this.naipe;
    }

    /**
     * Define a "home" da carta (e das cartas conectadas), que pode ser uma ListaHome, PilhaHome ou Baralho.
     *
     * @param home A instância de "home" associada à carta.
     */
    public void setHome(Home home) {
        this.home = home;
        if (this.getProx() != null) {
            this.getProx().setHome(home);
        }
    }

    /**
     * Obtém a instância de "home" associada à carta.
     *
     * @return O "home" da carta.
     */
    public Home getHome() {
        return this.home;
    }
}
