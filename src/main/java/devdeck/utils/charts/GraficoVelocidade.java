package devdeck.utils.charts;

import devdeck.view.JogoApp;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

public class GraficoVelocidade {
    private final JogoApp jogoApp;

    /**
     * Construtor que inicializa o gráfico com os dados do jogo.
     *
     * @param jogoApp Instância do JogoApp para acessar os dados do jogo.
     */
    public GraficoVelocidade(JogoApp jogoApp) {
        this.jogoApp = jogoApp;
    }

    /**
     * Gera o gráfico de velocidade do progresso com base nos dados do jogo.
     *
     * @return Instância de JFreeChart representando o gráfico.
     */
    public JFreeChart getGrafico() {
        // Série de dados para velocidade do progresso
        XYSeries velocidadeSeries = new XYSeries("Velocidade do Progresso");

        List<Integer> duracaoPartida = jogoApp.getDuracaoPartida();
        List<Integer> movimentosValidos = jogoApp.getMovimentosValidosAoLongoDoTempo();

        // Calcula a velocidade do progresso
        for (int i = 1; i < duracaoPartida.size(); i++) {
            int deltaProgresso = movimentosValidos.get(i) - movimentosValidos.get(i - 1);
            int deltaTempo = duracaoPartida.get(i) - duracaoPartida.get(i - 1);

            double velocidade = deltaTempo == 0 ? 0 : (double) deltaProgresso / deltaTempo;
            velocidadeSeries.add((double) duracaoPartida.get(i), velocidade);
        }

        // Adiciona a série ao conjunto de dados
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(velocidadeSeries);

        // Cria o gráfico
        return ChartFactory.createXYLineChart(
                "Velocidade do Progresso",
                "Tempo (s)",
                "Cartas/s",
                dataset
        );
    }
}
