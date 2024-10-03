package devdeck.utils;

import javax.swing.*;
import java.awt.*;

public class RecursosUteis {

    private static ImageIcon getImageIcon(String caminho) {
        java.net.URL recurso = ClassLoader.getSystemResource(caminho);
        if (recurso == null) {
            System.err.println("Imagem não encontrada: " + caminho);
            return null;
        }
        // Carrega a imagem original
        ImageIcon imageIcon = new ImageIcon(recurso);

        // Redimensiona a imagem para se ajustar ao tamanho da carta
        Image imagemRedimensionada = imageIcon.getImage().getScaledInstance(
                ConfigCarta.LARGURA_CARTA,
                ConfigCarta.ALTURA_CARTA,
                Image.SCALE_SMOOTH
        );

        // Retorna a nova ImageIcon redimensionada
        return new ImageIcon(imagemRedimensionada);
    }

    public static ImageIcon getCarta(String nomeArquivo) {
        String caminho = "cartas/" + nomeArquivo;
        return getImageIcon(caminho);
    }

    public static ImageIcon getUi(String nomeArquivo) {
        String caminho = "ui/" + nomeArquivo;
        return getImageIcon(caminho);
    }
}
