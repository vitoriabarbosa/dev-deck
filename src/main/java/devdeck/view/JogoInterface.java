package devdeck.view;

import devdeck.model.NoCarta;
import devdeck.model.base.ListaBase;
import devdeck.model.base.PilhaBase;
import devdeck.model.home.ListaHome;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;
import devdeck.utils.ConfigPadrao;
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
 * A classe {@code JogoInterface} representa a interface gráfica de um jogo de paciência.
 * Esta classe é responsável por gerenciar a exibição e as interações com os
 * componentes visuais do jogo, como cartas, montes, bases e listas.
 */
public class JogoInterface extends JFrame {
    private final JogoApp jogoApp;
    private JLayeredPane layeredPane;

    private Stack<NoCarta> ult3Cartas = null;
    private boolean showingHolder = false;
    public JLabel cartaHolder;
    public JLabel[] visualCartasMonte = new JLabel[3];

    public static JLabel warningBg;
    public static JLabel warningBox;

    private JLabel cronometroLabel;
    private JLabel pontuacaoLabel;

    /**
     * Cria uma nova instância da interface gráfica do jogo.
     *
     * @param jogoApp A instância do aplicativo do jogo.
     */
    public JogoInterface(JogoApp jogoApp) {
        ArrayList<Image> icons = new ArrayList<Image>();
        this.setIconImages(icons);

        // Inicializa o painel principal
        this.setContentPane(new JogoPainel());
        initComponents();
        this.iniciaWarningBox();

        this.jogoApp = jogoApp;
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
        int barX = ConfigPadrao.PADDING;
        int barY = ConfigPadrao.PADDING;

        for (int i = 0; i < 3; i++) {
            JLabel carta = new JLabel(RecursoImagens.getCarta("fundo.png"));
            visualCartasMonte[i] = carta;

            carta.setBounds(0, 0, ConfigPadrao.LARGURA_CARTA, ConfigPadrao.ALTURA_CARTA);
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
        cartaHolder.setBounds(0, 0, ConfigPadrao.LARGURA_CARTA, ConfigPadrao.ALTURA_CARTA);
        cartaHolder.setLocation(ConfigPadrao.PADDING, ConfigPadrao.PADDING);
        cartaHolder.addMouseListener(new MonteEvento(this));
        cartaHolder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        layeredPane.add(cartaHolder, Integer.valueOf(2));
    }

    /**
     * Inicializa a caixa de aviso que exibe mensagens ao usuário.
     * A caixa de aviso pode ser clicada para ocultá-la.
     */
    private void iniciaWarningBox() {
        int boxWidth = 300;
        int boxHeight = 100;
        warningBg = new JLabel(RecursoImagens.getUi("caixa.png", boxWidth, boxHeight));
        warningBg.setBounds(0, 0, boxWidth, boxHeight);
        warningBg.setLocation(ConfigPadrao.PADDING, ConfigPadrao.TAMANHO_TELA.height - 250);

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
                JogoInterface.hideWarning();
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

    /**
     * Exibe as três próximas cartas do monte na interface.
     */
    public void abre3Cartas() {
        MonteHome monteHome = this.jogoApp.getMonteHome();

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

            int baralhoX = ConfigPadrao.DESLOCAMENTO_X + ConfigPadrao.PADDING + 30;
            ult3Cartas = monteHome.retira3Cartas();
            if (ult3Cartas != null) {
                int numCartas = ult3Cartas.size();
                for (int i = 0; i < numCartas; i++) {
                    NoCarta carta = ult3Cartas.get(i);
                    carta.setOpen(true);
                    carta.setVisible(true);
                    carta.setLocation(baralhoX + (i * 40), ConfigPadrao.PADDING);
                    carta.setSize(ConfigPadrao.LARGURA_CARTA, ConfigPadrao.ALTURA_CARTA);
                    carta.setDraggable(i == numCartas - 1);
                    carta.addMouseListener(new CartaEvento(this, this.jogoApp));
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
        int baseX = ConfigPadrao.TAMANHO_TELA.width - ConfigPadrao.LARGURA_CARTA - ConfigPadrao.PADDING;

        for (PilhaHome pilha : this.jogoApp.getBASES()) {
            PilhaBase pilhaBase = new PilhaBase(baseX, ConfigPadrao.PADDING, pilha);
            pilha.setBase(pilhaBase);
            pilhaBase.setSize(ConfigPadrao.LARGURA_CARTA, ConfigPadrao.ALTURA_CARTA);
            layeredPane.add(pilhaBase, Integer.valueOf(1));

            baseX -= ConfigPadrao.LARGURA_CARTA + (ConfigPadrao.PADDING / 4);
        }
    }

    /**
     * Inicializa as listas de cartas na interface.
     */
    private void iniciaListas() {
        int numListas = this.jogoApp.getLISTAS().length;
        int totalListaWidth = ConfigPadrao.LARGURA_CARTA * numListas + (ConfigPadrao.DESLOCAMENTO_X / 2) * (numListas - 1);

        int cartaX = (ConfigPadrao.TAMANHO_TELA.width - totalListaWidth) / 2;
        int cartaY = (ConfigPadrao.TAMANHO_TELA.height / 4) + ConfigPadrao.PADDING;

        for (ListaHome lista : this.jogoApp.getLISTAS()) {
            ListaBase listaBase = new ListaBase(cartaX, cartaY, lista);
            lista.setBase(listaBase);
            listaBase.setSize(ConfigPadrao.LARGURA_CARTA, ConfigPadrao.ALTURA_CARTA);
            layeredPane.add(listaBase, Integer.valueOf(1));

            // Adiciona as cartas da lista
            for (int j = 0; j < lista.contarNos(); j++) {
                NoCarta carta = lista.recuperaNo(j + 1);
                if (carta != null) {
                    carta.setLocation(cartaX, cartaY);
                    carta.setSize(ConfigPadrao.LARGURA_CARTA, ConfigPadrao.ALTURA_CARTA);
                    carta.addMouseListener(new CartaEvento(this, this.jogoApp));
                    layeredPane.add(carta, Integer.valueOf(2 + j));
                    cartaY += ConfigPadrao.DESLOCAMENTO_Y;
                }
            }
            cartaX += ConfigPadrao.LARGURA_CARTA + (ConfigPadrao.DESLOCAMENTO_X / 2);
            cartaY = (ConfigPadrao.TAMANHO_TELA.height / 4) + ConfigPadrao.PADDING;
        }
    }

    /**
     * Inicializa os componentes principais da interface, como o background e a área
     * de layout.
     */
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Solitaire Game - Dev Deck");

        // Cria o JLayeredPane
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        layeredPane.setLayout(null);
        setContentPane(layeredPane);

        // Adiciona o background na camada mais baixa usando o painel customizado
        Image imagemFundo = RecursoImagens.getBackground("tela-fundo.png", ConfigPadrao.TAMANHO_TELA).getImage();
        FundoPainel backgroundPanel = new FundoPainel(imagemFundo);
        backgroundPanel.setBounds(0, 0, ConfigPadrao.TAMANHO_TELA.width, ConfigPadrao.TAMANHO_TELA.height-60);
        layeredPane.add(backgroundPanel, Integer.valueOf(0));

        // Configurar cronômetro
        cronometroLabel = new JLabel("Tempo da Partida: 00:00");
        cronometroLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cronometroLabel.setForeground(new Color(255, 255, 255));
        cronometroLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cronometroLabel.setBounds(ConfigPadrao.TAMANHO_TELA.width - 450, 20, 300, 30);
        layeredPane.add(cronometroLabel, Integer.valueOf(1));

        // Configurar pontuação
        pontuacaoLabel = new JLabel("Pontuação: 0");
        pontuacaoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pontuacaoLabel.setForeground(Color.WHITE);
        pontuacaoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pontuacaoLabel.setBounds((ConfigPadrao.TAMANHO_TELA.width / 2) - 100, 20, 200, 30);
        layeredPane.add(pontuacaoLabel, Integer.valueOf(1));

        JButton btnNovoJogo = getJButton();
        layeredPane.add(btnNovoJogo, Integer.valueOf(1));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
    }

    private JButton getJButton() {
        JButton btnNovoJogo = new JButton("Novo Jogo");
        btnNovoJogo.setBackground(new Color(231, 231, 231));
        btnNovoJogo.setForeground(new Color(94, 23, 235));
        btnNovoJogo.setFont(new Font("Arial", Font.BOLD, 14));
        btnNovoJogo.setFocusPainted(false);
        btnNovoJogo.setBounds((ConfigPadrao.TAMANHO_TELA.width / 2) - 150, ConfigPadrao.TAMANHO_TELA.height - 115, 300, 40);
        btnNovoJogo.addActionListener(e -> {
            dispose(); // Fechando a tela atual
            new JogoApp(); // Reiniciando o jogo
        });
        return btnNovoJogo;
    }

    // Atualizar o cronômetro
    public void atualizaCronometro(String tempo) {
        cronometroLabel.setText("Tempo da Partida: " + tempo);
    }

    // Atualizar pontuação em tempo real
    public void atualizaPontuacao() {
        pontuacaoLabel.setText("Pontuação: " + jogoApp.getPontuacao());
    }
}