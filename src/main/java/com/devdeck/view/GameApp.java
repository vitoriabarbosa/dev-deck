package com.devdeck.view;

import javax.swing.*;
import java.awt.*;

public class GameApp extends JPanel {
    private Image cardImage;

    public GameApp() {
        cardImage = new ImageIcon("src/main/resources/card/java.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Definindo dimensões da mesa e borda
        int tableMargin = 50;
        int tableWidth = getWidth() - 2 * tableMargin;
        int tableHeight = getHeight() - 2 * tableMargin;

        g.setColor(new Color(114,64,64));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(new Color(0, 128, 0));
        g.fillRect(tableMargin, tableMargin, tableWidth, tableHeight);

        // Desenha as cartas
        int cardWidth = 180, cardHeight = 250;
        int x = tableMargin + 20, y = tableMargin + 20; // Posições iniciais dentro da mesa verde
        int rows = 1, cols = 5; // 2 fileiras, 5 cartas por fileira

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g.drawImage(cardImage, x + j * (cardWidth + 10), y + i * (cardHeight + 10), cardWidth, cardHeight, this);
            }
        }
    }

    public static void main(String[] args) {
        JFrame gameFrame = new JFrame("The Card Game");
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);

        GameApp gameApp = new GameApp();
        gameFrame.add(gameApp);
        gameFrame.setVisible(true);
    }
}
