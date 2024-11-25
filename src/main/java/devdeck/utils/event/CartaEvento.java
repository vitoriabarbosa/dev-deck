package devdeck.utils.event;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;
import devdeck.model.home.Home;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;
import devdeck.utils.ConfigCarta;
import devdeck.utils.charts.GraficoMovimentos;
import devdeck.view.JogoApp;
import devdeck.view.JogoGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A classe {@code CartaEvento} gerencia os eventos de mouse relacionados
 * às cartas no jogo. Ela implementa as interfaces {@link MouseListener}
 * e {@link MouseMotionListener} para permitir interações de arrastar
 * e soltar das cartas.
 */
public class CartaEvento extends JFrame implements MouseListener, MouseMotionListener {
    private NoCarta carta;
    private final Container PAINEL;
    private final JogoApp JOGO;
    private final GraficoMovimentos graficoMovimentos;

    private Point cartaOriginalLocation;
    private Point primeiroClique = new Point();

    /**
     * Constrói uma nova instância de {@code CartaEvento}.
     *
     * @param frame o frame onde os eventos ocorrerão
     * @param JOGO  a instância do jogo associada a este evento
     */
    public CartaEvento(JFrame frame, JogoApp JOGO) {
        this.JOGO = JOGO;
        this.PAINEL = frame.getContentPane();

        graficoMovimentos = new GraficoMovimentos(JOGO.getMovimentosValidos(), JOGO.getMovimentosInvalidos(), JOGO.getMovimentosTotal());
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        // Se houver um duplo clique em alguma carta, tenta inserir o nó na base onde conseguir
        if (me.getClickCount() == 2) {
            boolean cartaColocada = false;
            for (PilhaHome base : this.JOGO.getBASES()) {
                if (!cartaColocada) {
                    try {
                        base.receberNo(carta); // Movimenta a carta para a base

                        Point nextCardPoint = base.getBase().getNextCardPoint();
                        this.setNoCartaLocation(carta, nextCardPoint);
                        cartaColocada = true;

                        // Incrementa os movimentos válidos e totais
                        this.JOGO.incrementarMovimentosValidos();
                    } catch (MovimentosInvalidos ignored) {}
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        carta = (NoCarta) me.getComponent();
        cartaOriginalLocation = carta.getLocation();
        primeiroClique = me.getPoint();
        carta.addMouseMotionListener(this);
        JogoGUI.hideWarning();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (!carta.isDraggable()) {
            return;
        }

        carta.removeMouseMotionListener(this);
        try {
            Home homeTo = this.findHomeAt(me);
            if (homeTo == ((NoCarta) me.getComponent()).getHome() || homeTo instanceof MonteHome) {
                throw new MovimentosInvalidos(MovimentosInvalidos.GENERICO);
            } else if (homeTo != null) {
                // Manda a carta para o componente
                Point nextCardPoint = homeTo.getBase().getNextCardPoint();
                homeTo.receberNo(carta);

                // coloca as cartas no lugar
                this.setNoCartaLocation(carta, nextCardPoint);
                JogoGUI.hideWarning();

                // incrementar e atualizar o gráfico para movimentos válidos
                JOGO.incrementarMovimentosValidos();
                graficoMovimentos.atualizarDados(JOGO.getMovimentosValidos(), JOGO.getMovimentosInvalidos(), JOGO.getMovimentosTotal());

                if (homeTo instanceof PilhaHome) {
                    this.JOGO.verificaFimDeJogo();
                }
            } else {
                throw new MovimentosInvalidos(MovimentosInvalidos.GENERICO);
            }
        } catch (MovimentosInvalidos ex) {
            this.setNoCartaLocation(carta, cartaOriginalLocation);

            if (ex.getCode() != MovimentosInvalidos.GENERICO) {
                JogoGUI.showWarning("<html>" + ex.getMessage());

                // incrementar e atualizar o gráfico para movimentos inválidos
                JOGO.incrementarMovimentosInvalidos();
                graficoMovimentos.atualizarDados(JOGO.getMovimentosValidos(), JOGO.getMovimentosInvalidos(), JOGO.getMovimentosTotal());
            }
        }
    }

    /**
     * Move um nó completo para uma posição específica.
     *
     * @param nc    o nó da carta a ser movido
     * @param ponto o ponto para onde a carta deve ser movida
     */
    private void setNoCartaLocation(NoCarta nc, Point ponto) {
        this.carta = nc;

        // Controle de Z-Index e cartas conectadas
        NoCarta aux = carta;
        int totalProx = carta.getCountProx(); // Número de cartas conectadas
        int locationX = ponto.x;
        int locationY = ponto.y;
        int i = 0;

        while (aux != null) {
            aux.setLocation(locationX, locationY);

            // Atualiza apenas o Z-order se necessário (evita tremores)
            if (this.PAINEL.getComponentZOrder(aux) != totalProx - i) {
                this.PAINEL.setComponentZOrder(aux, totalProx - i);
            }

            locationY += ConfigCarta.DESLOCAMENTO_Y; // Ajusta o deslocamento vertical
            aux = aux.getProx(); // Vai para a próxima carta
            i++;
        }
    }

    /**
     * Encontra a área "Home" que está sob o cursor do mouse.
     *
     * @param me o evento do mouse
     * @return o componente "Home" onde a carta pode ser colocada
     * @throws MovimentosInvalidos se não for possível determinar a área "Home"
     */
    private Home findHomeAt(MouseEvent me) throws MovimentosInvalidos {

        /*
          Recupera o ponto do evento do mouse.
          Como o componente neste local vai ser a própria carta,
          desabilita a carta e pega o elemento de trás depois reabilita a carta de volta
         */
        try {
            Point ponto = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), this.PAINEL);
            me.getComponent().setVisible(false);
            Component componente = this.PAINEL.findComponentAt(ponto);
            me.getComponent().setVisible(true);

            if (componente != null) {
                if (componente instanceof NoCarta nc) {
                    return nc.getHome();
                } else if (componente instanceof Base base) {
                    return base.getHome();
                }

                throw new MovimentosInvalidos(MovimentosInvalidos.GENERICO);
            }

        } catch(ClassCastException e) {
            throw new MovimentosInvalidos(MovimentosInvalidos.GENERICO);
        }
        return null;
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    private boolean emMovimento = false;

    @Override
    public void mouseDragged(MouseEvent me) {
        if (!carta.isDraggable() || emMovimento) {
            return;
        }

        emMovimento = true; // Evita processamento duplicado

        Point ponto1 = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), this.PAINEL);
        Point ponto = new Point(ponto1.x - primeiroClique.x, ponto1.y - primeiroClique.y);

        this.setNoCartaLocation(carta, ponto);
        emMovimento = false; // Libera o bloqueio
    }

    @Override
    public void mouseMoved(MouseEvent me) {}
}
