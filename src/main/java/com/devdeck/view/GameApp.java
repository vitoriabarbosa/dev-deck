package com.devdeck.view;

import com.devdeck.model.Deck;
import com.devdeck.model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameApp extends JPanel {
    int tableMargin;
    int rectMargin;
    Deck deck;

    public GameApp() {
        tableMargin = 30;
        rectMargin = 20;
        deck = new Deck(); // Cria o baralho
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dimensões da mesa e borda
        int tableWidth = getWidth() - 2 * tableMargin;
        int tableHeight = getHeight() - 2 * tableMargin;

        // Cor das bordas
        g.setColor(new Color(114, 64, 64));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Cor do fundo da tela
        g.setColor(new Color(0, 128, 0));
        g.fillRect(tableMargin, tableMargin, tableWidth, tableHeight);

        // Dimensões e espaçamento das cartas
        int cardWidth = 180, cardHeight = 250;
        int cardSpacing = 20;
        int cardCols = 7; // Número de colunas de cartas na segunda fileira

        // Calcular o total de largura ocupada pelas cartas e o espaçamento entre elas
        int totalCardsWidth = cardCols * cardWidth + (cardCols - 1) * cardSpacing;

        // Centralizar as cartas horizontalmente na área verde
        int cardXStart = tableMargin + (tableWidth - totalCardsWidth) / 2;

        // Desenhar a primeira fileira (1 carta na primeira coluna, 4 nas últimas)
        List<Card> cards = deck.getCards();
        int firstRowCardY = tableMargin + rectMargin; // Posição Y da primeira fileira

        // Desenhar a primeira fileira: carta na primeira posição e nas últimas 4
        for (int i = 0; i < cardCols; i++) {
            if (i == 0 || i >= 3) { // Desenha a primeira carta e as últimas quatro
                g.drawImage(cards.get(i).getImage(), cardXStart + i * (cardWidth + cardSpacing), firstRowCardY, cardWidth, cardHeight, this);
            }
        }

        // Desenhar a segunda fileira (todas as 7 cartas)
        int secondRowCardY = firstRowCardY + cardHeight + 2 * rectMargin; // Posição Y da segunda fileira

        // Desenhar a segunda fileira com todas as cartas
        for (int i = 0; i < cardCols; i++) {
            g.drawImage(cards.get(i + cardCols).getImage(), cardXStart + i * (cardWidth + cardSpacing), secondRowCardY, cardWidth, cardHeight, this);
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