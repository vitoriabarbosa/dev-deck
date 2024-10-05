package devdeck.utils.event;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;
import devdeck.model.home.Home;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;
import devdeck.utils.ConfigCarta;
import devdeck.view.JogoApp;
import devdeck.view.JogoGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CartaEvento extends JFrame implements MouseListener, MouseMotionListener {
    private NoCarta carta;
    private final Container PAINEL;
    private final JogoApp JOGO;

    private Point cartaOriginalLocation;
    private Point primeiroClique = new Point();

    public CartaEvento(JFrame frame, JogoApp JOGO) {
        this.JOGO = JOGO;
        this.PAINEL = frame.getContentPane();
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        // Se houver um duplo clique em alguma carta, tenta inserir o nó na base onde conseguir
        if (me.getClickCount() == 2) {
            boolean cartaColocada = false;
            for (PilhaHome base : this.JOGO.getBASES()) {
                if (!cartaColocada) {
                    try {
                        me.getComponent();
                        base.receberNo(carta);

                        Point nextCardPoint = base.getBase().getNextCardPoint();
                        this.setNoCartaLocation(carta, nextCardPoint);
                        cartaColocada = true;
                    } catch(MovimentosInvalidos ignored) {}
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

                // Por fim, coloca as cartas no lugar
                this.setNoCartaLocation(carta, nextCardPoint);
                JogoGUI.hideWarning();
                
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
            }
        }
    }
    
    /**
     * Move um nó completo para alguma posição
     * @param nc 
     * @param ponto Ponto para aonde a carta deve ser levada
     */
    private void setNoCartaLocation(NoCarta nc, Point ponto) {
        this.carta = nc;
        // Controle de Z-Index e cartas-filha
        NoCarta aux = carta;
        int totalProx = carta.getCountProx();
        int locationX = ponto.x;
        int locationY = ponto.y;
        int i = 0;
        while (aux != null) {
            aux.setLocation(locationX, locationY);
            this.PAINEL.setComponentZOrder(aux, totalProx - i);
            
            locationY += ConfigCarta.DESLOCAMENTO_Y;
            aux = aux.getProx();
            i++;
        }
    }

    private Home findHomeAt(MouseEvent me)
        throws MovimentosInvalidos
    {

    // Recupera o ponto do evento do mouse.
    // Como o componente neste local vai ser a própria carta,desabilita a carta e pega o elemento de trás depois reabilita a carta de volta

        try {
            Point ponto = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), this.PAINEL);
            me.getComponent().setVisible(false);
            Component componente = this.PAINEL.findComponentAt(ponto);
            me.getComponent().setVisible(true);
            
            if (componente != null) {
                if (componente instanceof NoCarta nc) {
                    return nc.getHome();
                } else
                if (componente instanceof Base base) {
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

    @Override
    public void mouseDragged(MouseEvent me) {
        if (!carta.isDraggable()) {
            return;
        }
        
        Point ponto1 = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), this.PAINEL);
        Point ponto = new Point(ponto1.x - primeiroClique.x, ponto1.y - primeiroClique.y);
        
        this.setNoCartaLocation(carta, ponto);
    }

    @Override
    public void mouseMoved(MouseEvent me) {}
}
