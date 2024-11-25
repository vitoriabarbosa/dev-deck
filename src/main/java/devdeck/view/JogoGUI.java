package devdeck.view;

import devdeck.model.NoCarta;
import devdeck.model.base.ListaBase;
import devdeck.model.base.PilhaBase;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;
import devdeck.utils.ConfigCarta;
import devdeck.utils.component.FundoPainel;
import devdeck.utils.component.JogoPainel;
import devdeck.utils.event.CartaEvento;
import devdeck.utils.event.MonteEvento;
import devdeck.utils.RecursoImagens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Stack;

/**
 * A classe JogoGUI representa a interface gráfica de um jogo de paciência.
 * Esta classe é responsável por gerenciar a exibição e as interações com os
 * componentes visuais do jogo, como cartas, montes, bases e listas.
 */
public class JogoGUI extends JFrame {
    private final JogoApp JOGO;
    private JLayeredPane layeredPane;
    private static final int PADDING = 100;

    public JLabel cartaHolder;
    public JLabel[] visualCartasMonte = new JLabel[3];
    public static JLabel warningBg;
    public static JLabel warningBox;
    private JLabel cronometroLabel;
    private JLabel pontuacaoLabel;

    /**
     * Cria uma nova instância da interface gráfica do jogo.
     *
     * @param JOGO A instância do aplicativo do jogo.
     */
    public JogoGUI(JogoApp JOGO) {
        ArrayList<Image> icons = new ArrayList<Image>();
        this.setIconImages(icons);

        // Inicializa o painel principal
        this.setContentPane(new JogoPainel());
        initComponents();
        this.iniciaWarningBox();

        this.JOGO = JOGO;
        this.iniciaJogo();
    }

    /**
     * Inicializa os componentes do jogo, incluindo o baralho, as bases e as listas.
     */
    private void iniciaJogo() {
        this.iniciaBaralho();
        this.iniciaBases();
        this.iniciaListas();
    }

    /**
     * Inicializa o baralho de cartas na interface.
     * Configura a posição e eventos associados às cartas do monte.
     */
    public void iniciaBaralho() {
        int barX = PADDING;
        int barY = PADDING;

        for (int i = 0; i < 3; i++) {
            JLabel carta = new JLabel(RecursoImagens.getCarta("fundo.png"));
            visualCartasMonte[i] = carta;

            carta.setBounds(0, 0, ConfigCarta.LARGURA, ConfigCarta.ALTURA);
            carta.setLocation(barX, barY);
            layeredPane.add(carta, JLayeredPane.PALETTE_LAYER);
            layeredPane.setComponentZOrder(carta, 0);

            barX += 2;
            barY += 2;

            if (i == 2) {
                carta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                carta.addMouseListener(new MonteEvento(this));
            }
        }

        // Adiciona a carta "vazia"
        cartaHolder = new JLabel(RecursoImagens.getCarta("carta-vazia.png"));
        cartaHolder.setBounds(0, 0, ConfigCarta.LARGURA, ConfigCarta.ALTURA);
        cartaHolder.setLocation(PADDING, PADDING);
        cartaHolder.addMouseListener(new MonteEvento(this));
        cartaHolder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        layeredPane.add(cartaHolder, Integer.valueOf(2));
    }

