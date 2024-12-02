package devdeck.utils.charts;

import devdeck.view.JogoApp;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class GraficoEficiencia {
    private final JogoApp jogoApp;

    public GraficoEficiencia(JogoApp jogoApp) {
        this.jogoApp = jogoApp;
    }

    public JFreeChart getGrafico() {
        // Cria as séries de dados
        XYSeries validosSeries = new XYSeries("Movimentos Válidos");
        XYSeries invalidosSeries = new XYSeries("Movimentos Inválidos");

        for (int i = 0; i < jogoApp.getDuracaoPartida().size(); i++) {
            validosSeries.add(jogoApp.getDuracaoPartida().get(i), jogoApp.getMovimentosValidosAoLongoDoTempo().get(i));
            invalidosSeries.add(jogoApp.getDuracaoPartida().get(i), jogoApp.getMovimentosInvalidosAoLongoDoTempo().get(i));
        }

        // Adiciona as séries ao conjunto de dados
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(validosSeries);
        dataset.addSeries(invalidosSeries);

        // Retorna o gráfico criado
        return ChartFactory.createXYLineChart(
                "Desempenho do Jogador",
                "Tempo (s)",
                "Movimentos",
                dataset
        );
    }
}