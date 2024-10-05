package devdeck.utils;

import javax.swing.*;
import java.awt.*;

public class RecursoImagens {

    // Metodo que recebe o caminho da imagem e os tamanhos dinâmicos
    private static ImageIcon getImageIcon(String caminho, int largura, int altura) {
        java.net.URL recurso = ClassLoader.getSystemResource(caminho);
        if (recurso == null) {
            System.err.println("Imagem não encontrada: " + caminho);
            return null;
        }
        ImageIcon imageIcon = new ImageIcon(recurso);

        Image imagemRedimensionada = imageIcon.getImage().getScaledInstance(
                largura,
                altura,
                Image.SCALE_SMOOTH
        );

        // Retorna a nova ImageIcon redimensionada
        return new ImageIcon(imagemRedimensionada);
    }

    public static ImageIcon getCarta(String nomeArquivo) {
        String caminho = "cartas/" + nomeArquivo;
        return getImageIcon(caminho, ConfigCarta.LARGURA, ConfigCarta.ALTURA);
    }

    public static ImageIcon getUi(String nomeArquivo, int largura, int altura) {
        String caminho = "ui/" + nomeArquivo;
        return getImageIcon(caminho, largura, altura);
    }

    public static ImageIcon getBackground(String nomeArquivo, Dimension dimensaoTela) {
        String caminho = "ui/" + nomeArquivo;
        return getImageIcon(caminho, dimensaoTela.width, dimensaoTela.height);
    }

}
