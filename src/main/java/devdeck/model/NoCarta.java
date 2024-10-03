package devdeck.model;

import devdeck.model.home.Home;
import devdeck.utils.ConfigCarta;
import devdeck.utils.RecursosUteis;

import javax.swing.*;
import java.awt.*;

final public class NoCarta extends JLabel {
    private int numero;
    private ImageIcon openedIcon;
    private Naipe naipe;

    private NoCarta prox = null;
    private boolean aberta = false;
    private boolean draggable = false;

    /**
     * Instancia da classe pai (home), pode ser uma ListaHome ua PilhaHome ou Baralho
     */
    private Home home = null;
    
    public NoCarta(int numero, Naipe naipe) {
        this.numero = numero;
        this.naipe = naipe;
        this.setBounds(0, 0, ConfigCarta.LARGURA_CARTA, ConfigCarta.ALTURA_CARTA);
        this.openedIcon = RecursosUteis.getCarta(this.getNumRep().toLowerCase() + "-" + this.getNaipeRep() + ".png");
        this.setOpen(false);
    }
    
    public int getCountProx() {
        int totalProx = 0;
        NoCarta aux = this;
        while (aux.getProx() != null) {
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
        if (open) {
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
    
    /**
     * Recupera a representação do número da carta, por ex:
     * Ao invés de 13, 12, 11, 1 será retornado K, Q, J, A
     * @return String
     */
    public String getNumRep() {
        String ret = switch (this.numero) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> String.valueOf(this.numero);
        };
        return ret;
    }
    
    public String getNaipeRep() {
        String ret = switch (this.naipe.getNaipe()) {
            case COPAS -> "c";
            case ESPADAS -> "e";
            case OURO -> "o";
            default -> "p";
        };
        return ret;
    }

    public Naipe getNaipe() {
        return this.naipe;
    }
    
    /**
     * Mostra uma representação do objeto da carta no padrão: "A de COPAS"
     * @return String
     */
    @Override
    public String toString() {
        return this.getNumRep() + " de " + this.getNaipe().getNaipe().name();
    }
    
    /**
     * Seta a home da carta (e também de todos as cartas filhas).
     * Pode ser uma ListaHome, PilhaHome ou Baralho
     * @param home 
     */
    public void setHome(Home home) {
        this.home = home;
        
        if (this.getProx() != null) {
            this.getProx().setHome(home);
        }
    }
    
    public Home getHome() {
        return this.home;
    }
}