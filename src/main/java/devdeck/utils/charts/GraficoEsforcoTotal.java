package devdeck.utils.charts;

import devdeck.view.JogoApp;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

/**
 * Classe para criar um gráfico de Esforço Total ao longo do tempo.
 */
public class GraficoEsforcoTotal {
    private final JogoApp jogoApp;

    /**
     * Construtor que inicializa o gráfico com os dados do jogo.
     *
     * @param jogoApp Instância do JogoApp para acessar os dados do jogo.
     */
    public GraficoEsforcoTotal(JogoApp jogoApp) {
        this.jogoApp = jogoApp;
    }

    /**
     * Gera o gráfico de esforço total com base nos dados do jogo.
     *
     * @return Instância de JFreeChart representando o gráfico.
     */
    public JFreeChart getGrafico() {
        // Série de dados para o esforço total
        XYSeries esforcoTotalSeries = new XYSeries("Esforço Total");

        List<Integer> duracaoPartida = jogoApp.getDuracaoPartida();
        List<Integer> movimentosValidos = jogoApp.getMovimentosValidosAoLongoDoTempo();

        double areaAcumulada = 0;

        // Calcula o esforço total como a soma das áreas trapezoidais
        for (int i = 1; i < duracaoPartida.size(); i++) {
            int tempoAtual = duracaoPartida.get(i);
            int tempoAnterior = duracaoPartida.get(i - 1);

            int progressoAtual = movimentosValidos.get(i);
            int progressoAnterior = movimentosValidos.get(i - 1);

            double area = 0.5 * (tempoAtual - tempoAnterior) * (progressoAtual + progressoAnterior);
            areaAcumulada += area;

            // Adiciona o ponto ao gráfico
            esforcoTotalSeries.add((double) tempoAtual, areaAcumulada);
        }

        // Adiciona a série ao conjunto de dados
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(esforcoTotalSeries);

        // Cria o gráfico de linhas
        return ChartFactory.createXYLineChart(
                "Esforço Total ao Longo do Tempo",
                "Tempo (s)",
                "Área Acumulada",
                dataset
        );
    }
}
