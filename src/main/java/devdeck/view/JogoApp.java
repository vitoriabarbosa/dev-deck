package devdeck.view;

import devdeck.model.Baralho;
import devdeck.model.NoCarta;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;

import javax.swing.*;

public final class JogoApp {
    public static void main(String[] args) {
        try {
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

    public static void novoJogo() {
        // Se já existir um ui criado, remover
        // @TODO trocar
        if (JogoApp.frame != null) {
            JogoApp.frame.dispose();
        }
        new JogoApp();
    }

    private JogoApp() {
        BARALHO = new Baralho();

        // Cria as pilhas base (que terão as cartas finais montadas)
        BASES[0] = new PilhaHome("Pilha 1");
        BASES[1] = new PilhaHome("Pilha 2");
        BASES[2] = new PilhaHome("Pilha 3");
        BASES[3] = new PilhaHome("Pilha 4");

        // Inicia as listas
        this.iniciaMonte();
        this.iniciaListas();

        // Inicia a UI
        JogoApp.frame = new JogoGUI(this);
        JogoApp.frame.setVisible(true);
    }
    /**
     * Inicia os montes de carta
     */
    public void iniciaMonte() {
        // Separa os 8 montes de 3 cartas que não vão pras listas
        this.monteHome = new MonteHome();
        for (int j = 0; j < 13; j++) {
            NoCarta carta = this.BARALHO.retiraCartaTopo();
            monteHome.inserir(carta);
        }
    }

    /**
     * Inicia as listas de cartas
     */
    private void iniciaListas() {
        for (int i = 0; i < 5; i++) {
            ListaHome listaHome = new ListaHome("Lista " + (i + 1));

            // Retira do baralho e receberNo nas listas de cartas
            for (int j = 0; j < (i + 1); j++) {
                NoCarta noCarta = this.BARALHO.retiraCartaTopo();
                noCarta.setHome(listaHome);
                listaHome.inserir(noCarta);
            }
            this.LISTAS[i] = listaHome;
        }
    }

    /**
     * Valida se a carta1 é a sequencia da carta2 (crescentemente).
     * 
     * @param carta1
     * @param carta2
     */
    public static boolean cartaSequenciaValida(NoCarta carta1, NoCarta carta2) {
        if (carta1 == null || carta2 == null) {
            return false;
        }
        return ((carta1.getNumero() == carta2.getNumero() + 1) && (carta1.getNaipe().getCor() == carta2.getNaipe().getCor()));
    }

    public ListaHome[] getLISTAS() {
        return this.LISTAS;
    }
    
    public MonteHome getMonteHome() {
        return this.monteHome;
    }
    
    public PilhaHome[] getBASES() {
        return this.BASES;
    }
    
    public Baralho getBARALHO() {
        return this.BARALHO;
    }
    
    /**
     * Verifica se o jogo acabou.
     * O jogo termina quando o topo de todas as pilhas-base são 7 (equivale ao K)
     */
    public void verificaFimDeJogo() {
        int numK = 0;
        for (PilhaHome pilhaHome : this.BASES) {
            if (pilhaHome.elementoTopo() != null && pilhaHome.elementoTopo().getNumRep().equals("K")) {
                numK++;
            }
        }

        if (numK == 4) {
            JOptionPane.showMessageDialog(null, "Parabéns, você terminou o jogo!");
            JogoApp.novoJogo();
        }
    }
}