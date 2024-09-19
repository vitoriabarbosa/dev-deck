package com.devdeck.util;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {
    private int rectWidth;
    private int rectHeight;
    private Color rectColor;
    private Image rectImage;

    public CardPanel(int width, int height, Color color, Image image) {
        this.rectWidth = width;
        this.rectHeight = height;
        this.rectColor = color;
        this.rectImage = image;
        setPreferredSize(new Dimension(rectWidth, rectHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (rectImage != null) {
            g.drawImage(rectImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(rectColor);
            g.fillRect(0, 0, rectWidth, rectHeight);
        }
    }
}
