package devdeck.view;

import devdeck.model.Baralho;
import devdeck.model.NoCarta;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;
import devdeck.utils.ConfigPadrao;
import devdeck.utils.RecursoImagens;
import devdeck.utils.charts.GraficoEficiencia;
import devdeck.utils.charts.GraficoEsforcoTotal;
import devdeck.utils.charts.GraficoMovimentos;
import devdeck.utils.charts.GraficoVelocidade;
import devdeck.utils.component.EfeitoConfetes;
import devdeck.utils.component.FundoPainel;
import devdeck.utils.event.CartaEvento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe {@code JogoApp} é responsável por gerenciar a lógica principal do jogo de paciência,
 * incluindo a inicialização do baralho, as listas, montes, pilhas-base e a interface gráfica.
 * Também contém métodos para validar regras do jogo e verificar o estado de término do jogo.
 */
public final class JogoApp {
    /**
     * O método principal que inicializa a aplicação do jogo.
     *
     * @param args Os argumentos da linha de comando.
     */
    public static void main(String[] args) {
        try {
            // Define o visual da interface para o padrão do sistema
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Inicia o jogo
            JogoApp.novoJogo();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro ao iniciar o jogo :(");
        }
    }

    private static final Baralho BARALHO = new Baralho();
    private static final PilhaHome[] BASES = new PilhaHome[4];
    private static final ListaHome[] LISTAS = new ListaHome[5];
    private final CartaEvento cartaEvento;
    private MonteHome monteHome;
    public static JFrame frame;

    private final GraficoMovimentos graficoMovimentos;
    private int movimentosValidos = 0;
    private int movimentosInvalidos = 0;
    private int movimentosTotal = 0;

    private final GraficoEficiencia graficoEficiencia;
    private Timer cronometroTimer;
    private int segundosDecorridos;

    private final GraficoVelocidade graficoVelocidade;
    private final GraficoEsforcoTotal graficoEsforcoTotal;

    private final List<Integer> movimentosValidosAoLongoDoTempo = new ArrayList<>();
    private final List<Integer> movimentosInvalidosAoLongoDoTempo = new ArrayList<>();
    private final List<Integer> duracaoPartida = new ArrayList<>();

    private int pontuacao = 0;

    /**
     * Inicia um novo jogo, criando uma nova instância do jogo e sua interface gráfica.
     */
    public static void novoJogo() {
        if (JogoApp.frame != null) {
            JogoApp.frame.dispose();
        }
        new JogoApp();
    }

    /**
     * Construtor privado da classe {@code JogoApp}.
     * Inicializa o baralho, as bases, as listas, os montes e a interface gráfica.
     */
    public JogoApp() {
        new Baralho();
        this.iniciaMonte();
        this.inicializaPilhas();
        this.iniciaListas();

        JogoApp.frame = new JogoInterface(this);
        cartaEvento = new CartaEvento(frame, this);

        graficoMovimentos = new GraficoMovimentos(this);
        graficoEficiencia = new GraficoEficiencia(this);
        graficoVelocidade = new GraficoVelocidade(this);
        graficoEsforcoTotal = new GraficoEsforcoTotal(this);

        this.iniciaCronometro((JogoInterface) JogoApp.frame);
        JogoApp.frame.setVisible(true);
    }

    // Métodos getters para acessar as variáveis
    public List<Integer> getMovimentosValidosAoLongoDoTempo() {
        return movimentosValidosAoLongoDoTempo;
    }

    public List<Integer> getMovimentosInvalidosAoLongoDoTempo() {
        return movimentosInvalidosAoLongoDoTempo;
    }

    public List<Integer> getDuracaoPartida() {
        return duracaoPartida;
    }

    public void iniciaCronometro(JogoInterface jogoGUI) {
        segundosDecorridos = 0;
        final int LIMITE_TEMPO_SEGUNDOS = 5 * 60; // 5 minutos

        cronometroTimer = new Timer(1000, e -> {
            segundosDecorridos++;
            int minutos = segundosDecorridos / 60;
            int segundos = segundosDecorridos % 60;
            String tempoFormatado = String.format("%02d:%02d", minutos, segundos);

            jogoGUI.atualizaCronometro(tempoFormatado);

            // Verifica se o limite de tempo foi atingido
            if (segundosDecorridos >= LIMITE_TEMPO_SEGUNDOS) {
                paraCronometro();
                JOptionPane.showMessageDialog(frame, "O tempo acabou! Melhor sorte na próxima vez.");
                // Fecha a janela principal do jogo
                frame.dispose();
                System.exit(0);
            }

            // Armazena os dados para o gráfico
            duracaoPartida.add(segundosDecorridos);
            movimentosValidosAoLongoDoTempo.add(movimentosValidos);
            movimentosInvalidosAoLongoDoTempo.add(movimentosInvalidos);
        });
        cronometroTimer.start();
    }

    public void paraCronometro() {
        if (cronometroTimer != null) {
            cronometroTimer.stop();
        }
    }

    /**
     * Inicia o monte de cartas, separando as 13 cartas do monte inicial.
     */
    public void iniciaMonte() {
        this.monteHome = new MonteHome(cartaEvento);
        for (int j = 0; j < 13; j++) {
            NoCarta carta = BARALHO.retiraCartaTopo();
            monteHome.inserir(carta);
        }
    }

    private void inicializaPilhas() {
        for (int i = 0; i < 4; i++) {
            PilhaHome pilha = new PilhaHome("Pilha " + (i + 1), this, cartaEvento);
            BASES[i] = pilha;
        }
    }

    /**
     * Inicia as listas de cartas. As listas são preenchidas com um número crescente de cartas, de 1 a 5.
     */
    private void iniciaListas() {
        for (int i = 0; i < 5; i++) {
            ListaHome listaHome = new ListaHome("Lista " + (i + 1));

            // Insere cartas retiradas do baralho nas listas
            for (int j = 0; j < (i + 1); j++) {
                NoCarta noCarta = BARALHO.retiraCartaTopo();
                noCarta.setHome(listaHome);
                listaHome.inserir(noCarta);
            }
            LISTAS[i] = listaHome;
        }
    }

    /**
     * Verifica se uma carta é a sequência válida de outra.
     * Uma carta é válida se for o próximo número na sequência e se ambas as cartas tiverem a mesma cor.
     *
     * @param carta1 A primeira carta a ser validada.
     * @param carta2 A segunda carta a ser validada.
     * @return {@code true} se a carta1 for a sequência válida da carta2, caso contrário, {@code false}.
     */
    public static boolean cartaSequenciaValida(NoCarta carta1, NoCarta carta2) {
        if (carta1 == null || carta2 == null) {
            return false;
        }
        return ((carta1.getNumero() == carta2.getNumero() + 1) && (carta1.getNaipe().getCor() == carta2.getNaipe().getCor()));
    }

    /**
     * Obtém as listas do jogo.
     *
     * @return Um array de {@link ListaHome} contendo as listas do jogo.
     */
    public ListaHome[] getLISTAS() {
        return LISTAS;
    }

    /**
     * Obtém o monte de cartas do jogo.
     *
     * @return O monte de cartas {@link MonteHome}.
     */
    public MonteHome getMonteHome() {
        return this.monteHome;
    }

    /**
     * Obtém as pilhas-base do jogo.
     *
     * @return Um array de {@link PilhaHome} contendo as pilhas-base.
     */
    public PilhaHome[] getBASES() {
        return BASES;
    }

    /**
     * Obtém o baralho do jogo.
     *
     * @return O {@link Baralho} do jogo.
     */
    public Baralho getBARALHO() {
        return BARALHO;
    }

    public void incrementarMovimentosValidos() {
        movimentosValidos++;
        movimentosTotal = movimentosValidos + movimentosInvalidos;
        graficoMovimentos.atualizarDados(movimentosValidos, movimentosInvalidos, movimentosTotal);

        atualizarPontuacao(true);
        ((JogoInterface) frame).atualizaPontuacao();
    }

    public void incrementarMovimentosInvalidos() {
        movimentosInvalidos++;
        movimentosTotal = movimentosValidos + movimentosInvalidos;
        graficoMovimentos.atualizarDados(movimentosValidos, movimentosInvalidos, movimentosTotal);

        atualizarPontuacao(false);
        ((JogoInterface) frame).atualizaPontuacao();
    }

    public int getMovimentosValidos() {
        return movimentosValidos;
    }

    public int getMovimentosInvalidos() {
        return movimentosInvalidos;
    }

    public int getMovimentosTotal() {
        return movimentosTotal;
    }

    public void atualizarPontuacao(boolean movimentoValido) {
        if (movimentoValido) {
            int recompensa = 20;
            pontuacao += recompensa;
        } else {
            int penalidade = -15;
            pontuacao += penalidade;
        }
    }

    public void aplicarBonusFinal() {
        int tempoEconomizado = 120 - segundosDecorridos; // Exemplo: jogo deve ser completado em 120 segundos
        int bonusFinal = 100;
        if (tempoEconomizado > 0) {
            // Multiplicador de tempo (ex.: 0.5 por segundo economizado)
            double bonusTempo = 0.5;
            pontuacao += bonusFinal + (int) (tempoEconomizado * bonusTempo);
        } else {
            pontuacao += bonusFinal; // Apenas o bônus final se exceder o tempo
        }
    }

    public int getPontuacao() {
        return pontuacao;
    }

    /**
     * Método para verificar se o jogo acabou.
     * Chama as verificações de fim de jogo nas pilhas.
     */
    public void verificaFimDeJogo() {
        boolean todasCompletas = true;
        for (PilhaHome pilha : BASES) {
            if (!pilha.verificarPilhaCompleta()) {
                todasCompletas = false;
                break;
            }
        }

        if (todasCompletas) {
            aplicarBonusFinal();
            ((JogoInterface) frame).atualizaPontuacao();
            paraCronometro();

            // Inicia o efeito de confetes
            EfeitoConfetes efeitoConfetes = new EfeitoConfetes(ConfigPadrao.TAMANHO_TELA);

            JPanel confetePanel = new JPanel(new BorderLayout());
            confetePanel.setOpaque(false);
            confetePanel.setBounds(0, 0, ConfigPadrao.TAMANHO_TELA.width, ConfigPadrao.TAMANHO_TELA.height);
            confetePanel.add(efeitoConfetes, BorderLayout.CENTER);

            JLayeredPane layeredPane = frame.getLayeredPane();
            layeredPane.add(confetePanel, JLayeredPane.POPUP_LAYER);
            efeitoConfetes.startConfettiEffect();

            final int telaWidth = (int) (ConfigPadrao.TAMANHO_TELA.width * 0.4);
            final int telaHeigth = (int) (ConfigPadrao.TAMANHO_TELA.height * 0.4);

            JDialog dialogParabens = new JDialog(frame, "Parabéns!", true);
            dialogParabens.setUndecorated(true);
            dialogParabens.setSize(telaWidth, telaHeigth);
            dialogParabens.setLocationRelativeTo(frame);

            ImageIcon imagemParabens = RecursoImagens.getBackground("parabens.png", new Dimension(telaWidth, telaHeigth));
            FundoPainel painelParabens = new FundoPainel(imagemParabens.getImage());
            painelParabens.setLayout(new BorderLayout());

            JButton btnContinuar = new JButton("Continuar");
            btnContinuar.setFont(new Font("Arial", Font.BOLD, 16));
            btnContinuar.setPreferredSize(new Dimension(300, 40));
            btnContinuar.setForeground(new Color(46,27,91,255));
            btnContinuar.setBackground(new Color(231, 231, 231));
            btnContinuar.setFocusPainted(false);
            btnContinuar.addActionListener(e -> {
                dialogParabens.dispose();
                exibirEstatisticas(efeitoConfetes, layeredPane, confetePanel);
            });

            painelParabens.add(btnContinuar, BorderLayout.SOUTH);
            dialogParabens.add(painelParabens);
            dialogParabens.setVisible(true);
        }
    }

    // Adiciona um pequeno atraso antes de exibir a tela de estatísticas
    private void exibirEstatisticas(EfeitoConfetes efeitoConfetes, JLayeredPane layeredPane, JPanel confetePanel) {
        SwingUtilities.invokeLater(() -> {
            TelaEstatisticas telaEstatisticas = new TelaEstatisticas(
                    graficoEficiencia,
                    graficoMovimentos,
                    graficoVelocidade,
                    graficoEsforcoTotal,
                    efeitoConfetes, // Reutilizando o mesmo efeito de confetes
                    pontuacao,
                    movimentosValidosAoLongoDoTempo,
                    movimentosInvalidosAoLongoDoTempo,
                    duracaoPartida,
                    movimentosValidos,
                    movimentosInvalidos,
                    segundosDecorridos
            );
            telaEstatisticas.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    efeitoConfetes.stopConfettiEffect();
                    layeredPane.remove(confetePanel);
                    layeredPane.repaint();

                    frame.dispose();
                    System.exit(0);
                }
            });
            telaEstatisticas.setVisible(true);
        });
    }
}