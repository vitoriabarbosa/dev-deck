package devdeck.utils;

import javax.swing.*;
import java.awt.*;

public class ImageCardUtil {
    public static ImageIcon getCarta(String nomeArquivo) {
        String caminho = "cards/" + nomeArquivo;
        System.out.println("Tentando carregar imagem: " + caminho);

        // Tenta carregar a imagem
        java.net.URL recurso = ClassLoader.getSystemResource(caminho);
        if (recurso == null) {
            System.err.println("Imagem não encontrada: " + caminho);
            return null;
        }

        // Carrega a imagem original
        ImageIcon imageIcon = new ImageIcon(recurso);

        // Redimensiona a imagem para se ajustar ao tamanho da carta
        Image imagemRedimensionada = imageIcon.getImage().getScaledInstance(
                ConfigCard.LARGURA_CARTA,
                ConfigCard.ALTURA_CARTA,
                Image.SCALE_SMOOTH
        );

        // Retorna a nova ImageIcon redimensionada
        return new ImageIcon(imagemRedimensionada);
    }
}
