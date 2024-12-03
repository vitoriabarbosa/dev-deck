package devdeck.utils.charts;

import devdeck.view.JogoApp;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficoMovimentos {
    private final DefaultCategoryDataset dataset;
    private final JogoApp jogoApp;

    /**
     * Construtor que inicializa o gráfico com os dados de movimentos.
     *
     * @param jogoApp Instância de JogoApp para acessar os dados do jogo.
     */
    public GraficoMovimentos(JogoApp jogoApp) {
        this.jogoApp = jogoApp;
        this.dataset = new DefaultCategoryDataset();
        inicializarDados();
    }

    /**
     * Inicializa o conjunto de dados do gráfico.
     */
    private void inicializarDados() {
        dataset.setValue(jogoApp.getMovimentosValidos(), "Movimentos", "Válidos");
        dataset.setValue(jogoApp.getMovimentosInvalidos(), "Movimentos", "Inválidos");
        dataset.setValue(jogoApp.getMovimentosTotal(), "Movimentos", "Total");
    }

    /**
     * Retorna o gráfico de barras com os dados de movimentos.
     *
     * @return Instância de JFreeChart.
     */
    public JFreeChart getGrafico() {
        return ChartFactory.createBarChart(
                "Movimentos", // Título do gráfico
                "Tipo de Movimento",         // Eixo X
                "Quantidade",                // Eixo Y
                dataset
        );
    }

    /**
     * Atualiza os dados do gráfico.
     *
     * @param movimentosValidos   Número de movimentos válidos.
     * @param movimentosInvalidos Número de movimentos inválidos.
     * @param movimentosTotal     Número total de movimentos.
     */
    public void atualizarDados(int movimentosValidos, int movimentosInvalidos, int movimentosTotal) {
        dataset.setValue(movimentosValidos, "Movimentos", "Válidos");
        dataset.setValue(movimentosInvalidos, "Movimentos", "Inválidos");
        dataset.setValue(movimentosTotal, "Movimentos", "Total");
    }
}
