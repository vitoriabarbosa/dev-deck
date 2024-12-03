package devdeck.model;

import devdeck.utils.ConfigPadrao;
import devdeck.utils.GeradorAleatorio;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * A classe {@code Baralho} representa um baralho de cartas contendo 28 cartas com 4 naipes diferentes.
 * Ela também inclui a lógica para embaralhar as cartas e fornecer a carta do topo.
 */
public class Baralho {

    /**
     * Pilha de cartas do baralho.
     */
    public Stack<NoCarta> cartas = new Stack<>();

    /**
     * Enumeração representando os diferentes naipes disponíveis no jogo.
     */
    public enum ENaipe {
        JAVA, PYTHON, C, C_MAIS
    }

    /**
     * Enumeração representando as cores associadas a cada naipe.
     */
    public enum ECor {
        VERMELHO, AMARELO, CINZA, AZUL
    }

    /**
     * Array contendo todos os naipes do jogo, com suas respectivas cores.
     */
    final public static Naipe[] naipes = {
            new Naipe(ENaipe.JAVA, ECor.VERMELHO),
            new Naipe(ENaipe.PYTHON, ECor.AMARELO),
            new Naipe(ENaipe.C, ECor.CINZA),
            new Naipe(ENaipe.C_MAIS, ECor.AZUL)
    };

    /**
     * Ícone da carta fechada (ou carta virada para baixo), carregado a partir de um arquivo de imagem.
     */
    public final static ImageIcon cartaFechada = new ImageIcon(
            new ImageIcon(Baralho.class.getResource("/cartas/fundo.png"))
                    .getImage()
                    .getScaledInstance(ConfigPadrao.LARGURA_CARTA, ConfigPadrao.ALTURA_CARTA, Image.SCALE_SMOOTH)
    );

    /**
     * Construtor da classe {@code Baralho}. Ele cria as 28 cartas, distribuídas entre os 4 naipes,
     * e as embaralha.
     */
    public Baralho() {
        // Cria as cartas numeradas de 1 a 7 para cada naipe
        for (int numCarta = 0; numCarta < 7; numCarta++) {
            for (Naipe naipe : Baralho.naipes) {
                NoCarta nc = new NoCarta(numCarta + 1, naipe);
                cartas.push(nc);
            }
        }
        this.embaralhar();
    }

    /**
     * Embaralha o baralho trocando as posições das cartas de forma aleatória.
     */
    public void embaralhar() {
        GeradorAleatorio geradorAleatorio = new GeradorAleatorio();
        for (int i = 0; i < 99999; i++) {
            int rand1 = geradorAleatorio.nextInt(cartas.size());
            int rand2 = geradorAleatorio.nextInt(cartas.size());

            // Troca as cartas nas posições rand1 e rand2
            NoCarta aux = cartas.get(rand1);
            cartas.set(rand1, cartas.get(rand2));
            cartas.set(rand2, aux);
        }
    }

    /**
     * Remove e retorna a carta do topo da pilha de cartas.
     *
     * @return A {@link NoCarta} do topo, ou {@code null} se o baralho estiver vazio.
     */
    public NoCarta retiraCartaTopo() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.pop();
    }
}
