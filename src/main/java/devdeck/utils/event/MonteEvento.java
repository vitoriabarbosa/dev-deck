package devdeck.utils.event;

import devdeck.view.JogoInterface;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A classe {@code MonteEvento} gerencia os eventos do mouse relacionados
 * ao monte de cartas. Implementa as interfaces {@link MouseListener}
 * e {@link MouseMotionListener} para lidar com interações de clique e
 * movimento do mouse sobre o monte.
 */
public class MonteEvento extends JFrame implements MouseListener, MouseMotionListener {
    private JogoInterface jogoGUI;

    /**
     * Constrói uma nova instância de {@code MonteEvento}.
     *
     * @param jogoGUI a interface gráfica do jogo usada para interagir com o monte
     */
    public MonteEvento(JogoInterface jogoGUI) {
        this.jogoGUI = jogoGUI;
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    /**
     * Chamado quando o mouse é liberado sobre o monte de cartas.
     * Ao liberar o mouse, três cartas são reveladas no monte.
     *
     * @param me o evento do mouse
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        this.jogoGUI.abre3Cartas();
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    /**
     * Lança uma exceção {@code UnsupportedOperationException} ao tentar
     * arrastar o monte de cartas, pois essa operação não é suportada.
     *
     * @param e o evento de arrastar o mouse
     * @throws UnsupportedOperationException ao tentar arrastar o monte
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Lança uma exceção {@code UnsupportedOperationException} ao tentar
     * mover o mouse sobre o monte, pois essa operação não é suportada.
     *
     * @param e o evento de mover o mouse
     * @throws UnsupportedOperationException ao tentar mover o mouse sobre o monte
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
