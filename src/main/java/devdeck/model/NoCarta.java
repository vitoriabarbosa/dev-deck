package devdeck.model;

import devdeck.model.interfaces.Home;
import devdeck.utils.ConfigCard;
import devdeck.utils.ImageCardUtil;

import javax.swing.*;
import java.awt.*;

public class NoCarta extends JLabel {
    private int numero;
    private Naipe naipe; // Aqui naipe é uma String

    private NoCarta prox = null;
    private boolean aberta = false;
    private boolean draggable = false;

    private ImageIcon openedIcon;
    private Home home = null;

    public NoCarta(int numero, Naipe naipe) {
        this.numero = numero;
        this.naipe = naipe;
        this.setBounds(0, 0, ConfigCard.LARGURA_CARTA, ConfigCard.ALTURA_CARTA);
        this.openedIcon = ImageCardUtil.getCarta(this.getNaipeRep() + this.getNumRep().toLowerCase() + ".png");
        this.setOpen(false);
    }

    public int getCountProx() {
        int totalProx = 0;
        NoCarta aux = this;
        while(aux.getProx() != null) {
            totalProx++;
            aux = aux.getProx();
        }

        return totalProx;
    }

    public NoCarta getProx() {
        return this.prox;
    }

    public void setProx(NoCarta prox) {
        this.prox = prox;
    }

    public boolean isOpen() {
        return this.aberta;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setOpen(boolean open) {
        this.setOpen(open, true);
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public void setOpen(boolean open, boolean draggable) {
        if(open) {
            this.setIcon(this.openedIcon);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.setIcon(Baralho.cartaFechada);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        this.aberta = open;
        this.setDraggable(draggable);
    }

    public int getNumero() {
        return this.numero;
    }

    public String getNumRep() {
        String ret = switch (this.numero) {
            case 1 -> "A";
            case 5 -> "5";
            default -> String.valueOf(this.numero);
        };
        return ret;
    }

    public String getNaipeRep() {
        String ret = switch (this.naipe.getNaipe()) {
            case JAVA -> "java";
            case PYTHON -> "python";
            case C -> "c";
            default -> "c++";
        };
        return ret;
    }

    public Naipe getNaipe() {
        return this.naipe;
    }

    @Override
    public String toString() {
        return this.getNumRep() + " de " + this.getNaipe().getNaipe().name();
    }

    public void setHome(Home home) {
        this.home = home;

        if(this.getProx() != null)
            this.getProx().setHome(home);
    }

    public Home getHome() {
        return this.home;
    }
}
