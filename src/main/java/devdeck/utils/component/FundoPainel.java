package devdeck.utils.component;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa um painel com uma imagem de fundo.
 * Esta classe estende {@code JPanel} e permite a renderização de uma imagem
 * que ocupa toda a área do painel.
 */
public class FundoPainel extends JPanel {
    private final Image IMAGEM_FUNDO;

    /**
     * Construtor para a classe FundoPainel.
     *
     * @param IMAGEM_FUNDO A imagem que será utilizada como fundo do painel.
     */
    public FundoPainel(Image IMAGEM_FUNDO) {
        this.IMAGEM_FUNDO = IMAGEM_FUNDO;
    }

    /**
     * Método que pinta o componente.
     * Este método sobrescreve o método {@code paintComponent} para desenhar
     * a imagem de fundo redimensionada para caber no painel.
     *
     * @param g O objeto {@code Graphics} que será usado para desenhar a imagem.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Redimensiona a imagem para caber no painel
        if (IMAGEM_FUNDO != null) {
            g.drawImage(IMAGEM_FUNDO, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
