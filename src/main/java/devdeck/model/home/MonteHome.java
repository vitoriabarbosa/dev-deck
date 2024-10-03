package devdeck.model.home;

import devdeck.exceptions.MovimentosInvalidos;
import devdeck.model.NoCarta;
import devdeck.model.base.Base;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Classe de monte de cartas.
 * Os montes possuem 3 cartas e são mostrados no topo esquerdo do jogo.
 */
public class MonteHome implements Home {
    private ArrayList<Stack<NoCarta>> montes = new ArrayList<Stack<NoCarta>>();

    @Override
    public void receberNo(NoCarta carta) throws MovimentosInvalidos {
    }

    @Override
    public void remover(NoCarta noCarta) {
        int i = 0;
        Stack<NoCarta> monteRemover = null;
        for (Stack<NoCarta> monte : this.montes) {
            if (monte.contains(noCarta)) {
                monteRemover = monte;
            }
            i++;
        }
        
        // Remove a carta do monte, se ele esvaziar, remove o monte do array original
        if (monteRemover != null) {
            monteRemover.remove(noCarta);
            
            if (!monteRemover.isEmpty()) {
                NoCarta carta = monteRemover.get(monteRemover.size()-1);
                carta.setOpen(true);
            } else {
                this.montes.remove(monteRemover);
            }
        }
    }
    
    Stack<NoCarta> monteAtual = null;
    public void inserir(NoCarta noCarta) {
        if (monteAtual == null || monteAtual.size() == 3) {
            monteAtual = new Stack<NoCarta>();
            this.montes.add(monteAtual);
        }
        noCarta.setHome(this);
        monteAtual.add(noCarta);
    }
    
    private int numMonteAtivo = 0;
    public Stack<NoCarta> retira3Cartas() {
        if (this.montes.isEmpty()) {
            return null;
        }
        
        if (this.numMonteAtivo + 1 < this.montes.size()) {
            this.numMonteAtivo++;
        } else {
            this.numMonteAtivo = 0;
        }
        return this.montes.get(this.numMonteAtivo);
    }
    
    public int getNumMonteAtivo() {
        return this.numMonteAtivo;
    }
    
    public ArrayList<Stack<NoCarta>> getMontes() {
        return this.montes;
    }

    @Override
    public Base getBase() {
        return null;
    }

    @Override
    public void setBase(Base base) {
        return;
    }
}