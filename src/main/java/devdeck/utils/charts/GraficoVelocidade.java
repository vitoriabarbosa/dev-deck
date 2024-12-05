package devdeck.utils.charts;

import devdeck.view.JogoApp;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

/**
 * Classe responsável por gerar um gráfico de velocidade do progresso do jogo.
 * O gráfico exibe a taxa de progresso em termos de movimentos válidos ao longo do tempo.
 */
public class GraficoVelocidade {
    private final JogoApp jogoApp;

    /**
     * Construtor que inicializa o gráfico com os dados do jogo.
     *
     * @param jogoApp Instância do {@link JogoApp} para acessar os dados do jogo.
     */
    public GraficoVelocidade(JogoApp jogoApp) {
        this.jogoApp = jogoApp;
    }

    /**
     * Gera o gráfico de velocidade do progresso com base nos dados do jogo.
     * A velocidade é calculada como a diferença nos movimentos válidos dividida
     * pela diferença no tempo entre dois pontos consecutivos.
     *
     * @return Instância de {@link JFreeChart} representando o gráfico de velocidade do progresso.
     */
    public JFreeChart getGrafico() {
        // Série de dados para velocidade do progresso
        XYSeries velocidadeSeries = new XYSeries("Velocidade do Progresso");

        // Obtém os dados do jogo
        List<Integer> duracaoPartida = jogoApp.getDuracaoPartida();
        List<Integer> movimentosValidos = jogoApp.getMovimentosValidosAoLongoDoTempo();

        // Calcula a velocidade do progresso em cada intervalo de tempo
        for (int i = 1; i < duracaoPartida.size(); i++) {
            int deltaProgresso = movimentosValidos.get(i) - movimentosValidos.get(i - 1);
            int deltaTempo = duracaoPartida.get(i) - duracaoPartida.get(i - 1);

            // Evita divisão por zero e calcula a velocidade
            double velocidade = deltaTempo == 0 ? 0 : (double) deltaProgresso / deltaTempo;
            velocidadeSeries.add((double) duracaoPartida.get(i), velocidade);
        }

        // Adiciona a série ao conjunto de dados
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(velocidadeSeries);

        // Cria e retorna o gráfico
        return ChartFactory.createXYLineChart(
                "Velocidade do Progresso", // Título do gráfico
                "Tempo (s)",             // Rótulo do eixo X
                "Cartas/s",              // Rótulo do eixo Y
                dataset                  // Dados do gráfico
        );
    }
}
