package devdeck.utils;

import java.awt.*;

/**
 * A classe {@code ConfigPadrao} fornece configurações padrão relacionadas ao
 * layout do jogo, incluindo o tamanho e o posicionamento das cartas, bem como
 * o padding da tela. Todas as configurações são calculadas dinamicamente com
 * base no tamanho da tela para garantir uma interface responsiva em diferentes
 * resoluções de dispositivos.
 *
 * <p>
 * As constantes disponíveis incluem:
 * <ul>
 *     <li>{@link #PADDING} - O espaçamento padrão ao redor da tela.</li>
 *     <li>{@link #LARGURA_CARTA} - A largura padrão das cartas.</li>
 *     <li>{@link #ALTURA_CARTA} - A altura padrão das cartas.</li>
 *     <li>{@link #DESLOCAMENTO_X} - O deslocamento horizontal padrão entre cartas.</li>
 *     <li>{@link #DESLOCAMENTO_Y} - O deslocamento vertical padrão entre cartas.</li>
 * </ul>
 * Todas as dimensões são calculadas como proporções do tamanho da tela.
 * </p>
 */
public class ConfigPadrao {

    /**
     * Dimensão da tela obtida pelo {@link Toolkit}.
     * É utilizada para calcular dinamicamente outras dimensões.
     */
    public static final Dimension TAMANHO_TELA = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Padding da tela, calculado como 8% da largura da tela.
     * Define o espaçamento padrão ao redor do conteúdo do jogo.
     */
    public static final int PADDING = (int) (TAMANHO_TELA.width * 0.08);

    /**
     * Largura padrão das cartas, calculada como 7% da largura da tela.
     */
    public static final int LARGURA_CARTA = (int) (TAMANHO_TELA.width * 0.07);

    /**
     * Altura padrão das cartas, calculada como 16.5% da altura da tela.
     */
    public static final int ALTURA_CARTA = (int) (TAMANHO_TELA.height * 0.165);

    /**
     * Deslocamento horizontal padrão entre cartas, calculado como 8% da largura da tela.
     */
    public static final int DESLOCAMENTO_X = (int) (TAMANHO_TELA.width * 0.08);

    /**
     * Deslocamento vertical padrão entre cartas, calculado como 4% da altura da tela.
     */
    public static final int DESLOCAMENTO_Y = (int) (TAMANHO_TELA.height * 0.04);
}
