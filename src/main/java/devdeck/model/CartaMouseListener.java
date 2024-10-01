package devdeck.model;

import devdeck.exceptions.InvalidMoves;
import devdeck.model.home.MonteHome;
import devdeck.model.home.PilhaHome;
import devdeck.model.interfaces.Base;
import devdeck.model.interfaces.Home;
import devdeck.view.GameApp;
import devdeck.view.GameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CartaMouseListener extends JFrame implements MouseListener, MouseMotionListener {
    private NoCarta carta;
    private JFrame frame;
    private Container pane;
    private Point cartaOriginalLocation;
    private GameApp gameApp;
    private Point firstClick = new Point();

    public CartaMouseListener(JFrame frame, GameApp gameApp) {
        this.gameApp = gameApp;
        this.frame = frame;
        this.pane = this.frame.getContentPane();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        // Se houver um duplo clique em alguma carta, tenta inserir o nó na base onde conseguir
        if(me.getClickCount() == 2) {
            boolean cartaColocada = false;
            for(PilhaHome base : this.gameApp.getBases()) {
                if(!cartaColocada) {
                    try {
                        NoCarta nc = (NoCarta) me.getComponent();
                        base.receberNo(carta);

                        Point nextCardPoint = base.getBase().getNextCardPoint();
                        this.setNoCartaLocation(carta, nextCardPoint);
                        cartaColocada = true;
                    } catch(InvalidMoves ex) {
                    }
                }
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent me) {
        carta = (NoCarta) me.getComponent();
        cartaOriginalLocation = carta.getLocation();
        firstClick = me.getPoint();
        carta.addMouseMotionListener(this);
//        GameGUI.hideWarning();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if(!carta.isDraggable())
            return;

        carta.removeMouseMotionListener(this);
        try {
            Home homeTo = this.findHomeAt(me);
            if(homeTo == ((NoCarta) me.getComponent()).getHome() || homeTo instanceof MonteHome) {
                throw new InvalidMoves(InvalidMoves.GENERICO);
            } else if(homeTo != null) {
                // Manda a carta para o componente
                Point nextCardPoint = homeTo.getBase().getNextCardPoint();
                homeTo.receberNo(carta);

                // Por fim, coloca as cartas no lugar
                this.setNoCartaLocation(carta, nextCardPoint);
//                GameGUI.hideWarning();

                if(homeTo instanceof PilhaHome)
                    this.gameApp.verificaFimDeJogo();
            } else {
                throw new InvalidMoves(InvalidMoves.GENERICO);
            }
        } catch (InvalidMoves ex) {
            this.setNoCartaLocation(carta, cartaOriginalLocation);

//            if(ex.getCode() != InvalidMoves.GENERICO)
//                GameGUI.showWarning("<html>" + ex.getMessage());
        }
    }

    /**
     * Move um nó completo para alguma posição
     * @param nc
     * @param p Ponto para aonde a carta deve ser levada
     */
    private void setNoCartaLocation(NoCarta nc, Point p) {
        // Controle de Z-Index e cartas-filha
        NoCarta aux = carta;
        int totalProx = carta.getCountProx();
        int locationX = p.x;
        int locationY = p.y;
        int i = 0;
        while(aux != null) {
            aux.setLocation(locationX, locationY);
            this.pane.setComponentZOrder(aux, totalProx - i);

            locationY += GameGUI.cardYOffset;
            aux = aux.getProx();
            i++;
        }
    }

    private Home findHomeAt(MouseEvent me)
            throws InvalidMoves
    {
        try {
            /**
             * Recupera o ponto do evento do mouse.
             * Como o componente neste local vai ser a própria carta,
             * desabilita a carta e pega o elemento de trás depois reabilita
             * a carta de volta
             */
            Point p = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), this.pane);
            me.getComponent().setVisible(false);
            Component component = this.pane.findComponentAt(p);
            me.getComponent().setVisible(true);

            if(component != null) {
                if(component instanceof NoCarta) {
                    NoCarta nc = (NoCarta) component;
                    return nc.getHome();
                } else
                if(component instanceof Base) {
                    Base base = (Base) component;
                    return base.getHome();
                }

                throw new InvalidMoves(InvalidMoves.GENERICO);
            }

        } catch(java.lang.ClassCastException e) {
            throw new InvalidMoves(InvalidMoves.GENERICO);
        }

        return null;
    }


    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseDragged(MouseEvent me) {
        if(!carta.isDraggable())
            return;

        Point p1 = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), this.pane);
        Point p = new Point(p1.x - firstClick.x, p1.y - firstClick.y);

        this.setNoCartaLocation(carta, p);
    }

    @Override
    public void mouseMoved(MouseEvent me) {}
}
