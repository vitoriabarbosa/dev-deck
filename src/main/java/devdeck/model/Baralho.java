package devdeck.model;

import devdeck.utils.ConfigCard;
import devdeck.utils.RandomGenerator;

import java.awt.*;
import java.util.Stack;
import javax.swing.ImageIcon;

public class Baralho {
    private Stack<NoCarta> cards = new Stack<>();

    final public static Naipe[] naipes = {
            new Naipe(Naipe.EnumNaipe.JAVA),
            new Naipe(Naipe.EnumNaipe.PYTHON),
            new Naipe(Naipe.EnumNaipe.C),
            new Naipe(Naipe.EnumNaipe.CPLUS)
    };

    // Carrega o ícone da carta fechada
    public final static ImageIcon cartaFechada = new ImageIcon(
            new ImageIcon(ClassLoader.getSystemResource("cards/back.png"))
                    .getImage()
                    .getScaledInstance(ConfigCard.LARGURA_CARTA, ConfigCard.ALTURA_CARTA, Image.SCALE_SMOOTH)
    );

    public Baralho() {
        // Cria as cartas
        int i = 0;
        for(int numCarta = 0; numCarta < 13; numCarta++) {
            for(Naipe naipe : Baralho.naipes) {
                NoCarta nc = new NoCarta(numCarta + 1, naipe);
                cards.push(nc);
                i++;
            }
        }

        // Embaralha
        this.embaralhar();
    }

    public void embaralhar() {
        RandomGenerator randomGenerator = new RandomGenerator();
        for(int i = 0; i < 99; i++) {
            int rand1 = randomGenerator.nextInt(cards.size());
            int rand2 = randomGenerator.nextInt(cards.size());

            NoCarta aux = cards.get(rand1);
            cards.set(rand1, cards.get(rand2));
            cards.set(rand2, aux);
        }
    }

    public NoCarta retiraCartaTopo() {
        if(cards.isEmpty())
            return null;

        return cards.pop();
    }
}
