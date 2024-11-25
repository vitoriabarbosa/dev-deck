package devdeck.utils.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

/**
 * A classe {@code GraficoMovimentos} exibe um gráfico de barras mostrando
 * os movimentos válidos e inválidos durante a partida.
 */
public class    GraficoMovimentos extends JFrame {
    private DefaultCategoryDataset dataset;

    private int movimentosValidos;
    private int movimentosInvalidos;
    private int movimentosTotal;

    /**
     * Constrói uma nova instância de {@code GraficoMovimentos}.
     *
     * @param validos  número de movimentos válidos
     * @param invalidos número de movimentos inválidos
     * @param total número total de movimentos
     */
    public GraficoMovimentos(int validos, int invalidos, int total) {
        this.movimentosValidos = validos;
        this.movimentosInvalidos = invalidos;
        this.movimentosTotal = total;

        setTitle("Estatísticas de Movimentos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Cria o gráfico e adiciona ao painel
        JFreeChart chart = createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        setContentPane(chartPanel);
    }

    /**
     * Cria o gráfico de barras com base nos dados de movimentos.
     *
     * @return o gráfico criado
     */
    private JFreeChart createChart() {
        dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Estatísticas de Movimentos", // Título
                "Tipo de Movimento",         // Eixo X
                "Quantidade",                // Eixo Y
                dataset
        );

        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(57, 110, 255)); // Cor para "Válidos"
        chart.getCategoryPlot().getRenderer().setSeriesPaint(1, new Color(253, 65, 113)); // Cor para "Inválidos"
        chart.getCategoryPlot().getRenderer().setSeriesPaint(2, new Color(94,23,235));

        return chart;
    }

    /**
     * Atualiza o gráfico com novos valores.
     *
     * @param movimentosValidos  número atualizado de movimentos válidos
     * @param movimentosInvalidos número atualizado de movimentos inválidos
     */
    public void atualizarDados(int movimentosValidos, int movimentosInvalidos, int movimentosTotal) {
        this.movimentosValidos = movimentosValidos;
        this.movimentosInvalidos = movimentosInvalidos;
        this.movimentosTotal = movimentosTotal;

        // Atualiza os dados no dataset
        dataset.setValue(movimentosValidos, "Movimentos Válidos", "Válidos");
        dataset.setValue(movimentosInvalidos, "Movimentos Inválidos", "Inválidos");
        dataset.setValue(movimentosTotal, "Movimento Total", "Total");

        // Redesenha o gráfico
        this.revalidate();
        this.repaint();
    }

    /**
     * Método para exibir o gráfico.
     */
    public void exibir() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Exibindo gráfico...");
            System.out.println("Movimentos Válidos: " + movimentosValidos +
                    ", Inválidos: " + movimentosInvalidos + ", Total: " + movimentosTotal);
            this.setVisible(true);
        });
    }
}
