package devdeck.utils;

import javax.swing.*;
import java.awt.*;

public class FundoPainel extends JPanel {
    private final Image FUNDO;

    public FundoPainel(Image FUNDO) {
        this.FUNDO = FUNDO;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Redimensiona a imagem para caber no painel
        if (FUNDO != null) {
            g.drawImage(FUNDO, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
