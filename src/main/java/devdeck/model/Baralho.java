package devdeck.model;

import devdeck.utils.ConfigCarta;
import devdeck.utils.GeradorAleatorio;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Baralho de cartas, possui todas as 52 cartas e objeto de carta virada.
 */
public class Baralho {
    public Stack<NoCarta> cartas = new Stack<NoCarta>();

    public enum ENaipe {
        JAVA, PYTHON, C, C_MAIS
    }
    
    public enum ECor {
        VERMELHO, AMARELO, CINZA, AZUL
    }

    final public static Naipe[] naipes = {
        new Naipe(ENaipe.JAVA, ECor.VERMELHO),
        new Naipe(ENaipe.PYTHON, ECor.AMARELO),
        new Naipe(ENaipe.C, ECor.CINZA),
        new Naipe(ENaipe.C_MAIS, ECor.AZUL)
    };
    
    // Carrega o icone da carta fechada
    public final static ImageIcon cartaFechada = new ImageIcon(
            new ImageIcon(ClassLoader.getSystemResource("cartas/fundo.png"))
                    .getImage()
                    .getScaledInstance(ConfigCarta.LARGURA, ConfigCarta.ALTURA, Image.SCALE_SMOOTH)
    );

    public Baralho() {
        // Cria as cartas
        int i = 0;
        for (int numCarta = 0; numCarta < 7; numCarta++) {
            for (Naipe naipe : Baralho.naipes) {
                NoCarta nc = new NoCarta(numCarta + 1, naipe);
                cartas.push(nc);
                i++;
            }
        }
        this.embaralhar();
    }
    
    public void embaralhar() {
        GeradorAleatorio geradorAleatorio = new GeradorAleatorio();
        for (int i = 0; i < 99999; i++) {
            int rand1 = geradorAleatorio.nextInt(cartas.size());
            int rand2 = geradorAleatorio.nextInt(cartas.size());
            
            NoCarta aux = cartas.get(rand1);
            cartas.set(rand1, cartas.get(rand2));
            cartas.set(rand2, aux);
        }
    }
    
    public NoCarta retiraCartaTopo() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.pop();
    }
}
