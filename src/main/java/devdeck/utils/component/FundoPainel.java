package devdeck.utils.component;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa um painel com uma imagem de fundo.
 * Suporta adicionar componentes sobre a imagem.
 */
public class FundoPainel extends JPanel {
    private final Image IMAGEM_FUNDO;

    /**
     * Construtor para a classe FundoPainel.
     *
     * @param imagemFundo A imagem que será usada como fundo do painel.
     */
    public FundoPainel(Image imagemFundo) {
        this.IMAGEM_FUNDO = imagemFundo;
        setLayout(null); // Permite posicionar componentes livremente
    }

    /**
     * Sobrescreve o método paintComponent para desenhar a imagem de fundo
     * e manter os componentes filhos.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (IMAGEM_FUNDO != null) {
            g.drawImage(IMAGEM_FUNDO, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
