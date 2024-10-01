package devdeck.model.base;

import devdeck.model.NoCarta;

public class PilhaCarta {
    private int topo = -1;
    protected NoCarta[] cartas;
    private String nome = "";

    public PilhaCarta(String nome) {
        this.nome = nome;
    }

    public boolean pilhaVazia() {
        if (topo < 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean pilhaCheia() {
        if (topo < cartas.length - 1) {
            return false;
        } else {
            return true;
        }
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

    public void mostrar() {
        int i = topo;
        while (topo >= 0) {
            System.out.println(i);
            i--;
        }
    }

    public String getNome() {
        return this.nome;
    }
}