    /**
     * Inicializa a caixa de aviso que exibe mensagens ao usuário.
     * A caixa de aviso pode ser clicada para ocultá-la.
     */
    private void iniciaWarningBox() {
        warningBg = new JLabel(RecursoImagens.getUi("caixa.png", 300, 100));
        warningBg.setBounds(0, 0, 300, 100);
        warningBg.setLocation(PADDING, PADDING * 8);

        warningBox = new JLabel();
        warningBox.setForeground(Color.black);
        warningBox.setBounds(15, 25, warningBg.getWidth(), warningBg.getHeight());
        warningBox.setHorizontalAlignment(JLabel.LEFT);
        warningBox.setVerticalAlignment(JLabel.TOP);
        warningBg.add(warningBox);

        layeredPane.add(warningBg, Integer.valueOf(2));
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

    /**
     * Exibe uma mensagem de aviso na caixa de aviso.
     *
     * @param texto O texto da mensagem de aviso.
     */
    public static void showWarning(String texto) {
        warningBg.setVisible(true);
        warningBox.setVisible(true);
        warningBox.setText(texto);
    }

    /**
     * Oculta a caixa de aviso.
     */
    public static void hideWarning() {
        warningBg.setVisible(false);
        warningBox.setVisible(false);
    }

    private Stack<NoCarta> ult3Cartas = null;
    private boolean showingHolder = false;

    /**
     * Exibe as três próximas cartas do monte na interface.
     */
    public void abre3Cartas() {
        MonteHome monteHome = this.JOGO.getMonteHome();

        // Esconde as 3 cartas anteriores e remove-as do painel
        if (ult3Cartas != null) {
            for (NoCarta carta : ult3Cartas) {
                carta.setVisible(false); // Esconde a carta, mas não remove
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

            int baralhoX = ConfigCarta.DESLOCAMENTO_X + PADDING;
            int baralhoY = PADDING;
            ult3Cartas = monteHome.retira3Cartas();
            if (ult3Cartas != null) {
                int numCartas = ult3Cartas.size();
                for (int i = 0; i < numCartas; i++) {
                    NoCarta carta = ult3Cartas.get(i);
                    carta.setOpen(true);
                    carta.setVisible(true);
                    carta.setLocation(baralhoX + (i * 40), baralhoY);
                    carta.setSize(ConfigCarta.LARGURA, ConfigCarta.ALTURA);
                    carta.setDraggable(i == numCartas - 1);
                    carta.addMouseListener(new CartaEvento(this, this.JOGO));
                    layeredPane.add(carta, Integer.valueOf(2 + i));
                }
            }
        }
    }

    /**
     * Mostra o espaço "vazio" para o monte quando todas as cartas foram usadas.
     */
    private void showHolder() {
        for (JLabel label : this.visualCartasMonte) {
            label.setVisible(false);
        }
        this.cartaHolder.setVisible(true);
        this.showingHolder = true;
    }

    /**
     * Oculta o espaço "vazio" do monte e exibe as cartas do monte.
     */
    private void hideHolder() {
        for (JLabel label : this.visualCartasMonte) {
            label.setVisible(true);
        }
        this.cartaHolder.setVisible(false);
        this.showingHolder = false;
    }

    /**
     * Inicializa as pilhas de bases na interface.
     */
    private void iniciaBases() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int baseX = screenSize.width - ConfigCarta.LARGURA - PADDING;
        int baseY = PADDING;

        for (PilhaHome pilha : this.JOGO.getBASES()) {
            PilhaBase pilhaBase = new PilhaBase(baseX, baseY, pilha);
            pilha.setBase(pilhaBase);
            pilhaBase.setSize(ConfigCarta.LARGURA, ConfigCarta.ALTURA);
            layeredPane.add(pilhaBase, Integer.valueOf(1));

            baseX -= (ConfigCarta.LARGURA + PADDING);
        }
    }

    /**
     * Inicializa as listas de cartas na interface.
     */
    private void iniciaListas() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int numListas = this.JOGO.getLISTAS().length;
        int totalListaWidth = ConfigCarta.LARGURA * numListas + (ConfigCarta.DESLOCAMENTO_X / 2) * (numListas - 1);

        int cartaX = (screenSize.width - totalListaWidth) / 2;
        int cartaY = screenSize.height / 3 + PADDING;

        for (ListaHome lista : this.JOGO.getLISTAS()) {
            ListaBase listaBase = new ListaBase(cartaX, cartaY, lista);
            lista.setBase(listaBase);
            listaBase.setSize(ConfigCarta.LARGURA, ConfigCarta.ALTURA);
            layeredPane.add(listaBase, Integer.valueOf(1));

            // Adiciona as cartas da lista
            for (int j = 0; j < lista.contarNos(); j++) {
                NoCarta carta = lista.recuperaNo(j + 1);
                if (carta != null) {
                    carta.setLocation(cartaX, cartaY);
                    carta.setSize(ConfigCarta.LARGURA, ConfigCarta.ALTURA);
                    carta.addMouseListener(new CartaEvento(this, this.JOGO));
                    layeredPane.add(carta, Integer.valueOf(2 + j));
                    cartaY += ConfigCarta.DESLOCAMENTO_Y;
                }
            }

            cartaX += ConfigCarta.LARGURA + (ConfigCarta.DESLOCAMENTO_X / 2);
            cartaY = screenSize.height / 3 + PADDING;
        }
    }

    /**
     * Inicializa os componentes principais da interface, como o background e a área
     * de layout.
     */
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paciência - DevDeck");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Cria o JLayeredPane
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(screenSize);
        setContentPane(layeredPane);
        layeredPane.setLayout(null);

        // Adiciona o background na camada mais baixa usando o painel customizado
        Image imagemFundo = RecursoImagens.getBackground("tela-fundo.png", screenSize).getImage();
        FundoPainel backgroundPanel = new FundoPainel(imagemFundo);
        backgroundPanel.setBounds(0, 0, screenSize.width, screenSize.height-60);
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        // Configurar cronômetro
        cronometroLabel = new JLabel("Tempo da Partida: 00:00");
        cronometroLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cronometroLabel.setForeground(Color.WHITE);
        cronometroLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cronometroLabel.setBounds(screenSize.width-400, 20, 300, 30);
        layeredPane.add(cronometroLabel, Integer.valueOf(1));

        pontuacaoLabel = new JLabel("Pontuação: 0");
        pontuacaoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pontuacaoLabel.setForeground(Color.WHITE);
        pontuacaoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pontuacaoLabel.setBounds(screenSize.width/2-100, 20, 200, 30); // Posicionado no canto superior esquerdo
        layeredPane.add(pontuacaoLabel, Integer.valueOf(1));

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
    }

    // Atualizar o cronômetro
    public void atualizaCronometro(String tempo) {
        cronometroLabel.setText("Tempo da Partida: " + tempo);
    }

    // Atualizar pontuação em tempo real
    public void atualizaPontuacao() {
        pontuacaoLabel.setText("Pontuação: " + JOGO.getPontuacao());
    }
}