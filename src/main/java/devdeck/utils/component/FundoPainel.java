package devdeck.utils.component;

import javax.swing.*;
import java.awt.*;

public class FundoPainel extends JPanel {
    private final Image IMAGEM_FUNDO;

    public FundoPainel(Image IMAGEM_FUNDO) {
        this.IMAGEM_FUNDO = IMAGEM_FUNDO;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Redimensiona a imagem para caber no painel
        if (IMAGEM_FUNDO != null) {
            g.drawImage(IMAGEM_FUNDO, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
