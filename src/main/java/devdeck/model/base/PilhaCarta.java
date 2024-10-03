package devdeck.model.base;

import devdeck.model.NoCarta;

public class PilhaCarta {
    private int topo = - 1;
    protected NoCarta[] cartas;
    private String nome;
    
    public PilhaCarta(String nome) {
        this.nome = nome;
    }

    public boolean pilhaVazia() {
        return topo < 0;
    }

    public boolean pilhaCheia() {
        return topo >= cartas.length - 1;
    }

    public NoCarta elementoTopo() {
        if (!pilhaVazia()) {
            return cartas[topo];
        } else {
            return null;
        }
    }

    protected boolean empilhar(NoCarta elemento) {
        if (pilhaCheia()) {
            return false;
        } else {
            cartas[++topo] = elemento;
            return true;
        }
    }

    protected NoCarta desempilhar() {
        if (pilhaVazia()) {
            return null;
        } else {
            topo--;
            return cartas[topo + 1];
        }
    }

    public String getNome() {
        return this.nome;
    }
}
