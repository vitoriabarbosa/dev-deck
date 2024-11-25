package devdeck.utils;

import java.util.Random;

/**
 * A classe {@code GeradorAleatorio} fornece métodos para gerar
 * números aleatórios e embaralhar arrays.
 * Utiliza a classe {@code Random} do Java para realizar as
 * operações de geração aleatória.
 */
public class GeradorAleatorio {
    private Random random;

    /**
     * Construtor que inicializa o gerador de números aleatórios.
     */
    public GeradorAleatorio() {
        random = new Random();
    }

    /**
     * Retorna um inteiro aleatório dentro do intervalo [0, bound).
     *
     * @param bound O limite superior (não incluído) do intervalo
     *              de números aleatórios a serem gerados.
     * @return Um número inteiro aleatório entre 0 (inclusivo) e
     *         o valor de {@code bound} (exclusivo).
     */
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
