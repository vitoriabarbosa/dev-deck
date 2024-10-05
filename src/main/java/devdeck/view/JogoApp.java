package devdeck.view;

import devdeck.model.Baralho;
import devdeck.model.NoCarta;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;

import javax.swing.*;

/**
 * A classe {@code JogoApp} é responsável por gerenciar a lógica principal do jogo de paciência,
 * incluindo a inicialização do baralho, as listas, montes, pilhas-base e a interface gráfica.
 * Também contém métodos para validar regras do jogo e verificar o estado de término do jogo.
 */
public final class JogoApp {

    /**
     * O método principal que inicializa a aplicação do jogo.
     *
     * @param args Os argumentos da linha de comando.
     */
    public static void main(String[] args) {
        try {
            // Define o visual da interface para o padrão do sistema
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());

            // Inicia o jogo
            JogoApp.novoJogo();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro ao iniciar o jogo :(");
        }
    }

    private final Baralho BARALHO;
    private final PilhaHome[] BASES = new PilhaHome[4];
    private final ListaHome[] LISTAS = new ListaHome[5];
    private MonteHome monteHome;
    public static JFrame frame;

    /**
     * Inicia um novo jogo, criando uma nova instância do jogo e sua interface gráfica.
     */
    public static void novoJogo() {
        // Se já existir uma janela de interface, ela será fechada.
        if (JogoApp.frame != null) {
            JogoApp.frame.dispose();
        }
        new JogoApp();
    }

    /**
     * Construtor privado da classe {@code JogoApp}.
     * Inicializa o baralho, as bases, as listas, os montes e a interface gráfica.
     */
    private JogoApp() {
        BARALHO = new Baralho();

        // Cria as pilhas-base para cartas finais
        BASES[0] = new PilhaHome("Pilha 1");
        BASES[1] = new PilhaHome("Pilha 2");
        BASES[2] = new PilhaHome("Pilha 3");
        BASES[3] = new PilhaHome("Pilha 4");

        // Inicializa os montes e listas
        this.iniciaMonte();
        this.iniciaListas();

        // Inicializa a interface gráfica
        JogoApp.frame = new JogoGUI(this);
        JogoApp.frame.setVisible(true);
    }

    /**
     * Inicia o monte de cartas, separando as 13 cartas do monte inicial.
     */
    public void iniciaMonte() {
        this.monteHome = new MonteHome();
        for (int j = 0; j < 13; j++) {
            NoCarta carta = this.BARALHO.retiraCartaTopo();
            monteHome.inserir(carta);
        }
    }

    /**
     * Inicia as listas de cartas. As listas são preenchidas com um número crescente de cartas, de 1 a 5.
     */
    private void iniciaListas() {
        for (int i = 0; i < 5; i++) {
            ListaHome listaHome = new ListaHome("Lista " + (i + 1));

            // Insere cartas retiradas do baralho nas listas
            for (int j = 0; j < (i + 1); j++) {
                NoCarta noCarta = this.BARALHO.retiraCartaTopo();
                noCarta.setHome(listaHome);
                listaHome.inserir(noCarta);
            }
            this.LISTAS[i] = listaHome;
        }
    }

    /**
     * Verifica se uma carta é a sequência válida de outra.
     * Uma carta é válida se for o próximo número na sequência e se ambas as cartas tiverem a mesma cor.
     *
     * @param carta1 A primeira carta a ser validada.
     * @param carta2 A segunda carta a ser validada.
     * @return {@code true} se a carta1 for a sequência válida da carta2, caso contrário, {@code false}.
     */
    public static boolean cartaSequenciaValida(NoCarta carta1, NoCarta carta2) {
        if (carta1 == null || carta2 == null) {
            return false;
        }
        return ((carta1.getNumero() == carta2.getNumero() + 1) && (carta1.getNaipe().getCor() == carta2.getNaipe().getCor()));
    }

    /**
     * Obtém as listas do jogo.
     *
     * @return Um array de {@link ListaHome} contendo as listas do jogo.
     */
    public ListaHome[] getLISTAS() {
        return this.LISTAS;
    }

    /**
     * Obtém o monte de cartas do jogo.
     *
     * @return O monte de cartas {@link MonteHome}.
     */
    public MonteHome getMonteHome() {
        return this.monteHome;
    }

    /**
     * Obtém as pilhas-base do jogo.
     *
     * @return Um array de {@link PilhaHome} contendo as pilhas-base.
     */
    public PilhaHome[] getBASES() {
        return this.BASES;
    }

    /**
     * Obtém o baralho do jogo.
     *
     * @return O {@link Baralho} do jogo.
     */
    public Baralho getBARALHO() {
        return this.BARALHO;
    }

    /**
     * Verifica se o jogo acabou.
     * O jogo é considerado concluído quando o topo de todas as quatro pilhas-base contém uma carta de valor "K" (Rei).
     */
    public void verificaFimDeJogo() {
        int numK = 0;
        for (PilhaHome pilhaHome : this.BASES) {
            if (pilhaHome.elementoTopo() != null && pilhaHome.elementoTopo().getNumRep().equals("k")) {
                numK++;
            }
        }

        if (numK == 4) {
            JOptionPane.showMessageDialog(null, "Parabéns, você terminou o jogo!");
            JogoApp.novoJogo();
        }
    }
}
