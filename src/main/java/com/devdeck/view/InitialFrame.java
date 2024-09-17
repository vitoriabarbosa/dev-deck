package com.devdeck.view;

import com.devdeck.util.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Classe que representa a tela inicial (splash screen) do sistema de gerenciamento de supermercado.
 * Esta tela é exibida por um curto período antes de redirecionar para a tela do jogo.
 */
public class InitialFrame extends JFrame {

    public InitialFrame() {
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Adiciona o painel de fundo com a imagem redimensionada
        BackgroundPanel backgroundPanel = new BackgroundPanel(new ImageIcon(Objects.requireNonNull(InitialFrame.class.getResource("/background.png"))).getImage());

        JPanel content = new JPanel(new BorderLayout());
        content.add(backgroundPanel, BorderLayout.CENTER);

        add(content);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Método principal para iniciar a aplicação.
     * Cria uma instância da InitialFrame (tela inicial), exibe uma splash screen por 10 segundos
     * e depois abre a tela de jogo.
     *
     * @param args Argumentos da linha de comando (não utilizados neste contexto).
     */
    public static void main(String[] args) {
        // Cria a Splash Screen
        InitialFrame initialFrame = new InitialFrame();
        initialFrame.setVisible(true);

        // Mantém a splash screen visível por 10 segundos
        try {
            Thread.sleep(10000); // 10000 milissegundos = 10 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Fecha a splash screen
        initialFrame.setVisible(false);
        initialFrame.dispose();

        // Cria e mostra a tela do jogo
        SwingUtilities.invokeLater(() -> {
            GameApp.main(new String[0]);
        });
    }
}
