package devdeck.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import devdeck.model.Baralho;
import devdeck.model.NoCarta;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;

import java.util.Objects;

public final class GameApp {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Inicia o jogo
            GameApp.novoJogo();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro ao iniciar o jogo.\nSeu sistema operacional não é suportado.");
        }
    }

    private Baralho baralho;
    private PilhaHome[] bases = new PilhaHome[4];
    private ListaHome[] listas = new ListaHome[7];
    private MonteHome monteHome;
    public static JFrame f;

    public static void novoJogo() {
        // Se já existir um ui criado, remover
        // @TODO trocar
        if (GameApp.f != null)
            GameApp.f.dispose();

        // Inicia o jogo
        new GameApp();
    }

    private GameApp() {
        // Inicia o jogo
        baralho = new Baralho();

        // Cria as pilhas base (que terão as cartas finais montadas)
        bases[0] = new PilhaHome("Pilha 1");
        bases[1] = new PilhaHome("Pilha 2");
        bases[2] = new PilhaHome("Pilha 3");
        bases[3] = new PilhaHome("Pilha 4");

        // Inicia as listas
        this.iniciaMonte();
        this.iniciaListas();

        // Inicia a UI
        GameApp.f = new GameGUI(this);
        GameApp.f.setVisible(true);  // Torna a janela visível
    }

    public void iniciaMonte() {
        this.monteHome = new MonteHome();
        for(int j = 0; j < 24; j++) {
            NoCarta carta = this.baralho.retiraCartaTopo();
            monteHome.inserir(carta);
        }
    }

    private void iniciaListas() {
        for (int i = 0; i < 7; i++) {
            ListaHome lista = new ListaHome("Lista " + (i + 1));

            for (int j = 0; j < (i + 1); j++) {
                NoCarta nc = this.baralho.retiraCartaTopo();
                nc.setHome(lista);
                lista.inserir(nc);
            }
            this.listas[i] = lista;
        }
    }

    public static boolean cartaSequenciaValida(NoCarta carta1, NoCarta carta2) {
        if (carta1 == null || carta2 == null)
            return false;

        return ((Objects.equals(carta1.getNumero(), carta2.getNumero() + 1))
                && (carta1.getNaipe() != carta2.getNaipe()));
    }

    public ListaHome[] getListas() {
        return this.listas;
    }

    public MonteHome getMonteHome() {
        return this.monteHome;
    }

    public PilhaHome[] getBases() {
        return this.bases;
    }

    public Baralho getBaralho() {
        return this.baralho;
    }

    public void verificaFimDeJogo() {
        int num5 = 0;
        for(PilhaHome pilhaHome : this.bases) {
            if(pilhaHome.elementoTopo() != null && pilhaHome.elementoTopo().getNumRep().equals("5")) {
                num5++;
            }
        }

        if(num5 == 4) {
            JOptionPane.showMessageDialog(null, "Parabéns, você terminou o jogo!");
            GameApp.novoJogo();
        }
    }
}