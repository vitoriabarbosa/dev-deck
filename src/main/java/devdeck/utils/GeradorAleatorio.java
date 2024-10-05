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

    /**
     * Retorna um número double aleatório entre 0.0 (inclusivo)
     * e 1.0 (exclusivo).
     *
     * @return Um número double aleatório entre 0.0 e 1.0.
     */
    public double nextDouble() {
        return random.nextDouble();
    }

    /**
     * Embaralha um array de inteiros in-place.
     * Utiliza o algoritmo de Fisher-Yates para garantir que todos
     * os arranjos possíveis do array tenham a mesma probabilidade
     * de serem gerados.
     *
     * @param array O array de inteiros a ser embaralhado.
     */
    public void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
