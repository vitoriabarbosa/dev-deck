package com.devdeck.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] values = {"A", "1", "2", "3", "4", "5"}; // 6 valores
        String[] suits = {"java", "python", "c", "c++"}; // 4 naipes

        // Criando as cartas
        for (String suit : suits) {
            for (String value : values) {
                try {
                    // Carregar a imagem correspondente ao valor e naipe
                    BufferedImage image = ImageIO.read(new File("src/main/resources/cards/" + suit + value + ".png"));
                    cards.add(new Card(value, suit, image));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Collections.shuffle(cards); // Embaralhar o baralho
    }

    public List<Card> getCards() {
        return cards;
    }
}
