package devdeck.view;

import devdeck.utils.charts.GraficoEficiencia;
import devdeck.utils.charts.GraficoMovimentos;
import devdeck.utils.component.EfeitoConfetes;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de Estatísticas para exibir gráficos de desempenho após o término do jogo.
 */
public class TelaEstatisticas extends JDialog {
    private final GraficoEficiencia graficoEficiencia;
    private final GraficoMovimentos graficoMovimentos;
    private final EfeitoConfetes efeitoConfetes;
    private final int pontuacao;


    public TelaEstatisticas(GraficoEficiencia graficoEficiencia, GraficoMovimentos graficoMovimentos, EfeitoConfetes efeitoConfetes, int pontuacao) {
        super((JFrame) null, "Estatísticas do Jogo", true);
        this.graficoEficiencia = graficoEficiencia;
        this.graficoMovimentos = graficoMovimentos;
        this.efeitoConfetes = efeitoConfetes;
        this.pontuacao = pontuacao;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1500, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); // Cor de fundo neutra

        inicializarLayout();

        // Adiciona listener para parar os confetes ao fechar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                efeitoConfetes.stopConfettiEffect(); // Para o efeito de confetes
            }
        });
    }

    private void inicializarLayout() {
        JPanel painelPrincipal = new JPanel(new GridLayout(1, 2, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.setBackground(new Color(240, 240, 240));

        JLabel pontuacaoLabel = new JLabel("Pontuação Final: " + pontuacao, SwingConstants.CENTER);
        pontuacaoLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        pontuacaoLabel.setForeground(new Color(70, 70, 70));

        // Adiciona os gráficos ao painel principal
        painelPrincipal.add(pontuacaoLabel, BorderLayout.NORTH);
        painelPrincipal.add(new ChartPanel(graficoEficiencia.getGrafico()));
        painelPrincipal.add(graficoMovimentos.getContentPane());

        // Título
        JLabel titulo = new JLabel("Estatísticas do Jogo", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(70, 70, 70));

        // Botão para fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        btnFechar.setFocusPainted(false);

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelRodape.add(btnFechar);

        // Adiciona os componentes à janela
        add(titulo, BorderLayout.NORTH);
        add(painelPrincipal, BorderLayout.CENTER);
        add(painelRodape, BorderLayout.SOUTH);
    }
}
