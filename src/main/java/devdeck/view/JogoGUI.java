package devdeck.view;

import devdeck.model.NoCarta;
import devdeck.model.base.ListaBase;
import devdeck.model.base.PilhaBase;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;
import devdeck.utils.CartaMouseListener;
import devdeck.utils.ConfigCarta;
import devdeck.utils.MonteMouseListener;
import devdeck.utils.RecursosUteis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JogoGUI extends JFrame {
    public JLabel cartaHolder;
    public JLabel[] visualCartasMonte = new JLabel[3];

    private final JogoApp JOGO;
    public final static int DESLOCAMENTO_CARTA_Y = 40;
    public final static int DESLOCAMENTO_CARTA_X = 180;

    public static JLabel warningBg;
    public static JLabel warningBox;

    /** Creates new form PacienciaUI */
    public JogoGUI(JogoApp JOGO) {
        ArrayList<Image> icons = new ArrayList<Image>();
        this.setIconImages(icons);

        this.setContentPane(new JogoPainel());
        initComponents();
        this.getContentPane().setBackground(new Color(9, 87, 37));
        this.setLocationRelativeTo(null);
        this.iniciaWarningBox();

        this.JOGO = JOGO;
        this.iniciaJogo();
    }

    private void iniciaJogo() {
        this.iniciaBaralho();
        this.iniciaBases();
        this.iniciaListas();
    }

    public void iniciaBaralho() {
        // Adiciona a cartinha clicável para pegar outras cartas
        int barX = 40;
        int barY = 22;
        for (int i=0; i < 3; i++) {
            JLabel carta = new JLabel(RecursosUteis.getCarta("back.png"));
            visualCartasMonte[i] = carta;

            carta.setBounds(0, 0, ConfigCarta.LARGURA_CARTA, ConfigCarta.ALTURA_CARTA);
            carta.setLocation(barX, barY);
            this.getContentPane().add(carta);
            this.getContentPane().setComponentZOrder(carta, 0);

            barX += 2;
            barY += 2;

            if (i == 2) {
                carta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                carta.addMouseListener(new MonteMouseListener(this));
            }
        }

        // Adiciona as cartas do monte no pane, invisiveis.
        MonteHome monteHome = this.JOGO.getMonteHome();
        for (int i = 0; i < 8; i++) {
            Stack<NoCarta> monte = monteHome.retira3Cartas();
            for (NoCarta carta : monte) {
                this.getContentPane().add(carta);
                carta.addMouseListener(new CartaMouseListener(this, this.JOGO));
                this.getContentPane().setComponentZOrder(carta, 0);
                carta.setVisible(false);
            }
        }

        // Adiciona a carta "vazia"
        cartaHolder = new JLabel(RecursosUteis.getCarta("empty_card.png"));
        cartaHolder.setBounds(0, 0, ConfigCarta.LARGURA_CARTA, ConfigCarta.ALTURA_CARTA);
        cartaHolder.setLocation(40, 22);
        cartaHolder.addMouseListener(new MonteMouseListener(this));
        cartaHolder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.getContentPane().add(cartaHolder);
        this.getContentPane().setComponentZOrder(cartaHolder, this.getContentPane().getComponentCount() - 1);
    }

    private void iniciaWarningBox() {
        warningBg = new JLabel(RecursosUteis.getUi("caixa.png"));
        warningBg.setBounds(0, 0, 206, 106);
        warningBg.setLocation(40, 800);

        warningBox = new JLabel();
        warningBox.setForeground(Color.black);
        warningBox.setBounds(8, 26, JogoGUI.warningBg.getWidth(), JogoGUI.warningBg.getHeight());
        warningBox.setHorizontalAlignment(JLabel.LEFT);
        warningBox.setVerticalAlignment(JLabel.TOP);
        warningBg.add(warningBox);

        this.getContentPane().add(warningBg);
        hideWarning();

        warningBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {}

            @Override
            public void mousePressed(MouseEvent me) {
                JogoGUI.hideWarning();
            }

            @Override
            public void mouseReleased(MouseEvent me) {}

            @Override
            public void mouseEntered(MouseEvent me) {}

            @Override
            public void mouseExited(MouseEvent me) {}
        });
    }

    public static void showWarning(String texto) {
        warningBg.setVisible(true);
        warningBox.setVisible(true);
        warningBox.setText(texto);
    }

    public static void hideWarning() {
        warningBg.setVisible(false);
        warningBox.setVisible(false);
    }

    private Stack<NoCarta> ult3Cartas = null;
    private boolean showingHolder = false;
    /**
     * Abre 3 cartas do baralho
     */
    public void abre3Cartas() {
        MonteHome monteHome = this.JOGO.getMonteHome();

        // Esconde as 3 cartas anteriores
        if (ult3Cartas != null) {
            for (NoCarta carta : ult3Cartas) {
                carta.setVisible(false);
            }
        }

        if (showingHolder && monteHome.getMontes().size() > 1) {
            this.hideHolder();
        } else {
            if ((monteHome.getNumMonteAtivo() + 1 == monteHome.getMontes().size()) || monteHome.getMontes().size() == 1) {
                this.showHolder();
            } else {
                this.hideHolder();
            }

            // Habilita novas 3 cartas
            int baralhoX = DESLOCAMENTO_CARTA_X + 80;
            int baralhoY = 22;
            ult3Cartas = monteHome.retira3Cartas();
            if (ult3Cartas != null) {
                int i = 1;
                for (NoCarta carta : ult3Cartas) {
                    carta.setOpen(true);
                    carta.setVisible(true);
                    carta.setLocation(baralhoX, baralhoY);
                    this.getContentPane().setComponentZOrder(carta, ult3Cartas.size()-i);

                    carta.setDraggable(i == ult3Cartas.size());

                    i++;
                    baralhoX += 40;
                }
            }
        }
    }

    private void showHolder() {
        for (JLabel label : this.visualCartasMonte) {
            label.setVisible(false);
        }

        this.cartaHolder.setVisible(true);
        this.showingHolder = true;
    }

    private void hideHolder() {
        for (JLabel label : this.visualCartasMonte) {
            label.setVisible(true);
        }

        this.cartaHolder.setVisible(false);
        this.showingHolder = false;
    }

    /**
     * Inicia a interface das pilhas de base
     */
    private void iniciaBases() {
        int baseX = 900;
        int baseY = 22;
        for (PilhaHome pilha : this.JOGO.getBASES()) {
            // Coloca a imagem de meia-borda
            PilhaBase pilhaBase = new PilhaBase(baseX, baseY, pilha);
            pilha.setBase(pilhaBase);
            this.getContentPane().add(pilhaBase);

            baseX += DESLOCAMENTO_CARTA_X;
        }
    }

    private void iniciaListas() {
        int cartaX = 350;
        int cartaY = 300;
        int i = 0;
        for (ListaHome lista : this.JOGO.getLISTAS()) {
            // Coloca a imagem de base inicial (meia-borda)
            ListaBase listaBase = new ListaBase(cartaX, cartaY, lista);
            lista.setBase(listaBase);
            this.getContentPane().add(listaBase);

            // Começa a colocar as cartas de fato
            for (int j = 0; j < lista.contarNos(); j++) {
                NoCarta carta = lista.recuperaNo(j+1);
                if (carta != null) {
                    carta.setLocation(cartaX, cartaY);
                    this.getContentPane().add(carta);
                    carta.addMouseListener(new CartaMouseListener(this, this.JOGO));
                    this.getContentPane().setComponentZOrder(carta, lista.contarNos() - j - 1);

                    cartaY += DESLOCAMENTO_CARTA_Y;
                }
            }

            cartaX += DESLOCAMENTO_CARTA_X;
            cartaY = 300;
            i++;
        }
    }

    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paciência - Dev Deck");
        setBackground(new Color(8, 87, 37));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        getContentPane().setLayout(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JogoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
