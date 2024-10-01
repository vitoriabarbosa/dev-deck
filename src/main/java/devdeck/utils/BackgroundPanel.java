package devdeck.utils;

import java.awt.*;
import javax.swing.*;

public class BackgroundPanel extends JPanel {
    private final Image BACKGROUND;

    public BackgroundPanel(Image BACKGROUND) {
        this.BACKGROUND = BACKGROUND;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Redimensiona a imagem para caber no painel
        if (BACKGROUND != null) {
            g.drawImage(BACKGROUND, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
