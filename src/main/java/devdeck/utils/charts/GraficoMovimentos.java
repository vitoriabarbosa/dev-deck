package devdeck.utils.charts;

import devdeck.view.JogoApp;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Classe responsável por criar um gráfico de barras representando os movimentos
 * (válidos, inválidos e total) de um jogo.
 */
public class GraficoMovimentos {
    private final DefaultCategoryDataset dataset;
    private final JogoApp jogoApp;

    /**
     * Construtor que inicializa o gráfico com os dados de movimentos extraídos do jogo.
     *
     * @param jogoApp Instância de {@link JogoApp} para acessar os dados do jogo.
     */
    public GraficoMovimentos(JogoApp jogoApp) {
        this.jogoApp = jogoApp;
        this.dataset = new DefaultCategoryDataset();
        inicializarDados();
    }

    /**
     * Inicializa o conjunto de dados para o gráfico com base nos dados do jogo.
     */
    private void inicializarDados() {
        dataset.setValue(jogoApp.getMovimentosValidos(), "Movimentos", "Válidos");
        dataset.setValue(jogoApp.getMovimentosInvalidos(), "Movimentos", "Inválidos");
        dataset.setValue(jogoApp.getMovimentosTotal(), "Movimentos", "Total");
    }

    /**
     * Retorna o gráfico de barras com os dados de movimentos.
     * O gráfico apresenta três categorias: movimentos válidos, inválidos e total.
     *
     * @return Instância de {@link JFreeChart} representando o gráfico de barras.
     */
    public JFreeChart getGrafico() {
        return ChartFactory.createBarChart(
                "Movimentos",   // Título do gráfico
                "Tipo de Movimento",   // Rótulo do eixo X
                "Quantidade",          // Rótulo do eixo Y
                dataset                // Conjunto de dados
        );
    }

    /**
     * Atualiza os dados do gráfico com novos valores para movimentos válidos, inválidos e total.
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
