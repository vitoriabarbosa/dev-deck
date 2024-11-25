package devdeck.view;

import devdeck.model.Baralho;
import devdeck.model.NoCarta;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;
import devdeck.utils.charts.GraficoEficiencia;
import devdeck.utils.charts.GraficoMovimentos;
import devdeck.utils.component.EfeitoConfetes;
import devdeck.utils.event.CartaEvento;

import javax.swing.*;
import java.awt.*;
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
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Inicia o jogo
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

    private GraficoMovimentos graficoMovimentos;
    private int movimentosValidos = 0;
    private int movimentosInvalidos = 0;
    private int movimentosTotal = 0;

    private GraficoEficiencia graficoEficiencia;
    private Timer cronometroTimer;
    private int segundosDecorridos;

    private final List<Integer> movimentosValidosAoLongoDoTempo = new ArrayList<>();
    private final List<Integer> movimentosInvalidosAoLongoDoTempo = new ArrayList<>();
    private final List<Integer> tempoAoLongoDoTempo = new ArrayList<>();

    private int pontuacao = 0; // Pontuação atual do jogador
    private final int BONUS_FINAL = 100; // Bônus ao completar o jogo
    private final int PENALIDADE_INVALIDOS = -15; // Penalidade para movimentos inválidos
    private final int RECOMPENSA_VALIDOS = 20; // Pontos por movimentos válidos
    private final double BONUS_TEMPO = 0.5; // Multiplicador de tempo (ex.: 0.5 por segundo economizado)

    /**
     * Inicia um novo jogo, criando uma nova instância do jogo e sua interface gráfica.
     */
    public static void novoJogo() {
        // Se já existir uma janela de interface, ela será fechada.
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
        BARALHO = new Baralho();

        // Inicializa os montes, pilhas e listas
        this.iniciaMonte();
        this.inicializaPilhas();
        this.iniciaListas();

        // Inicializa a interface gráfica
        JogoApp.frame = new JogoGUI(this);

        // Instância de CartaEvento
        cartaEvento = new CartaEvento(frame, this);
        graficoMovimentos = new GraficoMovimentos(this.getMovimentosValidos(), this.getMovimentosInvalidos(), this.movimentosTotal);
        graficoEficiencia = new GraficoEficiencia(this);

        // Inicia o cronômetro
        this.iniciaCronometro((JogoGUI) JogoApp.frame);

        // Exibe o frame
        JogoApp.frame.setVisible(true);

    }

    // Métodos getters para acessar as variáveis
    public List<Integer> getMovimentosValidosAoLongoDoTempo() {
        return movimentosValidosAoLongoDoTempo;
    }

    public List<Integer> getMovimentosInvalidosAoLongoDoTempo() {
        return movimentosInvalidosAoLongoDoTempo;
    }

    public List<Integer> getTempoAoLongoDoTempo() {
        return tempoAoLongoDoTempo;
    }


    // Iniciar o cronômetro
    public void iniciaCronometro(JogoGUI jogoGUI) {
        segundosDecorridos = 0;

        cronometroTimer = new Timer(1000, e -> {
            segundosDecorridos++;
            int minutos = segundosDecorridos / 60;
            int segundos = segundosDecorridos % 60;
            String tempoFormatado = String.format("%02d:%02d", minutos, segundos);

            // Atualiza o cronômetro na GUI
            jogoGUI.atualizaCronometro(tempoFormatado);

            // Armazena os dados para o gráfico
            tempoAoLongoDoTempo.add(segundosDecorridos);
            movimentosValidosAoLongoDoTempo.add(movimentosValidos);
            movimentosInvalidosAoLongoDoTempo.add(movimentosInvalidos);
        });
        cronometroTimer.start();
    }

    // Parar o cronômetro, se necessário
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
            NoCarta carta = this.BARALHO.retiraCartaTopo();
            monteHome.inserir(carta);
        }
    }

    private void inicializaPilhas() {
        for (int i = 0; i < 4; i++) {
            PilhaHome pilha = new PilhaHome("Pilha " + (i + 1), this, cartaEvento);
            this.BASES[i] = pilha;
        }
    }

    /**
     * Inicia as listas de cartas. As listas são preenchidas com um número crescente de cartas, de 1 a 5.
     */
    private void iniciaListas() {
        for (int i = 0; i < 5; i++) {
            ListaHome listaHome = new ListaHome("Lista " + (i + 1), cartaEvento);

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

        atualizarPontuacao(true); // Incrementa a pontuação por movimento válido
        ((JogoGUI) frame).atualizaPontuacao(); // Atualiza a pontuação na GUI
    }

    public void incrementarMovimentosInvalidos() {
        movimentosInvalidos++;
        movimentosTotal = movimentosValidos + movimentosInvalidos;
        graficoMovimentos.atualizarDados(movimentosValidos, movimentosInvalidos, movimentosTotal);

        atualizarPontuacao(false); // Penaliza a pontuação por movimento inválido
        ((JogoGUI) frame).atualizaPontuacao(); // Atualiza a pontuação na GUI
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

    // Calcula a pontuação com base em critérios definidos
    public void atualizarPontuacao(boolean movimentoValido) {
        if (movimentoValido) {
            pontuacao += RECOMPENSA_VALIDOS;
        } else {
            pontuacao += PENALIDADE_INVALIDOS;
        }
    }

    // Bônus final ao concluir o jogo
    public void aplicarBonusFinal() {
        int tempoEconomizado = 120 - segundosDecorridos; // Exemplo: jogo deve ser completado em 150 segundos
        if (tempoEconomizado > 0) {
            pontuacao += BONUS_FINAL + (int) (tempoEconomizado * BONUS_TEMPO);
        } else {
            pontuacao += BONUS_FINAL; // Apenas o bônus final se exceder o tempo
        }
    }

    // Obter a pontuação atual
    public int getPontuacao() {
        return pontuacao;
    }

    /**
     * Método para verificar se o jogo acabou.
     * Chama as verificações de fim de jogo nas pilhas.
     */
    public void verificaFimDeJogo() {
        // Verifica todas as pilhas se estão completas
        boolean todasCompletas = true;
        for (PilhaHome pilha : BASES) {
            if (!pilha.verificarPilhaCompleta()) {
                todasCompletas = false;
                break;
            }
        }

        if (todasCompletas) {
            aplicarBonusFinal();
            ((JogoGUI) frame).atualizaPontuacao(); // Atualiza a pontuação após aplicar o bônus

            // Exibe mensagem de parabéns
            System.out.println("Você completou todas as pilhas! Parabéns!");
            paraCronometro();
            JOptionPane.showMessageDialog(frame, "Você completou todas as pilhas! Parabéns!");

            // Inicia o efeito de confetes diretamente no frame principal
            Dimension screenSize = frame.getSize();
            EfeitoConfetes efeitoConfetes = new EfeitoConfetes(screenSize);

            // Painel de confetes transparente sobre a tela do jogo
            JPanel confettiPanel = new JPanel(new BorderLayout());
            confettiPanel.setOpaque(false); // Transparente para manter a visibilidade do jogo
            confettiPanel.setBounds(0, 0, screenSize.width, screenSize.height);
            confettiPanel.add(efeitoConfetes, BorderLayout.CENTER);

            // Adiciona o painel de confetes ao frame
            JLayeredPane layeredPane = frame.getLayeredPane();
            layeredPane.add(confettiPanel, JLayeredPane.POPUP_LAYER); // Colocado acima dos elementos do jogo
            efeitoConfetes.startConfettiEffect();

            // Exibe a tela de estatísticas
            SwingUtilities.invokeLater(() -> {
                TelaEstatisticas telaEstatisticas = new TelaEstatisticas(graficoEficiencia, graficoMovimentos, efeitoConfetes, pontuacao);
                telaEstatisticas.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        efeitoConfetes.stopConfettiEffect(); // Para os confetes
                        layeredPane.remove(confettiPanel);  // Remove o painel de confetes
                        layeredPane.repaint();
                    }
                });
                telaEstatisticas.setVisible(true);
            });
        }
    }
}