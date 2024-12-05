package devdeck.utils.charts;

import devdeck.view.JogoApp;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Classe responsável por criar um gráfico de eficiência, representando
 * a evolução dos movimentos válidos e inválidos ao longo do tempo.
 */
public class GraficoEficiencia {
    private final JogoApp jogoApp;

    /**
     * Construtor que inicializa a classe com os dados do jogo.
     *
     * @param jogoApp Instância de {@link JogoApp} para acessar os dados do jogo.
     */
    public GraficoEficiencia(JogoApp jogoApp) {
        this.jogoApp = jogoApp;
    }

    /**
     * Gera um gráfico de linha que mostra a eficiência do jogador,
     * representada pelos movimentos válidos e inválidos ao longo do tempo.
     *
     * @return Instância de {@link JFreeChart} representando o gráfico de eficiência.
     */
    public JFreeChart getGrafico() {
        // Cria as séries de dados
        XYSeries validosSeries = new XYSeries("Movimentos Válidos");
        XYSeries invalidosSeries = new XYSeries("Movimentos Inválidos");

        // Popula as séries com os dados de duração da partida e movimentos
        for (int i = 0; i < jogoApp.getDuracaoPartida().size(); i++) {
            validosSeries.add(
                    jogoApp.getDuracaoPartida().get(i),
                    jogoApp.getMovimentosValidosAoLongoDoTempo().get(i)
            );
            invalidosSeries.add(
                    jogoApp.getDuracaoPartida().get(i),
                    jogoApp.getMovimentosInvalidosAoLongoDoTempo().get(i)
            );
        }

        // Adiciona as séries ao conjunto de dados
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(validosSeries);
        dataset.addSeries(invalidosSeries);

        // Cria e retorna o gráfico de linhas
        return ChartFactory.createXYLineChart(
                "Desempenho",    // Título do gráfico
                "Tempo (s)",            // Rótulo do eixo X
                "Movimentos",           // Rótulo do eixo Y
                dataset                 // Conjunto de dados
        );
    }
}