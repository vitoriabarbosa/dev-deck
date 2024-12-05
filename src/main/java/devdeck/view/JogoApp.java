package devdeck.view;

import devdeck.model.Baralho;
import devdeck.model.NoCarta;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;
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
import java.util.ArrayList;
import java.util.List;

/**
 * A classe {@code JogoApp} é responsável por gerenciar a lógica principal do jogo de paciência.
 * Ela inclui a inicialização do baralho, listas de cartas, montes, pilhas-base, a interface gráfica
 * e a validação das regras do jogo. Também gerencia o estado de término da partida e a pontuação do jogador.
 *
 * <p>Principais funcionalidades:
 * <ul>
 *     <li>Inicia e gerencia os componentes do jogo.</li>
 *     <li>Controla o cronômetro e gráficos de desempenho.</li>
 *     <li>Valida as regras de movimentação das cartas.</li>
 *     <li>Calcula e atualiza a pontuação do jogador.</li>
 *     <li>Verifica condições de término do jogo.</li>
 * </ul>
 *
 * <p>Esta classe utiliza diversas classes auxiliares para implementar funcionalidades como gráficos,
 * animações e manipulação de eventos de cartas.</p>
 *
 * @see devdeck.model.Baralho
 * @see devdeck.model.home.ListaHome
 * @see devdeck.model.home.MonteHome
 * @see devdeck.model.home.PilhaHome
 * @see devdeck.utils.charts.GraficoMovimentos
 * @see devdeck.utils.charts.GraficoEficiencia
 * @see devdeck.utils.component.EfeitoConfetes
 */

public final class JogoApp {
    /**
     * Método principal do aplicativo, responsável por iniciar o jogo.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        try {
            JogoApp.novoJogo();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro ao iniciar o jogo :(");
        }
    }

    private final Baralho BARALHO;
    private final PilhaHome[] BASES = new PilhaHome[4];
    private final ListaHome[] LISTAS = new ListaHome[5];
    private MonteHome monteHome;
    public static JFrame frame;
    private final CartaEvento cartaEvento;

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
     * Construtor da classe {@code JogoApp}.
     * Inicializa os componentes principais do jogo, incluindo o baralho, listas, montes e interface gráfica.
     */
    public JogoApp() {
        BARALHO = new Baralho();
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

    // Métodos de inicialização

    /**
     * Inicializa o monte de cartas, transferindo 13 cartas do baralho para o monte.
     */
    public void iniciaMonte() {
        this.monteHome = new MonteHome(cartaEvento);
        for (int j = 0; j < 13; j++) {
            NoCarta carta = this.BARALHO.retiraCartaTopo();
            monteHome.inserir(carta);
        }
    }

    /**
     * Inicializa as pilhas-base onde as cartas serão organizadas por naipe.
     */
    private void inicializaPilhas() {
        for (int i = 0; i < 4; i++) {
            PilhaHome pilha = new PilhaHome("Pilha " + (i + 1), this, cartaEvento);
            this.BASES[i] = pilha;
        }
    }

    /**
     * Inicializa as listas de cartas, preenchendo-as com números crescentes de cartas.
     */
    private void iniciaListas() {
        for (int i = 0; i < 5; i++) {
            ListaHome listaHome = new ListaHome("Lista " + (i + 1));

            // Insere cartas retiradas do baralho nas listas
            for (int j = 0; j < (i + 1); j++) {
                NoCarta noCarta = this.BARALHO.retiraCartaTopo();
                noCarta.setHome(listaHome);
                listaHome.inserir(noCarta);
            }
            this.LISTAS[i] = listaHome;
        }
    }

    /**
     * Inicia o cronômetro do jogo, atualizando o tempo decorrido e verificando o limite de tempo.
     *
     * @param jogoInterface A interface gráfica principal do jogo.
     */
    public void iniciaCronometro(JogoInterface jogoInterface) {
        segundosDecorridos = 0;
        final int LIMITE_TEMPO_SEGUNDOS = 5 * 60; // 5 minutos

        cronometroTimer = new Timer(1000, e -> {
            segundosDecorridos++;
            int minutos = segundosDecorridos / 60;
            int segundos = segundosDecorridos % 60;
            String tempoFormatado = String.format("%02d:%02d", minutos, segundos);

            jogoInterface.atualizaCronometro(tempoFormatado);

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

    // Métodos utilitários e de controle do jogo

    /**
     * Verifica se duas cartas estão em sequência válida.
     *
     * @param carta1 Primeira carta a ser analisada.
     * @param carta2 Segunda carta a ser analisada.
     * @return {@code true} se a sequência for válida, {@code false} caso contrário.
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
        return this.LISTAS;
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
        return this.BASES;
    }

    /**
     * Obtém o baralho do jogo.
     *
     * @return O {@link Baralho} do jogo.
     */
    public Baralho getBARALHO() {
        return this.BARALHO;
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
            Dimension screenSize = frame.getSize();
            EfeitoConfetes efeitoConfetes = new EfeitoConfetes(screenSize);

            JPanel confettiPanel = new JPanel(new BorderLayout());
            confettiPanel.setOpaque(false);
            confettiPanel.setBounds(0, 0, screenSize.width, screenSize.height);
            confettiPanel.add(efeitoConfetes, BorderLayout.CENTER);

            JLayeredPane layeredPane = frame.getLayeredPane();
            layeredPane.add(confettiPanel, JLayeredPane.POPUP_LAYER);
            efeitoConfetes.startConfettiEffect();

            JDialog dialogParabens = new JDialog(frame, "Parabéns!", true);
            dialogParabens.setUndecorated(true);
            dialogParabens.setSize(500, 300);
            dialogParabens.setLocationRelativeTo(frame);

            ImageIcon imagemParabens = RecursoImagens.getBackground("parabens.png", new Dimension(500, 300));
            FundoPainel painelParabens = new FundoPainel(imagemParabens.getImage());
            painelParabens.setLayout(new BorderLayout());

            // Botão "Continuar"
            JButton btnContinuar = new JButton("Continuar");
            btnContinuar.setFont(new Font("Arial", Font.BOLD, 16));
            btnContinuar.setPreferredSize(new Dimension(300, 40));
            btnContinuar.setForeground(new Color(46,27,91,255));
            btnContinuar.setBackground(new Color(231, 231, 231));
            btnContinuar.setFocusPainted(false);
            btnContinuar.addActionListener(e -> {
                dialogParabens.dispose();
                exibirEstatisticas(efeitoConfetes, layeredPane, confettiPanel);
            });

            painelParabens.add(btnContinuar, BorderLayout.SOUTH);
            dialogParabens.add(painelParabens);
            dialogParabens.setVisible(true);
        }
    }

    // Adiciona um pequeno atraso antes de exibir a tela de estatísticas
    private void exibirEstatisticas(EfeitoConfetes efeitoConfetes, JLayeredPane layeredPane, JPanel confettiPanel) {
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
            telaEstatisticas.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    efeitoConfetes.stopConfettiEffect();
                    layeredPane.remove(confettiPanel);
                    layeredPane.repaint();

                    frame.dispose();
                    System.exit(0);
                }
            });
            telaEstatisticas.setVisible(true);
        });
    }

}