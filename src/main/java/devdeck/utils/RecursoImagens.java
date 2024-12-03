package devdeck.utils;

import javax.swing.*;
import java.awt.*;

/**
 * A classe {@code RecursoImagens} fornece métodos para carregar e redimensionar
 * imagens a partir de recursos do projeto. É utilizada para obter imagens de
 * cartas, interfaces de usuário e fundos para a aplicação.
 */
public class RecursoImagens {

    /**
     * Método que recebe o caminho da imagem e os tamanhos desejados,
     * e retorna uma {@code ImageIcon} redimensionada.
     *
     * @param caminho O caminho relativo da imagem a ser carregada.
     * @param largura A largura desejada da imagem redimensionada.
     * @param altura A altura desejada da imagem redimensionada.
     * @return A {@code ImageIcon} redimensionada ou {@code null} se a imagem
     *         não for encontrada.
     */
    private static ImageIcon getImageIcon(String caminho, int largura, int altura) {
        // Usa o classloader da própria classe para encontrar o recurso
        java.net.URL recurso = RecursoImagens.class.getResource("/" + caminho);
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

    /**
     * Obtém a {@code ImageIcon} de uma carta específica, redimensionada para
     * as dimensões configuradas.
     *
     * @param nomeArquivo O nome do arquivo da carta a ser carregada.
     * @return A {@code ImageIcon} da carta redimensionada.
     */
    public static ImageIcon getCarta(String nomeArquivo) {
        String caminho = "cartas/" + nomeArquivo;
        return getImageIcon(caminho, ConfigPadrao.LARGURA_CARTA, ConfigPadrao.ALTURA_CARTA);
    }

    /**
     * Obtém a {@code ImageIcon} de um recurso de interface do usuário,
     * redimensionada para as dimensões especificadas.
     *
     * @param nomeArquivo O nome do arquivo da interface a ser carregada.
     * @param largura A largura desejada da imagem.
     * @param altura A altura desejada da imagem.
     * @return A {@code ImageIcon} da interface do usuário redimensionada.
     */
    public static ImageIcon getUi(String nomeArquivo, int largura, int altura) {
        String caminho = "ui/" + nomeArquivo;
        return getImageIcon(caminho, largura, altura);
    }

    /**
     * Obtém a {@code ImageIcon} de um fundo, redimensionada para as dimensões
     * da tela especificadas.
     *
     * @param nomeArquivo O nome do arquivo do fundo a ser carregado.
     * @param dimensaoTela As dimensões da tela para redimensionar a imagem.
     * @return A {@code ImageIcon} do fundo redimensionada.
     */
    public static ImageIcon getBackground(String nomeArquivo, Dimension dimensaoTela) {
        String caminho = "ui/" + nomeArquivo;
        return getImageIcon(caminho, dimensaoTela.width, dimensaoTela.height);
    }
}
