package devdeck.view;

import devdeck.utils.ConfigPadrao;
import devdeck.utils.charts.*;
import devdeck.utils.component.EfeitoConfetes;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

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

    static final int TELA_WIDTH = (int) (ConfigPadrao.TAMANHO_TELA.width * 0.7);
    static final int TELA_HEIGTH = (int) (ConfigPadrao.TAMANHO_TELA.height * 0.6);

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
        setSize(TELA_WIDTH, TELA_HEIGTH);
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

    private void inicializarLayout() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(ConfigPadrao.TAMANHO_TELA.width / 2 - 130);

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

    private int calcularBonusTempo() {
        int tempoEconomizado = Math.max(0, 120 - tempoFinal);
        double multiplicador = 0.5;
        return (int) (tempoEconomizado * multiplicador);
    }

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
