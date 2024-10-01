package devdeck.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.*;

import devdeck.model.CartaMouseListener;
import devdeck.model.MonteMouseListener;
import devdeck.model.base.ListaBase;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.NoCarta;
import devdeck.model.base.PilhaBase;
import devdeck.model.home.PilhaHome;
import devdeck.utils.ConfigCard;
import devdeck.utils.ImageCardUtil;

public class GameGUI extends javax.swing.JFrame {
    public JLabel cartaHolder;
    public JLabel[] visualCartasMonte = new JLabel[3];
    private GameApp gameApp;
    public final static int cardYOffset = 46;
    public final static int cardXOffset = 175;

    public GameGUI(GameApp gameApp) {
        ArrayList<Image> icons = new ArrayList<>();
        this.setIconImages(icons);

        this.setContentPane(new GamePanel());
        this.gameApp = gameApp;
        this.iniciaJogo();
        this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(new Color(9, 87, 37));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    private void iniciaJogo() {
        this.iniciaBaralho();
        this.iniciaBases();
        this.iniciaListas();
    }

    public void iniciaBaralho() {
        int barX = 40;
        int barY = 22;
        for(int i=0; i<3; i++) {
            JLabel carta = new JLabel(ImageCardUtil.getCarta("back.png"));
            visualCartasMonte[i] = carta;

            carta.setBounds(0, 0, ConfigCard.LARGURA_CARTA, ConfigCard.ALTURA_CARTA);
            carta.setLocation(barX, barY);
            this.getContentPane().add(carta);
            this.getContentPane().setComponentZOrder(carta, 0);

            barX += 2;
            barY += 2;

            if(i == 2) {
                carta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                carta.addMouseListener(new MonteMouseListener(this));
            }
        }

        MonteHome monteHome = this.gameApp.getMonteHome();
        for (int i = 0; i < 8; i++) {
            Stack<NoCarta> monte = monteHome.retira3Cartas();
            for (NoCarta carta : monte) {
                this.getContentPane().add(carta);
                carta.addMouseListener(new CartaMouseListener(this, this.gameApp));
                this.getContentPane().setComponentZOrder(carta, 0);
                carta.setVisible(false);
            }
        }

        // Adiciona a carta "vazia"
        cartaHolder = new JLabel(ImageCardUtil.getCarta("empty-card.png"));
        cartaHolder.setBounds(0, 0, ConfigCard.LARGURA_CARTA, ConfigCard.ALTURA_CARTA);
        cartaHolder.setLocation(40, 22);
        cartaHolder.addMouseListener(new MonteMouseListener(this));
        cartaHolder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.getContentPane().add(cartaHolder);
        this.getContentPane().setComponentZOrder(cartaHolder, this.getContentPane().getComponentCount()-1);

    }

    private Stack<NoCarta> ult3Cartas = null;
    private boolean showingHolder = false;


    public void abre3Cartas() {
        MonteHome monteHome = this.gameApp.getMonteHome();  // Pega o monte de cartas
        if (ult3Cartas != null) {
            for (NoCarta carta : ult3Cartas) {
                carta.setVisible(false);
            }
        }

        if (ult3Cartas != null) {
            for (NoCarta carta : ult3Cartas) {
                carta.setVisible(true);  // Torna a carta visível
            }
        }

        if(showingHolder && monteHome.getMontes().size() > 1) {
            this.hideHolder();
        } else {
            if((monteHome.getNumMonteAtivo()+1 == monteHome.getMontes().size())
                    || monteHome.getMontes().size() == 1
            ) {
                this.showHolder();
            } else {
                this.hideHolder();
            }

            // Habilita novas 3 cartas
            int baralhoX = GameGUI.cardXOffset + 40;
            int baralhoY = 22;
            ult3Cartas = monteHome.retira3Cartas();
            if(ult3Cartas != null) {
                int i = 1;
                for(NoCarta carta : ult3Cartas) {
                    carta.setOpen(true);
                    carta.setVisible(true);
                    carta.setLocation(baralhoX, baralhoY);
                    this.getContentPane().setComponentZOrder(carta, ult3Cartas.size()-i);

                    if(i == ult3Cartas.size())
                        carta.setDraggable(true);
                    else
                        carta.setDraggable(false);

                    i++;
                    baralhoX += 16;
                }
            }
        }
    }

    private void showHolder() {
        for (JLabel c : this.visualCartasMonte)
            c.setVisible(false);

        this.cartaHolder.setVisible(true);
        this.showingHolder = true;
    }

    private void hideHolder() {
        for (JLabel c : this.visualCartasMonte)
            c.setVisible(true);

        this.cartaHolder.setVisible(false);
        this.showingHolder = false;
    }

    /**
     * Inicia a interface das pilhas de base
     */
    private void iniciaBases() {
        int baseX = 950;
        int baseY = 22;

        for (PilhaHome pilha : this.gameApp.getBases()) {
            // Coloca a imagem de meia-borda
            PilhaBase pilhaBase = new PilhaBase(baseX, baseY, pilha);
            pilha.setBase(pilhaBase);
            this.getContentPane().add(pilhaBase);

            baseX += GameGUI.cardXOffset;
        }
    }

    private void iniciaListas() {
        int cartaX = 450;
        int cartaY = 450;
        int i = 0;
        for(ListaHome lista : this.gameApp.getListas()) {
            // Coloca a imagem de base inicial (meia-borda)
            ListaBase listaBase = new ListaBase(cartaX, cartaY, lista);
            lista.setBase(listaBase);
            this.getContentPane().add(listaBase);

            // Começa a colocar as cartas de fato
            for(int j = 0; j < lista.contarNos(); j++) {
                NoCarta carta = lista.recuperaNo(j+1);
                if(carta != null) {
                    NoCarta label = carta;
                    label.setLocation(cartaX, cartaY);
                    this.getContentPane().add(label);
                    label.addMouseListener(new CartaMouseListener(this, this.gameApp));
                    this.getContentPane().setComponentZOrder(carta, lista.contarNos()-j-1);

                    cartaY += GameGUI.cardYOffset;
                }
            }

            cartaX += GameGUI.cardXOffset;
            cartaY = 450;
            i++;
        }
    }

    public static class GamePanel extends JPanel {

        @Override
        public boolean isOptimizedDrawingEnabled() {
            return false;
        }
    }
}
