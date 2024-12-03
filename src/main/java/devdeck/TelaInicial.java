package devdeck;

import devdeck.utils.ConfigPadrao;
import devdeck.utils.RecursoImagens;
import devdeck.utils.component.FundoPainel;
import devdeck.view.JogoApp;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa a tela inicial (splash screen) do jogo.
 * Esta tela é exibida por um curto período antes de redirecionar para a tela do jogo.
 */
public class TelaInicial extends JFrame {

    /**
     * Construtor da classe {@code InicialFrame}.
     * Configura a tela inicial, define seu tamanho, título e o fundo da tela.
     * O fundo é carregado a partir de um recurso de imagem.
     */
    public TelaInicial() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(ConfigPadrao.TAMANHO_TELA.width, ConfigPadrao.TAMANHO_TELA.height);
        setTitle("Welcome To Dev Deck!");

        Image imagemFundo = RecursoImagens.getBackground("tela-inicial.png", ConfigPadrao.TAMANHO_TELA).getImage();
        FundoPainel fundoPainel = new FundoPainel(imagemFundo);

        JPanel painel = new JPanel(new BorderLayout());
        painel.add(fundoPainel, BorderLayout.CENTER);
        add(painel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Método principal para iniciar a aplicação.
     * Cria uma instância da {@code InicialFrame} (tela inicial), exibe uma splash screen por 10 segundos
     * e depois abre a tela do jogo.
     *
     * @param args Argumentos da linha de comando (não utilizados neste contexto).
     */
    public static void main(String[] args) {
        // Cria a Splash Screen
        TelaInicial telaInicial = new TelaInicial();
        telaInicial.setVisible(true);

        // Mantém a splash screen visível por 10 segundos
        try {
            Thread.sleep(10000); // 10000 milissegundos = 10 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cria e mostra a tela do jogo
        SwingUtilities.invokeLater(() -> {
            JogoApp.main(new String[0]);
            telaInicial.setVisible(false);
        });
    }
}
