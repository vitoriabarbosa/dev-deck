package com.devdeck.model;

import java.awt.image.BufferedImage;

public class Card {
    private String value;
    private String suit;
    private BufferedImage image;

    public Card(String valor, String suit, BufferedImage imagem) {
        this.value = valor;
        this.suit = suit;
        this.image = imagem;
    }

    public String getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return value + " de " + suit;
    }
}
