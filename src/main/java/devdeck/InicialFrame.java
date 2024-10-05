package devdeck;

import devdeck.utils.RecursoImagens;
import devdeck.utils.component.FundoPainel;
import devdeck.view.JogoApp;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa a tela inicial (splash screen) do sistema de gerenciamento de supermercado.
 * Esta tela é exibida por um curto período antes de redirecionar para a tela do jogo.
 */
public class InicialFrame extends JFrame {
    public InicialFrame() {
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setTitle("Welcome To DevDeck!");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Image imagemFundo = RecursoImagens.getBackground("tela-inicial.png", screenSize).getImage();
        FundoPainel backgroundPanel = new FundoPainel(imagemFundo);

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
        InicialFrame initialFrame = new InicialFrame();
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
            JogoApp.main(new String[0]);
        });
    }
}
