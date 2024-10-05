package devdeck.model;

import devdeck.model.Baralho.ECor;
import devdeck.model.Baralho.ENaipe;

/**
 * A classe {@code Naipe} representa o naipe de uma carta, contendo o tipo do naipe e sua cor associada.
 */
public class Naipe {

    /**
     * O tipo do naipe (e.g., JAVA, PYTHON, C, C++).
     */
    private ENaipe naipe;

    /**
     * A cor do naipe (e.g., VERMELHO, AMARELO, CINZA, AZUL).
     */
    private ECor cor;

    /**
     * Construtor que cria um novo naipe com um tipo de naipe e uma cor.
     *
     * @param naipe O tipo do naipe.
     * @param cor A cor do naipe.
     */
    public Naipe(ENaipe naipe, ECor cor) {
        this.naipe = naipe;
        this.cor = cor;
    }

    /**
     * Obtém o tipo do naipe.
     *
     * @return O tipo do naipe.
     */
    public ENaipe getNaipe() {
        return this.naipe;
    }

    /**
     * Obtém a cor do naipe.
     *
     * @return A cor do naipe.
     */
    public ECor getCor() {
        return this.cor;
    }
}
