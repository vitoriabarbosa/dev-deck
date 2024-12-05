package devdeck.view;

import devdeck.utils.charts.*;
import devdeck.utils.component.EfeitoConfetes;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * TelaEstatisticas é um diálogo modal que exibe estatísticas de um jogo,
 * incluindo gráficos e informações detalhadas de pontuação.
 *
 * <p>Esta classe utiliza gráficos personalizados e um efeito de confetes,
 * se fornecido, para melhorar a experiência visual. As estatísticas exibidas
 * incluem movimentos válidos e inválidos, pontuação final, tempo total e um
 * cálculo de esforço total representado pela área sob o progresso dos movimentos.
 */

public class TelaEstatisticas extends JDialog {
    private final GraficoEficiencia graficoEficiencia;
    private final GraficoMovimentos graficoMovimentos;
    private final GraficoVelocidade graficoVelocidade;
    private final GraficoEsforcoTotal graficoEsforcoTotal;
    private final int pontuacao;
    private final List<Integer> movimentosValidosAoLongoDoTempo;
    private final List<Integer> movimentosInvalidosAoLongoDoTempo;
    private final List<Integer> duracaoPartida;
    private final int movimentosValidos;
    private final int movimentosInvalidos;
    private final int tempoFinal;

    /**
     * Construtor para inicializar os componentes gráficos e as estatísticas.
     *
     * @param graficoEficiencia gráfico de eficiência do jogador.
     * @param graficoMovimentos gráfico de movimentos do jogador.
     * @param graficoVelocidade gráfico de velocidade do jogador.
     * @param graficoEsforcoTotal gráfico de esforço total.
     * @param efeitoConfetes efeito visual opcional de confetes.
     * @param pontuacao pontuação final do jogador.
     * @param movimentosValidosAoLongoDoTempo lista de movimentos válidos ao longo do tempo.
     * @param movimentosInvalidosAoLongoDoTempo lista de movimentos inválidos ao longo do tempo.
     * @param duracaoPartida lista de tempos ao longo da partida.
     * @param movimentosValidos número total de movimentos válidos.
     * @param movimentosInvalidos número total de movimentos inválidos.
     * @param tempoFinal tempo total da partida em segundos.
     */
    public TelaEstatisticas(
            GraficoEficiencia graficoEficiencia,
            GraficoMovimentos graficoMovimentos,
            GraficoVelocidade graficoVelocidade,
            GraficoEsforcoTotal graficoEsforcoTotal,
            EfeitoConfetes efeitoConfetes,
            int pontuacao,
            List<Integer> movimentosValidosAoLongoDoTempo,
            List<Integer> movimentosInvalidosAoLongoDoTempo,
            List<Integer> duracaoPartida,
            int movimentosValidos,
            int movimentosInvalidos,
            int tempoFinal) {
        super((JFrame) null, "Estatísticas do Jogo", true);
        this.graficoEficiencia = graficoEficiencia;
        this.graficoMovimentos = graficoMovimentos;
        this.graficoVelocidade = graficoVelocidade;
        this.graficoEsforcoTotal = graficoEsforcoTotal;
        this.pontuacao = pontuacao;
        this.movimentosValidosAoLongoDoTempo = movimentosValidosAoLongoDoTempo;
        this.movimentosInvalidosAoLongoDoTempo = movimentosInvalidosAoLongoDoTempo;
        this.duracaoPartida = duracaoPartida;
        this.movimentosValidos = movimentosValidos;
        this.movimentosInvalidos = movimentosInvalidos;
        this.tempoFinal = tempoFinal;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240, 29));

        inicializarLayout();

        // Adiciona listener para parar os confetes ao fechar
        if (efeitoConfetes != null) {
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    efeitoConfetes.stopConfettiEffect(); // Para o efeito de confetes
                }
            });
        }
    }

    /**
     * Inicializa o layout da tela, incluindo os gráficos e o painel de estatísticas.
     */
    private void inicializarLayout() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(750);

        // Painel com gráficos
        JPanel painelGraficos = new JPanel(new GridLayout(2, 2, 10, 10));
        painelGraficos.setBorder(new EmptyBorder(10, 10, 10, 10));
        painelGraficos.add(new ChartPanel(graficoEficiencia.getGrafico()));
        painelGraficos.add(new ChartPanel(graficoMovimentos.getGrafico()));
        painelGraficos.add(new ChartPanel(graficoVelocidade.getGrafico()));
        painelGraficos.add(new ChartPanel(graficoEsforcoTotal.getGrafico()));

        // Painel com estatísticas
        JPanel painelEstatisticas = new JPanel(new BorderLayout(10, 10));
        painelEstatisticas.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel tituloPontuacao = new JLabel("Detalhamento da Pontuação", SwingConstants.CENTER);
        tituloPontuacao.setFont(new Font("SansSerif", Font.BOLD, 20));
        painelEstatisticas.add(tituloPontuacao, BorderLayout.NORTH);

        JTextArea areaTexto = getJTextArea();
        painelEstatisticas.add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFechar.setBackground(new Color(94, 23, 235));
        btnFechar.setForeground(new Color(231, 231, 231));
        btnFechar.setPreferredSize(new Dimension(getWidth(), 50));
        btnFechar.addActionListener(e -> dispose());
        painelEstatisticas.add(btnFechar, BorderLayout.SOUTH);

        // Adiciona os paineis ao SplitPane
        splitPane.setLeftComponent(painelGraficos);
        splitPane.setRightComponent(painelEstatisticas);

        // Adiciona o SplitPane à janela principal
        add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Cria e retorna um JTextArea com detalhes da pontuação do jogador.
     *
     * @return JTextArea contendo informações detalhadas da pontuação.
     */
    private JTextArea getJTextArea() {
        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 15));
        areaTexto.setEditable(false);

        double areaProgresso = calcularAreaProgresso();

        String detalhesPontuacao = String.format("""
                 
                 > Movimentos Válidos:        %d  (+20 cada)
                 > Movimentos Inválidos:      %d  (-15 cada)
                 > Pontos por Movimentos:     %d
                 > Tempo Total (segundos):    %d
                 > Bônus de Tempo:            %d
                ------------------------------------------
                
                 >>> Pontuação Final:         %d
                 
                ------------------------------------------
                
                
                 > Esforço Total (Área):      %.2f
                 
                 
                    O esforço total reflete o trabalho que 
                 você fez para concluir o jogo. Ele combina
                 quantos movimentos válidos você fez com a 
                 consistência ao longo do tempo.

                """,
                movimentosValidos,
                movimentosInvalidos,
                (movimentosValidos * 20) + (movimentosInvalidos * -15),
                tempoFinal,
                calcularBonusTempo(),
                pontuacao,
                areaProgresso);

        areaTexto.setText(detalhesPontuacao);
        return areaTexto;
    }


    /**
     * Calcula o bônus de tempo com base no tempo economizado.
     *
     * @return o bônus de tempo em pontos.
     */
    private int calcularBonusTempo() {
        int tempoEconomizado = Math.max(0, 120 - tempoFinal);
        double multiplicador = 0.5;
        return (int) (tempoEconomizado * multiplicador);
    }

    /**
     * Calcula a área de progresso do jogador, que reflete o esforço total.
     *
     * @return área de progresso como um valor numérico.
     */
    private double calcularAreaProgresso() {
        double area = 0;

        for (int i = 1; i < duracaoPartida.size(); i++) {
            int tempoAtual = duracaoPartida.get(i);
            int tempoAnterior = duracaoPartida.get(i - 1);

            int progressoAtual = movimentosValidosAoLongoDoTempo.get(i);
            int progressoAnterior = movimentosValidosAoLongoDoTempo.get(i - 1);

            area += 0.5 * (tempoAtual - tempoAnterior) * (progressoAtual + progressoAnterior);
        }
        return area;
    }
}
